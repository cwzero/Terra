plugins {
    id("com.github.ben-manes.versions") version "0.36.0"
    id("java-library")
    id("application")
}

allprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
        jcenter()
        google()
    }

    dependencies {
        annotationProcessor("org.projectlombok:lombok:1.18.16")
        compileOnly("org.projectlombok:lombok:1.18.16")

        testImplementation(platform("org.junit:junit-bom:5.7.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        testImplementation("org.mockito:mockito-core:3.7.7")
        testImplementation("org.mockito:mockito-junit-jupiter:3.7.7")

        testImplementation("org.assertj:assertj-core:3.18.1")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":Core"))
    implementation(project(":Client"))
    implementation(project(":Cache"))
    implementation(project(":CurseClient"))
    implementation(project(":Database"))

    implementation("com.netflix.governator:governator-commons-cli:1.17.11")

    runtimeOnly("org.slf4j:slf4j-simple:1.7.30")
}

application {
    mainClass.set("com.liquidforte.terra.main.Main")
}

tasks.named<JavaExec>("run") {
    workingDir = file("run")
    args = listOf("resolve")
}