# gradle-util-plugins

When classpath for Gradle JavaExec task is long, Windows command executions give error because limitation to length of command line 32K.

With large number of classpath dependencies in a large project, this would a stopping error. To solve this issue, use ManifestClasspath plugin.

ManifestClasspath plugin creates a manifest jar for jars files in classpath for JavaExec task and sets the classpath with manifest jar.

To build
./gradelw build

To use the plugin, stage plugin jar, define a dependency in build script and have plugin entry in Gradle project.

For e.g. copy jar file to lib folder in target project and have plugin added to build script as follows

build.gradle snippet to use ManifestClasspath plugin
....
buildscript {
    dependencies {
         classpath files('lib/gradle-util-plugins-0.1.0-RELEASE.jar')
    }
}
....
apply plugin: 'ManifestClasspath'

TODO
Publish plugin jar to a central repository
