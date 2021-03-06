dependencies {
    api(project(":Api"))

    api("com.netflix.governator:governator-annotations:1.17.11")
    api("com.netflix.governator:governator-core:1.17.11")

    api("com.google.inject:guice:5.0.0-BETA-1")
    api("com.google.inject.extensions:guice-assistedinject:5.0.0-BETA-1")

    api("com.fasterxml.jackson.core:jackson-annotations:2.12.1")
    api("com.fasterxml.jackson.core:jackson-databind:2.12.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.1")

    api("commons-cli:commons-cli:1.4")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.apache.commons:commons-compress:1.20")
    api("commons-io:commons-io:2.8.0")
    //api("org.apache.commons:commons-jexl3:3.1")
}