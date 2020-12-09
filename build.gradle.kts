plugins {
    java
    id("io.freefair.lombok") version "5.3.0"
}

group = "de.md5lukas"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()

    maven(url = "https://repo.sytm.de/repository/maven-hosted/")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")

}

dependencies {
    implementation("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
}
