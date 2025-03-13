package api.auth.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    public Integer id;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public Role role;
    public Status status;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;

    public UserModel(String first_name, String last_name, String password, String email, String role) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.password = password;
        this.email = email;
        this.role = role != null ? Role.valueOf(role) : Role.USER;
        this.status = Status.ACTIVE;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
}