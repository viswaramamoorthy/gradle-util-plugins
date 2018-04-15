package vr.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import vr.listener.JavaExecActionListener

/**
 * Adds JavaExec listener to Gradle project
 * @author Viswa Ramamoorthy
 */
class ManifestClasspathPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        def ext = project.extensions.create("ManifestClasspath", ManifestClasspathPluginExtension)
        project.gradle.addListener(new JavaExecActionListener(extension: ext))
    }
}


