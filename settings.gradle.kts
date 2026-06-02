pluginManagement {
  repositories {
    maven {
      name = "Fabric"
      url = uri("https://maven.fabricmc.net/")
    }
    mavenCentral()
    gradlePluginPortal()
  }

  plugins {
    id("net.fabricmc.fabric-loom-remap") version providers.gradleProperty("deps.fabric_loom")
  }
}

// Should match your modid
rootProject.name = "convenient-name-tags"