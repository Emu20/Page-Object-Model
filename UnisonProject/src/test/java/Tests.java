import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class Tests {

	@Test
	public void getNotes() {
		// 1
		String BaseUri = "http://localhost:3000/notes";
		int statusCode = given().when().get(BaseUri).getStatusCode();

		System.out.println(statusCode);

		// 2
		ValidatableResponse body = given().when().get(BaseUri).then().assertThat().statusCode(200).and().log().body();
		System.out.println(body);
		// given().when().get(BaseUri).then().assertThat().statusCode(210);

	}

	@Test
	public void postNotes() {
		String BaseUri = "http://localhost:3000/notes";
		Response res = given().contentType("application/json")
				.body("{\r\n" + "  \"title\": \"You Are Invited To Come On\",\r\n"
						+ "  \"schedule\": \"2022-01-01T00:00:00.000-07:01\"\r\n" + "}")
				.post(BaseUri).then().statusCode(200).log().body().extract().response();
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("id"), true);
		System.out.println("Header " + res.headers());

	}

	@Test
	public void getNotesID() {
		String BaseUri2 = "http://localhost:3000/notes/31cbe1ea-a159-4e87-bf44-96775b99e0e2";

		Response res2 = given().when().get(BaseUri2).then().statusCode(200).log().body().extract().response();
		String jsonStr = res2.asString();
		Assert.assertEquals(jsonStr.contains("You Are Invited To Come On"), true);

	}

	@Test
	public void putID() {

		String BaseUri3 = "http://localhost:3000/notes/31cbe1ea-a159-4e87-bf44-96775b99e0e2";
		given().given().contentType("application/json")
				.body("{\r\n" + "    \"title\": \"Our Appointment is going to be by \",\r\n"
						+ "    \"schedule\": \"2022-01-01T07:01:00.000Z\",\r\n"
						+ "    \"id\": \"31cbe1ea-a159-4e87-bf44-96775b99e0e2\"\r\n" + "}")
				.when().put(BaseUri3).then().statusCode(200).log().body();
		

	}

	@Test
	public void deleteID() {
		String BaseUri2 = "http://localhost:3000/notes/31cbe1ea-a159-4e87-bf44-96775b99e0e2";

		given().when().delete(BaseUri2).then().statusCode(200);

	}

	@Test
	public void getNotesAfterdelete() {
		// 1
		String BaseUri4 = "http://localhost:3000/notes/31cbe1ea-a159-4e87-bf44-96775b99e0e2";
		ValidatableResponse body = given().when().get(BaseUri4).then().log().body();
		System.out.println(body);

	}

}
