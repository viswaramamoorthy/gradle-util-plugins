# gradle-util-plugins

When classpath for Gradle JavaExec task is long, Windows command executions give error because limitation to length of command line 32K.

With large number of classpath dependencies in a large project, this would a stopping error. To solve this issue, use ManifestClasspath plugin.

ManifestClasspath plugin creates a manifest jar for jars files in classpath for JavaExec task and sets the classpath with manifest jar.

Usage

To use the plugin, define a dependency in build script and have plugin entry in Gradle project.

build.gradle snippet to use ManifestClasspath plugin
....
buildscript {
    repositories {
        mavenCentral()
    }

	dependencies {
        classpath "com.github.viswaramamoorthy:gradle-util-plugins:0.1.0-RELEASE"
    }
}
....
apply plugin: 'ManifestClasspath'

Build
./gradelw build
