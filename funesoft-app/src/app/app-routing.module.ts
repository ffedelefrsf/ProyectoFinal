import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthComponent } from '@app/components/auth/auth.component';
import { DashboardComponent } from '@app/components/dashboard/dashboard.component';
import { NavbarComponent } from '@app/components/navbar/navbar.component';
import { ListadoSocioComponent } from '@app/components/socio/listado/listado.component';
import { AltaSocioComponent } from '@app/components/socio/alta/alta.component';
import { DetalleSocioComponent } from '@app/components/socio/detalle/detalle.component';
import { ModificacionSocioComponent } from '@app/components/socio/modificacion/modificacion.component';
import { ZonasComponent } from '@app/components/zona/listado/zonas.component';
import { BajaSocioComponent } from '@app/components/socio/baja/baja.component';
import { ComprobanteComponent } from '@app/components/comprobante/comprobante.component';
import { TarifaListadoComponent } from '@app/components/tarifa/tarifa-listado/tarifa-listado.component';
import { TarifaAltaComponent } from '@app/components/tarifa/tarifa-alta/tarifa-alta.component';
import { RangosTarifaListadoComponent } from '@app/components/rangos-tarifa/rangos-tarifa-listado/rangos-tarifa-listado.component';
import { RangosTarifasAltaComponent } from '@app/components/rangos-tarifa/rangos-tarifas-alta/rangos-tarifas-alta.component';
import { FechaCoberturaComponent } from '@app/components/socio/alta/fecha-cobertura/fecha-cobertura.component';
import { AltaAdherenteComponent } from '@app/components/adherente/alta/alta.component';
import { AuthGuard } from '@app/utils/auth.guard';
import { PageEnum } from '@app/utils/page.enum';
import { AltaZonaComponent } from './components/zona/alta/alta-zona.component';
import { CobradorListadoComponent } from './components/cobrador/cobrador-listado/cobrador-listado.component';
import { CobradorAltaComponent } from './components/cobrador/cobrador-alta/cobrador-alta.component';
import { ListadoAdherenteComponent } from './components/adherente/listado/listado.component';
import { ModificacionAdherenteComponent } from './components/adherente/modificacion/modificacion.component';
import { BajaAdherenteComponent } from './components/adherente/baja/baja.component';
import { DetalleAdherenteComponent } from './components/adherente/detalle/detalle.component';
import { InformarPagoComponent } from './components/pago/informar-pago/informar-pago.component';
import { ObraSocialListadoComponent } from './components/obra-social/obra-social-listado/obra-social-listado.component';
import { ObraSocialAltaComponent } from './components/obra-social/obra-social-alta/obra-social-alta.component';

const routes: Routes = [

  {path: '', component: DashboardComponent, canActivate: [AuthGuard]},
  {path: PageEnum.AUTH, component: AuthComponent},
  {path: PageEnum.MENU, component: NavbarComponent, canActivate: [AuthGuard]},
  {path: PageEnum.DASHBOARD, component: DashboardComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_LISTADO, component: ListadoSocioComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_ALTA, component: AltaSocioComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_DETALLE, component: DetalleSocioComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_MODIFICACION, component: ModificacionSocioComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ZONA_LISTADO, component: ZonasComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_BAJA, component: BajaSocioComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_FECHA_COBERTURA, component: FechaCoberturaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.COMPROBANTE, component: ComprobanteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.TARIFA_LISTADO, component: TarifaListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.TARIFA_ALTA, component: TarifaAltaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.RANGOS_TARIFA, component: RangosTarifaListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.TARIFA_ALTA, component: RangosTarifasAltaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ADHERENTE_ALTA, component: AltaAdherenteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ZONA_ALTA, component: AltaZonaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.COBRADOR_LISTADO, component: CobradorListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.COBRADOR_ALTA, component: CobradorAltaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ADHERENTE_LISTADO, component: ListadoAdherenteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ADHERENTE_MODIFICACION, component: ModificacionAdherenteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ADHERENTE_DETALLE, component: DetalleAdherenteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.ADHERENTE_BAJA, component: BajaAdherenteComponent, canActivate: [AuthGuard]},
  {path: PageEnum.INFORMAR_PAGO, component: InformarPagoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.OBRA_SOCIAL_LISTADO, component: ObraSocialListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.OBRA_SOCIAL_ALTA, component: ObraSocialAltaComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
