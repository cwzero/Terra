dependencies {
    api(project(":Common"))
    implementation(project(":Database"))

    implementation("org.glassfish.jersey.core:jersey-client:2.33")

    implementation("io.github.openfeign:feign-core:11.0")
    implementation("io.github.openfeign:feign-jackson:11.0")
    implementation("io.github.openfeign:feign-httpclient:11.0")
    implementation("io.github.openfeign:feign-reactive-wrappers:11.0")
    implementation("io.github.openfeign:feign-slf4j:11.0")
    implementation("io.github.openfeign:feign-jaxrs2:11.0")
    testImplementation("io.github.openfeign:feign-mock:11.0")
}