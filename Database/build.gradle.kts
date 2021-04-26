dependencies {
    implementation(project(":Core"))

    implementation("com.zaxxer:HikariCP:4.0.3")

    implementation(platform("org.jdbi:jdbi3-bom:3.19.0"))
    implementation("org.jdbi:jdbi3-jpa")
    implementation("org.jdbi:jdbi3-jackson2")
    implementation("org.jdbi:jdbi3-commons-text")
    implementation("org.jdbi:jdbi3-sqlobject")
    implementation("org.jdbi:jdbi3-stringtemplate4")
    implementation("org.jdbi:jdbi3-guava")

    implementation("com.h2database:h2:1.4.200")
}