plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

buildscript {
    dependencies {

    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
