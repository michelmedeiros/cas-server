package org.cas.web.flow;

import lombok.val;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;


@EnableConfigurationProperties(CasConfigurationProperties.class)
@Configuration("SignupWebflowConfigurer")
public class SignupWebflowConfigurer extends AbstractCasWebflowConfigurer {

    private static final String SIGNUP_ACTION = "signupAction";
    private static final String SIGNUP_VIEW = "signupView";

    public SignupWebflowConfigurer(FlowBuilderServices flowBuilderServices,
                                   FlowDefinitionRegistry flowDefinitionRegistry,
                                   ApplicationContext applicationContext,
                                   CasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
     }

    @Override
    protected void doInitialize() {
        final Flow flow = super.getLoginFlow();
        if (flow != null) {
            createSignupActionActionState(flow);
        }
    }

    private void createSignupActionActionState(Flow flow) {
        final ActionState actionState = createActionState(flow, CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM,
                createEvaluateAction(CasWebflowConstants.TRANSITION_ID_SUCCESS));
        final ViewState viewState = createViewState(flow, CasWebflowConstants.TRANSITION_ID_SUCCESS, SIGNUP_VIEW);
        createTransitionForState(viewState, CasWebflowConstants.TRANSITION_ID_SUBMIT, SIGNUP_ACTION);
        setStartState(flow, actionState);
    }
}

