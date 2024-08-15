plugins {
    `kotlin-dsl`
}

group = "com.spyker3d.buildlogic"

dependencies {
    implementation(projects.gradleExt)

    implementation(libs.staticAnalysis.detektPlugin)
    // workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
