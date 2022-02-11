import org.gradle.kotlin.dsl.DependencyHandlerScope

const val composeCompilerVersion = "1.2.0-alpha02"

fun DependencyHandlerScope.impl() {
    //Compose
    with(composeCompilerVersion) {
        listOf(
            "androidx.compose.ui:ui:$this",
            "androidx.compose.ui:ui-tooling:$this",
            "androidx.compose.compiler:compiler:$this",
            "androidx.compose.foundation:foundation:$this",
            "androidx.compose.material:material:$this",
            "androidx.compose.material:material-icons-core:$this",
            "androidx.compose.material:material-icons-extended:$this",
            "com.google.accompanist:accompanist-pager-indicators:0.24.1-alpha",
            "com.google.accompanist:accompanist-pager:0.24.1-alpha",
            "com.google.accompanist:accompanist-navigation-animation:0.24.1-alpha",
            "androidx.compose.animation:animation:$this"
        ).forEach { addD(dep = it) }
    }
    addD(dep = "androidx.activity:activity-compose:1.4.0")
    addD(dep = "com.squareup.leakcanary:leakcanary-android:2.8.1")

    //Firebase
    listOf(
        platform("com.google.firebase:firebase-bom:29.0.4"),
        "com.google.firebase:firebase-firestore-ktx",
        "com.google.firebase:firebase-auth-ktx"
    ).forEach { addD(dep = it) }

    //Base
    addD(dep = "androidx.core:core-ktx:1.7.0")
    addD(dep = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    addD(dep = "androidx.appcompat:appcompat:1.4.1")
    addD(dep = "com.google.android.material:material:1.6.0-alpha02")


//Lifecycle
    with("2.4.0") {
        listOf(
            "androidx.lifecycle:lifecycle-livedata-ktx:$this",
            "androidx.lifecycle:lifecycle-runtime-ktx:$this",
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$this"
        ).forEach { addD(dep = it) }
    }
}

val listPlugins = listOf(
    "com.android.application",
    "org.jetbrains.kotlin.android",
    "com.google.gms.google-services",
    "kotlin-kapt"
)

private fun DependencyHandlerScope.addD(method: String = imp, dep: Any) = add(method, dep)
private val imp get() = "implementation"




