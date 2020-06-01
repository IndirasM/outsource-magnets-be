package com.psk.demo.Repository;

import com.psk.demo.Entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILimitRepository extends JpaRepository<Limit, Long> {
    Limit findByIsGlobalEquals(boolean isGlobal);
    void deleteByLimitIdNot(Long id);
}
