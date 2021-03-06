plugins {
    id("com.neva.fork")
    id("com.cognifide.aem.common")
}

apply(from = "gradle/fork/fork.gradle.kts")
apply(from = "gradle/fork/props.gradle.kts")
apply(from = "gradle/common.gradle.kts")

description = "Example"
defaultTasks("develop")

aem {
    tasks {
        registerSequence("develop", {
            description = "Builds and deploys AEM application to instances, cleans environment then runs all tests"
        }) {
            if (!prop.flag("setup.skip")) {
                dependsOn(":aem:instanceSetup")
            }
            dependsOn(":aem:assembly:full:packageDeploy")
            if (!prop.flag("migration.skip")) {
                dependsOn(":aem:migration:packageDeploy")
            }
            dependsOn(
                    ":aem:environmentReload",
                    ":aem:environmentAwait"
            )
            if (!prop.flag("test.skip")) {
                dependsOn(
                        ":test:integration:integrationTest",
                        ":test:functional:generateReport",
                        ":test:performance:lighthouseRun"
                )
            }
        }
    }
}
