import { Component, OnInit } from '@angular/core';
import { WarehouseService } from 'app/warehouse-service/warehouse.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  private readonly authUrl: string =
    'http://localhost:8083/auth/realms/roifmr/protocol/openid-connect/auth'
    + '?response_type=code&&scope=openid%20write%20read&client_id='
  public isLoggedIn: boolean = false;

  constructor(private service: WarehouseService) {
  }

  ngOnInit() {
    this.isLoggedIn = this.service.checkCredentials();
    let i = window.location.href.indexOf('code');
    if (!this.isLoggedIn && i != -1) {
      this.service.retrieveToken(window.location.href.substring(i + 5));
    }
  }

  login() {
    window.location.href = this.authUrl + this.service.clientId
      + '&redirect_uri=' + this.service.appUrl;
  }

  logout() {
    this.service.logout();
  }
}
