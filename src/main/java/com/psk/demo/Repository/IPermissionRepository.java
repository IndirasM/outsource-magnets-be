package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.Entity.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Integer> {
}
