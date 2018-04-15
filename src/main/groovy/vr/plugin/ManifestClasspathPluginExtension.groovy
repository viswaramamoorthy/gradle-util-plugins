package vr.plugin

import org.apache.tools.ant.taskdefs.condition.Os

/**
 * Extension class for ManifestClasspathPlugin to provide parameter to customize
 * @author Viswa Ramamoorthy
 */
class ManifestClasspathPluginExtension {
    boolean enableForAllOS = false

    def shouldApplyManifestJar() {
        this.enableForAllOS || Os.isFamily(Os.FAMILY_WINDOWS)
    }

}


