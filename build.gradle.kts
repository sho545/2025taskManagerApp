
plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
  // id("org.mybatis.generator") version "1.6.1"
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
	// compileOnly {
	// 	extendsFrom(configurations.annotationProcessor.get())
	// }
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
	implementation ("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
  // implementation("org.mybatis:mybatis:3.5.14")
	// implementation("org.mybatis:mybatis-spring:3.0.3") 
	implementation("org.mybatis:mybatis-typehandlers-jsr310:1.0.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
  implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("javax.validation:validation-api:2.0.1.Final")
  implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}


tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("mybatisGenerate") {
    group = "MyBatis Generator"
    description = "Generates MyBatis artifacts"
		classpath = configurations["mybatisGenerator"] + files("$buildDir/classes/java/main")
    mainClass.set("org.mybatis.generator.api.ShellRunner")
    args("-configfile", "src/main/resources/generatorConfig.xml", "-overwrite")
}

tasks.named("mybatisGenerate") {
    dependsOn("classes")
}