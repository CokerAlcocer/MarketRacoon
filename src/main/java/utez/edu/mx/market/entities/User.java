package utez.edu.mx.market.entities;

import java.util.Date;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private String recovery;
    private Date recoveryExpireDate;

    public User() {
    }

    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String recovery, Date recoveryExpireDate) {
        this.username = username;
        this.recovery = recovery;
        this.recoveryExpireDate = recoveryExpireDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public Date getRecoveryExpireDate() {
        return recoveryExpireDate;
    }

    public void setRecoveryExpireDate(Date recoveryExpireDate) {
        this.recoveryExpireDate = recoveryExpireDate;
    }
}
