import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
    id("com.gradleup.shadow") version "9.2.2"

}


repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.minehub.live/releases")
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "placeholderapi"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    api(project(":core"))
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    compileOnly("it.einjojo.playerapi:api:1.2.0")
    compileOnly("it.einjojo:economy:2.0.1")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("live.minehub:polarpaper:1.21.4.6")

    paperLibrary("org.reflections:reflections:0.10.2")
    paperLibrary("com.zaxxer:HikariCP:7.0.2")
    paperLibrary("org.postgresql:postgresql:42.7.8")
    paperLibrary("org.flywaydb:flyway-database-postgresql:11.13.2")
    paperLibrary("org.incendo:cloud-core:2.0.0")
    paperLibrary("org.incendo:cloud-annotations:2.0.0")
    annotationProcessor("org.incendo:cloud-annotations:2.0.0")
    paperLibrary("org.incendo:cloud-paper:2.0.0-beta.10")

    paperLibrary("com.hierynomus:sshj:0.38.0")
}


paper {
    name = "PolarRealms"
    main = "it.einjojo.polarrealms.PolarRealmsPlugin"
    foliaSupported = false
    authors = listOf("EinJOJO")
    description = "Realm System"
    website = "https://einjojo.it"
    apiVersion = "1.20"
    loader = "it.einjojo.polarrealms.PluginLibrariesLoader"
    generateLibrariesJson = true
    serverDependencies {
        register("PlaceholderAPI") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
        }
        register("polarpaper") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }
    }
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()

}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs.add("-parameters")
    }
    runServer {
        minecraftVersion("1.21.4")
        downloadPlugins {
            hangar("PlaceholderAPI", "2.11.6")
            hangar("PolarPaper", "1.21.4.6")
            //url("https://cloud.einjojo.it/s/YK8WMIJgrPIycnH/download")  // economy provider 3.0.1
            //url("https://cloud.einjojo.it/s/cXeZeZXUEgsUPoP/download") // players api 1.4.1
        }
    }
    shadowJar {
        relocate("io.lettuce", "net.wandoria.essentials.libs.lettuce")
        //relocate("org.incendo.cloud", "net.wandoria.essentials.libs.cloud")
        relocate("io.netty", "net.wandoria.essentials.libs.netty")

    }
}
tasks.test {
    useJUnitPlatform()
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}