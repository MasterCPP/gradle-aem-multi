![Cognifide logo](docs/cognifide-logo.png)

[![Gradle Status](https://gradleupdate.appspot.com/Cognifide/gradle-aem-example/status.svg)](https://gradleupdate.appspot.com/Cognifide/gradle-aem-example/status)
[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/Cognifide/gradle-aem-example.svg?label=License)](http://www.apache.org/licenses/)

# Gradle AEM Example

<br>
<p align="center">
  <img src="docs/logo.png" alt="Gradle AEM Plugin Logo"/>
</p>
<br>


## Description

This project should be used while starting new project based on AEM.

Documentation for AEM plugin is available in project [Gradle AEM Plugin](https://github.com/Cognifide/gradle-aem-plugin).

## Table of Contents

* [Environment](#environment)
* [Structure](#structure)
* [Features](#features)
* [Forking](#forking)
* [Building](#building)
* [Tips &amp; tricks](#tips--tricks)
* [Attaching debugger](#attaching-debugger)
* [Extending build](#extending-build)

## Environment

Tested on:

* Java 1.8
* Gradle 4.4
* Adobe AEM 6.3

## Structure

Project is divided into subpackages (designed with reinstallabilty on production environments in mind):

* *root* - non-reinstallable complete all-in-one package with application and contents.
* *app* - reinstallable assembly package that contains all sub-parts of application:
    * *common* - OSGi bundle with integrations of libraries needed by other bundles and AEM extensions (dialogs, form controls etc).
    * *core* - OSGi bundle with core business logic and AEM components implementation.
    * *config* - OSGi services configuration.
    * *design* - AEM design configuration responsible for look & feel of AEM pages.
* *content* - non-reinstallable assembly package that contains all type of contents listed below:
    * *init* - contains all JCR content needed initially to rollout new site(s) using installed application.
    * *demo* - consists of extra AEM pages that presents features of application (useful for testing).

## Features

* Integrated [Fork Plugin](https://github.com/neva-dev/gradle-fork-plugin) / project generator based on live archetypes.
* Interoperable Java and [Kotlin](https://kotlinlang.org) code examples.
* Integrated popular UI build toolkit: [NodeJS](https://nodejs.org/en/), [Yarn](https://yarnpkg.com) and [Webpack](https://webpack.github.io/) for advanced assets bundling (modular JS, ECMAScript6 transpilation, SCSS compilation with [PostCSS](http://postcss.org), code style checks etc).
* Integrated SCSS compilation on AEM side using [AEM Sass Compiler](https://github.com/mickleroy/aem-sass-compiler).
* Integrated popular AEM testing toolkit: [wcm.io Testing](http://wcm.io/testing).
* Example configuration for embedding OSGi bundles into CRX package (`aemInstall`, `aemEmbed`).
* Example configuration for installing dependant CRX packages on AEM before application deployment (`aemSatisfy`).

## Forking

This project can be easily copied and customized automatically to target application related names by using [Gradle Fork Plugin](https://github.com/neva-dev/gradle-fork-plugin).

1. Run command:

    ```bash
    git clone git@github.com:Cognifide/gradle-aem-example.git && cd gradle-aem-example && sh gradlew fork -i -Pfork.interactive=true
    ```

2. Specify configuration of Maven's like archetype properties:

    ![Fork Props Dialog](docs/fork-props-dialog.png)
    
3. Wait until project is generated at configured target path.

## Building

1. Install Gradle
    * Use bundled wrapper (always use command `sh gradlew` instead of `gradle`). It will be downloaded automatically (recommended).
    * Use standalone from [here](https://docs.gradle.org/current/userguide/installation.html).
2. Run `gradle idea` or `gradle eclipse` to generate configuration for your favourite IDE.
3. Build application and deploy:
    * Assembly packages:
        * `gradle` <=> `:aemSatisfy :aemBuild :aemAwait`,
        * `gradle :app:aemBuild`,
        * `gradle :content:aemBuild`.
    * Single package:
        * `gradle :app:core:aemBuild`,
        * `gradle :app:common:aemBuild`,
        * `gradle :app:config:aemBuild`,
        * `gradle :app:design:aemBuild`,
        * `gradle :content:init:aemBuild`,
        * `gradle :content:demo:aemBuild`.

## Tips & tricks

* To speed up build, use [build cache](https://docs.gradle.org/current/userguide/build_cache.html) by appending to command `--build-cache` option.
* To run some task only for subproject, use project path as a prefix, for instance: `sh gradlew :app:design:aemSync`.
* Declare bundle dependencies available on AEM (like Maven's provided scope) in root project *build.gradle* in section `plugins.withId 'biz.aQute.bnd.builder'` to avoid defining them separately for each subproject.
* According to [recommendations](https://docs.gradle.org/current/userguide/gradle_daemon.html), Gradle daemon should be: 
    * enabled on development environments,
    * disabled on continuous integration environments.
* If build caches to much, you could try with `--rerun-tasks` option. See this [link](https://docs.gradle.org/current/userguide/gradle_command_line.html) for more details.
* To see more descriptive details about errors, you could use `-i`, `--stacktrace`, `--debug` options.
* To skip tests or any other task by name use `-x test`


## Attaching debugger

1. Execute build with options `-Dorg.gradle.debug=true --no-daemon`, it will suspend,
2. Attach debugger on port 5005,
3. Suspension will be released and build should stop at breakpoint.


## Extending build

For defining new tasks directly in build see:

 * [Build Script Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html)
 * [More about Tasks](https://docs.gradle.org/current/userguide/more_about_tasks.html)

The easiest way to implement custom plugins and use them in project is a technique related with _buildSrc/_ directory.
For more details please read [documentation](https://docs.gradle.org/current/userguide/organizing_build_logic.html#sec:build_sources).
