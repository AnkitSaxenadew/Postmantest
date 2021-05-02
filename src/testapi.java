import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.testtocall;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class testapi {

	public static void main(String[] args) {
		RestAssured.baseURI=("https://rahulshettyacademy.com");
		//add
		
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(testtocall.addplace1())
		.when().log().all().post("maps/api/place/add/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		String placeid = js.getString("place_id");
		//update
		
		String updadd = "noida 37";
	       
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+updadd+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("maps/api/place/update/json")
		.then().log().all().statusCode(200);
		
		
		String ext = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).header("Content-Type", "application/json")
		.when().log().all().get("maps/api/place/get/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		
		JsonPath actualadd=new JsonPath(ext);
		String aadd = actualadd.getString("address");
		
		System.out.println(aadd);
		
	Assert.assertEquals(aadd, updadd);

	}

}
