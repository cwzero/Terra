includeBuild("../CurseClient") {
    dependencySubstitution {
        substitute(module("com.liquidforte.terra:CurseClient")).with(project(":"))
    }
}

include("Client", "Common", "Database", "Model", "Server")

rootProject.name = "Terra"
