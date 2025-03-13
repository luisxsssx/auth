package api.auth.services;

import api.auth.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerUser(UserModel userModel) {
        String sql = "CALL sp_register_user(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getPassword(),
                userModel.getEmail(),
                "USER",
                "ACTIVE"
        );
    }

    public void deleteUser(Integer id) {
        String sql = "CALL sp_delete_user(?)";
        jdbcTemplate.update(sql, id);
    }
}