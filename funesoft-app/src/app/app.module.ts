import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from '@app/app-routing.module';
import { AppComponent } from '@app/app.component';
import { AuthService } from '@app/services/auth.service';
import { AuthComponent } from '@app/components/auth/auth.component';
import { NavbarComponent } from '@app/components/navbar/navbar.component';
import { SidebarComponent } from '@app/views/sidebar/sidebar.component';
import { FooterComponent } from '@app/components/footer/footer.component';
import { DashboardComponent } from '@app/components/dashboard/dashboard.component';
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
import { ProvinciaService } from '@app/services/provincia.service';
import { AuthInterceptor } from '@app/services/auth-interceptor.service';
import { AltaComponent } from '@app/components/socio/alta/alta.component';
import { ModificacionComponent } from '@app/components/socio/modificacion/modificacion.component';
import { ListadoComponent } from '@app/components/socio/listado/listado.component';
import { SocioService } from '@app/services/socio.service';
import { SideMenuComponent } from '@app/components/side-menu/side-menu.component';
import { ToolbarComponent } from '@app/components/toolbar/toolbar.component';
import { DetalleComponent } from '@app/components/socio/detalle/detalle.component';
import { ZonasComponent } from '@app/components/zona/listado/zonas.component';
import { BajaComponent } from '@app/components/socio/baja/baja.component';
import { ComprobanteComponent } from '@app/components/comprobante/comprobante.component';
import { TarifaListadoComponent } from '@app/components/tarifa/tarifa-listado/tarifa-listado.component';
import { TarifaAltaComponent } from '@app/components/tarifa/tarifa-alta/tarifa-alta.component';
import { RangosTarifaListadoComponent } from '@app/components/rangos-tarifa/rangos-tarifa-listado/rangos-tarifa-listado.component';
import { RangosTarifasAltaComponent } from '@app/components/rangos-tarifa/rangos-tarifas-alta/rangos-tarifas-alta.component';
import { FechaCoberturaComponent } from '@app/components/socio/alta/fecha-cobertura/fecha-cobertura.component';


@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    DashboardComponent,
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
    AltaComponent,
    ModificacionComponent,
    ListadoComponent,
    SideMenuComponent,
    ToolbarComponent,
    DetalleComponent,
    ZonasComponent,
    BajaComponent,
    ComprobanteComponent,
    TarifaListadoComponent,
    TarifaAltaComponent,
    RangosTarifaListadoComponent,
    RangosTarifasAltaComponent,
    FechaCoberturaComponent
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
