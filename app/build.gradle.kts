plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    alias(libs.plugins.hilt)
    id("android.aop")
}

android {
    namespace = "com.wls.poke"
    compileSdk = 34
    buildFeatures.buildConfig = true
    defaultConfig {
        applicationId = "com.wls.poke"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            // 日志打印开关
            buildConfigField("Boolean", "LOG_ENABLE", "true")
        }
        release {
            // 日志打印开关
            buildConfigField("Boolean", "LOG_ENABLE", "false")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}
ksp {}
androidAopConfig {

    // enabled 为 false 切面不再起作用，默认不写为 true
    enabled = true
    // include 不设置默认全部扫描，设置后只扫描设置的包名的代码
    include(
        android.defaultConfig.applicationId.orEmpty(),
    )
    // exclude 是扫描时排除的包
    // 可排除 kotlin 相关，提高速度
//    exclude ("kotlin.jvm", "kotlin.internal","kotlinx.coroutines.internal",
//        "kotlinx.coroutines.android")

    // verifyLeafExtends 是否开启验证叶子继承，默认打开，如果没有设置 @AndroidAopMatchClassMethod 的 type = MatchType.LEAF_EXTENDS，可以关闭
    verifyLeafExtends = true
    //默认关闭，开启在 Build 或 打包后 将会生成切点信息json文件在 app/build/tmp/cutInfo.json
    cutInfoJson = false
    //默认开启，设置 false 后会没有增量编译效果 筛选（关键字： AndroidAOP woven info code） build 输出日志可看时间
    increment = true
//修改、增加、删除匹配切面的话，就会走全量编译
}
dependencies {
    api(project(":base"))
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.compose.compiler)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.constraintlayout.compose)
    //implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.webview)
    //datastore
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preference)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)

    //hilt代替dagger
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.common)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.glide.compose)
    //联网
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.paging.compose)
    //aop插件
    implementation(libs.aspectj.core)
    kapt(libs.aspectj.annotation)
    //非必须项 👇，如果你想自定义切面需要用到，⚠️支持Java和Kotlin代码写的切面
    ksp(libs.aspectj.ksp)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}