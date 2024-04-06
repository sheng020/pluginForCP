
plugins {
    `kotlin-dsl`
    `java-library`
    `kotlin-dsl-precompiled-script-plugins`
}

/*gradlePlugin {
    plugins {
        register("common-binary-plugin") {
            id = "common-binary-plugin"
            implementationClass = "com.ckundr.plugins.CommonBinaryPlugin"
        }
    }
}*/

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    /*dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }*/
}

repositories {
    mavenCentral()
    google()
}


dependencies {
    //implementation ("com.android.tools.build:gradle:8.3.0")
    //implementation ("com.blankj:utilcodex:1.31.1")
    //implementation ("com.android.tools.build:gradle:3.6.4")
    //implementation ("com.android.tools.build:gradle-api:3.6.4")
    implementation ("commons-io:commons-io:2.6")
    implementation("org.apache.commons:commons-crypto:1.2.0")
    implementation (group = "commons-net", name = "commons-net", version = "3.2")
//Thanks for using https://jar-download.com
}