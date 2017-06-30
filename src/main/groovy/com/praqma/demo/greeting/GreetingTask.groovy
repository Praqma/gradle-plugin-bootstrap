package com.praqma.demo.greeting

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

/**
 * A custom task type, allows projects to create tasks of type 'GreetingTask'
 * Reference:
 * https://docs.gradle.org/3.3/userguide/more_about_tasks.html#sec:task_input_output_annotations
 * Example 19.23
 */
class GreetingTask extends DefaultTask {
    @Internal
    String message = "Hello"

    @Internal
    String target = "World"

    @TaskAction
    void greet() {
        println "${message}, ${target}!"
    }
}
