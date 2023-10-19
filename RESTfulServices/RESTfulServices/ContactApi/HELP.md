# ContactController

ContactController is a simple RESTful API that serves contact info.
It is provided as a backend for a Node-based API in an exercise 
that involves implementing a mid-tier API.

The base URL for this API is http://localhost:8080/backend-api/contacts
The "/backend-api" URL context path is set in application.properties

## Supported Requests

1. GET http://localhost:8080/backend-api/contacts
2. GET http://localhost:8080/backend-api/contacts/{id}
3. POST http://localhost:8080/backend-api/contacts
4. PUT http://localhost:8080/backend-api/contacts
5. DELETE http://localhost:8080/backend-api/contacts/{id}

## Contact JSON

	{
		"id":1,
		"firstname":"Ada",
		"lastname":"Lovelace",
		"title":"Ms.",
		"company":"Dev Inc.",
		"jobtitle":"Developer",
		"primarycontactnumber":"+359777123456",
		"othercontactnumbers":["+359777456789","+359777112233"],
		"primaryemailaddress":"adal@computeengine.com",
		"emailaddresses":["adal@firstprogrammer.com"],
		"groups":["Dev","Family"]
	}
