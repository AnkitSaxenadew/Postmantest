import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.maindata;
import payload.reusablemethod;

import static io.restassured.RestAssured.*;                   //we have to import this and add static and at the end .*
import static org.hamcrest.Matchers.*;

import org.testng.Assert;


public class basic {

	public static void main(String[] args) {
       RestAssured.baseURI="https://rahulshettyacademy.com";
		
        //1.ADD place
       /*
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")  //to add given we have to  import (import static io.restassured.RestAssured.*;    )
		.body(maindata.Addplace())                                              //eclipse will convert json format in java standard which eclipse understand
		.when().log().all().post("maps/api/place/add/json")      
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))                          //equalTo method is static method so import static org.hamcrest.Matchers.*; (want to validate that scope is equals to app or not)
		.header("Server", "Apache/2.4.18 (Ubuntu)");
		*/
		
		
		//2.ADD place>>Update place with new address>>then get place to validate if new address is present or not

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")  
		.body(maindata.Addplace())                                              
		.when().log().all().post("maps/api/place/add/json")      
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))                         
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		
		
		JsonPath js=new JsonPath(response);             //we are taking string as an input and convert it into json(for parsing json)
		String placeid = js.getString("place_id");
		                
		
		//update place
		String newaddress = "noida 37";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json") 
		.body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+newaddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//get place
		
		String vldaddrss = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeid)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		JsonPath js2 = reusablemethod.rawtojson(vldaddrss);
		String actualaddress = js2.getString("address");
		
		System.out.println(actualaddress);
		Assert.assertEquals(actualaddress, newaddress);
		
	}

}
