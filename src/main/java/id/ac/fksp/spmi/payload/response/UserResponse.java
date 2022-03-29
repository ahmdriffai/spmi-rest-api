package id.ac.fksp.spmi.payload.response;

import id.ac.fksp.spmi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Collection<Role> roles = new ArrayList<>();
}
