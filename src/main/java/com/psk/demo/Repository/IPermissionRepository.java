package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.entity.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Integer> {
}
