package com.praqma.demo.greeting

import org.gradle.api.Project

/**
 * The module isn't anything Gradle specific.
 * It's just a way of grouping tasks and configuration that share a certain theme.
 * The module's 'load' method is called in the plugin's entry point at {@link com.praqma.demo.DemoPlugin}
 */
class GreetingModule {
    static void load(Project project) {
        /*
        * Register a 'greeting' extension, with the properties defined in GreetingExtension
        * Reference:
        * https://docs.gradle.org/3.5/userguide/custom_plugins.html#sec:getting_input_from_the_build
        * Example 41.2
        */
        project.extensions.create("greeting", GreetingExtension)

        /*
        * Clever trick so users don't have to reference a custom task class by its fully qualified name.
        * Reference:
        * https://discuss.gradle.org/t/how-to-create-custom-gradle-task-type-and-not-have-to-specify-full-path-to-type-in-build-gradle/6059/4
        */
        project.ext.GreetingTask = GreetingTask

        /*
        * A task that uses an extension for configuration.
        * Reference:
        * https://docs.gradle.org/3.5/userguide/custom_plugins.html#sec:getting_input_from_the_build
        * Example 41.2
        */
        project.task('helloWorld') {
            group = "Greeting"
            description = "Greets the world. Greeting configured in the 'greeting' extension."

            doLast {
                String greeting = project.extensions.greeting.alternativeGreeting ?: "Hello"
                println "$greeting, world!"
            }
        }

        /*
        * A task using a project property for configuration.
        * Reference:
        * https://docs.gradle.org/3.5/userguide/build_environment.html#sec:gradle_properties_and_system_properties
        * Example 12.1
        */
        project.task('helloTarget') {
            group = "Greeting"
            description = "Greets the user. Target configured through properties."

            doLast {
                String target = project.findProperty("target") ?: "user"
                println "Hello, $target!"
            }
        }
    }
}
