plugins {
	id("com.github.ben-manes.versions") version "0.36.0"
	id("java-library")
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

		implementation("com.google.guava:guava:29.0-jre")

		implementation("io.vavr:vavr:0.10.3")
		implementation("io.vavr:vavr-jackson:0.10.3")
		implementation("io.vavr:vavr-match:0.10.3")
		implementation("io.vavr:vavr-match-processor:0.10.3")

		testImplementation("io.vavr:vavr-test:0.10.3")

		testImplementation(platform("org.junit:junit-bom:5.7.0"))
		testImplementation("org.junit.jupiter:junit-jupiter")

		testImplementation("org.mockito:mockito-core:3.7.7")
		testImplementation("org.mockito:mockito-junit-jupiter:3.7.7")

		testImplementation("org.assertj:assertj-core:3.18.1")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}