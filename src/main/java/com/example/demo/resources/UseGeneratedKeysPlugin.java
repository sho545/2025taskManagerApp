package com.example.demo.resources;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.List;

public class UseGeneratedKeysPlugin extends PluginAdapter {
    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        List<Parameter> parameters = method.getParameters();
        if (!parameters.isEmpty()) {
            String parameterName = parameters.get(0).getName();
            String annotation = String.format("@Options(useGeneratedKeys=true, keyProperty=\"%s.id\")", parameterName);
            method.addAnnotation(annotation);
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options"));
        }
        // --- 2. メソッドのボディを修正する ---
        List<String> bodyLines = method.getBodyLines();
        for (int i = 0; i < bodyLines.size(); i++) {
            String line = bodyLines.get(i);
            
            // idのマッピング行を見つける
            if (line.contains(".map(id)") && line.contains(".toProperty(\"id\")")) {
                
                // その行を削除する
                bodyLines.remove(i);
                
                // もし削除したことで、リストの次の行が存在し、かつその行がメソッドチェーンの続きである場合
                if (i < bodyLines.size()) {
                    String nextLine = bodyLines.get(i);
                    if (nextLine.trim().startsWith(".")) {
                        // 次の行を修正して、チェーンの始まりにする
                        bodyLines.set(i, nextLine.replaceFirst("\\.", "c."));
                    }
                }
                
                // 処理が完了したのでループを抜ける
                break;
            }
        }
       
        return true;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}


