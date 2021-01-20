includeBuild("../CurseClient") {
    dependencySubstitution {
        substitute(module("com.liquidforte.terra:CurseClient")).with(project(":"))
    }
}

rootProject.name = "Terra"
