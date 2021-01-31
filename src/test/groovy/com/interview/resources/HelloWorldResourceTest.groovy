package com.interview.resources

import com.interview.HelloWorldApplication
import com.interview.HelloWorldConfiguration
import io.dropwizard.configuration.YamlConfigurationFactory
import io.dropwizard.jackson.Jackson
import io.dropwizard.jersey.validation.Validators
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.util.Resources
import org.glassfish.jersey.client.JerseyClientBuilder
import spock.lang.Shared
import spock.lang.Specification

class HelloWorldResourceTest extends Specification {

    @Shared DropwizardTestSupport<HelloWorldConfiguration> SUPPORT =
        new DropwizardTestSupport<HelloWorldConfiguration>(HelloWorldApplication.class,
                ResourceHelpers.resourceFilePath("hello-world.yml")
        )

    def message = "Hello! How are you today?"
    def objectMapper = Jackson.newObjectMapper()
    def validator = Validators.newValidator()
    def factory = new YamlConfigurationFactory<>(HelloWorldResource.class, validator, objectMapper, "dw")


    def setupSpec() {
        SUPPORT.before()
    }

    def cleanupSpec() {
        SUPPORT.after()
    }

    def "sayHello with no yaml file returns default greeting"() {
        given: "new HelloWorldResource is created"
            def helloWorldResource = factory.build()

        when: "sayHello is called"
            def result = helloWorldResource.sayHello()

        then:
            result == "Hello world!"
    }

    def "sayHello with correct yaml file returns expected greeting"() {
        given: "new HelloWorldResource is created"
            def file = new File(Resources.getResource("hello-world.yml").toURI())
            def helloWorldResource = factory.build(file)

        when: "sayHello is called"
            def result = helloWorldResource.sayHello()

        then:
            result == message
    }

    def "GET root with no query parameters returns plain text"() {
        given:
        def client = new JerseyClientBuilder().build()

        when: "client sends GET to root"
        def result = client.target(String.format("http://localhost:%d/hello-world", SUPPORT.getLocalPort()))
                .request()
                .get(String)

        then: "client receives expected greeting"
        result == message
    }

    def "GET /format with no parameters returns default greeting"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to root"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == message
    }

    def "GET /format with reverse as true returns reversed greeting"() {
        given:
        def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
        def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                .queryParam("reverse", true)
                .request()
                .get(String)

        then: "client receives expected greeting"
        result == "?yadot uoy era woH !olleH"
    }

    def "GET /format with reverse as false returns default greeting"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .queryParam("reverse", false)
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == message
    }

    def "GET /format with scream as true returns template as uppercase"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .queryParam("scream", true)
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == message.toUpperCase()
    }

    def "GET /format with scream as false returns default template"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .queryParam("scream", false)
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == message
    }

    def "GET /format with charsChopped as 1 returns template with last character removed"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .queryParam("chopped", 1)
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == message.substring(0, message.length()-1)
    }

    def "GET /format with charsChopped as 100 returns empty string"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .queryParam("chopped", 100)
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == ""
    }

    def "GET /format with reverse as true and charsChopped as 1 returns template with last character removed then reversed"() {
        given:
            def client = new JerseyClientBuilder().build()

        when: "client sends GET to /format"
            def result = client.target(String.format("http://localhost:%d/hello-world/format", SUPPORT.getLocalPort()))
                    .queryParam("reverse", true)
                    .queryParam("chopped", 1)
                    .request()
                    .get(String)

        then: "client receives expected greeting"
            result == "yadot uoy era woH !olleH"
    }
}