package com.interview.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.OptionalInt;

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

    @GET
    @Timed
    @Path("/format")
    public String variableHello(@QueryParam("reverse") @DefaultValue("false") boolean reverse,
                                @QueryParam("scream") @DefaultValue("false") boolean scream,
                                @QueryParam("chopped") @DefaultValue("0") int charsChopped) {
        String tempGreeting = template;

        if (charsChopped > 0 && charsChopped < template.length()) {
            tempGreeting = tempGreeting.substring(0, tempGreeting.length() - charsChopped);
        }
        else if (charsChopped > 0) {
            return "";
        }

        if (reverse) {
            tempGreeting = new StringBuffer(tempGreeting).reverse().toString();
        }

        if (scream) {
            tempGreeting = tempGreeting.toUpperCase();
        }

        return tempGreeting;
    }
}