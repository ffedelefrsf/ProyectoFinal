package com.funesoft.dto;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author faust
 */

@Getter
@Setter
@NoArgsConstructor
public class SocioDTO{

    @NotNull
    private Integer id;
    @NotNull
    @Max(99999999)
    private Integer dni;
    @NotNull
    private String apellido;
    @NotNull
    private String nombre;
    @NotNull
    private String direccion;
    @NotNull
    @Size(min = 5, max = 10)
    private String telefono;
    private String email;
    @NotNull
    private String sexo;
    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date fechaNacimiento;
    @NotNull
    private Integer usuarioAlta;
    @NotNull
    private Double saldo;
    @NotNull
    private Integer idTarifa;
    @NotNull
    private Integer idZona;
    @NotNull
    private Integer idLocalidad;
    @NotNull
    private Integer idObraSocial;
    @NotNull
    private Integer idEnfermedad;
    @Nullable
    private List<AdherenteDTO> adherentesAltaDTO;

    public SocioDTO(Integer id, Integer dni, String apellido, String nombre, String direccion, String telefono, String email, String sexo, Date fechaNacimiento, Integer usuarioAlta, Double saldo, Integer idTarifa, Integer idZona, Integer idLocalidad, Integer idObraSocial, Integer idEnfermedad, List<AdherenteDTO> adherenteAltaDTO) {
        this.id = id;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.usuarioAlta = usuarioAlta;
        this.saldo = saldo;
        this.idTarifa = idTarifa;
        this.idZona = idZona;
        this.idLocalidad = idLocalidad;
        this.idObraSocial = idObraSocial;
        this.idEnfermedad = idEnfermedad;
        this.adherentesAltaDTO = adherenteAltaDTO;
    }

    @Override
    public String toString() {
        return "SocioDTO{" + "id=" + id + ", dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + ", usuarioAlta=" + usuarioAlta + ", saldo=" + saldo + ", idTarifa=" + idTarifa + ", idZona=" + idZona + ", idLocalidad=" + idLocalidad + ", idObraSocial=" + idObraSocial + ", idEnfermedad=" + idEnfermedad + ", adherentesAltaDTO=" + adherentesAltaDTO + '}';
    }
    
}
