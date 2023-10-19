## Group Exercise 4.1: OAuth2 with Spring Boot and Angular

In this exercise, we will study the interactions between a Spring Boot authorization server, a Spring Boot resource server, and an Angular client using the OAuth2 protocol. 

The authorization server is deployed at localhost:8083. It is implemented with Keycloak, an open-source identity and access management framework. Spring Boot provides wrappers that make Keycloak easier to configure.

The resource server, deployed at localhost:8081, is our WidgetSevice with the addition of OAuth2 and CORS.

The client, deployed at localhost:8089, is a simple Angular front-end that communicates with both the authentication server and the resource server.

Summary of the authorization sequence:
1. The Angular client passes the user's credentials to the authorization server.
2. The authorization server verifies the user's credentials and responds with an access token, which is signed with the server's private key. 
3. The client adds the access token to a GET request to the resource server.
5. The resource server validates the token using the authorization server's public key.
6. The resource server returns the requested resource to the client.

EXERCISE STEPS

Copy m:\FSE\RESTfulServices\OAuth2Exercise.zip to D:\

Extract OAuth2Exercise.zip to D:\

Open an Eclipse workspace and import D:\oauth2Exercise\oauth-authorization-server
	It's big and it takes 2 minutes to build. While it's building, continue to the next step.

In VS Code, open Oauth2Exercise\oauth-angular-client. Build the client:
	npm install
	
Switch back to Eclipse and import Oauth2Exercise\oauth-resource-server
	This is a stub WarehouseService with OAuth2 and CORS enabled.
		
Start both servers (be sure to wait until the auth server is running before starting the resource server):
	Run as Java Application:
		oauth-authorization-server > com.roifmr.auth.AuthorizationServerApp
		
		Wait for the following console output: 
			Embedded Keycloak started: http://localhost:8083/auth to use keycloak

	Run as Java Application:
		oauth-resource-server > com.roifmr.resource.ResourceServerApp
		
		Wait for the following console output:
			Tomcat started on port(s): 8081 (http) with context path '/resource-server'

Switch back to VS Code and start the oauth-angular-client application: 
	npm start

	The Angular client will retrieve widget data by sending a GET request to the resource server at http://localhost:8081/resource-server/warehouse/widgets

Start Fiddler Classic to view the HTTP exchanges.
	Click the Filters tab > Request Headers > check Show only if URL contains
		Enter the following (note the hosts are separated by spaces):
			localhost:8081 localhost:8083 localhost:8089
	Switch to the Inspectors tab 

In Chrome, open Developer Tools > Application > Storage > Cookies

First, try to access the WarehouseService without authentication:
	Browse to http://localhost:8081/resource-server/warehouse/widgets
	Note the response: HTTP ERROR 401 (Unauthorized)
	
Now browse to the Angular client at http://localhost:8089

Click the Login button.

Switch to Fiddler and note the requests to localhost:8083 (the authorization server).
	Click on the first request to URL /auth/realms/roifmr/protocol/openid-connect/auth
	In the response pane, click the Raw button on the upper right to see the entire HTTP response returned by the authorization server.	
	Click the Headers buttons for the request and the response and note the authorization coookies being exchanged.
	Switch to Chrome Developer Tools, note the setting of the cookies from localhost:8083.

In Chrome, sign in to the Angular client:
	Login: john@test.com
	Password: 123
	
	In Fiddler, note the requests to localhost:8089 (Angular client)
		The Tunnel request (an encrypted HTTPS request) contains the access token.
		Later requests to localhost:8089/ include a cookie with the access token.
		
	In Chrome, note the access_token cookie associated with the Angular client.
		The client will include this access token in all requests to the Warehouse service>

Click Get Widgets
	You'll see a table with several widgets, which means the client's request to http://localhost:8081/resource-server/warehouse/widgets was successful.
	
	In Fiddler, find the request to localhost:8081 that contains an Authorization header.
		The header specifies Bearer (Token) Authentication. In this case, the access token is a JSON Web Token (JWT).
		To decode the JWT:
			Right-click the Authorization header > Copy value only
			Open a new browser window to http://jwt.io
			Scroll to the Encode text field, delete its contents, and paste the access token there.
			Note the decoded value of the access token.
		We'll discuss JWTs in detail in the next section.
		
	If you wait more than 5 minutes to click Get Widgets, the token expires and you get a 401 error. Click Logout and login again.
	
Click Logout.
