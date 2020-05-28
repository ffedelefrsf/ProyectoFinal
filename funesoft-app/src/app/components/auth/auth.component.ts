import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '@app/services/auth.service';
import { PageEnum } from '@app/utils/page.enum';


@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  authForm: FormGroup;
  loading : boolean = false;
  wrongCredentials : boolean = false;

  constructor( 
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService) { 

   }

  ngOnInit(): void {
    this.authForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required]),
      password: this.formBuilder.control('', [Validators.required])
    });
  }

  auth(){
    const loginDTO = this.authForm.getRawValue();
    this.loading = true;
    this.wrongCredentials = false;
    this.authService.auth(loginDTO).subscribe(
      response => {
        if (response){
          this.router.navigate(['/'+PageEnum.DASHBOARD]);
        }else{
          this.wrongCredentials = true;
          this.loading = false;
        }
      },
      err => {
        this.wrongCredentials = true;
        this.loading = false;
      }
    );
  }

}
