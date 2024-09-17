plugins {
    id("java")
}



group = "com.moyanshushe"
version = "0.0.1-SNAPSHOT"

val feignCoreVersion = "4.1.3"
val springCloudLoadBalancerVersion = "4.1.3"
val springVersion = "3.2.5"
val jimmerVersion = "0.8.104"
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

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$feignCoreVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.test {
    useJUnitPlatform()
}