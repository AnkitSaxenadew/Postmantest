package payload;

import io.restassured.path.json.JsonPath;

public class reusablemethod {

	public static JsonPath rawtojson (String response)
	{
		JsonPath js2=new JsonPath(response);
		return js2;

	}

}
