import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	`maven-publish`

	alias(libs.plugins.kotlin)
	alias(libs.plugins.quilt.loom)

	id("com.modrinth.minotaur") version "2.+"
}

val archives_base_name: String by project
val minecraft_version: String by project
base.archivesName.set(archives_base_name + minecraft_version)
val version: String by project
group = "amerebagatelle.github.io"

val javaVersion = 17

repositories {
	maven (
		url = "https://maven.quiltmc.org/repository/release"
	)
	maven (
		url = "https://maven.terraformersmc.com/"
	)
	maven (
		url = "https://maven.gegy.dev"
	)
}

dependencies {
	//to change the versions see the gradle.properties file
	minecraft(libs.minecraft)
	mappings(
		variantOf(libs.quilt.mappings) {
			classifier("intermediary-v2")
		}
	)
	modImplementation(libs.quilt.loader)

	modImplementation(libs.qfapi)

	modImplementation(libs.modmenu)

	modImplementation(libs.spruceui)
	include(libs.spruceui)

	compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = javaVersion.toString()
			languageVersion = libs.plugins.kotlin.get().version.strictVersion
			incremental = true
		}
	}

	withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.isDeprecation = true
		options.isIncremental = true
		options.release.set(javaVersion)
	}

	processResources {
		inputs.property("version", project.version)

		filesMatching("quilt.mod.json") {
			expand(
				mapOf(
					"version" to project.version
				)
			)
		}
	}

	// Change the gradle version here and run `./gradlew wrapper` or `gradle wrapper` to update gradle scripts
	// BIN distribution should be sufficient for the majority of mods
	wrapper {
		distributionType = Wrapper.DistributionType.BIN
	}

	remapJar {
		dependsOn(remapSourcesJar)
	}

	jar {
		from("LICENSE")
	}
}

kotlin {
	jvmToolchain(javaVersion)
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	// If this mod is going to be a library, then it should also generate Javadocs in order to aid with development.
	// Uncomment this line to generate them.
	// withJavadocJar()

	// Still required by IDEs such as Eclipse and VSC
	sourceCompatibility = JavaVersion.toVersion(javaVersion)
	targetCompatibility = JavaVersion.toVersion(javaVersion)
}


val sourceJar = task("sourceJar", Jar::class) {
	dependsOn(tasks["classes"])
	archiveClassifier.set("source")
	from(sourceSets.main.get().allSource)
}

val javadoc = task("javadocJar", Jar::class) {
	archiveClassifier.set("javadoc")
	from(tasks.javadoc)
	from(tasks.javadoc)
}

modrinth {
	token.set(System.getenv("MODRINTH_TOKEN"))
	projectId.set("65jTHvHz")
	versionName.set("$archives_base_name $version")
	versionNumber.set(version)
	changelog.set(System.getenv("CHANGELOG_BODY"))
	uploadFile.set(tasks.remapJar.get())
	gameVersions.addAll(minecraft_version)
	loaders.add("quilt")
	dependencies {
		required.project("qsl")
	}
}
