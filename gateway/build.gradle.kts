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

repositories {
    mavenCentral()
}

dependencies {
//    implementation(project(":global-processing"))
//    {
//        exclude("org.springframework.boot","spring-boot-starter-mail")
//        exclude("org.babyfish.jimmer", "jimmer-apt")
//        exclude("com.mysql", "mysql-connector-j")
//        exclude("org.springframework.boot", "spring-boot-starter-data-redis")
//        exclude("com.alibaba","druid")
//        exclude("org.babyfish.jimmer","jimmer-spring-boot-starter")
//        exclude("org.babyfish.jimmer","jimmer-sql")
//    }
    implementation("org.springframework:spring-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-gateway
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$springCloudGatewayVersion")
    implementation("io.jsonwebtoken:jjwt:${jwtVersion}")

    implementation("org.projectlombok:lombok:${lombokVersion}")
// https://mvnrepository.com/artifact/org.jetbrains/annotations
    implementation("org.jetbrains:annotations:${jetBrainAnnotationVersion}")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}