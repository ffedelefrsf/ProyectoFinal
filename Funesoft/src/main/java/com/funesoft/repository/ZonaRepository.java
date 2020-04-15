package com.funesoft.repository;

import com.funesoft.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
    Optional<List<Zona>> findByNroZona(Integer nroZona);
    Optional<List<Zona>> findByNombre(String nombre);
    Optional<List<Zona>> findByNroZonaAndNombre(Integer nroZona, String nombre);
}
