dependencies {
    api(project(":Model"))

    api("com.liquidforte.terra:CurseClient:0.0.1-SNAPSHOT")

    api("org.glassfish.jersey.core:jersey-common:2.33")
    api("org.glassfish.jersey.ext.rx:jersey-rx-client-rxjava2:2.33")
    api("org.glassfish.jersey.ext.rx:jersey-rx-client-guava:2.33")
    api("org.glassfish.jersey.media:jersey-media-json-jackson:2.33")
    api("org.glassfish.jersey.inject:jersey-hk2:2.33")

    api("com.fasterxml.jackson.core:jackson-annotations:2.12.1")
    api("com.fasterxml.jackson.core:jackson-databind:2.12.1")
}