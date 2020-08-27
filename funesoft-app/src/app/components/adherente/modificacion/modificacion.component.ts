import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { Adherente } from '@app/model/adherente';
import { Provincia } from '@app/model/provincia';
import { Zona } from '@app/model/zona';
import { Localidad } from '@app/model/localidad';
import { ObraSocial } from '@app/model/obraSocial';
import { Enfermedad } from '@app/model/enfermedad';
import { ProvinciaService } from '@app/services/provincia.service';
import { LocalidadService } from '@app/services/localidad.service';
import { ZonaService } from '@app/services/zona.service';
import { ObraSocialService } from '@app/services/obra-social.service';
import { SocioService } from '@app/services/socio.service';
import { AdherenteService } from '@app/services/adherente.service';
import { EnfermedadService } from '@app/services/enfermedad.service';
import { Socio } from '@app/model/socio';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-modificacion',
  templateUrl: './modificacion.component.html',
  styleUrls: ['./modificacion.component.scss']
})
export class ModificacionAdherenteComponent implements OnInit {

  adherente: Adherente;
  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  provincias: Provincia[];
  zonas: Zona[];
  localidadesNombres: String[];
  localidades: Localidad[];
  obrasSociales: ObraSocial[];
  enfermedades: Enfermedad[];
  socios: Socio[];
  editAdherenteForm: FormGroup;
  currentYear: number;
  currentMonth: number;
  currentDay: number;

  constructor(private router: Router,
    private provinciaService: ProvinciaService,
    private localidadService: LocalidadService,
    private zonaService: ZonaService,
    private obraSocialService: ObraSocialService,
    private socioService: SocioService,
    private adherenteService: AdherenteService,
    private enfermedadService: EnfermedadService,
    private formBuilder: FormBuilder,
    private location: Location) {

      this.adherente = JSON.parse(this.router.getCurrentNavigation().extras.state.adherente);
      // FECHA ACTUAL

      var currentDate :Date = new Date();
      this.currentYear = currentDate.getFullYear();
      this.currentMonth = currentDate.getMonth() + 1;
      this.currentDay = currentDate.getDate();

    }

  ngOnInit() {
    this.loading = true;

    this.editAdherenteForm = this.formBuilder.group({
      nombre: this.formBuilder.control(this.adherente.nombre, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      apellido: this.formBuilder.control(this.adherente.apellido, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      dni: this.formBuilder.control(this.adherente.dni, [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control(this.adherente.email, [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control(this.adherente.sexo, [Validators.required]),
      fechaNacimiento: this.formBuilder.control(this.adherente.fechaNacimiento, [Validators.required]),
      direccion: this.formBuilder.control(this.adherente.direccion, [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control(this.adherente.telefono, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control(this.adherente.zona.nombre, [Validators.required]),
      localidad: this.formBuilder.control(this.adherente.localidad.nombre, [Validators.required]),
      obraSocial: this.formBuilder.control(this.adherente.obraSocial.descripcion, [Validators.required]),
      fechaCobertura: this.formBuilder.control(this.adherente.fechaCobertura, [Validators.required]),
      enfermedad: this.formBuilder.control(this.adherente.enfermedad.descripcion, [Validators.required]),
      socio: this.formBuilder.control(this.adherente.socio.apellido + ', ' + this.adherente.socio.nombre, [Validators.required])
    });

    // const currentSocio: string = this.adherente.socio.apellido + ', ' + this.adherente.socio.nombre;
    // console.log(currentSocio);
    // this.editAdherenteForm.get('socio').setValue(currentSocio);
    var fechaNacimiento: Date = new Date(this.adherente.fechaNacimiento);
    var fechaCobertura: Date = new Date(this.adherente.fechaCobertura);
    var fechaNacimientoForDatePicker = {year: fechaNacimiento.getFullYear(), month: fechaNacimiento.getMonth()+1, day: fechaNacimiento.getDate()+1};
    var fechaCoberturaForDatePicker = {year: fechaCobertura.getFullYear(), month: fechaCobertura.getMonth()+1, day: fechaCobertura.getDate()+1};
    this.editAdherenteForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);
    this.editAdherenteForm.get('fechaNacimiento').setValue(fechaNacimientoForDatePicker);

    var provincia: Provincia = {};
    this.provinciaService.getProvincias(provincia).subscribe(
      provinciasResponse => {
        this.provincias = provinciasResponse.data;
        var localidad: Localidad = {provincia: {id: this.adherente.localidad.provincia.id}};
        this.localidadService.getNombresLocalidades(localidad).subscribe(
          localidadesNombresResponse => {
            this.localidadesNombres = localidadesNombresResponse.data;
            this.localidadService.getLocalidades(localidad).subscribe(
              localidadesResponse => {
                this.localidades = localidadesResponse.data;
                var zona: Zona = {};
                this.zonaService.getZonas(zona).subscribe(
                  zonaResponse => {
                    this.zonas = zonaResponse.data;
                    var obraSocial: ObraSocial = {};
                    this.obraSocialService.getObrasSociales(obraSocial).subscribe(
                      obraSocialResponse => {
                        this.obrasSociales = obraSocialResponse.data;
                        var enfermedad: Enfermedad = {};
                        this.enfermedadService.getEnfermedades(enfermedad).subscribe(
                          enfermedadResponse => {
                            this.enfermedades = enfermedadResponse.data;
                            let socio: Socio = {};
                            this.socioService.getSocios(socio).subscribe(
                              sociosResponse => {
                                this.socios = sociosResponse.data;
                                this.error = false;
                                this.loading = false;
                              },
                              error => {
                                if (error.status === 401){
                                  this.router.navigate(['/'+PageEnum.AUTH]);
                                  this.error = true;
                                  this.loading = false;
                                }else{
                                  console.log('ERROR', error);
                                  this.error = true;
                                  this.loading = false;
                                }
                              }
                            );
                          },
                          errorEnfermedad => {
                            if (errorEnfermedad.status === 401){
                              this.router.navigate(['/'+PageEnum.AUTH]);
                              this.loading = false;
                            }else{
                              console.log('ERROR', errorEnfermedad);
                            }
                          }
                        );
                      },
                      errorObrasSociales => {
                        if (errorObrasSociales.status === 401){
                          this.router.navigate(['/'+PageEnum.AUTH]);
                          this.loading = false;
                        }else{
                          console.log('ERROR', errorObrasSociales);
                        }
                      }
                    );
                  },
                  errorZonas => {
                    if (errorZonas.status === 401){
                      this.router.navigate(['/'+PageEnum.AUTH]);
                      this.loading = false;
                    }else{
                      console.log('ERROR', errorZonas);
                    }
                  }
                );
              },
              errorLocalidades => {
                if (errorLocalidades.status === 401){
                  this.router.navigate(['/'+PageEnum.AUTH]);
                  this.loading = false;
                }else{
                  console.log('ERROR', errorLocalidades);
                }
              }
            )
          },
          errorNombresLocalidades => {
            if (errorNombresLocalidades.status === 401){
              this.router.navigate(['/'+PageEnum.AUTH]);
              this.loading = false;
            }else{
              console.log('ERROR', errorNombresLocalidades);
            }
          }
        );
      },
      errorProvincias => {
        if (errorProvincias.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.loading = false;
        }else{
          console.log('ERROR', errorProvincias);
        }
      }
    );
  }

  editAdherente(){
    if (!this.editAdherenteForm.invalid){
      var adherenteForm : Socio = this.editAdherenteForm.getRawValue();
      console.log(adherenteForm);
      // SETEAR TODOS LOS CAMPOS EXCEPTO LOS ACTUALIZADOS EN LOS MÉTODOS DE ABAJO
      this.adherente.nombre = adherenteForm.nombre;
      this.adherente.apellido = adherenteForm.apellido;
      this.adherente.dni = adherenteForm.dni;
      this.adherente.direccion = adherenteForm.direccion;
      this.adherente.email = adherenteForm.email;
      var fechaAux = this.adherente.fechaNacimiento;
      this.adherente.fechaNacimiento = new Date(adherenteForm.fechaNacimiento['year'] + '-' + adherenteForm.fechaNacimiento['month'] + '-' + adherenteForm.fechaNacimiento['day']);
      this.adherente.fechaCobertura = new Date(adherenteForm.fechaCobertura['year'] + '-' + adherenteForm.fechaCobertura['month'] + '-' + adherenteForm.fechaCobertura['day']);
      this.adherente.telefono = adherenteForm.telefono;
      this.adherente.sexo = adherenteForm.sexo;
      console.log(this.adherente);
      this.loading = true;
      this.adherenteService.editAdherente(this.adherente).subscribe(
        response => {
          if (response.success){
            this.success = true;
            this.error = false;
            this.loading = false;
          }else{
            this.loading = false;
            this.error = true;
          }
        },
        err => {
          this.loading = false;
          this.error = true;
        },
        () => {
          this.adherente.fechaNacimiento = fechaAux;
        }
      );
    }
  }

  updateObraSocial(event: any){
    this.adherente.obraSocial = this.obrasSociales[event.target.selectedIndex];
  }

  updateLocalidades(event: any){
    this.adherente.localidad = this.localidades[event.target.selectedIndex];
  }

  updateSocio(event: any){
    this.adherente.socio.id = this.socios[event.target.selectedIndex].id;
  }

  updateEnfermedad(event: any){
    this.adherente.enfermedad.id = this.enfermedades[event.target.selectedIndex].id;
  }

  updateZonas(event: any){
    console.log('zona', this.editAdherenteForm.controls['zona']);
    this.adherente.zona = this.zonas[event.target.selectedIndex];
  }

  onCancel(){
    this.location.back();
  }

}
