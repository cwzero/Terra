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
		implementation("com.google.inject:guice:4.2.3:no_aop")
		implementation("com.google.inject.extensions:guice-assistedinject:4.2.3")
		implementation("com.google.inject.extensions:guice-multibindings:4.2.3")

		implementation("commons-cli:commons-cli:1.4")
		implementation("commons-codec:commons-codec:1.14")
		implementation("commons-io:commons-io:2.6")
		implementation("org.apache.commons:commons-jexl3:3.1")
		implementation("org.apache.httpcomponents:httpclient:4.5.12")

		runtimeOnly("org.slf4j:slf4j-simple:1.7+")

		//implementation("org.jooq:jool:0.9.14")

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
	//implementation("org.jdbi:jdbi3-vavr")
	implementation("org.jdbi:jdbi3-jpa")
	implementation("org.jdbi:jdbi3-jackson2")
	implementation("org.jdbi:jdbi3-commons-text")
	implementation("org.jdbi:jdbi3-sqlobject")
	implementation("org.jdbi:jdbi3-stringtemplate4")
	implementation("org.jdbi:jdbi3-guava")

	implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.1")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.12.1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.1")

	implementation("com.h2database:h2:1.4.200")
}

application {
	mainClass.set("com.liquidforte.terra.main.Main")
}

tasks {
	named<JavaExec>("run") {
		workingDir = file("run/")
		args = listOf("--command=install")
	}
}