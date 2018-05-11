package com.praqma.demo.greeting

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.*
import org.junit.rules.TemporaryFolder

import static org.gradle.testkit.runner.TaskOutcome.*

/**
 * Contains functional tests that use the GradleRunner to run the plugin's task in a controlled environment.
 * Reference:
 * https://docs.gradle.org/4.6/userguide/test_kit.html#sec:functional_testing_with_the_gradle_runner
 */
class GreetingModuleTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File build_gradle

    @Before
    public void setup() {
        // Prepare build.gradle
        build_gradle = testProjectDir.newFile('build.gradle')
        build_gradle << 'plugins { id "com.praqma.demo.DemoPlugin" }\n'
    }

    /**
     * Helper method that runs a Gradle task in the testProjectDir
     * @param arguments the task arguments to execute
     * @param isSuccessExpected boolean representing whether or not the build is supposed to fail
     * @return the task's BuildResult
     */
    private BuildResult gradle(boolean isSuccessExpected, String[] arguments = ['tasks']) {
        arguments += '--stacktrace'
        def runner = GradleRunner.create()
                .withArguments(arguments)
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
                .withDebug(true)
        return isSuccessExpected ? runner.build() : runner.buildAndFail()
    }

    private BuildResult gradle(String[] arguments = ['tasks']) {
        gradle(true, arguments)
    }

    @Test
    public void helloWorld_standard() {
        def result = gradle('helloWorld')
        assert result.task(":helloWorld").outcome == SUCCESS
        assert result.output.contains("Hello, world!")
    }

    @Test
    public void helloWorld_alternative() {
        build_gradle << 'greeting.alternativeGreeting = "Howdy"'

        def result = gradle('helloWorld')

        assert result.task(":helloWorld").outcome == SUCCESS
        assert result.output.contains("Howdy, world!")
    }

    @Test
    public void helloTarget_standard() {
        def result = gradle('helloTarget')

        assert result.task(":helloTarget").outcome == SUCCESS
        assert result.output.contains("Hello, user!")
    }

    @Test
    public void helloTarget_option() {
        def result = gradle('helloTarget', '-Ptarget=Robert')

        assert result.task(":helloTarget").outcome == SUCCESS
        assert result.output.contains("Hello, Robert!")
    }

    @Test
    public void greetingTask_standard() {
        build_gradle << """
            task goodTask(type: GreetingTask) {
                message = "Ahoy"
                target = "William"
            }
            """

        def result = gradle('goodTask')

        assert result.task(":goodTask").outcome == SUCCESS
        assert result.output.contains("Ahoy, William!")
    }

    @Test
    public void greetingTask_broken() {
        build_gradle << """
            task badTask(type: GreetingTask) {
                message = "Goodbye"
                target = "William"
            }
            """

        def result = gradle(false, 'badTask')

        assert result.task(":badTask").outcome == FAILED
        assert result.output.contains("I can't let you do that, Starfox.")
    }
}
