package vr.listener

import org.gradle.api.Task
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.JavaExec
import java.io.File
import vr.util.ManifestUtil

/**
 *
 * Action listener to JavaExec tasks that can be used to make changes to input/output;
 * for e.g. set classpath for as a JAR file with manifest containing jars files in input classpath
 * This addresses long classpath issue that is noticed when a command line exceed 32K length in Windows
 *
 * @author Viswa Ramamoorthy
 */
class JavaExecActionListener implements TaskActionListener
{
    private static final Logger LOG = Logging.getLogger(JavaExecActionListener.class);
    private static final String jarFolder = "mfjars";

    @Override
    /**
    * Action listener that gets invoked before Gradle task executed
    */
    void beforeActions(Task task) {
        if (task instanceof JavaExec ) {
        	JavaExec jExecTask = (JavaExec)task;
            LOG.info("Updating classpath to use jar file manifest for task: " + task.name)
             
            FileCollection classpathFiles = jExecTask.getClasspath()
            def jarPath = task.project.buildDir.absolutePath + File.separator + jarFolder + File.separator
            def jarFile = jarPath + task.name + "_ManifestJar.jar"
            LOG.info("Executing Jar preparation action to create " + jarFile)
            File file = new File(jarFile)
            ManifestUtil.prepareManifestJar(file, classpathFiles)
            
            //Set manifest jar
            jExecTask.setClasspath(task.getProject().files(file.getAbsolutePath()))
            LOG.info("Added file " + file.getAbsolutePath() + " to classpath for task: " + task.name)
        }
    }

    @Override
    /**
    * Action listener that gets invoked after Gradle task executed
    */
    void afterActions(Task task)  {
        //Nothing to add for after action at this time
    }
}
