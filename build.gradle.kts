plugins {
    id("com.github.ben-manes.versions") version "0.39.0"
    id("java-library")
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

allprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        annotationProcessor("org.projectlombok:lombok:1.18.22")
        compileOnly("org.projectlombok:lombok:1.18.22")

        testImplementation(platform("org.junit:junit-bom:5.8.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        testImplementation("org.mockito:mockito-core:4.1.0")
        testImplementation("org.mockito:mockito-junit-jupiter:4.1.0")

        testImplementation("org.assertj:assertj-core:3.21.0")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":Core"))
    implementation(project(":Cache"))
    implementation(project(":CurseClient"))
    implementation(project(":Database"))

    implementation("com.netflix.governator:governator-commons-cli:1.17.12")

<<<<<<< HEAD
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    api("org.apache.logging.log4j:log4j-api:2.14.1")
=======
    runtimeOnly("org.slf4j:slf4j-simple:2.0.0-alpha5")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "com.liquidforte.terra.main.Main")
    }
>>>>>>> b537d67 (Update Dependencies)
}

application {
    mainClass.set("com.liquidforte.terra.main.Main")
}

tasks.named<JavaExec>("run") {
    workingDir = file("run")
    args = listOf("runMMCInstance")
}

tasks.register<Sync>("syncBin") {
    dependsOn("installDist")

    from(file("$buildDir/install/Terra/bin"))
    into(file("${System.getProperty("user.home")}/.terra/bin"))
}

tasks.register<Sync>("syncLib") {
    dependsOn("installDist")

    from(file("$buildDir/install/Terra/lib"))
    into(file("${System.getProperty("user.home")}/.terra/lib"))
}

tasks.register("install") {
    dependsOn("syncBin", "syncLib")
}