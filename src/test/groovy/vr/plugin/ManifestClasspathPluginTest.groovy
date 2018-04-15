package vr.plugin

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static vr.listener.JavaExecActionListener.JAR_FOLDER

class ManifestClasspathPluginTest extends Specification {

    Project project

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()

    void setup() {
        project = ProjectBuilder.builder().withProjectDir(testProjectDir.root).build()
        project.pluginManager.apply "com.github.ManifestClasspath"
        project.pluginManager.apply 'java'

        FileUtils.copyURLToFile(getClass().getResource("/SampleMain.class"), new File(project.buildDir, "SampleMain.class"))
        FileUtils.copyURLToFile(getClass().getResource("/gradle-util-plugins-sources.jar"), new File(project.buildDir, "gradle-util-plugins-sources.jar"))
    }

    def 'should register plugin extension'() {
        when:
        project.extensions.getByType(ManifestClasspathPluginExtension).enableForAllOS = true
        then:
        project.extensions.getByType(ManifestClasspathPluginExtension).shouldApplyManifestJar()
    }

    def "should set classpath of JavaExec tasks to manifest jar"() {
        given: 'JavaExec task with some classpath'
        def t = project.tasks.create("myJavaExec", JavaExec) {
            classpath(project.buildDir, new File(project.buildDir, "gradle-util-plugins-sources.jar"))
            main = 'SampleMain'
        }
        and: 'initial classpath size is two'
        assert t.classpath.size() == 2
        and:
        project.extensions.getByType(ManifestClasspathPluginExtension).enableForAllOS = true

        when:
        t.execute()

        then: 'resulting classpath consists of single item'
        t.classpath.size() == 1
        and: 'this item is a manifest jar'
        t.classpath.head() == project.file("${project.buildDir}/$JAR_FOLDER/${t.name}_ManifestJar.jar")
        and: 'manifest jar exists'
        t.classpath.head().exists()
    }

    def "should set classpath of default Test tasks to manifest jar"() {
        given: 'default Test task'
        def t = project.tasks['test']
        and: 'initial classpath size has more than one item'
        assert t.classpath.size() > 1
        and:
        project.extensions.getByType(ManifestClasspathPluginExtension).enableForAllOS = true

        when:
        t.execute()

        then: 'classpath consists of single item'
        t.classpath.size() == 1
        and: 'this item is a manifest jar'
        t.classpath.head() == project.file("${project.buildDir}/$JAR_FOLDER/${t.name}_ManifestJar.jar")
        and: 'manifest jar exists'
        t.classpath.head().exists()
    }

    def "should set classpath of custom Test tasks to manifest jar"() {
        given: 'custom Test task'
        def t = project.tasks.create("myTest", Test)
        and: 'initial classpath size has more than one item'
        assert t.classpath.size() > 1
        and:
        project.extensions.getByType(ManifestClasspathPluginExtension).enableForAllOS = true

        when:
        t.execute()

        then: 'classpath consists of single item'
        t.classpath.size() == 1
        and: 'this item is a manifest jar'
        t.classpath.head() == project.file("${project.buildDir}/$JAR_FOLDER/${t.name}_ManifestJar.jar")
        and: 'manifest jar exists'
        t.classpath.head().exists()
    }
}
