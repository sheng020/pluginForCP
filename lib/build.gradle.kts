plugins {
    //id("java-library")
   // alias(libs.plugins.jetbrainsKotlinJvm)

    id ("groovy") // Groovy Language
    id ("org.jetbrains.kotlin.jvm") // Kotlin
    id ("java-gradle-plugin") // Java Gradle Plugin
    `maven-publish`
}



sourceSets {
    main {
        groovy {
            srcDir ("src/main/groovy")
        }

        java {
            srcDir ("src/main/java")
        }

        /*resources {
            srcDir ("src/main/resources")
        }*/
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation(kotlin("gradle-plugin", version = "1.8.10"))
    implementation ("commons-io:commons-io:2.6")
    implementation("org.apache.commons:commons-crypto:1.2.0")
    implementation (group = "commons-net", name = "commons-net", version = "3.2")
    implementation ("commons-codec:commons-codec:1.16.1")
    implementation ("com.google.code.gson:gson:2.10.1")
}


gradlePlugin {
    val pluginId = "com.example.test"
    plugins {
        create(pluginId) {
            description = "Gradle Plugins for Lovely Systems Projects"
            tags.set(listOf("git", "docker"))

            id = pluginId
            implementationClass = "com.example.lib.MyClass"
            displayName = "Lovely Systems Project Helpers"
            description = "Gradle Plugin for Lovely Systems Projects"
        }
    }

}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.sample"
            artifactId = "library"
            version = "1.1"

            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}
