import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from '@app/app-routing.module';
import { AppComponent } from '@app/app.component';
import { AuthService } from '@app/services/auth.service';
import { MainMenuComponent } from '@app/components/main-menu/main-menu.component';
import { AuthComponent } from '@app/components/auth/auth.component';
import { NavbarComponent } from '@app/views/navbar/navbar.component';
import { SidebarComponent } from '@app/views/sidebar/sidebar.component';
import { FooterComponent } from '@app/views/footer/footer.component';
import { DashboardComponent } from '@app/views/dashboard/dashboard.component';
import { FormsComponent } from '@app/views/forms/forms.component';
import { ButtonsComponent } from '@app/views/buttons/buttons.component';
import { TablesComponent } from '@app/views/tables/tables.component';
import { TypographyComponent } from '@app/views/typography/typography.component';
import { IconsComponent } from '@app/views/icons/icons.component';
import { AlertsComponent } from '@app/views/alerts/alerts.component';
import { AccordionsComponent } from '@app/views/accordions/accordions.component';
import { BadgesComponent } from '@app/views/badges/badges.component';
import { ProgressbarComponent } from '@app/views/progressbar/progressbar.component';
import { BreadcrumbsComponent } from '@app/views/breadcrumbs/breadcrumbs.component';
import { PaginationComponent } from '@app/views/pagination/pagination.component';
import { DropdownComponent } from '@app/views/dropdown/dropdown.component';
import { TooltipsComponent } from '@app/views/tooltips/tooltips.component';
import { CarouselComponent } from '@app/views/carousel/carousel.component';
import { TabsComponent } from '@app/views/tabs/tabs.component';
import { LoginComponent } from '@app/views/login/login.component';
import { ProvinciaService } from '@app/services/provincia.service';
import { AuthInterceptor } from '@app/services/auth-interceptor.service';
import { AltaComponent } from '@app/components/socio/alta/alta.component';
import { ModificacionComponent } from '@app/components/socio/modificacion/modificacion.component';
import { ListadoComponent } from '@app/components/socio/listado/listado.component';
import { SocioService } from '@app/services/socio.service';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    DashboardComponent,
    FormsComponent,
    ButtonsComponent,
    TablesComponent,
    TypographyComponent,
    IconsComponent,
    AlertsComponent,
    AccordionsComponent,
    BadgesComponent,
    ProgressbarComponent,
    BreadcrumbsComponent,
    PaginationComponent,
    DropdownComponent,
    TooltipsComponent,
    CarouselComponent,
    TabsComponent,
    LoginComponent,
    MainMenuComponent,
    AltaComponent,
    ModificacionComponent,
    ListadoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule
  ],
  providers:  [AuthService, 
              ProvinciaService,
              SocioService,
              {
                provide: HTTP_INTERCEPTORS,
                useClass: AuthInterceptor,
                multi: true
              }],
  bootstrap: [AppComponent]
})
export class AppModule { }
