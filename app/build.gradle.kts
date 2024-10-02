plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.crashlytics)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.protobuf") version "0.9.4" apply true
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")


}

android {
    namespace = "com.hamza.ecommerce"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hamza.ecommerce"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        forEach {
            it.buildConfigField(
                "String",
                "clientServerId",
                "\"502666478158-mn3rqsjcj9mfidk7kn9d346443s8htmo.apps.googleusercontent.com\""
            )

            it.resValue(
                "string",
                "facebook_app_id",
                "\"${project.properties["FACEBOOK_APP_ID"]}\""
            )
            it.resValue(
                "string",
                "fb_login_protocol_scheme",
                "\"${project.properties["FB_LOGIN_PROTOCOL_SCHEME"]}\""
            )
            it.resValue(
                "string",
                "facebook_client_token",
                "\"${project.properties["FACEBOOK_CLIENT_TOKEN"]}\""
            )

        }
        debug {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "clientServerId",
                "\"502666478158-mn3rqsjcj9mfidk7kn9d346443s8htmo.apps.googleusercontent.com\""
            )

        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.functions)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.android.material:material:1.13.0-alpha06")
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.play.services.auth)


    //splash screen
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.reactivenetwork.rx2)

    //navigation component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    implementation(libs.androidx.viewpager2)
    //data store
    implementation(libs.androidx.datastore.preferences)

    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //facebook login
    implementation(libs.facebook.android.sdk)

    //proto data store
    implementation(libs.androidx.datastore)
    // to generate proto task for kt file
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.kotlinx.serialization.json)

    //dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)
    kapt(libs.androidx.hilt.compiler)

    //retrofit dependency
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)


}
// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.26.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

kapt {
    correctErrorTypes = true
}