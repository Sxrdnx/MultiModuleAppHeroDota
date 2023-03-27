apply{
    from("$rootDir/library-build.gradle")
}
dependencies {
    "implementation" ("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")

    "implementation"(project(Modules.core))
}