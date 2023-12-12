plugins {
    id("multiplatform-setup")
    id("android-setup-plugin")
//    id("com.vanniktech.maven.publish")
    id("org.jetbrains.compose")
}

group = property("GROUP").toString()
version = property("VERSION_NAME").toString()

android {
    namespace = "io.github.skeptick.libres.compose"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.libresCore)
                implementation(compose.ui)
            }
        }
    }
}

apply(from = "../publish.gradle")