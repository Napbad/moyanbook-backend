plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.moyanshushe"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()

}

val feignCoreVersion = "13.3"
val springVersion = "3.2.5"
val springCloudLoadBalancerVersion = "4.1.3"
val jimmerVersion = "0.8.147"
val druidVersion = "1.2.21"
val jwtVersion = "0.9.1"
val jaxbVersion = "4.0.2"
val jaxbRuntimeVersion = "2.3.1"
val lombokVersion = "1.18.32"
val aspectVersion = "1.9.21"
val junitPlatformLauncherVersion = "1.10.2"
val javaMailVersion = "1.6.2"
val commonsPoolVersion = "2.11.1"
val nacosVersion = "2023.0.1.0"
val aliOOSVersion = "3.17.4"
val jsrVersion = "2.17.2"
//
//val feignCoreVersion = "newest"
//val springVersion = "newest"
//val springCloudLoadBalancerVersion = "newest"
//val jimmerVersion = "newest"
//val druidVersion = "newest"
//val jwtVersion = "newest"
//val jaxbVersion = "newest"
//val jaxbRuntimeVersion = "newest"
//val lombokVersion = "newest"
//val aspectVersion = "newest"
//val junitPlatformLauncherVersion = "newest"
//val javaMailVersion = "newest"
//val commonsPoolVersion = "newest"
//val nacosVersion = "newest"
//val aliOOSVersion = "newest"

allprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    dependencies {

        implementation("org.springframework:spring-webflux")

        // Data
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:$jimmerVersion")
        implementation("org.babyfish.jimmer:jimmer-sql:$jimmerVersion")
        implementation("com.alibaba:druid:$druidVersion")
        implementation("com.aliyun.oss:aliyun-sdk-oss:$aliOOSVersion")
        runtimeOnly("com.mysql:mysql-connector-j")


        // Utils
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsrVersion")
        implementation("io.jsonwebtoken:jjwt:$jwtVersion")
        implementation("org.springframework.boot:spring-boot-starter-mail")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        annotationProcessor("org.babyfish.jimmer:jimmer-apt:$jimmerVersion")

        // https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery
        implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:$nacosVersion")
        // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-loadbalancer
        implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:$springCloudLoadBalancerVersion")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        // https://mvnrepository.com/artifact/io.github.openfeign/feign-core
        implementation("io.github.openfeign:feign-core:$feignCoreVersion")

        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

}


//allprojects {
//    apply(plugin = "java")
//    apply(plugin = "io.spring.dependency-management")
//    apply(plugin = "org.springframework.boot")
//
//    dependencies {
//        compileOnly("org.projectlombok:lombok:
//        lombokVersion}")
//        annotationProcessor("org.projectlombok:lombok:
//        lombokVersion}")
//    }
//}


tasks.withType<Test> {
    useJUnitPlatform()
}
