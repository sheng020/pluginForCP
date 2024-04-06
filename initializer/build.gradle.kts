plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.browser.feedui.initializer"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        //buildConfigField("String[]", "converts", "{${getCovertsFiles()}}")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("jniLibs")
        }
    }
}

/*fun getCovertsFiles(): String {
    val sb = StringBuilder()
    File(rootDir, "converts").listFiles().map { it.name }.forEach {
        sb.append("\"")
        sb.append(it)
        sb.append("\"")
        sb.append(",")
    }
    return sb.toString()
}*/


dependencies {

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat.appcompat14)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.startup:startup-runtime:1.1.1")
    implementation ("com.blankj:utilcodex:1.31.1")
    implementation ("commons-io:commons-io:2.6")
    implementation("org.apache.commons:commons-crypto:1.2.0")
    implementation (group = "commons-net", name = "commons-net", version = "3.2")
    implementation ("commons-codec:commons-codec:1.16.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
}