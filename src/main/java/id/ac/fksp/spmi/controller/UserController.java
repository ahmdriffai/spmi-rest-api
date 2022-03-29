package id.ac.fksp.spmi.controller;

import id.ac.fksp.spmi.payload.request.UserAddRoleRequest;
import id.ac.fksp.spmi.payload.request.UserListRequest;
import id.ac.fksp.spmi.payload.request.UserSaveRequest;
import id.ac.fksp.spmi.payload.response.ApiResponse;
import id.ac.fksp.spmi.payload.response.UserResponse;
import id.ac.fksp.spmi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> listUser(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){

        UserListRequest userListRequest = new UserListRequest(page,size);
        List<UserResponse> userResponses = userService.listUser(userListRequest);

        return ResponseEntity.ok(new ApiResponse<List<UserResponse>>(
                true,
                "list user",
                userResponses
        ));

    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserSaveRequest request){
        UserResponse userResponse = userService.saveUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(new ApiResponse<UserResponse>(
                true,
                "success create user",
                userResponse
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId){
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok(new ApiResponse<UserResponse>(
           true,
           "succes get user by id : " + userId,
           userResponse
        ));
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRoleUser(@RequestBody @Valid UserAddRoleRequest request){
        userService.addRoleUser(request);
        return ResponseEntity.ok(new ApiResponse<String>(
                true,
                "success",
                String.format("Berhasil menambahkan role %s ke user %s", request.getRoleName(),request.getUsername())
        ));
    }
}
