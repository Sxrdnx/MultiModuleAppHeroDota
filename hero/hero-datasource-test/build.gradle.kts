apply{
    from("$rootDir/library-build.gradle")

}

plugins{
    kotlin(KotlinPlugins.serialization2) version Kotlin.version
}

dependencies{
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.clientSerialization)
    "implementation" (Ktor.contentNegotiation)

}
