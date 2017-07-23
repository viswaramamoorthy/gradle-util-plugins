package vr.plugin

import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Plugin
import org.gradle.api.Project
import vr.listener.JavaExecActionListener

/**
 * Adds JavaExec listner to gradle project
 * @author Viswa Ramamoorthy
 */
class ManifestClasspathPlugin implements Plugin<Project>
{
    private static final Logger LOG = Logging.getLogger(ManifestClasspathPlugin.class);

    @Override
    /**
    * Hook up JavaExecActionListener to project when enabled
    * either use ManifestClasspath.enableForAllOS to enable or
    * when environment is Windows which fails when long classpath is part of 
    * command line exceeds 32k length
    */
    public void apply(Project project)
    {
    	project.extensions.create("ManifestClasspath", ManifestClasspathPluginExtenstion)
    	
    	//If it is Windows env or enableForAllOS is true, add listener; else do nothing
        if (project.ManifestClasspath.enableForAllOS ||
        		Os.isFamily(Os.FAMILY_WINDOWS)) {
            //Add project listeners for Windows env to fix long classpath issue
            LOG.info("Adding JavaExecActionListener to project name: " + project.name);
            project.gradle.addListener( new JavaExecActionListener() )
        }
    }
}


