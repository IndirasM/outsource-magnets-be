package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.Entity.Office;

public interface IOfficeRepository extends JpaRepository<Office, Integer> {
}
