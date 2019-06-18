# location-info

## Overview:
	This application is an API that will result in details about the given location.
	We can also get filtered result based on categoryName provided with the request.

## Software Installation:
	java 1.8
	git
	maven


 Git ropository need to be cloned on local machine and run following command to start an application

## Access Credentials Creation:
	Geocode api keys generation
	FourSquare client secret and client ID generation.

 Replace google Geocode api keys in AppConstants.java file with `GOOGLE_API_KEY` key.
 Replace Foursquare client_id and client_secret in AppConstants.java file with `CLIENT_SECRET` and `CLIENT_ID`.

##### Build the application without test-cases
``` mvn clean install -DskipTests```
##### Run the application
``` mvn spring-boot:run```
##### Run tests
``` mvn test```

## API endpoints

#### Get location details
Returns a list of places for a given location with attribute like category(e.g.Building,Aquarium,Art Gallery etc).
> POST: http://localhost:8180/api/getLocationInfo

**Parameters:**
- location: (Required) name of location
- categoryName: (Optional) Search for place by category name.

 Sample Response:

 {
    "status": "Success",
    "message": "Location data retrieved successfully",
    "httpStatus": "OK",
    "data": {
        "result": [
            {
                "name": "cafe coffee day",
                "category": "Coffee Shop",
                "city": null,
                "state": null,
                "country": "India",
                "countryCode": null,
                "postalCode": "[Pradhikaran (Agarwal Buisness Center), Pune, Mahārāshtra, India]",
                "address": null,
                "latitude": "18.521649558279073",
                "longitude": "73.85692119598389",
                "googlePlaceId": null
            },
            {
                "name": "Implala Hair Saloon",
                "category": "Salon / Barbershop",
                "city": null,
                "state": null,
                "country": "India",
                "countryCode": null,
                "postalCode": "[opposite lal mahal (ganesh road), Pune, Mahārāshtra, India]",
                "address": null,
                "latitude": "18.518622690053864",
                "longitude": "73.85733513062473",
                "googlePlaceId": null
            }
        ]
    }
 }