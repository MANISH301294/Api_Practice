package api_Automation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.PayloadData;
import files.ReusableMethods;

public class Basic {

	// validate if add place API is working as expected
	// given = all input details 
	// when = submit the API
	// Then = validate the response
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		// Post Place 
		
	String response =	given().log().all().queryParam("key", "qacick123").header("Content-Type","application/json")
		.body(PayloadData.Body()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
	System.out.println(response);
	
	JsonPath js= ReusableMethods.rowToJson(response);
    String placeId = js.getString("place_id");
   System.out.println("place_id = "+placeId);
   
   String newAddress = "summer walk africa";
   
   
   
   // update place 
   
  
   given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
   .body("{\n"
   		+ "\"place_id\":\""+placeId+"\",\n"
   		+ "\"address\":\""+newAddress+"\",\n"
   		+ "\"key\":\"qaclick123\"\n"
   		+ "}").when().put("maps/api/place/update/json")
        .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
   
   
   
   // Get Place
   
 
   String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
   .queryParam("place_id", placeId)
   .when().get("maps/api/place/get/json")
   .then().assertThat().log().all().statusCode(200).extract().response().asString();
   
  JsonPath js1 = ReusableMethods.rowToJson(getPlaceResponse);
  String actualAddress = js1.getString("address");
  
  
   System.out.println(actualAddress);
   
	}

}

