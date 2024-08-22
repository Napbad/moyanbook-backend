plugins {
    id("java")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.moyanshushe"
version = "0.0.1-SNAPSHOT"

val jwtVersion = "0.9.1"
val springCloudGatewayVersion = "4.1.4"
val lombokVersion = "1.18.30"
val jetBrainAnnotationVersion = "24.1.0"
val nacosVersion = "2023.0.1.0"
val springCloudLoadBalancerVersion = "4.1.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$springCloudGatewayVersion")
    // https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:$nacosVersion")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-loadbalancer
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:$springCloudLoadBalancerVersion")

    implementation("io.jsonwebtoken:jjwt:${jwtVersion}")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.2")

    implementation("org.projectlombok:lombok:${lombokVersion}")
    implementation("org.jetbrains:annotations:${jetBrainAnnotationVersion}")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}