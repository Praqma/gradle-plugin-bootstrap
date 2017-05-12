---
maintainer: praqma-thi
---

# Gradle Plugin bootstrap

This project contains the minimum scaffolding required to get you up and running with a custom Gradle plugin.
Clone it, rename some classes to taste and presto, your very own custom Gradle plugin!

 
See the [blog post on Praqma.com](http://www.praqma.com/stories/gradle-plugin-bootstrap) for more information,
including how to publish and distribute your custom Gradle plugin.

## The bits and pieces

### DemoPlugin

_The plugin entry point._
```src/main/groovy/com/praqma/demo/DemoPlugin.groovy```

It's `apply` method is executed when you apply your plugin to a Gradle project.
This is where custom functionality gets added to the Gradle project.

#### gradle-plugins properties

_Allows Gradle to identify your entry point._
```src/main/resources/META-INF/gradle-plugins/demo.properties```

The property file's filename represents your _plugin id_, used to apply the plugin, e.g. `plugins { id: 'demo' }`.
It contains a single `implementation-class` property, set plugin class' full name, in this case `com.praqma.demo.DemoPlugin`.  

### GreetingModule

_Showcases a way to keep your plugin organized._
```src/main/groovy/com/praqma/demo/greeting/GreetingModule.groovy```

A simple class used to separate 'greeting' logic, adds a task to a given Gradle project.

#### GreetingExtension
 
_Showcases how to add a configurable property to your Gradle project._
```src/main/groovy/com/praqma/demo/greeting/GreetingExtension.groovy```

Used as an extension (configured in `GreetingModule`) containing a single property (`alternativeGreeting`).
After applying the plugin, users can set `greeting.alternativeGreeting` in their `build.gradle` file.

### GreetingModuleTest

_Showcases how to run functional tests for your Grade plugin._
```src/test/groovy/com/praqma/demo/greeting/GreetingModuleTest.groovy```

Uses the [Gradle TestKit](https://docs.gradle.org/3.5/userguide/test_kit.html) to run functional tests.
Contains two tests which test task in the _GreetingModule_.

## Useful resources

[gradle.org - Writing Custom Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html)
[gradle.org - The Gradle TestKit](https://docs.gradle.org/3.5/userguide/test_kit.html)
[gradle.org - Writing Custom Tasks](https://docs.gradle.org/0.9.1/userguide/custom_tasks.html)
[mrhaki.blogspot.se - Gradle Goodness: Define a short plugin id for custom plugins](http://mrhaki.blogspot.se/2010/09/gradle-goodness-define-short-plugin-id.html)
