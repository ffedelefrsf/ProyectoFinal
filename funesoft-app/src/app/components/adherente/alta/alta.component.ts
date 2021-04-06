import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { NgbModal, NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';

import { Provincia } from '@app/model/provincia';
import { Zona } from '@app/model/zona';
import { Localidad } from '@app/model/localidad';
import { ObraSocial } from '@app/model/obraSocial';
import { ProvinciaService } from '@app/services/provincia.service';
import { LocalidadService } from '@app/services/localidad.service';
import { ZonaService } from '@app/services/zona.service';
import { ObraSocialService } from '@app/services/obra-social.service';
import { SocioService } from '@app/services/socio.service';
import { PageEnum } from '@app/utils/page.enum';
import { Socio } from '@app/model/socio';
import { Estado } from '@app/model/estado';
import { TarifaService } from '@app/services/tarifa.service';
import { Tarifa } from '@app/model/tarifa';
import { SocioAltaDTO } from '@app/dtos/socioAlta.dto';
import { Enfermedad } from '@app/model/enfermedad';
import { EnfermedadService } from '@app/services/enfermedad.service';
import { FechaCoberturaComponent } from '@app/components/socio/alta/fecha-cobertura/fecha-cobertura.component';
import { PlanService } from '@app/services/plan.service';
import { Plan } from '@app/model/plan';
import { Adherente } from '@app/model/adherente';
import { AdherenteService } from '@app/services/adherente.service';
import { AdherenteAltaDTO } from '@app/dtos/adherenteAlta.dto';
import { FechaCoberturaService } from '@app/services/fecha-cobertura.service';
import { FechaCoberturaGetRequestDTO } from '@app/dtos/fechaCoberturaGetRequest.dto';

@Component({
  selector: 'app-alta-adherente',
  templateUrl: './alta.component.html',
  styleUrls: ['./alta.component.scss']
})
export class AltaAdherenteComponent implements OnInit {

  adherenteToInsert: AdherenteAltaDTO;
  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  provincias: Provincia[];
  zonas: Zona[];
  localidadesNombres: String[];
  localidades: Localidad[];
  obrasSociales: ObraSocial[];
  socios: Socio[];
  enfermedades: Enfermedad[];
  altaAdherenteForm: FormGroup;
  currentYear: number;
  currentMonth: number;
  currentDay: number;

  @Input() socioInput: Socio;

  @Output() datosAdherenteAgregado = new EventEmitter<any>();

  constructor(private router: Router,
    private provinciaService: ProvinciaService,
    private localidadService: LocalidadService,
    private zonaService: ZonaService,
    private obraSocialService: ObraSocialService,
    private socioService: SocioService,
    private enfermedadService: EnfermedadService,
    private adherenteService: AdherenteService,
    private fechaCoberturaService: FechaCoberturaService,
    private formBuilder: FormBuilder,
    private modalService: NgbModal,
    config: NgbDropdownConfig,
    private location: Location) {

      config.placement = 'bottom-right';
      this.adherenteToInsert = {};
      var currentDate: Date = new Date();
      this.currentYear = currentDate.getFullYear();
      this.currentMonth = currentDate.getMonth() + 1;
      this.currentDay = currentDate.getDate();
  }

  ngOnInit() {
    this.loading = true;
    console.log('socioInput', this.socioInput);
    this.altaAdherenteForm = this.formBuilder.group({
      nombre: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      apellido: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      dni: this.formBuilder.control('', [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control('', [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control('', [Validators.required]),
      fechaNacimiento: this.formBuilder.control('', [Validators.required]),
      direccion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control('', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control('', [Validators.required]),
      localidad: this.formBuilder.control('', [Validators.required]),
      provincia: this.formBuilder.control('ENTRE RÍOS', [Validators.required]),
      obraSocial: this.formBuilder.control('', [Validators.required]),
      enfermedad: this.formBuilder.control('SIN ENFERMEDAD', [Validators.required]),
      socio: this.formBuilder.control({value: this.socioInput ? this.socioInput : '', disabled: this.socioInput}, [Validators.required]),
      fechaCobertura: this.formBuilder.control('', [Validators.required])
    });
    var fechaCobertura: Date = new Date();
    var fechaCoberturaForDatePicker = {year: fechaCobertura.getFullYear(), month: fechaCobertura.getMonth()+1, day: fechaCobertura.getDate()+1};
    this.altaAdherenteForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);

    var provincia: Provincia = {};
    this.provinciaService.getProvincias(provincia).subscribe(
      provinciasResponse => {
        this.provincias = provinciasResponse.data;
        var localidad: Localidad = {provincia: {id: 8}};
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
                          if (!this.socioInput) {
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
                          } else {
                            this.socios = [this.socioInput]
                            this.error = false;
                            this.loading = false;
                          }
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

  createAdherente(){
    if (!this.altaAdherenteForm.invalid){
      var adherenteForm : Adherente = this.altaAdherenteForm.getRawValue();
      this.adherenteToInsert.nombre = adherenteForm.nombre;
      this.adherenteToInsert.apellido = adherenteForm.apellido;
      this.adherenteToInsert.dni = adherenteForm.dni;
      this.adherenteToInsert.direccion = adherenteForm.direccion;
      this.adherenteToInsert.email = adherenteForm.email;
      this.adherenteToInsert.fechaNacimiento = adherenteForm.fechaNacimiento;
      this.adherenteToInsert.telefono = adherenteForm.telefono;
      this.adherenteToInsert.sexo = adherenteForm.sexo;
      var estado: Estado = {
        id: 1,
        nroEstado: 1,
        descripcion: 'ALTA'
      };
      this.adherenteToInsert.fechaNacimiento = new Date(adherenteForm.fechaNacimiento['year'] + '-' + adherenteForm.fechaNacimiento['month'] + '-' + adherenteForm.fechaNacimiento['day']);
      this.adherenteToInsert.idEnfermedad = this.adherenteToInsert.idEnfermedad == null ? this.enfermedades[0].id : this.adherenteToInsert.idEnfermedad;
      console.log(this.adherenteToInsert);
      if (this.socioInput) {
        this.adherenteToInsert.fechaCobertura = new Date(adherenteForm.fechaCobertura['year'] + '-' + adherenteForm.fechaCobertura['month'] + '-' + adherenteForm.fechaCobertura['day']);
        this.datosAdherenteAgregado.emit({ adherenteToInsert: this.adherenteToInsert, form: this.altaAdherenteForm });
      } else {
        this.adherenteService.createAdherente(this.adherenteToInsert).subscribe(
          response => {
            if (response.success){
              
              this.error = false;
              this.loading = false;
              const modalRef = this.modalService.open(FechaCoberturaComponent, { size: 'xl' });
              modalRef.componentInstance.adherente = response.data;
              modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
                if (receivedEntry)
                  this.success = true;
                  this.error = false;
              });
            }else{
              this.loading = false;
              this.error = true;
              this.success = false;
            }
          },
          err => {
            this.loading = false;
            this.error = true;
            this.success = false;
          }
        );
      }
    }
  }

  updateProvincias(event: any){
    var idProvincia = this.provincias[event.target.selectedIndex].id;
    var localidad: Localidad = {provincia: {id: idProvincia}};
    this.localidadService.getLocalidades(localidad).subscribe(
      response => {
        if (response.success){
          this.localidades = response.data;
        } else {
          this.localidades = null;
        }
      },
      err => {
        this.localidades = null;
      }
    );
  }

  updateObraSocial(event: any){
    this.adherenteToInsert.idObraSocial = this.obrasSociales[event.target.selectedIndex].id;
  }

  updateLocalidades(event: any){
    this.adherenteToInsert.idLocalidad = this.localidades[event.target.selectedIndex].id;
  }

  updateZonas(event: any){
    this.adherenteToInsert.idZona = this.zonas[event.target.selectedIndex].id;
  }

  updateEnfermedad(event: any){
    this.adherenteToInsert.idEnfermedad = this.enfermedades[event.target.selectedIndex].id;
    this.updateFechaCobertura();
  }

  updateSocio(event: any){
    this.adherenteToInsert.idSocio = this.socios[event.target.selectedIndex].id;
  }

  updateFechaNacimiento(event: any){
    this.updateFechaCobertura();
  }

  updateFechaCobertura(){
    var adherenteForm : Adherente = this.altaAdherenteForm.getRawValue();
    var newFechaNacimiento: Date = new Date(adherenteForm.fechaNacimiento['year'] + '-' + adherenteForm.fechaNacimiento['month'] + '-' + adherenteForm.fechaNacimiento['day']);
    var fechaCoberturaGetRequestDTO: FechaCoberturaGetRequestDTO = {
      fechaNacimientoAsociado: newFechaNacimiento,
      idEnfermedadAsociado: this.adherenteToInsert.idEnfermedad ? this.adherenteToInsert.idEnfermedad : this.enfermedades[0].id
    }
    this.fechaCoberturaService.getFechaCobertura(fechaCoberturaGetRequestDTO).subscribe(
      response => {
        this.adherenteToInsert.fechaCobertura = response.data['fechaCobertura'];
        var fechaCobertura: Date = new Date(this.adherenteToInsert.fechaCobertura);
        var fechaCoberturaForDatePicker = {year: fechaCobertura.getFullYear(), month: fechaCobertura.getMonth()+1, day: fechaCobertura.getDate()+1};
        this.altaAdherenteForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);
      }
    );
  }

  onCancel(){
    console.log(this.altaAdherenteForm.controls);
    // this.location.back();
  }

}
