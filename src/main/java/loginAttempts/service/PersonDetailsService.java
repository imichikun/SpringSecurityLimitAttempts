package loginAttempts.service;

import jakarta.transaction.Transactional;
import loginAttempts.model.Person;
import loginAttempts.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class PersonDetailsService implements UserDetailsService {
    private final PersonService personService;

    @Autowired
    public PersonDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> possiblePerson = personService.getPersonByUsername(username);
        if (possiblePerson.isEmpty())
            throw new UsernameNotFoundException("Username is not exist");

        return new PersonDetails(possiblePerson.get());
    }
}