## Instructor Demo: Authenticate requests with OAuth2 with Insomnia

Start the auth server, then the resource server.

In oauth-resource-server WarehouseService.java, change:
	@CrossOrigin(origins = "http://localhost:8089")
to:
	@CrossOrigin(origins = "*")
(Server will restart automatically)

In Insomnia: 

Click Cookies button (on the left of the URL field) and delete all cookies

Click Preferences (lower left) > General 
	Request/Response > check Follow Redirects
	Security
		Check Clear OAuth2 session on start
		Click Clear OAuth2 session button

1. Send a request without authorization:
	GET http://localhost:8081/resource-server/warehouse/widgets
		Response: 401 Unauthorized

2. GET http://localhost:8083/auth/realms/roifmr/protocol/openid-connect/auth?response_type=code&scope=openid write read&client_id=newClient&redirect_uri=http://localhost:8089/

First view the response in Preview format. Note the signin page from Keycloak.

Switch the format to Raw.

3. From the raw Keycloak response page, copy the URL in the "action" attribute of the login button (mid-way through the page).

4. Create a new POST request
	In the list of requests, hover over the POST request, open the dropdown, select Settings > Don't follow redirects

	Paste the URL that you copied from the "action" value for the POST URL
	http://localhost:8083/auth/realms/roifmr/login-actions/authenticate?session_code=...
		In the URL, delete amp; from the query params (they're artifacts of the URL encoding in the HTML form)
	
	In the Body dropdown under the URL field, select Form URL Encoded
		Open roifmr-realm.json in the oauth-authorization-server project and find the "users" element for john@test.com
		Copy the id value UUID
		Add these form params to the POST request body:
			username: john@test.com
			password: 123
			credentialId: <id from roifmr-realm.json> 

5. You'll get a 302 response. Go to the response headers and look for Location header. 
	From the Location value, copy the "code" value.

6. Create another POST request
	POST http://localhost:8083/auth/realms/roifmr/protocol/openid-connect/token

	Body > URL Form Encoded
	Body params:
		grant_type: authorization_code
		client_id: newClient
		redirect_uri: http://localhost:8089/
		code: <code value from previous step>
	
7. You should now get the access token in the response body. Copy the token value.

	The token is a JWT. To decode it:
		Browse to http://jwt.io
		Paste the access token into Encoded field
		Note JWT contents
			kid: key identifier (i.e., which key to use to validate the signature)

8. Now retry the request for widgets, using the access token to authorize requests to the API
	GET http://localhost:8081/resource-server/warehouse/widgets
	Headers:
		Name: Authorization
		Value: Bearer <access-token>
	
	GET http://localhost:8081/resource-server/warehouse/widgets/2
	
9. Examine the cookies. Note the AUTH and KEYCLOAK cookies.
	
NOTES:
https://www.baeldung.com/postman-keycloak-endpoints
