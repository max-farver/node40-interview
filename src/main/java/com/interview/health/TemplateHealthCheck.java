package com.interview.health;

import com.codahale.metrics.health.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        if (!template.equals("Hello! How are you today?")) {
            return Result.unhealthy("template not loaded correctly");
        }
        return Result.healthy();
    }
}
