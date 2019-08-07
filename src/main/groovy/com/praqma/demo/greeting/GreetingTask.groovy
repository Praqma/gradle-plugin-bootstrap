package com.praqma.demo.greeting

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

/**
 * A custom task type, allows projects to create tasks of type 'GreetingTask'
 * Reference:
 * https://docs.gradle.org/5.5.1/userguide/more_about_tasks.html#sec:task_inputs_outputs
 */
class GreetingTask extends DefaultTask {
    @Internal
    String message = "Hello"

    @Internal
    String target = "World"

    @TaskAction
    void greet() {
        if (message.toLowerCase(Locale.ROOT).contains("bye")) {
            throw new GradleException("I can't let you do that, Starfox.")
        }

        println "${message}, ${target}!"
    }
}
