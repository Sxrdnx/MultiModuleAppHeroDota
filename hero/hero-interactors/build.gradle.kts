apply{
    from("$rootDir/library-build.gradle")
}
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Kotlinx.coroutinesCore)
    "implementation" (project(Modules.heroDataSourceTest))

    "testImplementation"(Junit.junit4)
    "testImplementation"(Ktor.ktorClientMock)
    "testImplementation"(Ktor.clientSerialization)
    "implementation"("ch.qos.logback:logback-classic:1.2.6")

}