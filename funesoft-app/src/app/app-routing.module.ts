import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthComponent } from '@app/components/auth/auth.component';
import { DashboardComponent } from '@app/components/dashboard/dashboard.component';
import { NavbarComponent } from '@app/components/navbar/navbar.component';
import { ListadoComponent } from '@app/components/socio/listado/listado.component';
import { AltaComponent } from '@app/components/socio/alta/alta.component';
import { DetalleComponent } from '@app/components/socio/detalle/detalle.component';
import { ModificacionComponent } from '@app/components/socio/modificacion/modificacion.component';
import { ZonasComponent } from '@app/components/zonas/listado/zonas.component';
import { BajaComponent } from '@app/components/socio/baja/baja.component';
import { ComprobanteComponent } from '@app/components/comprobante/comprobante.component';
import { TarifaListadoComponent } from '@app/components/tarifa/tarifa-listado/tarifa-listado.component';
import { TarifaAltaComponent } from '@app/components/tarifa/tarifa-alta/tarifa-alta.component';
import { RangosTarifaListadoComponent } from '@app/components/rangos-tarifa/rangos-tarifa-listado/rangos-tarifa-listado.component';
import { RangosTarifasAltaComponent } from '@app/components/rangos-tarifa/rangos-tarifas-alta/rangos-tarifas-alta.component';
import { FechaCoberturaComponent } from '@app/components/socio/alta/fecha-cobertura/fecha-cobertura.component';
import { AuthGuard } from '@app/utils/auth.guard';
import { PageEnum } from '@app/utils/page.enum';


const routes: Routes = [

  {path: PageEnum.AUTH, component: AuthComponent},
  {path: PageEnum.MENU, component: NavbarComponent, canActivate: [AuthGuard]},
  {path: PageEnum.DASHBOARD, component: DashboardComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_LISTADO, component: ListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_ALTA, component: AltaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_DETALLE, component: DetalleComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_MODIFICACION, component: ModificacionComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ZONA_LISTADO, component: ZonasComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_BAJA, component: BajaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_FECHA_COBERTURA, component: FechaCoberturaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.COMPROBANTE, component: ComprobanteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.TARIFA_LISTADO, component: TarifaListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.TARIFA_ALTA, component: TarifaAltaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.RANGOS_TARIFA, component: RangosTarifaListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.TARIFA_ALTA, component: RangosTarifasAltaComponent, canActivate: [AuthGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
