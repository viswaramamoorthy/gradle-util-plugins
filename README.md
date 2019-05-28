# gradle-util-plugins

When classpath for a Gradle JavaExec task is long, Windows command executions give error because of limitation to command line length greater than 32K.

With a number of classpath dependencies in a large project, typically JavaExec Gradle task fails with error "The filename or extension is too long" and this would be a stopping error. To solve this issue, use ManifestClasspath plugin.

ManifestClasspath plugin creates a manifest jar for jar files in the classpath of JavaExec task and sets the classpath with manifest jar.

### Usage

To use the plugin, define a dependency in build script and have plugin entry in Gradle project.

build.gradle snippet to use ManifestClasspath plugin
##### Build script snippet for plugins DSL for Gradle 2.1 and later
```
plugins {
  id "com.github.ManifestClasspath" version "0.2.0-RELEASE"
}
```
##### Build script snippet for use in older Gradle versions or where dynamic configuration is required
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.viswaramamoorthy:gradle-util-plugins:0.2.0-RELEASE"
  }
}

apply plugin: "com.github.ManifestClasspath"
```

### Build
./gradlew build
