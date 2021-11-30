dependencies {
    implementation(project(":Core"))

    implementation("org.glassfish.jersey.core:jersey-client:2.33")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.33")
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.33")

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.0")

    implementation("io.github.openfeign:feign-core:11.7")
    implementation("io.github.openfeign:feign-jackson:11.7")
    implementation("io.github.openfeign:feign-httpclient:11.7")
    implementation("io.github.openfeign:feign-reactive-wrappers:11.7")
    implementation("io.github.openfeign:feign-slf4j:11.7")
    implementation("io.github.openfeign:feign-jaxrs2:11.7")
    testImplementation("io.github.openfeign:feign-mock:11.7")
}