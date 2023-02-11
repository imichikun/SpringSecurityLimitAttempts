package loginAttempts.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loginAttempts.model.Person;
import loginAttempts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private PersonService personService;

    @Autowired
    public CustomLoginFailureHandler(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        Person person = personService.getPersonByUsername(username).get();

        if (person != null){
            if (person.getAccountNonLocked() == true){
                if (person.getLoginAttempt() < personService.MAX_FAILED_ATTEMPTS -1) {
                    personService.increaseFailedAttempts(person);
                } else {
                    personService.lockPerson(person);
                    exception = new LockedException("Your account has been temporarily locked for 24 hours");
                }
            } else if (person.getAccountNonLocked() == false){
                if (personService.unlockPersonAccount(person)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again");
                }
            }
        }

        super.setDefaultFailureUrl("/auth/login");
        super.onAuthenticationFailure(request, response, exception);
    }
}