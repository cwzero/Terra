dependencies {
    implementation("com.zaxxer:HikariCP:3.4.5")


    implementation(platform("org.jdbi:jdbi3-bom:3.18.0"))
    implementation("org.jdbi:jdbi3-vavr")
    implementation("org.jdbi:jdbi3-jpa")
    implementation("org.jdbi:jdbi3-jackson2")
    implementation("org.jdbi:jdbi3-commons-text")
    implementation("org.jdbi:jdbi3-sqlobject")
    implementation("org.jdbi:jdbi3-stringtemplate4")
    implementation("org.jdbi:jdbi3-guava")
}