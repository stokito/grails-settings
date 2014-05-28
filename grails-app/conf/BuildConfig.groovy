grails.project.repos.default = 'bintray-stokito-maven-grails-settings'
grails.project.repos.'bintray-stokito-maven-grails-settings'.url = 'https://api.bintray.com/maven/stokito/maven/grails-settings'
grails.project.repos.'bintray-stokito-maven-grails-settings'.type = 'maven'
grails.project.repos.'bintray-stokito-maven-grails-settings'.portal = 'stokitoBintray'

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
    repositories {
        grailsCentral()
        mavenCentral()
    }
    dependencies {
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.2.1",
                ":rest-client-builder:1.0.3") {
            export = false
        }
        runtime ":hibernate:$grailsVersion"
        runtime ':cache:1.1.1'
        runtime ":resources:1.2.8"
        runtime ":spring-security-core:1.2.7.3"
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
        test ':fixtures:1.2'
        test ':build-test-data:2.1.2'
    }
}
