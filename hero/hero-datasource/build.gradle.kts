apply{
    from("$rootDir/library-build.gradle")
}

plugins{
    kotlin(KotlinPlugins.serialization2) version Kotlin.version
    id(SqlDelight.plugin)
}

dependencies {
    "implementation"(project(Modules.heroDomain))
    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
    "implementation" (Ktor.contentNegotiation)

    "implementation"(SqlDelight.runtime)
}
sqldelight{
    database("HeroDatabase"){
        packageName = "com.example.hero_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}