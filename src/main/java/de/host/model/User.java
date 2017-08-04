package de.host.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by yevheniia on 04.08.17
 */
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String confirmationPassword;

    public User(String name, String email, String password, String confirmationPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
    }

    public User() {

    }

    public static User random() {
        String random = UUID.randomUUID().toString();
        return new User(random + "Name", random + "-email@test.com", random);
    }

    public User(String name, String email, String password) {
        this(name, email, password, password);
    }

    public User withId(Integer id) {
        this.setId(id);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public Map<String, ?> toRequestParams() {
        return new HashMap<String, String>() {{
            Optional.ofNullable(name).map(name -> put("user.name", name));
            Optional.ofNullable(email).map(email -> put("user.email", email));
            Optional.ofNullable(password).map(email -> put("user.password", password));
            Optional.ofNullable(confirmationPassword).map(email -> put("confirmationPassword", confirmationPassword));
        }};
    }

    private void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Optional.ofNullable(name).filter(n -> n.equals(user.name)).isPresent() &&
                Optional.ofNullable(email).filter(e -> e.equals(user.email)).isPresent() &&
                Optional.ofNullable(password).filter(p -> p.equals(user.password)).isPresent();
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User" + (id == null ? "" : ("(" + id + ")")) +
                "{name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmationPassword='" + confirmationPassword + '\'' +
                '}';
    }
}
