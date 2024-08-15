import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    alias(kmpLibs.plugins.skie)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":breeds"))
                api(project(":analytics"))
            }
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        framework {
            export(project(":analytics"))
            baseName = "allshared"
            isStatic = false
            linkerOpts.add("-lsqlite3") // why do I need this? Get linke error if not this or isStatic = true
        }

        extraSpecAttributes["swift_version"] = "\"5.0\"" // <- SKIE Needs this!

        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }
}