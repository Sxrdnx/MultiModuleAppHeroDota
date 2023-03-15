apply{
    from("$rootDir/android-library-build.gradle")
}
dependencies {
    "implementation"(project(Modules.heroInteractors))
    "implementation"(project(Modules.heroDomain))
    "implementation"(project(Modules.core))

    "implementation"(Coil.coil)

}