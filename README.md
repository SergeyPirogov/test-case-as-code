# Test Cases as a Code

## Description
This plugin is used for exporting test cases from [Intellij IDEA](https://www.jetbrains.com/idea/) source code to [TestRail](https://www.gurock.com/testrail)

## Installation
1. Build the project: `./gradlew clean build -x test`
2. Install the plugin: `Preferences -> Plugins -> Install Plugin from Disk`
select `test-rail-plugin/build/distributions/test-rail-plugin.zip`

## Configuration
You must provide your TestRail credentials. 
In order to do that please open `test-rail/src/main/resources/config.properties` and fill in necessary properties.
It's required to configure the plugin settings as well: `Preferences -> Tools -> TestRail exporter plugin`