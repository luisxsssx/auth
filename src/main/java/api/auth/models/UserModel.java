package api.auth.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}