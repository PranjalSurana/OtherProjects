import { Injectable } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
 
@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  public readonly appUrl: string = 'http://localhost:8089/';
  public readonly clientId: string = 'newClient';

  private readonly authTokenUrl: string = 'http://localhost:8083/auth/realms/roifmr/protocol/openid-connect/token';
  private readonly logoutUrl: string = "http://localhost:8083/auth/realms/roifmr/protocol/openid-connect/logout?id_token_hint=";
  private readonly widgetsUrl: string = 'http://localhost:8081/resource-server/warehouse/widgets';  

  constructor(private http: HttpClient) {}

  retrieveToken(code: string){
    const params = new URLSearchParams();   
    params.append('grant_type', 'authorization_code');
    params.append('client_id', this.clientId);
    params.append('redirect_uri', this.appUrl);
    params.append('code', code);

    let httpHeaders = new HttpHeaders({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'});
    this.http.post(this.authTokenUrl, params.toString(), { headers: httpHeaders })
             .subscribe(
                  token => this.saveToken(token),
                  err => alert('Invalid Credentials')
              ); 
  }

  saveToken(token: any){
    // expires when the browser is closed
    Cookie.set("access_token", token.access_token, 0);
    Cookie.set("id_token", token.id_token);
    //var expireDate = new Date().getTime() + (1000 * token.expires_in);
    //Cookie.set("access_token", token.access_token, expireDate);
    //Cookie.set("id_token", token.id_token, expireDate);
    console.log('Obtained Access token');
    window.location.href = this.appUrl;
  }

  getWidgets() : Observable<any> {
    var headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 
      'Authorization': 'Bearer ' + Cookie.get('access_token')
    });
    return this.http.get(this.widgetsUrl, { headers: headers })
                    .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  checkCredentials(){
    return Cookie.check('access_token');
  } 

  logout() {
    let token = Cookie.get('id_token');
    Cookie.delete('access_token');
    Cookie.delete('id_token');
    let logoutUrlWithParams = this.logoutUrl + token + "&post_logout_redirect_uri=" + this.appUrl;
    window.location.href = logoutUrlWithParams;
  } 
}
