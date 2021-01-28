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

		implementation("com.google.guava:guava:30.1-jre")
		implementation("com.google.inject:guice:5.0.0-BETA-1")

		implementation("io.vavr:vavr:1.0.0-alpha-3")
		implementation("io.vavr:vavr-jackson:1.0.0-alpha-3")
		implementation("io.vavr:vavr-match:0.10.3")
		implementation("io.vavr:vavr-match-processor:0.10.3")

		implementation("org.jooq:jool:0.9.14")

		testImplementation("io.vavr:vavr-test:0.10.3")

		testImplementation(platform("org.junit:junit-bom:5.7.0"))
		testImplementation("org.junit.jupiter:junit-jupiter")

		testImplementation("org.mockito:mockito-core:3.7.7")
		testImplementation("org.mockito:mockito-junit-jupiter:3.7.7")

		testImplementation("org.assertj:assertj-core:3.19.0")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

dependencies {
	implementation("com.liquidforte.terra:CurseClient:0.0.1-SNAPSHOT")

	implementation("com.zaxxer:HikariCP:4.0.1")

	implementation(platform("org.jdbi:jdbi3-bom:3.18.0"))
	implementation("org.jdbi:jdbi3-vavr")
	implementation("org.jdbi:jdbi3-jpa")
	implementation("org.jdbi:jdbi3-jackson2")
	implementation("org.jdbi:jdbi3-commons-text")
	implementation("org.jdbi:jdbi3-sqlobject")
	implementation("org.jdbi:jdbi3-stringtemplate4")
	implementation("org.jdbi:jdbi3-guava")

	implementation("com.h2database:h2:1.4.200")
}

application {
	mainClass.set("com.liquidforte.terra.main.Main")
}