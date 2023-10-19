The WarehouseServiceDevTestProd project contains three profiles.

1. The dev profile:
	- This is the development profile.
	- It is configured in application-dev.properties
	- It uses the in-memory database for testing.
	- Logging is set to DEBUG level, sent to the console
	
2. The test profile:
	- This is the testing profile.
	- It is configured in application-test.properties.
	- It uses the Oracle scott database for testing.
	- Logging is set to DEBUG level, sent to the console and to the log file.
	
3. The prod profile:
	- This is the production profile.
	- It is configured in application-prod.properties
	- It uses the Oracle hr database.
		- The Oracle hr database is our "production" database.
	- This should NOT be used for testing.
	- An attempt to run tests with this profile will generate an exception for 
	  any test that attempts to communicate with the hr database.
	- Logging is set to INFO level, sent to the log file.
	
The WarehouseServiceApplication now defines a DataSource bean
	The DataSource properties are set by using the environment properties that are 
	defined in the active profile.	
	
To build the Spring Boot "uber" jar file:
1. Right click on the project in the Project Explorer
2. Choose Run As | Run Configurations ...
3. In the Maven Build section, select either WarehouseServiceDevTestProd(Dev with Tests)
   or WarehouseServiceDevTestProd(Test with Tests)
4. Notice the Goals are set to "clean install"
	This will instruct maven to clean the project, run the tests and then build the uber jar.
5. Select the JRE tab.
	Notice that the spring.profiles.active property is set as a command line option
6. Click on the Run button
	Maven will then clean the project, run the test suite, and build the uber jar file.
	The output will appear in the Console window.
	The uber jar file will be placed in the target folder.
	
To run the uber jar file with the production profile:
1. Open a command window
2. Change directory to the directory that contains the uber jar file.
3. Run the command "java -Dspring.profiles.active=prod -jar <uber jar file name>.jar

To stop the SpringBoot application:
1. In the command window that it is running in, type Ctrl-C 
	You may have to type Ctrl-C several times to shut it down
	