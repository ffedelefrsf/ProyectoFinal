import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ObraSocial } from '@app/model/obraSocial';
import { ObraSocialService } from '@app/services/obra-social.service';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-obra-social-alta',
  templateUrl: './obra-social-alta.component.html',
  styleUrls: ['./obra-social-alta.component.scss']
})
export class ObraSocialAltaComponent implements OnInit {

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  disableField: boolean = false;

  altaOsForm: FormGroup;
  obraSocialInsert: ObraSocial = {}; 

  constructor(
    private formBuilder: FormBuilder,
    private obraSocialService: ObraSocialService,
  ) { }

  ngOnInit() {
    this.altaOsForm = this.formBuilder.group({
      descripcion: this.formBuilder.control('', [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
      tiene_sepelio: this.formBuilder.control('', [Validators.required])
    });
  }

  createOs() {
    if (!this.altaOsForm.invalid) {
      var altaOsForm: ObraSocial = this.altaOsForm.getRawValue();
      this.obraSocialInsert.descripcion = altaOsForm.descripcion;
      this.obraSocialInsert.tiene_sepelio = altaOsForm.tiene_sepelio;

      this.obraSocialService.createObraSocial(this.obraSocialInsert).subscribe(
        response => {
          if (response.success){
            this.success = true;
            this.error = false;
            this.loading = false;
            Swal.fire(
              'Agregada!',
              'La obra social ha sido dada de alta.',
              'success'
            );
          }else{
            this.loading = false;
            this.error = true;
            this.success = false;
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Se produjo un error. Intenta de nuevo.'
            });
            console.error(response.errores);
          }
        },
        err => {
          this.loading = false;
          this.error = true;
          this.success = false;
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Se produjo un error. Intenta de nuevo.'
          });
        }
      );

    }
  }

  onCancel() {
  }

}
