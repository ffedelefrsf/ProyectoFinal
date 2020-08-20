package com.funesoft.controller;

import com.funesoft.model.Adherente;
import com.funesoft.model.CoberturaEdad;
import com.funesoft.model.CoberturaEnfermedad;
import com.funesoft.model.CoberturaPeso;
import com.funesoft.model.Enfermedad;
import com.funesoft.model.Socio;
import com.funesoft.repository.CoberturaEdadRepository;
import com.funesoft.repository.CoberturaEnfermedadRepository;
import com.funesoft.repository.CoberturaPesoRepository;
import com.funesoft.repository.EnfermedadRepository;
import com.funesoft.utilities.CualidadCoberturaEnum;
import com.ibm.icu.util.Calendar;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.stereotype.Controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class CoberturaController {
    
    private static final Integer CONSTANTE = 10;
    
    @Autowired
    private CoberturaPesoRepository coberturaPesoRepository;
    
    @Autowired
    private CoberturaEdadRepository coberturaEdadRepository;
    
    @Autowired
    private CoberturaEnfermedadRepository coberturaEnfermedadRepository;

    public Date calculoCobertura(Socio socio){
        //FALTA DESARROLLAR ESTA FUNCIÓN
        //CALCULA LA FECHA DE COBERTURA BASANDOSE EN LA FECHA DE NACIMIENTO y veremos sino pedimos alguna otra cosa.
        return calcular(socio.getEdad(), socio.getEnfermedad());
    }
    
    public Date calculoCobertura(Adherente adherente){
        //FALTA DESARROLLAR ESTA FUNCIÓN
        //CALCULA LA FECHA DE COBERTURA BASANDOSE EN LA FECHA DE NACIMIENTO y veremos sino pedimos alguna otra cosa.
        return calcular(adherente.getEdad(), adherente.getEnfermedad());
    }
    
    private Date calcular(Short edad, Enfermedad enfermedad){
        final LocalDate fechaActual = LocalDate.now();
        
        final CoberturaEnfermedad coberturaEnfermedad = coberturaEnfermedadRepository.findByEnfermedad(enfermedad);
        final CoberturaEdad coberturaEdad = coberturaEdadRepository.findByRangoEdad(edad);
        
        final CoberturaPeso coberturaPesoEnfermedad = coberturaPesoRepository.findByDescripcion(CualidadCoberturaEnum.ENFERMEDAD.getDescripcion());
        final CoberturaPeso coberturaPesoEdad = coberturaPesoRepository.findByDescripcion(CualidadCoberturaEnum.EDAD.getDescripcion());
        
        final Integer cantidadDiasEspera = Math.round(CONSTANTE * ((coberturaPesoEnfermedad.getPeso() * coberturaEnfermedad.getIndicador()) + (coberturaPesoEdad.getPeso() * coberturaEdad.getIndicador())));
        
        return Date.from(fechaActual.plusDays(cantidadDiasEspera).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
