dependencies {
    implementation(project(":Core"))

    implementation("org.glassfish.jersey.core:jersey-client:2.33")
    implementation("org.glassfish.jersey.ext.rx:jersey-rx-client-rxjava2:2.33")
    implementation("org.glassfish.jersey.ext.rx:jersey-rx-client-guava:2.33")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.33")
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.33")

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.3")

    implementation("io.github.openfeign:feign-core:11.1")
    implementation("io.github.openfeign:feign-jackson:11.1")
    implementation("io.github.openfeign:feign-httpclient:11.1")
    implementation("io.github.openfeign:feign-reactive-wrappers:11.1")
    implementation("io.github.openfeign:feign-slf4j:11.1")
    implementation("io.github.openfeign:feign-jaxrs2:11.1")
    testImplementation("io.github.openfeign:feign-mock:11.1")
}