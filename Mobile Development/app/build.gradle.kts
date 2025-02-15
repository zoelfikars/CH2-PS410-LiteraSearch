plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "dicoding.zulfikar.literasearchapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "dicoding.zulfikar.literasearchapp"
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
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    image slider
    implementation("com.github.bumptech.glide:glide:4.16.0")

//    navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.activity:activity-ktx:1.8.2")

//    gson & retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.paging:paging-common-android:3.3.0-alpha02")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
//    room
    implementation("androidx.room:room-paging:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")

//    logging interceptor http
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

//    firebase
    implementation("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-firestore")

//    circular image
    implementation("de.hdodenhof:circleimageview:3.1.0")

//    image slider viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.captain-miao:optroundcardview:1.0.0")
    implementation ("com.makeramen:roundedimageview:2.3.0")

//    recyclerview
    implementation("com.google.android.flexbox:flexbox:3.0.0")

}

