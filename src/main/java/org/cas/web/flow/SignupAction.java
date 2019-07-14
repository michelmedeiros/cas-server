package org.cas.web.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

@Slf4j
@RequiredArgsConstructor
public class SignupAction extends AbstractAction {

    @Override
    protected Event doExecute(final RequestContext requestContext) {
        final String username = requestContext.getRequestParameters().get("username");
        final String password = requestContext.getRequestParameters().get("password");
        log.debug("SignupAction - username {}", username);
        log.debug("SignupAction - password {}", password);
        WebUtils.putCredential(requestContext, new UsernamePasswordCredential(username, password));
        return success();
    }
}