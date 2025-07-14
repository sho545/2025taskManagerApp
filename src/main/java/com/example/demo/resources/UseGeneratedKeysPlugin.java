package com.example.demo.resources;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

import java.util.List;

public class UseGeneratedKeysPlugin extends PluginAdapter {
    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.addAnnotation("@Options(useGeneratedKeys=true, keyProperty=\"id\")");
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options"));
         List<String> bodyLines = method.getBodyLines();
        // idをマッピングしている行のインデックスを探す
        int lineIndexToRemove = -1;
        for (int i = 0; i < bodyLines.size(); i++) {
            String line = bodyLines.get(i);
            if (line.contains(".map(id)") && line.contains(".toProperty(\"id\")")) {
                lineIndexToRemove = i;
                break;
            }
        }

        if (lineIndexToRemove != -1) {
            // idのマッピング行を削除
            bodyLines.remove(lineIndexToRemove);

            // もし削除した行のすぐ下に、まだメソッドチェーンが続く場合
            if (bodyLines.size() > lineIndexToRemove) {
                String nextLine = bodyLines.get(lineIndexToRemove);
                // 次の行が "." で始まっている場合
                if (nextLine.trim().startsWith(".")) {
                    // 先頭に "c" を付けて構文を修正する
                    String fixedLine = nextLine.replaceFirst("\\.", "c.");
                    bodyLines.set(lineIndexToRemove, fixedLine);
                }
            }
        }
        return true;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}


