package com.praqma.demo.greeting

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.*
import org.junit.rules.TemporaryFolder

import static org.gradle.testkit.runner.TaskOutcome.*

/**
 * Contains functional tests that use the GradleRunner to run the plugin's task in a controlled environment.
 * Reference:
 * https://docs.gradle.org/3.5/userguide/test_kit.html#sec:functional_testing_with_the_gradle_runner
 * Example 45.3
 */
class GreetingModuleTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File build_gradle

    @Before
    public void setup() {
        // Prepare build.gradle
        build_gradle = testProjectDir.newFile('build.gradle') << """\
            plugins {
                id "com.praqma.demo.DemoPlugin"
            }""".stripIndent()
    }

    /**
     * Helper method that runs a Gradle task in the testProjectDir
     * @param arguments the task arguments to execute
     * @param isFailureExpected boolean representing whether or not the build is supposed to fail
     * @return the task's BuildResult
     */
    private BuildResult runGradleTask(String[] arguments = ['tasks'], boolean isFailureExpected = false) {
        arguments += '--stacktrace'
        def runner = GradleRunner.create()
                .withArguments(arguments)
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
                .withDebug(true)
        return isFailureExpected ? runner.buildAndFail() : runner.build()
    }

    @Test
    public void helloWorld_standard() {
        def result = runGradleTask('helloWorld')
        assert result.task(":helloWorld").outcome == SUCCESS
        assert result.output.contains("Hello, world!")
    }

    @Test
    public void helloWorld_alternative() {
        build_gradle.append("\ngreeting.alternativeGreeting = 'Howdy'")

        def result = runGradleTask('helloWorld')
        assert result.task(":helloWorld").outcome == SUCCESS
        assert result.output.contains("Howdy, world!")
    }

    @Test
    public void helloTarget_standard() {
        def result = runGradleTask('helloTarget')
        assert result.task(":helloTarget").outcome == SUCCESS
        assert result.output.contains("Hello, user!")
    }

    @Test
    public void helloTarget_option() {
        def result = runGradleTask('helloTarget', '-Ptarget=Robert')
        assert result.task(":helloTarget").outcome == SUCCESS
        assert result.output.contains("Hello, Robert!")
    }

    @Test
    public void greetingTask() {
        build_gradle.append("""
            task myTask(type: GreetingTask) {
                message = "Ahoy"
                target = "William"
            }
            """)
        println build_gradle.text
        def result = runGradleTask('myTask')
        assert result.task(":myTask").outcome == SUCCESS
        assert result.output.contains("Ahoy, William!")
    }
}
