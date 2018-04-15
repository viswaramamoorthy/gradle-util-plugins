package vr.listener

import org.gradle.api.Task
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.process.JavaForkOptions
import vr.plugin.ManifestClasspathPluginExtension
import vr.util.ManifestUtil

/**
 *
 * Action listener to JavaExec tasks that can be used to make changes to input/output;
 * for e.g. set classpath for as a JAR file with manifest containing jars files in input classpath
 * This addresses long classpath issue that is noticed when a command line exceed 32K length in Windows
 *
 * @author Viswa Ramamoorthy
 */
class JavaExecActionListener implements TaskActionListener {

    private ManifestClasspathPluginExtension extension

    static final Logger LOG = Logging.getLogger(JavaExecActionListener)
    static final String JAR_FOLDER = "mfjars"

    @Override
    /**
     * Action listener that gets invoked before Gradle task executed
     */
    void beforeActions(Task task) {
        if (task instanceof JavaForkOptions && extension.shouldApplyManifestJar()) {
            JavaForkOptions javaForkOptions = (JavaForkOptions) task
            LOG.info("Updating classpath to use jar file manifest for task: ${task.name}")

            def jarFile = new File(new File(task.project.buildDir, JAR_FOLDER), "${task.name}_ManifestJar.jar")
            LOG.info("Executing Jar preparation action to create ${jarFile.absolutePath}")
            ManifestUtil.prepareManifestJar(jarFile, javaForkOptions.classpath)

            //Set manifest jar
            javaForkOptions.setClasspath(task.project.files(jarFile.absolutePath))
            LOG.info("Added file ${jarFile.absolutePath} to classpath for task: ${task.name}")
        }
    }

    @Override
    /**
     * Action listener that gets invoked after Gradle task executed
     */
    void afterActions(Task task) {
        //Nothing to add for after action at this time
    }
}
