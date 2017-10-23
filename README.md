[![Build Status](https://travis-ci.org/wkramer/openda.svg?branch=master)](https://travis-ci.org/wkramer/openda)

OpenDA with Gradle and Pride
----------------------------

**Note:** This is an experimental version of OpenDA for testing a Gradle build system and module management with Pride.

These files are part of the OpenDA software. For more information see our website at
http://www.openda.org

## Gradle

[Gradle](https://gradle.org) is an automated build tool mainly for compiling, testing and distributing Java projects. 
It is written in Groovy (a Java dialect) and runs on top of the java virtual machine.

 - Contains a lot of functionality out of the box
 - Needs minimial configuration if your project layout follows the standard:
    - src/main/java
    - src/main/resources
    - src/test/java
    - src/test/resources
 - The configuration contains less markup compared to the xml configuration of Ant and Maven. 
 - Java programmers can easily extend it by writting Groovy (or Java).
 - IntellIJ supports Gradle, you can open the `build.gradle` (instead of a .ipr)
 - Supported by Eclipse (did not test this)
 - Support for [Maven repositories](https://mvnrepository.com) for automatic downloading dependencies.
 - Declare your dependencies per module and Gradle resolves any version conflicts

Supports [multi-projects](https://docs.gradle.org/current/userguide/multi_project_builds.html) which can be divided over seperate repositories. A multi project contains at least one root `build.gradle` script and a `settings.gradle` file for including sub-projects. The sub-projects most likely have their own `build.gradle` sripts listing dependencies and custom tasks.

### Automatic including model-PROJECT 

When you apply the Gradle `application` plugin, it adds tasks for create an application start script (like oda_run.sh), and creating a zip or tar.gz distribution. I have also added the functionality that automatically adds all sub-project named model-PROJECT (i.e. model wrappers) to the distribution. 

### Castor task

I have already written a Gradle task for creating the java classes from the XML Schema Definitions. 
You just need to configure a JavaExec task with the main class file, arguments and workdirectory. You can specify what is the input and output directory (or fileset) and Gradle automatically determines when it needs to rebuild (or not). The nice thing is that you can specify dependencies to run the Castor task (it needs `castor.jar`, `xalan.jar` and `xerxes.jar`), but these dependencies are not added to the distribution. 

## Split the OpenDA Git repository

On https://github.com/wkramer there are three test repositories I create by splitting the public repository in three parts:

- https://github.com/wkramer/openda (core, observer, algoritms and models)
- https://github.com/wkramer/openda-application (gui)
- https://github.com/wkramer/model-openfoam (openfoam wrapper)
- https://github.com/wkramer/repo (project Maven repos)

Only the history for each part is preserved (using `git filter-branch --tree-filter` or `git filter-branch --subdirectory-filter`) to keep the repositories size small. 

I split all modules with Castor generated Java code in to parts. For instance, `core` is split in `core-castor` and `core`, where the last has a dependency on the first. The castor task puts the generated Java source code in `src/main/java`. 

## Third party dependencies 

Most third party dependencies are available online in Maven repositories. The exceptions are: `sgt_v30.jar` (plots in the GUI), `alloy.jar` (look-and-feel used by the GUI, replaced with `jgoodies`), `DATools_castor.jar` and `DUE_subset.jar` (borrowed code from the Uncertainty Engine). For now I have created a fourth git repositories `repo` which contains a Maven repositories for just these three dependencies.

## Pride

When you split a project over a number of repositories, it becomes tedious to clone or checkout each sub-project and manualy edit the `settings.gradle`. [Pride](https://github.com/prezi/pride) is a small open source tool developed by Prezi (the presentations). It helps you manage the local development of large modular applications built with Gradle. It consists of a command-line tool to manage your prides of modules, and a Gradle plugin to add some extra functionality to the Gradle projects that resolves your modules to local projects.

### Comments on Pride

- You can not use the Gradle `idea` plugin of `eclipse` plugin for creating IntellIJ or Eclipse project files. In IntellIJ you can open the `build.gradle` which works just fine. Eclipse??
- We can easily do without Pride. Editing the `settings.gradle` to include your own model wrapper is a lot easier then adding it to the current Ant build.xml file. Manually cloning a number of git repositories is also not the biggest issue.


## Try it out yourself. 

Install Prezi pride (follow the instructions on https://github.com/prezi/pride#get-pride)

    $ pride config repo.base.url https://github.com/wkramer

Create root directory for OpenDA project

    $ mkdir openda-with-pride
    $ cd openda-with-pride

Initialize Pride to download Gradle and create the root `build.gradle` and `settings.gradle` files:

    $ pride init

Add required sub-projects:

    $ pride add openda
    $ pride add openda-application
    $ pride add repo

Now start IntelIJ and open the root `build.gradle`.

## Additional remarks

When developing a model wrapper you add openda as an external dependency:

    dependencies{
        compile group: "org.openda", name: "core", version: "3.0"
        compile group: "org.openda", name: "observers", version: "3.0"
    }

If we publish the OpenDA core libraries (jar files) on a Maven repository, Gradle can download these automatically. No need to checkout the OpenDA core repository. But if a local workdirectory containing the OpenDA core is present, Gradle will automatically use that instead of obtaining the libraries.

We can host a [maven repository on github](https://github.com/github/maven-plugins#readme).

## Todo

- OpenDaTestSupport needs to be more flexible. Place the `testRunDir` in module directory insted of the root project dir?  I have changed some stuff already, but there are still some test failing.
- What to do with native Costa code? CMake? Commit binaries and copy to `lib`. A number of tests are failing due to the missing native Costa library. 
- There is an issue with the castor generated code needing a regexp implementation.
- What to do native model code like (EFDC)? 
- Create repository for `DATools_castor.jar` and `DUE_subset.jar` source code?
- Task for examples
- Run script


