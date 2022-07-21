package com.khmal.hospital.service;

import com.khmal.hospital.dto.RoleDto;

public interface RoleService {
  void  addRole(RoleDto roleDto, String roleName);
  void  addRole(RoleDto roleDto);
}
