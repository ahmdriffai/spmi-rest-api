package id.ac.fksp.spmi.service;

import id.ac.fksp.spmi.payload.request.RoleSaveRequest;
import id.ac.fksp.spmi.payload.response.RoleResponse;

public interface RoleService {
    RoleResponse saveRole(RoleSaveRequest request);
}
