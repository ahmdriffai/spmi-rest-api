package id.ac.fksp.spmi.service;

import id.ac.fksp.spmi.payload.request.UserAddRoleRequest;
import id.ac.fksp.spmi.payload.request.UserListRequest;
import id.ac.fksp.spmi.payload.request.UserSaveRequest;
import id.ac.fksp.spmi.payload.request.UserUpdateRequest;
import id.ac.fksp.spmi.payload.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserSaveRequest request);
    List<UserResponse> listUser(UserListRequest reques);
    UserResponse getUser(Long id);
    void addRoleUser(UserAddRoleRequest request );
    UserResponse updateUser(UserUpdateRequest request, String userId);
}
