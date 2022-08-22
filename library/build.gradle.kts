import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    kotlin("multiplatform") version "1.7.10"
}

group = "moe.tlaster"
version = "1.0-SNAPSHOT"

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
       val main by compilations.getting
       main.cinterops {
           val rustlib by creating {
               headers(rootDir.resolve("rustlib/rustlib.h"))
           }
       }
//        binaries {
//            all {
//                linkerOpts("-v")
//               linkerOpts("-L${rootDir.resolve("rustlib/target/debug/").absolutePath}")
//               linkerOpts("-lrustlib")
//                linkerOpts("-Wl,-undefined,dynamic_lookup") // resolve symbols in runtime
//                baseName = "KotlinExecutable"
//            }
//        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}
