plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("stringfog")
    id("android-junk-code")
}

stringfog {
    // 开关
    enable = true
    // 加解密库的实现类路径，需和上面配置的加解密算法库一致。
    implementation = "com.github.megatronking.stringfog.xor.StringFogImpl"
}

androidJunkCode {
    variantConfig {
        create("release") {
            //注意：这里的release是变体名称，如果没有设置productFlavors就是buildType名称，如果有设置productFlavors就是flavor+buildType，例如（freeRelease、proRelease）
            packageBase = "com.uoyero.check.hrs"  //生成java类根包名
            packageCount = 33 //生成包数量
            activityCountPerPackage = 3 //每个包下生成Activity类数量
            excludeActivityJavaFile = false
            //是否排除生成Activity的Java文件,默认false(layout和写入AndroidManifest.xml还会执行)，主要用于处理类似神策全埋点编译过慢问题
            otherCountPerPackage = 33  //每个包下生成其它类的数量
            methodCountPerClass = 22  //每个类下生成方法数量
            resPrefix = "uoyero"  //生成的layout、drawable、string等资源名前缀
            drawableCount = 203  //生成drawable资源数量
            stringCount = 230  //生成string数量
        }
    }
}

android {
    namespace = "com.firstpoli.spotx"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.firstpoli.spotx"
        minSdk = 24
        targetSdk = 33
        versionCode = 3
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //重命名打包文件，对apk和aab都生效
        setProperty("archivesBaseName", "${applicationId}-${versionName}-${versionCode}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-process:2.6.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    //room 配合 RxJava
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-rxjava2:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")

    //RecyclerViewAdapter
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper:4.0.1")

    implementation("com.github.megatronking.stringfog:xor:5.0.0")

}