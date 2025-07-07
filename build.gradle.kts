
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	add("mybatisGenerator", "org.mybatis.generator:mybatis-generator-core:1.4.2")
	add("mybatisGenerator", "com.h2database:h2:2.2.224")
	implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.4.1") 
  implementation("org.mybatis:mybatis:3.5.14")
	implementation("org.mybatis:mybatis-spring:3.0.3") 
	implementation("org.mybatis:mybatis-typehandlers-jsr310:1.0.2")

}


tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("mybatisGenerate") {
    group = "MyBatis Generator"
    description = "Generates MyBatis artifacts"
		classpath = configurations["mybatisGenerator"]
    mainClass.set("org.mybatis.generator.api.ShellRunner")
    args("-configfile", "src/main/resources/generatorConfig.xml", "-overwrite")
}
