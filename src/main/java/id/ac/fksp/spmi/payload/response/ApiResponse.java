package id.ac.fksp.spmi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ApiResponse<T>{
    private Boolean success;
    private String message;
    private T data;
}
