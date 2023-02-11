package loginAttempts.service;

import jakarta.transaction.Transactional;
import loginAttempts.model.Person;
import loginAttempts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    public static final int MAX_FAILED_ATTEMPTS = 3;
//    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;  // 24 hours in mls
    private static final long LOCK_TIME_DURATION = 3 * 60 * 1000;  // 3 minutes for testing purpose

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getPersonByUsername(String username){
        return personRepository.findByUsername(username);
    }
                        // далее 3 метода для удобства управления количеством попыток входа и блокировки человека
    public void increaseFailedAttempts(Person person){
        int newCountOfFailedAttempts = person.getLoginAttempt() + 1;
        personRepository.updatePersonLoginAttempts(newCountOfFailedAttempts, person.getUsername());
    }

    public void resetFailedAttempts(String username){
        personRepository.updatePersonLoginAttempts(0, username);
    }

    public void lockPerson(Person person){
        person.setAccountNonLocked(false);
        person.setLockedTime(LocalDateTime.now());
        personRepository.save(person);
    }

    public boolean unlockPersonAccount(Person person){
        LocalDateTime personLockedTime = person.getLockedTime();
        long lockedTimeMillis = personLockedTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(); // в милисек
        long currentTime = System.currentTimeMillis();

        if (lockedTimeMillis + LOCK_TIME_DURATION < currentTime){

            person.setAccountNonLocked(true);
            person.setLoginAttempt(0);
            person.setLockedTime(null);
            personRepository.save(person);

            return true;
        }
        return false;
    }
}