// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories {
        mavenCentral()
        google()

    }
    dependencies {
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.6") {
            exclude(group = "com.google.protobuf")
        }
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false

}