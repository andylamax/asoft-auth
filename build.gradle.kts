import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    kotlin("multiplatform") version "1.3.70"
    kotlin("plugin.serialization") version "1.3.70"
    id("com.android.library") version "3.6.0"
    id("maven-publish")
}

object versions {
    val asoft_test = "4.2.1"
    val serialization = "0.20.0"
    val asoft_klock = "1.0.0"
    val asoft_neo4j = "2.0.0"
    val asoft_persist = "16.0.0"
    val asoft_rx = "6.2.0"
    val asoft_phone = "1.0.0"
    val asoft_email = "1.0.0"
    val asoft_krypto = "1.0.0"
    val asoft_io = "3.1.0"
}

fun andylamax(lib: String, platform: String, ver: String): String {
    return "com.github.andylamax.$lib:$lib-$platform:$ver"
}

fun KotlinDependencyHandler.asoftLibs(platform: String) {
    api(andylamax("asoft-klock", platform, versions.asoft_klock))
    api(andylamax("asoft-neo4j", platform, versions.asoft_neo4j))
    api(andylamax("asoft-persist", platform, versions.asoft_persist))
    api(andylamax("asoft-rx", platform, versions.asoft_rx))
    api(andylamax("asoft-phone", platform, versions.asoft_phone))
    api(andylamax("asoft-email", platform, versions.asoft_email))
    api(andylamax("asoft-krypto", platform, versions.asoft_krypto))
    api(andylamax("asoft-io", platform, versions.asoft_io))
}

fun asoftTest(platform: String) = andylamax("asoft-test", platform, versions.asoft_test)

group = "tz.co.asoft"
version = "31.0.0"

repositories {
    google()
    jcenter()
    maven(url = "https://jitpack.io")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(1)
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        val main by getting {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            resources.srcDirs("src/androidMain/resources")
        }
    }

    lintOptions {

    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
        publishLibraryVariants("release")
    }

    jvm {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }

    js {
        compilations.all {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                moduleKind = "commonjs"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${versions.serialization}")
                asoftLibs("metadata")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoftTest("metadata"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${versions.serialization}")
                asoftLibs("android")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(asoftTest("android"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${versions.serialization}")
                asoftLibs("jvm")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(asoftTest("jvm"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${versions.serialization}")
                asoftLibs("js")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(asoftTest("js"))
            }
        }
    }
}