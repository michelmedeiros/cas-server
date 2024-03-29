package org.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.cas.web.flow.SignupAction;
import org.cas.web.flow.SignupWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

@Configuration("signupConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class SignupConfiguration implements CasWebflowExecutionPlanConfigurer {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @ConditionalOnMissingBean(name = "signupWebflowConfigurer")
    @Bean
    public CasWebflowConfigurer signaupWebflowConfigurer() {
        return new SignupWebflowConfigurer(flowBuilderServices,
                    loginFlowDefinitionRegistry, applicationContext, casProperties);
    }

    @Bean
    @RefreshScope
    public Action initializeLoginAction() {
        return new SignupAction();
    }

    @ConditionalOnMissingBean(name = "signAction")
    @Bean
    @RefreshScope
    public Action signAction() {
        return new SignupAction();
    }

    @Override
    public void configureWebflowExecutionPlan(final CasWebflowExecutionPlan plan) {
        plan.registerWebflowConfigurer(signaupWebflowConfigurer());
    }
}