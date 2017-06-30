---
maintainer: praqma-thi
---

# Gradle Plugin bootstrap

This project contains the scaffolding required to get you up and running with a custom Gradle plugin.
Clone it, tweak it, and presto! Your very own custom Gradle plugin!

See the [blog post on Praqma.com](http://www.praqma.com/stories/gradle-plugin-bootstrap) for more information,
including how to publish and distribute your custom Gradle plugin.

## The bits and pieces

### The entry point

```src/main/groovy/com/praqma/demo/DemoPlugin.groovy```

This is the heart of your plugin.
It implements `org.gradle.api.Plugin`, which comes with the `apply` method.
This is called when your plugin is applied to a Gradle project.
Here you can add tasks, extensions…
To keep the entry point from exploding in size, I like to keep these things in separate modules.

### Registering the entry point

```src/main/resources/META-INF/gradle-plugins/demo.properties```

To allow Gradle to find and apply your plugin's entry point, it looks for property files under `META-INF.gradle-plugins`.
The name of the property file represents your _plugin id_, used to apply the plugin, e.g. `plugins { id: 'demo' }`.
The `implementation-class` property points to the actual entry point's class, in this case `com.praqma.demo.DemoPlugin`.

### Adding custom tasks

```src/main/groovy/com/praqma/demo/greeting/GreetingModule.groovy```

Splitting tasks into modules is by no means required, it's just a way to prevent explosive growth  of the plugin's entry point.

Two tasks are added here, one showcasing extensions, the other using project properties.
When applying the plugin, simply call the `task` method on the project, much like you would in the project's _build.gradle_.

### Adding task types

```src/main/groovy/com/praqma/demo/greeting/GreetingTask.groovy```

Adding a custom task type is fairly straightforward.
Simply having the task class in the plugin will allow users to define tasks of that type using its fully qualified name.

### Adding extensions

```src/main/groovy/com/praqma/demo/greeting/GreetingExtension.groovy
src/main/groovy/com/praqma/demo/greeting/GreetingModule.groovy```
Adding an extension allows you to set their properties in the project's `build.gradle` after the plugin is applied.
Create a simple class containing some properties, and add it as a project extension when applying the plugin: `project.extensions.create("greeting", GreetingExtension)`

### Functional tests

```src/test/groovy/com/praqma/demo/greeting/GreetingModuleTest.groovy```

Using _JUnit's_ `@Rule` and `@Before` annotations makes it trivial to set up a temporary directory containing a `build.gradle` file.
Running Gradle in that directory with the `GradleRunner` allows you to apply your plugin to the temporary Gradle project.
Access to the build result, output and directory structure allows you to check if the task ran according to expectations.

## Useful resources

 - [gradle.org - Writing Custom Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html)
 - [gradle.org - The Gradle TestKit](https://docs.gradle.org/3.5/userguide/test_kit.html)
 - [gradle.org - Writing Custom Tasks](https://docs.gradle.org/3.5/userguide/custom_tasks.html)
 - [mrhaki.blogspot.se - Gradle Goodness: Define a short plugin id for custom plugins](http://mrhaki.blogspot.se/2010/09/gradle-goodness-define-short-plugin-id.html)
 - Various references are also linked to in the source's documentation