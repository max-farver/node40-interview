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
}