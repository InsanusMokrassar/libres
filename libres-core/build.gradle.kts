plugins {
    id("multiplatform-setup")
    id("android-setup-plugin")
//    id("com.vanniktech.maven.publish")
}

group = property("GROUP").toString()
version = property("VERSION_NAME").toString()

android {
    namespace = "io.github.skeptick.libres"
}

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.core)
                compileOnly(libs.robovm)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.icu4j)
            }
        }

        val appleAndJsMain by creating {
            dependsOn(commonMain.get())
            jsMain.get().dependsOn(this)
        }

        val uikitMain by creating {
            dependsOn(appleMain.get())
            iosMain.get().dependsOn(this)
            tvosMain.get().dependsOn(this)
        }
    }
}

apply(from = "../publish.gradle")
