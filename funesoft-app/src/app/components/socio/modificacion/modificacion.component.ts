import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Location, formatDate } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/merge';
import 'rxjs/add/operator/filter';

import { Socio } from '@app/model/socio';
import { LocalidadService } from '@app/services/localidad.service';
import { ZonaService } from '@app/services/zona.service';
import { ObraSocialService } from '@app/services/obra-social.service';
import { ProvinciaService } from '@app/services/provincia.service';
import { Provincia } from '@app/model/provincia';
import { ObraSocial } from '@app/model/obraSocial';
import { Zona } from '@app/model/zona';
import { Localidad } from '@app/model/localidad';
import { PageEnum } from '@app/utils/page.enum';
import { SocioService } from '@app/services/socio.service';
import { Tarifa } from '@app/model/tarifa';
import { TarifaService } from '@app/services/tarifa.service';


@Component({
  selector: 'app-modificacion',
  templateUrl: './modificacion.component.html',
  styleUrls: ['./modificacion.component.scss']
})
export class ModificacionComponent implements OnInit {

  socio: Socio;
  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  provincias: Provincia[];
  zonas: Zona[];
  localidadesNombres: String[];
  localidades: Localidad[];
  obrasSociales: ObraSocial[];
  tarifas: Tarifa[];
  editSocioForm: FormGroup;
  currentYear: number;
  currentMonth: number;
  currentDay: number;

  constructor(private router: Router,
              private provinciaService: ProvinciaService,
              private localidadService: LocalidadService,
              private zonaService: ZonaService,
              private obraSocialService: ObraSocialService,
              private socioService: SocioService,
              private tarifaService: TarifaService,
              private formBuilder: FormBuilder,
              private location: Location) {

    this.socio = JSON.parse(this.router.getCurrentNavigation().extras.state.socio);
    // FECHA ACTUAL

    var currentDate :Date = new Date();
    this.currentYear = currentDate.getFullYear();
    this.currentMonth = currentDate.getMonth() + 1;
    this.currentDay = currentDate.getDate();
  }

  ngOnInit() {
    this.loading = true;

    this.editSocioForm = this.formBuilder.group({
      nombre: this.formBuilder.control(this.socio.nombre, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      apellido: this.formBuilder.control(this.socio.apellido, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      dni: this.formBuilder.control(this.socio.dni, [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control(this.socio.email, [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control(this.socio.sexo, [Validators.required]),
      fechaNacimiento: this.formBuilder.control(this.socio.fechaNacimiento, [Validators.required]),
      direccion: this.formBuilder.control(this.socio.direccion, [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control(this.socio.telefono, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control(this.socio.zona.nombre, [Validators.required]),
      localidad: this.formBuilder.control(this.socio.localidad.nombre, [Validators.required]),
      obraSocial: this.formBuilder.control(this.socio.obraSocial.descripcion, [Validators.required]),
      fechaCobertura: this.formBuilder.control(this.socio.fechaCobertura, [Validators.required]),
      tarifa: this.formBuilder.control(this.socio.tarifa.descripcion, [Validators.required])
    });

    var fechaNacimiento: Date = new Date(this.socio.fechaNacimiento);
    var fechaCobertura: Date = new Date(this.socio.fechaCobertura);
    var fechaNacimientoForDatePicker = {year: fechaNacimiento.getFullYear(), month: fechaNacimiento.getMonth()+1, day: fechaNacimiento.getDate()+1};
    var fechaCoberturaForDatePicker = {year: fechaCobertura.getFullYear(), month: fechaCobertura.getMonth()+1, day: fechaCobertura.getDate()+1};
    this.editSocioForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);
    this.editSocioForm.get('fechaNacimiento').setValue(fechaNacimientoForDatePicker);

    var provincia: Provincia = {};
    this.provinciaService.getProvincias(provincia).subscribe(
      provinciasResponse => {
        this.provincias = provinciasResponse.data;
        var localidad: Localidad = {provincia: {id: this.socio.localidad.provincia.id}};
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
                        var tarifa: Tarifa = {};
                        this.tarifaService.getTarifas(tarifa).subscribe(
                          tarifaResponse => {
                            this.tarifas = tarifaResponse.data;
                            this.loading = false;
                          },
                          errorTarifas => {
                            if (errorTarifas.status === 401){
                              this.router.navigate(['/'+PageEnum.AUTH]);
                              this.loading = false;
                            }else{
                              console.log('ERROR', errorTarifas);
                            }
                          }
                        );
                        this.loading = false;
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

  editSocio(){
    if (!this.editSocioForm.invalid){
      var socioForm : Socio = this.editSocioForm.getRawValue();
      console.log(socioForm);
      // SETEAR TODOS LOS CAMPOS EXCEPTO LOS ACTUALIZADOS EN LOS MÉTODOS DE ABAJO
      this.socio.nombre = socioForm.nombre;
      this.socio.apellido = socioForm.apellido;
      this.socio.dni = socioForm.dni;
      this.socio.direccion = socioForm.direccion;
      this.socio.email = socioForm.email;
      this.socio.fechaNacimiento = new Date(socioForm.fechaNacimiento['year'] + '-' + socioForm.fechaNacimiento['month'] + '-' + socioForm.fechaNacimiento['day']);
      this.socio.fechaCobertura = new Date(socioForm.fechaCobertura['year'] + '-' + socioForm.fechaCobertura['month'] + '-' + socioForm.fechaCobertura['day']);
      this.socio.telefono = socioForm.telefono;
      this.socio.sexo = socioForm.sexo;
      console.log(this.socio);
      this.loading = true;
      this.socioService.editSocio(this.socio).subscribe(
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
        }
      );
    }
  }

  updateObraSocial(event: any){
    this.socio.obraSocial = this.obrasSociales[event.target.selectedIndex];
  }

  updateLocalidades(event: any){
    this.socio.localidad = this.localidades[event.target.selectedIndex];
  }

  updateTarifa(event: any){
    this.socio.tarifa = this.tarifas[event.target.selectedIndex];
  }

  updateZonas(event: any){
    console.log('zona', this.editSocioForm.controls['zona']);
    this.socio.zona = this.zonas[event.target.selectedIndex];
  }

  onCancel(){
    this.location.back();
  }
}
