plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android { namespace = "com.putaolw.translation"; compileSdk = 35
    defaultConfig { applicationId = "com.putaolw.translation"; minSdk = 29; targetSdk = 35; versionCode = 1; versionName = "0.1.0" }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.mlkit:translate:17.0.3")
}
