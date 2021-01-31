package com.interview.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.TEXT_PLAIN)
public class HelloWorldResource {
    @JsonProperty
    private String template;

    public HelloWorldResource() { this.template = "Hello world!"; }

    public HelloWorldResource(String template) {
        this.template = template;
    }


    @GET
    @Timed
    public String sayHello() {
        return template;
    }
}