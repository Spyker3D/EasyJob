plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
}

group = "com.spyker3d.easyjob.plugins"

gradlePlugin {
    plugins {
        create("developPropertiesPlugin") {
            id = "com.spyker3d.easyjob.plugins.developproperties"
            implementationClass = "com.spyker3d.easyjob.plugins.developproperties.DevelopPropertiesPlugin"
        }
    }
}
