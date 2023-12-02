plugins {
  kotlin("jvm") version "1.9.21"
}

repositories {
  mavenCentral()
}

dependencies {
  val kotest = "5.8.0"
  testImplementation("io.kotest:kotest-runner-junit5:$kotest")
  testImplementation("io.kotest:kotest-assertions-core:$kotest")
}

sourceSets.main {
  java.srcDirs("src/main/kotlin")
}

sourceSets.test {
  java.srcDirs("src/test/kotlin")
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}
