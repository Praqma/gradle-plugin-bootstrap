package com.praqma.demo.greeting

import org.gradle.testkit.runner.GradleRunner
import org.junit.*
import org.junit.rules.TemporaryFolder

import static org.gradle.testkit.runner.TaskOutcome.*

class GreetingModuleTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File build_gradle

    @Before
    public void setup() {
        // Prepare build.gradle
        build_gradle = testProjectDir.newFile('build.gradle') << """\
            plugins {
                id "demo"
            }""".stripIndent()
    }

    @Test
    public void hello_world() {
        def result = GradleRunner.create()
                .withArguments('greet', "--stacktrace")
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
                .withDebug(true)
                .build()

        assert result.task(":greet").outcome == SUCCESS
        assert result.output.contains("Hello, world!")
    }

    @Test
    public void howdy_world() {
        build_gradle.append("\ngreeting.alternativeGreeting = 'Howdy'")

        def result = GradleRunner.create()
                .withArguments('greet', "--stacktrace")
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
                .withDebug(true)
                .build()

        assert result.task(":greet").outcome == SUCCESS
        assert result.output.contains("Howdy, world!")
    }
}
