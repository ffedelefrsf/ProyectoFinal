package com.funesoft.repository;

import com.funesoft.model.Comprobante;
import com.funesoft.model.ImpresionComprobante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpresionComprobanteRepository extends JpaRepository<ImpresionComprobante, Integer> {
}
