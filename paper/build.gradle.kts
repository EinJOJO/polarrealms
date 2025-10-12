plugins {
    id("java")
}

group = "it.einjojo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}