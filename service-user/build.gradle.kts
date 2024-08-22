plugins {
    id("java")
}

group = "com.moyanshushe"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val aspectVersion = "1.9.21"
val openFeignVersion = "4.1.3"
val loadBalancerVersion = "4.1.2"
val okHttpVersion = "4.12.0"
val nacosAuthPlugin = "2.4.0-BETA"
val alipayEasySdkVersion = "2.2.3"
val thymeleafVersion = "3.3.2"
val jsrVersion = "2.17.2"

dependencies {
    implementation(project(":common"))
    implementation(project(":global-processing"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$openFeignVersion")
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    // https://mvnrepository.com/artifact/com.alibaba.nacos/nacos-auth-plugin
    implementation("com.alibaba.nacos:nacos-auth-plugin:$nacosAuthPlugin")
    // https://mvnrepository.com/artifact/com.alipay.sdk/alipay-easysdk
    implementation("com.alipay.sdk:alipay-easysdk:$alipayEasySdkVersion")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$thymeleafVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}