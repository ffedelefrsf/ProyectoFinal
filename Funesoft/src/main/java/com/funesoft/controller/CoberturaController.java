package com.funesoft.controller;

import com.funesoft.dto.FechaCoberturaGetResponseDTO;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
    
    @Autowired
    private EnfermedadRepository enfermedadRepository;

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
    
    public FechaCoberturaGetResponseDTO calcularByFechaAndIdEnfermedad(Date fechaNacimientoAsociado, Integer idEnfermedad) {
        final FechaCoberturaGetResponseDTO fechaCoberturaGetResponseDTO = new FechaCoberturaGetResponseDTO();
        final Enfermedad enfermedad = enfermedadRepository.findById(idEnfermedad).get();
        fechaCoberturaGetResponseDTO.setFechaCobertura(calcular(getEdad(fechaNacimientoAsociado), enfermedad));
        return fechaCoberturaGetResponseDTO;
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
    
    private short getEdad(Date fechaNacimiento) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaNacimiento);
        final Integer year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH)+1, day = calendar.get(Calendar.DAY_OF_MONTH);
        final LocalDate fechaNacimientoAux = LocalDate.of (year, month, day);
        final LocalDate fechaActual = LocalDate.now();
        return (short) ChronoUnit.YEARS.between(fechaNacimientoAux, fechaActual);
    }

}
