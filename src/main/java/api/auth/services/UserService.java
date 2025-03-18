package api.auth.services;

import api.auth.models.SearchRequest;
import api.auth.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public List<UserModel> getUsers(SearchRequest request) {
        try {
            if (!"id".equals(request.getType()) && !"all".equals(request.getType())) {
                throw new IllegalArgumentException("Type must be 'id' or 'all'");
            }
            if ("id".equals(request.getType()) && request.getValue() != null) {
                try {
                    Integer.parseInt(request.getValue());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Value must be a valid integer for type 'id'");
                }
            }

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withFunctionName("sp_get_users")
                    .returningResultSet("return", BeanPropertyRowMapper.newInstance(UserModel.class));

            Map<String, Object> result = simpleJdbcCall.execute(
                    Map.of("p_type", request.getType(),
                            "p_value", request.getValue() != null ? request.getValue() : null)
            );

            @SuppressWarnings("unchecked")
            List<UserModel> users = (List<UserModel>) result.get("return");
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        }
    }

    // Get users with json
    public List<UserModel> getUsersJson() throws Exception {
        String query = "SELECT get_users_json()";
        String jsonResult = jdbcTemplate.queryForObject(query, String.class);
        return objectMapper.readValue(jsonResult, objectMapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
    }

    // Get users with type
    public Object getUserType(String type, String value) throws Exception {
        String query = "SELECT get_users_json(?, ?)";
        String jsonResult = jdbcTemplate.queryForObject(query, new Object[]{type, value}, String.class);
        if("all".equals(type)) {
            return objectMapper.readValue(jsonResult, objectMapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));
        } else if("id".equals(type)) {
            return objectMapper.readValue(jsonResult, UserModel.class);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public void registerUser(UserModel userModel) {
        try {
            String sql = "CALL sp_register_user(?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,
                    userModel.getId() != null ? userModel.getId() : null,
                    userModel.getFirstName(),
                    userModel.getLastName(),
                    userModel.getPassword(),
                    userModel.getEmail(),
                    userModel.getRole() != null ? userModel.getRole().name() : "USER",
                    userModel.getStatus() != null ? userModel.getStatus().name() : "ACTIVE"
            );
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }

    public void deleteUser(Integer id) {
        try {
            String sql = "CALL sp_delete_user(?)";
            int rowsAffected = jdbcTemplate.update(sql, id);
            if (rowsAffected == 0) {
                throw new RuntimeException("User with ID " + id + " not found or not deleted");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }
}