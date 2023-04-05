package test_class;
import java.io.IOException;
import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import common_method.common_method_api;
import common_method.common_method_utilities;
import io.restassured.path.json.JsonPath;
import request_repository.post_request_repository;

public class Post_tc1 
{	
	@Test
	public static void orchestrator() throws IOException
	{
		String responseBody = "";
		int responseStatuscode=0;
		String baseuri = post_request_repository.baseuri();
		String resource= post_request_repository.resource();
		String requestBody = post_request_repository.Post_request_tc1();
		for (int i=0; i<5; i++)
		{
			responseStatuscode = common_method_api.responsestatuscode_extractor(baseuri, resource, requestBody);
			if (responseStatuscode == 201)
			{
				responseBody = common_method_api.responsebody_extractor(baseuri, resource, requestBody);
				responseBodyValidator(responseBody);
				break;
			}
			else
			{
				System.out.println("correct status code is not found in the iteration" +i);
			}	
		}
		common_method_utilities.evidenceFileCreator("Post_tc1",requestBody,responseBody);
		Assert.assertEquals(responseStatuscode, 201);
		}

	public static void responseBodyValidator(String responseBody) 
	{
		{
			JsonPath jsp = new JsonPath(responseBody);
			
			String res_name = jsp.getString("name");
			String res_job = jsp.getString("job");
			String res_id = jsp.getString("id");
			String res_createdAt = jsp.getString("createdAt");
			String res_date = res_createdAt.substring(0, 10);
			

			//System.out.println("name : " + res_name + "\njob : " + res_job + "\nid : " + res_id + "\ncreatedAt : " + res_date);

			// validate responsebody parameter
			Assert.assertEquals(res_name, "Pallavi");
			Assert.assertEquals(res_job, "QA");
			//Assert.assertNotNull(res_id, "assertion error , id parameter is null");
			Assert.assertNotNull(res_id);

			///extract current date 
			String current_date = LocalDate.now().toString();
			Assert.assertEquals(res_date, current_date);
			//System.out.println("\nCurrent Date : " + current_date);

		
		}
	}
}

