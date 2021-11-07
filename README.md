# SpringBootRestApiListener
Read External Json File, return specific JSON, and info log output request body.

## Running the application locally

1. Clone a copy of git Public URL, replacing *YOUR-USERNAME* with your Github username.

   `git clone https://github.com/YOUR-USERNAME/spring_boot_rest_api_listener.git`
   
2. Export Jar File.

   `mvn package -Dmaven.skip.test`
   
3. Run Jar File with arguments parameter "db" and json file.

   `java -jar spring_boot_rest_api_listener-0.0.1-SNAPSHOT.jar --db=/{directory}/db.json`
   
## API EndPoints
All API endpoints are come from json file `http://localhost:8081/api/v01/{objectname}`

### Example API Call

1. Endpoint
    `http://localhost:8081/api/v01/customers`
  
2. Json File Example
    ```
    {
      "customers": [
        {
          "customer_id": 1,
          "firstname": "pyae",
          "lastname": "sone1"
        },
        {
          "customer_id": 2,
          "firstname": "pyae",
          "lastname": "sone2"
        }
      ]
    }
    ```   
3. GET log output as below
    ```
      Path            : customers
      Request Body    : null
    ```
    
4. POST with JSON Request Body,
   ```
      {
        "firstname": "pyae", 
        "lastname": "sone"
      }
   ```
   
   Log output as below
   
   ```
      Path            : customers
      Request Body    : {
          "firstname": "pyae",
          "lastname": "sone"
      }
   ```

