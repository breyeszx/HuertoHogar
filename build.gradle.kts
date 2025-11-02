// build.gradle.kts (raíz del proyecto)

plugins {
    //1. Aplica los plugins usando los alias definidos en libs.versions.toml
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.ksp) apply false
}
