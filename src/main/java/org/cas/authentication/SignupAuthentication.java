package org.cas.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apereo.cas.authentication.*;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionCompletedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class SignupAuthentication implements AuthenticationTransactionManager {

    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationTransactionManager handle(AuthenticationTransaction authenticationTransaction, AuthenticationResultBuilder authenticationResultBuilder) throws AuthenticationException {
        if (!authenticationTransaction.getCredentials().isEmpty()) {
            final Authentication authentication = this.authenticationManager.authenticate(authenticationTransaction);
            log.trace("Successful authentication; Collecting authentication result [{}]", authentication);
            publishEvent(new CasAuthenticationTransactionCompletedEvent(this, authentication));
            authenticationResultBuilder.collect(authentication);
        } else {
            log.debug("Transaction ignored since there are no credentials to authenticate");
        }
        return this;
    }

    private void publishEvent(final ApplicationEvent event) {
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(event);
        }
    }
}
