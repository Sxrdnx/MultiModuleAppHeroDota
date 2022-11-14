apply{
    from("$rootDir/library-build.gradle")
}

plugins{
    kotlin(KotlinPlugins.serialization2) version Kotlin.version
}

dependencies {
    "implementation"(project(Modules.heroDomain))
    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
    "implementation" (Ktor.contentNegotiation)
}