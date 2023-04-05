package test_class;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import common_method.common_method_get_api;
import common_method.common_method_utilities;
import io.restassured.path.json.JsonPath;
import request_repository.get_request_repository;

public class Get_tc1 {
	@Test
	public static void orchestrator() throws IOException 
	{
		String responseBody = "";
		int responseStatuscode = 0;
		String baseuri = get_request_repository.baseuri();
		String resource = get_request_repository.resource();
		System.out.println("status code: "+responseStatuscode);
		
		for (int i = 0; i < 5; i++) 
		{
			responseStatuscode = common_method_get_api.responsestatuscode_extractor(baseuri, resource);
			if (responseStatuscode == 200) 
				
			{
				responseBody = common_method_get_api.responsebody_extractor(baseuri, resource);
				responseBodyValidator(responseBody);
				
				break;
			} 
			else 
			{
				System.out.println("correct status code is not found in the iteration" + i);
			}
		}
		common_method_utilities.evidenceFileCreator("Get_tc1",null, responseBody);
		Assert.assertEquals(responseStatuscode, 200);
	}

	public static void responseBodyValidator(String responseBody) 
	{
		{
			
			JsonPath jsp = new JsonPath(responseBody);

			int count = jsp.getInt("data.size()");
			System.out.println("length of array:" + count);

			int id[] = { 7, 8, 9, 10, 11, 12 };
			String email[] = { "michael.lawson@reqres.in", "lindsay.ferguson@reqres.in", "tobias.funke@reqres.in",
					"byron.fields@reqres.in", "george.edwards@reqres.in", "rachel.howell@reqres.in" };
			String first_name[] = { "Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel" };
			String last_name[] = { "Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell" };
			String avatar[] = { "https://reqres.in/img/faces/7-image.jpg", "https://reqres.in/img/faces/8-image.jpg",
					"https://reqres.in/img/faces/9-image.jpg", "https://reqres.in/img/faces/10-image.jpg",
					"https://reqres.in/img/faces/11-image.jpg", "https://reqres.in/img/faces/12-image.jpg" };
			for (int i = 0; i < count; i++) {

				int re_id = id[i];
				String exp_email = email[i];
				String exp_fname = first_name[i];
				String exp_lname = last_name[i];
				String exp_avatar = avatar[i];

				int res_id = jsp.getInt("data[" + i + "].id");
				String res_email = jsp.getString("data[" + i + "].email");
				String res_Fname = jsp.getString("data[" + i + "].first_name");
				String res_Lname = jsp.getString("data[" + i + "].last_name");
				String res_Avatar = jsp.getString("data[" + i + "].avatar");

				System.out.println("id : " + res_id + "\nemail : " + res_email + "\nfirst_name : " + res_Fname
						+ "\nlast_name : " + res_Lname + "\navatar : " + res_Avatar);

				// validate response body parameters
				Assert.assertEquals(res_id, re_id);
				Assert.assertEquals(res_email, exp_email);
				Assert.assertEquals(res_Fname, exp_fname);
				Assert.assertEquals(res_Lname, exp_lname);
				Assert.assertEquals(res_Avatar, exp_avatar);
				
			}
		}
	}
}

