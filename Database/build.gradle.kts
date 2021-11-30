dependencies {
    implementation(project(":Core"))

    implementation("com.zaxxer:HikariCP:5.0.0")

    implementation(platform("org.jdbi:jdbi3-bom:3.24.1"))
    implementation("org.jdbi:jdbi3-jpa")
    implementation("org.jdbi:jdbi3-jackson2")
    implementation("org.jdbi:jdbi3-commons-text")
    implementation("org.jdbi:jdbi3-sqlobject")
    implementation("org.jdbi:jdbi3-stringtemplate4")
    implementation("org.jdbi:jdbi3-guava")

    implementation("com.h2database:h2:1.4.200")
}