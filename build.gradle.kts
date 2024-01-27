plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.ec2s3"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-core
    implementation("com.amazonaws:aws-java-sdk-core:1.12.645")

    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.645")

    // https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-ec2
    implementation("com.amazonaws:aws-java-sdk-ec2:1.12.645")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.1")

    // https://mvnrepository.com/artifact/me.paulschwarz/spring-dotenv
    implementation("me.paulschwarz:spring-dotenv:2.3.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")


    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
