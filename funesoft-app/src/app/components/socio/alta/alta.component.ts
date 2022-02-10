import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { NgbModal, NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2/dist/sweetalert2.js';

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
import { FechaCoberturaComponent } from './fecha-cobertura/fecha-cobertura.component';
import { PlanService } from '@app/services/plan.service';
import { Plan } from '@app/model/plan';
import { PlanesEnum } from '@app/utils/planes.enum';
import { AdherenteAltaDTO } from '@app/dtos/adherenteAlta.dto';

@Component({
  selector: 'app-alta',
  templateUrl: './alta.component.html',
  styleUrls: ['./alta.component.scss']
})
export class AltaSocioComponent implements OnInit {

  socioToInsert: SocioAltaDTO;
  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  provincias: Provincia[];
  zonas: Zona[];
  localidadesNombres: String[];
  localidades: Localidad[];
  obrasSociales: ObraSocial[];
  tarifas: Tarifa[];
  planConAdherentes: boolean;
  planes: Plan[];
  enfermedades: Enfermedad[];
  altaSocioForm: FormGroup;
  adherentesToInsert: AdherenteAltaDTO[] = [];
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
    private planService: PlanService,
    private enfermedadService: EnfermedadService,
    private formBuilder: FormBuilder,
    private modalService: NgbModal,
    config: NgbDropdownConfig,
    private location: Location) {

      config.placement = 'bottom-right';
      this.socioToInsert = {};
      var currentDate: Date = new Date();
      this.currentYear = currentDate.getFullYear();
      this.currentMonth = currentDate.getMonth() + 1;
      this.currentDay = currentDate.getDate();
  }

  ngOnInit() {
    this.loading = true;

    this.altaSocioForm = this.formBuilder.group({
      nombre: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      apellido: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      dni: this.formBuilder.control('', [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control('', [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control('', [Validators.required]),
      fechaNacimiento: this.formBuilder.control('', [Validators.required]),
      direccion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control('', [Validators.required, Validators.minLength(6), Validators.maxLength(11), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control('', [Validators.required]),
      provincia: this.formBuilder.control('ENTRE RÍOS', [Validators.required]),
      localidad: this.formBuilder.control('', [Validators.required]),
      obraSocial: this.formBuilder.control('', [Validators.required]),
      tarifa: this.formBuilder.control('', [Validators.required]),
      plan: this.formBuilder.control('', [Validators.required]),
      enfermedad: this.formBuilder.control('SIN ENFERMEDAD', [Validators.required])
    });

    this.altaSocioForm.statusChanges
    .filter(s => s == 'VALID')
    .subscribe(val => this.onValid());

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
                      var plan: Plan = {};
                      this.planService.getPlanes(plan).subscribe(
                        planResponse => {
                          this.planes = planResponse.data;
                          var enfermedad: Enfermedad = {};
                          this.enfermedadService.getEnfermedades(enfermedad).subscribe(
                            enfermedadResponse => {
                              this.enfermedades = enfermedadResponse.data;
                              this.loading = false;
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
                        errorPlanes => {
                          if (errorPlanes.status === 401){
                            this.router.navigate(['/'+PageEnum.AUTH]);
                            this.loading = false;
                          }else{
                            console.log('ERROR', errorPlanes);
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

  onValid() {
    var socioForm : Socio = this.altaSocioForm.getRawValue();
    this.socioToInsert.nombre = socioForm.nombre;
    this.socioToInsert.apellido = socioForm.apellido;
    this.socioToInsert.dni = socioForm.dni;
    this.socioToInsert.direccion = socioForm.direccion;
    this.socioToInsert.email = socioForm.email;
    this.socioToInsert.fechaNacimiento = socioForm.fechaNacimiento;
    this.socioToInsert.telefono = socioForm.telefono;
    this.socioToInsert.sexo = socioForm.sexo;
    var estado: Estado = {
      id: 1,
      nroEstado: 1,
      descripcion: 'ALTA'
    };
    this.socioToInsert.fechaNacimiento = new Date(socioForm.fechaNacimiento['year'] + '-' + socioForm.fechaNacimiento['month'] + '-' + socioForm.fechaNacimiento['day']);
    this.socioToInsert.saldo = 0;
    this.socioToInsert.idEnfermedad = this.socioToInsert.idEnfermedad == null ? this.enfermedades[0].id : this.socioToInsert.idEnfermedad;
    this.socioToInsert.adherentesAltaDTO = this.adherentesToInsert;
    console.log(this.socioToInsert);
  }

  createSocio(){
    if (!this.altaSocioForm.invalid){
      this.socioService.createSocio(this.socioToInsert).subscribe(
        response => {
          if (response.success){
            
            this.error = false;
            this.loading = false;
            const modalRef = this.modalService.open(FechaCoberturaComponent, { size: 'xl' });
            modalRef.componentInstance.socio = response.data;
            modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
              if (receivedEntry) {
                this.success = true;
                Swal.fire({
                  icon: 'success',
                  title: '¡Éxito!',
                  titleText: 'El socio ha sido agregado con éxito.',
                  showConfirmButton: false,
                  timer: 1500
                });
                this.error = false;
              }
              this.altaSocioForm.reset();
              this.planConAdherentes = false;
            });
          }else{
            this.loading = false;
            this.error = true;
            Swal.fire(
              'Error',
              'Se produjo un error. Intente nuevamente.',
              'error'
            );
            this.success = false;
          }
        },
        err => {
          this.loading = false;
          this.error = true;
          Swal.fire(
            'Error',
            'Se produjo un error. Intente nuevamente.',
            'error'
          );
          this.success = false;
        }
      );
    }
  }

  adherenteAgregado(event: any){
    const adherenteToInsert: AdherenteAltaDTO = event.adherenteToInsert;
    this.adherentesToInsert.push(adherenteToInsert);
    event.form.reset();
    this.planConAdherentes = false;
    console.log('this.planConAdherentes', this.planConAdherentes);
    console.log('this.adherentesToInsert', this.adherentesToInsert);
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

  addAdherente() {
    const currentPlan = this.altaSocioForm.controls['plan'].value;
    if (['TITULAR Y ADHERENTE', 'FAMILIAR'].includes(currentPlan)) {
      if (this.adherentesToInsert.length < 1 && currentPlan === 'TITULAR Y ADHERENTE') {
        this.planConAdherentes = false;
        this.planConAdherentes = true;
      } else if (currentPlan === 'FAMILIAR') {
        this.planConAdherentes = false;
        this.planConAdherentes = true;
      }
    }
  }

  removeAdherente(index: number) {
    if (index === 0) {
      this.adherentesToInsert.shift();
    } else {
      this.adherentesToInsert.splice(1, index);
    }
  }

  updateObraSocial(event: any){
    this.socioToInsert.idObraSocial = this.obrasSociales[event.target.selectedIndex].id;
  }

  updateLocalidades(event: any){
    this.socioToInsert.idLocalidad = this.localidades[event.target.selectedIndex].id;
  }

  updatePlan(event: any){
    this.tarifas = [];
    var selectedIdPlan: number = this.planes[event.target.selectedIndex].id;
    if (selectedIdPlan === PlanesEnum.FAMILIAR || selectedIdPlan === PlanesEnum.TITULAR_Y_ADHERENTE) {
      this.planConAdherentes = true;
    } else {
      this.planConAdherentes = false;
    }
    var tarifa: Tarifa = {
      plan: {
        id: selectedIdPlan
      }
    };
    this.adherentesToInsert = [];
    this.tarifaService.getTarifas(tarifa).subscribe(
      tarifasResponse => {
        this.tarifas = tarifasResponse.data;
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
  }
  
  updateTarifa(event: any){
    this.socioToInsert.idTarifa = this.tarifas[event.target.selectedIndex].id;
  }

  updateZonas(event: any){
    this.socioToInsert.idZona = this.zonas[event.target.selectedIndex].id;
  }

  updateEnfermedad(event: any){
    this.socioToInsert.idEnfermedad = this.enfermedades[event.target.selectedIndex].id;
  }

  onCancel(){
    console.log(this.altaSocioForm.controls);
    // this.location.back();
  }

}
