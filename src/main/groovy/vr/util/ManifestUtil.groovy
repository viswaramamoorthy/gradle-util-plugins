package vr.util

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Dependency
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.bundling.Jar
import java.util.jar.Attributes
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import java.util.zip.ZipEntry

/**
 * Utility class to prepare manifest jar from file collection 
 * This will help address long path issue in Windows. 
 *
 * @author Viswa Ramamoorthy
 *
 */
class ManifestUtil
{
    private static final Logger LOG = Logging.getLogger(ManifestUtil.class)

	/**
	* Utility API to create Manifest jar from file collection
	* typically can be used as a substitute for long classpath 
	* 
	* @param jarFile - File instance used to create Manifest file
	* @param fileCollection - Collection of Files to added to manifest file as classpath entry
	*/
    public static void prepareManifestJar(def jarFile, def fileCollection) {
        if (!fileCollection)
            return
        def classPathString = fileCollection.files.collect {
                                it.toURL().toString()
                              }.join(' ')
        createManifestJar(jarFile, manifestWithClasspath(classPathString))
    }

    private static def createManifestJar(def jarFile, def manifest = null) throws IOException {
        def jarOutputStream

        //To ensure parent directory structure created
        //e.g. new clone will not have build folder
        jarFile.getParentFile().mkdirs()
        if (manifest == null) {
            jarOutputStream = new JarOutputStream(new FileOutputStream(jarFile))
        } else {
            jarOutputStream = new JarOutputStream(new FileOutputStream(jarFile), manifest)
        }

        jarOutputStream.putNextEntry(new ZipEntry("META-INF/"));
        jarOutputStream.close();
    }

    private static def manifestWithClasspath(def manifestClasspath) {
        Manifest manifest = new Manifest();
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        if (manifestClasspath != null) {
            attributes.putValue("Class-Path", manifestClasspath);
        }
        return manifest
    }
}
