import org.apache.tools.ant.taskdefs.condition.Os
plugins {
    kotlin("multiplatform") version "1.7.10"
}

group = "moe.tlaster"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> if (Os.isArch("aarch64")) macosArm64("native") else macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    nativeTarget.apply {
//        val main by compilations.getting
//        main.cinterops {
//            val rtest by creating {
//                headers(rootDir.resolve("rtest/rtest.h"))
//            }
//        }
        binaries {
            executable {
//                linkerOpts("-v")
            //    linkerOpts("-L${rootDir.resolve("rustlib/target/debug/").absolutePath}")
            //    linkerOpts("-lrustlib")
//                linkerOpts("-Wl,-undefined,dynamic_lookup") // resolve symbols in runtime
//                baseName = "KotlinExecutable"
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation(project(":library"))
            }
        }
        val nativeTest by getting
    }
}
