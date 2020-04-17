package com.funesoft.repository;

import com.funesoft.model.Estado;
import com.funesoft.model.MotivoBaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoBajaRepository extends JpaRepository<MotivoBaja, Integer> {
}
