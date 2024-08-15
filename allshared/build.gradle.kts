import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    alias(kmpLibs.plugins.skie)
}

kotlin {
    val xcf = XCFramework()

    val iosTargets = listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    iosTargets.forEach {
        it.binaries.framework {
            baseName = "allshared"
            export(project(":analytics"))
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":breeds"))
                api(project(":analytics"))
            }
        }
    }
}