plugins {
    id("java")
    id("maven-publish")
    id("java-library")
}

group = "com.moyanshushe"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val jimmerVersion = "0.8.104"

dependencies {
//    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")
//    implementation("org.babyfish.jimmer:jimmer-sql:${jimmerVersion}")
//    annotationProcessor("org.babyfish.jimmer:jimmer-apt:${jimmerVersion}")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.moyanshushe"
            artifactId = "global-processing"
            version = "0.0.1-SNAPSHOT"
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}