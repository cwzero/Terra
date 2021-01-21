plugins {
    id("com.bmuschko.docker-java-application") version "6.7.0"
    application
}

dependencies {
    api(project(":Model"))

    implementation("com.zaxxer:HikariCP:3.4.5")

    implementation(platform("org.jdbi:jdbi3-bom:3.18.0"))
    implementation("org.jdbi:jdbi3-vavr")
    implementation("org.jdbi:jdbi3-jpa")
    implementation("org.jdbi:jdbi3-jackson2")
    implementation("org.jdbi:jdbi3-commons-text")
    implementation("org.jdbi:jdbi3-sqlobject")
    implementation("org.jdbi:jdbi3-stringtemplate4")
    implementation("org.jdbi:jdbi3-guava")

    implementation("org.jooq:jooq:3.14.4")
    implementation("org.jooq:jooq-checker:3.14.4")
    implementation("org.jooq:jooq-meta:3.14.4")
    implementation("org.jooq:jooq-meta-extensions:3.14.4")
    implementation("org.jooq:jooq-meta-extensions-hibernate:3.14.4")
    implementation("org.jooq:jooq-meta-extensions-liquibase:3.14.4")
    implementation("org.jooq:jooq-codegen:3.14.4")
    implementation("org.jooq:jooq-xtend:3.14.4")

    implementation("org.liquibase:liquibase-core:4.2.2")

    implementation("com.h2database:h2:1.4.200")

    implementation("org.jdbi:jdbi3-postgres:3.18.0")
    implementation("org.postgresql:postgresql:42.2.18")
}

docker {
    javaApplication {
        baseImage.set("adoptopenjdk/openjdk14:debian")
        images.set(listOf("liquidforte/terra/database:latest"))
    }
}

application {
    mainClass.set("com.liquidforte.terra.database.DBMain")
}