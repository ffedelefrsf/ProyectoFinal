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
import { AltaSocioComponent } from '@app/components/socio/alta/alta.component';
import { ModificacionSocioComponent } from '@app/components/socio/modificacion/modificacion.component';
import { ListadoSocioComponent } from '@app/components/socio/listado/listado.component';
import { SocioService } from '@app/services/socio.service';
import { SideMenuComponent } from '@app/components/side-menu/side-menu.component';
import { ToolbarComponent } from '@app/components/toolbar/toolbar.component';
import { DetalleSocioComponent } from '@app/components/socio/detalle/detalle.component';
import { ZonasComponent } from '@app/components/zona/listado/zonas.component';
import { BajaSocioComponent } from '@app/components/socio/baja/baja.component';
import { ComprobanteComponent } from '@app/components/comprobante/comprobante.component';
import { TarifaListadoComponent } from '@app/components/tarifa/tarifa-listado/tarifa-listado.component';
import { TarifaAltaComponent } from '@app/components/tarifa/tarifa-alta/tarifa-alta.component';
import { RangosTarifaListadoComponent } from '@app/components/rangos-tarifa/rangos-tarifa-listado/rangos-tarifa-listado.component';
import { RangosTarifasAltaComponent } from '@app/components/rangos-tarifa/rangos-tarifas-alta/rangos-tarifas-alta.component';
import { FechaCoberturaComponent } from '@app/components/socio/alta/fecha-cobertura/fecha-cobertura.component';
import { AltaAdherenteComponent } from './components/adherente/alta/alta.component';
import { BajaAdherenteComponent } from './components/adherente/baja/baja.component';
import { DetalleAdherenteComponent } from './components/adherente/detalle/detalle.component';
import { ListadoAdherenteComponent } from './components/adherente/listado/listado.component';
import { ModificacionAdherenteComponent } from './components/adherente/modificacion/modificacion.component';
import { AltaZonaComponent } from './components/zona/alta/alta-zona.component';
import { CobradorListadoComponent } from '@app/components/cobrador/cobrador-listado/cobrador-listado.component';
import { CobradorAltaComponent } from '@app/components/cobrador/cobrador-alta/cobrador-alta.component';
import { InformarPagoComponent } from './components/pago/informar-pago/informar-pago.component';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
    AltaSocioComponent,
    ModificacionSocioComponent,
    ListadoSocioComponent,
    SideMenuComponent,
    ToolbarComponent,
    DetalleSocioComponent,
    ZonasComponent,
    BajaSocioComponent,
    ComprobanteComponent,
    TarifaListadoComponent,
    TarifaAltaComponent,
    RangosTarifaListadoComponent,
    RangosTarifasAltaComponent,
    FechaCoberturaComponent,
    AltaAdherenteComponent,
    BajaAdherenteComponent,
    DetalleAdherenteComponent,
    ListadoAdherenteComponent,
    ModificacionAdherenteComponent,
    AltaZonaComponent,
    CobradorListadoComponent,
    CobradorAltaComponent,
    InformarPagoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    NgxSpinnerModule,
    BrowserAnimationsModule
  ],
  providers:  [AuthService, 
              ProvinciaService,
              SocioService,
              {
                provide: HTTP_INTERCEPTORS,
                useClass: AuthInterceptor,
                multi: true
              },
              NgxSpinnerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
