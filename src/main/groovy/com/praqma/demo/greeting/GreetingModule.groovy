package com.praqma.demo.greeting

import org.gradle.api.Project

class GreetingModule {
    static void load(Project project) {
        project.extensions.create("greeting", GreetingExtension)

        project.task('greet') {
            group = "Demo"
            description = "Greets the world."

            doLast {
                String greeting = project.extensions.greeting.alternativeGreeting ?: "Hello"
                println "$greeting, world!"
            }
        }
    }
}
