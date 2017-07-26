# gradle-util-plugins

When classpath for a Gradle JavaExec task is long, Windows command executions give error because of limitation to command line length greater than 32K.

With a number of classpath dependencies in a large project, typically JavaExec Gradle task fails with error "The filename or extension is too long" and this would be a stopping error. To solve this issue, use ManifestClasspath plugin.

ManifestClasspath plugin creates a manifest jar for jar files in the classpath of JavaExec task and sets the classpath with manifest jar.

### Usage

To use the plugin, define a dependency in build script and have plugin entry in Gradle project.

build.gradle snippet to use ManifestClasspath plugin
```
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
```

### Build
./gradelw build
