
plugins {
	java
	id("org.springframework.boot") version "3.0.3"
	id("io.spring.dependency-management") version "1.1.7"
  	// id("org.mybatis.generator") version "1.6.1"
	id("org.openapi.generator") version "7.5.0"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

configurations {
	create("mybatisGenerator")
}

repositories {
	mavenCentral()
}

dependencies {
	//implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.mybatis.generator:mybatis-generator-core:1.4.2")
	add("mybatisGenerator", "org.mybatis.generator:mybatis-generator-core:1.4.2")
	add("mybatisGenerator", "com.h2database:h2:2.2.224")
	implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.4.1") 
	implementation ("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")
  	// implementation("org.mybatis:mybatis:3.5.14")
	// implementation("org.mybatis:mybatis-spring:3.0.3") 
	implementation("org.mybatis:mybatis-typehandlers-jsr310:1.0.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
 	implementation("org.springframework.boot:spring-boot-starter-validation")
	// implementation("javax.validation:validation-api:2.0.1.Final")
  	// implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}


tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("mybatisGenerate") {
    group = "MyBatis Generator"
    description = "Generates MyBatis artifacts"
	classpath = configurations["mybatisGenerator"] + files(layout.buildDirectory.dir("classes/java/main"))
    mainClass.set("org.mybatis.generator.api.ShellRunner")
    args("-configfile", "src/main/resources/generatorConfig.xml", "-overwrite")
}

tasks.named("mybatisGenerate") {
    dependsOn("classes")
}

// APIコードをコピーし、不要なファイルを削除するカスタムタスクを定義
tasks.register("organizeApiFiles") {
    group = "API Generation"
    description = "Copies generated API source files to src/main/java and cleans up."

    // このタスクはopenApiGenerateタスクの後に実行する
    dependsOn(tasks.named("openApiGenerate"))

    doLast {
        // 1. build/generated/openapi/src/main/java ディレクトリから
        //    プロジェクトの src/main/java へJavaファイルのみをコピー
        copy {
            from("${project.buildDir}/generated-code/src/main/java/com/example/demo/generated/application/controller")
            into("src/main/java/com/example/demo/generated/application/controller")
			include("TasksApi.java", "ApiUtil.java")
        }
		copy {
            from("${project.buildDir}/generated-code/src/main/java/com/example/demo/generated/application/dto")
            into("src/main/java/com/example/demo/generated/application/dto")
			include("TaskDto.java", "TaskRequest.java")
        }

        // 2. コピー元となった一時的な生成物ディレクトリをすべて削除
        delete("${project.buildDir}/generated-code")
    }
}

// compileJavaの実行前に、このカスタムタスクが実行されるように設定
tasks.named("compileJava") {
    dependsOn(tasks.named("organizeApiFiles"))
}

openApiGenerate {
    // 生成するテンプレート名を指定
    generatorName.set("spring")

    // 入力となるOpenAPI仕様ファイルの場所を指定
    inputSpec.set("C:/projects/demo/demo/api/openapi.yaml")

    // 生成されたファイルの出力先を指定
    outputDir.set(layout.buildDirectory.get().dir("generated-code").asFile.path)

    // APIインターフェースのパッケージ名を指定
    apiPackage.set("com.example.demo.generated.application.controller")

    // DTOクラスのパッケージ名を指定
    modelPackage.set("com.example.demo.generated.application.dto")

    // その他の詳細設定
    configOptions.set(mapOf(
        "interfaceOnly" to "true", // 実装クラスは作らずインターフェースのみ生成
		"dateTimeFormat" to "java.time.OffsetDateTime",
		"useSpringBoot3" to "true"
    ))

}