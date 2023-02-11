package loginAttempts.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "person2_attempts")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "log_attempt")
    private int loginAttempt;

    @Column(name = "non_locked")
    private Boolean accountNonLocked;

    @Column(name = "role")
    private String role;

    @Column(name = "locked_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lockedTime;

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getLockedTime() {
        return lockedTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLockedTime(LocalDateTime lockedTime) {
        this.lockedTime = lockedTime;
    }
}