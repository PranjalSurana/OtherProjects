## Running the OAuth2 Demo

In Eclipse, import existing projects:
1. `oauth-authorization-server` (import takes 2 minutes)
2. `oauth-resource-server` (WarehouseService)

	Avoid rebuilding from scratch if possible. 
	The auth server takes a very long time to build because of all the Keycloak dependencies.
	
In VS Code, open `oauth-angular-client`
	`npm install`
	
Run as Java Applications (be sure to start auth server first):
1. `oauth-authorization-server com.roifmr.auth.AuthorizationServerApp`
2. `oauth-resource-server com.roifmr.resource.ResourceServerApp`

Back to VS Code: `npm start`

Send requests to the resource server:	
1. Browse to `http://localhost:8089`
2. Login: `john@test.com/123`  OR  `mike@other.com/pass`
3. Click Get Widgets
4. Click Logout
