package qaops.automation.api.dominio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {
    @JsonAlias({"first_name"})
    private String name;
    @JsonAlias({"last_name"})
    private String lastName;
    private String job;
    private String email;

    public Usuario() {}

    public Usuario(String name, String job, String email) {
        this.name = name;
        this.job = job;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }
}
