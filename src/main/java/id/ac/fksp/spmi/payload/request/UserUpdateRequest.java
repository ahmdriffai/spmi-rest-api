package id.ac.fksp.spmi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserUpdateRequest {
    private String username;
    private String email;
}
