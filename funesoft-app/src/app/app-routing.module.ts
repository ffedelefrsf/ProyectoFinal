import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthComponent } from '@app/components/auth/auth.component';
import { MainMenuComponent } from '@app/components/main-menu/main-menu.component';
import { ListadoComponent } from '@app/components/socio/listado/listado.component';
import { AltaComponent } from '@app/components/socio/alta/alta.component';
import { ModificacionComponent } from '@app/components/socio/modificacion/modificacion.component';
import { AuthGuard } from '@app/utils/auth.guard';
import { PageEnum } from '@app/utils/page.enum';

const routes: Routes = [
  {path: PageEnum.AUTH, component: AuthComponent},
  {path: PageEnum.MENU, component: MainMenuComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_LISTADO, component: ListadoComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_ALTA, component: AltaComponent, canActivate: [AuthGuard]},
  {path: PageEnum.SOCIO_MODIFICACION, component: ModificacionComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
