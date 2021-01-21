plugins {
    id("com.bmuschko.docker-java-application") version "6.7.0"
    application
}

dependencies {
    api(project(":Common"))
    implementation(project(":Database"))

    implementation("org.glassfish.jersey.core:jersey-server:2.33")

    implementation("io.dropwizard:dropwizard-core:2.0.18")
    implementation("io.dropwizard:dropwizard-db:2.0.18")
    implementation("io.dropwizard:dropwizard-jdbi3:2.0.18")
}

docker {
    javaApplication {
        baseImage.set("adoptopenjdk/openjdk14:debian")
        images.set(listOf("liquidforte/terra/server:latest"))
    }
}

tasks.create<Copy>("copyConfig") {
    dependsOn(":Server:dockerSyncBuildContext")
    from(file("terra.yml"))
    into(file("$buildDir/docker"))
}

tasks.named<com.bmuschko.gradle.docker.tasks.image.Dockerfile>("dockerCreateDockerfile") {
    dependsOn(":Server:copyConfig")
    copyFile("terra.yml", "/app/terra.yml")
}

application {
    mainClass.set("com.liquidforte.terra.server.TerraServer")
}
