import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';

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

@Component({
  selector: 'app-alta',
  templateUrl: './alta.component.html',
  styleUrls: ['./alta.component.scss']
})
export class AltaComponent implements OnInit {

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
  altaSocioForm: FormGroup;
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
      telefono: this.formBuilder.control('', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control('', [Validators.required]),
      localidad: this.formBuilder.control('', [Validators.required]),
      obraSocial: this.formBuilder.control('', [Validators.required]),
      tarifa: this.formBuilder.control('', [Validators.required])
    });

    var provincia: Provincia = {};
    this.provinciaService.getProvincias(provincia).subscribe(
      provinciasResponse => {
        this.provincias = provinciasResponse.data;
        var localidad: Localidad = {provincia: {id: 8}};
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

  createSocio(){
    if (!this.altaSocioForm.invalid){
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
      console.log(this.socioToInsert);
      this.socioService.createSocio(this.socioToInsert).subscribe(
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
    this.socioToInsert.idObraSocial = this.obrasSociales[event.target.selectedIndex].id;
  }

  updateLocalidades(event: any){
    this.socioToInsert.idLocalidad = this.localidades[event.target.selectedIndex].id;
  }
  
  updateTarifa(event: any){
    this.socioToInsert.idTarifa = this.tarifas[event.target.selectedIndex].id;
  }

  updateZonas(event: any){
    this.socioToInsert.idZona = this.zonas[event.target.selectedIndex].id;
  }

  onCancel(){
    console.log(this.altaSocioForm.controls);
    this.location.back();
  }

}
