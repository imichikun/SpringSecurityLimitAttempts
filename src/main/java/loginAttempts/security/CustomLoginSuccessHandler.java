package loginAttempts.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loginAttempts.model.Person;
import loginAttempts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private PersonService personService;

    @Autowired
    public CustomLoginSuccessHandler(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException { super.onAuthenticationSuccess(request, response, authentication);

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        if (person.getLoginAttempt() > 0) {
            personService.resetFailedAttempts(person.getUsername());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}