package test_class;
import java.io.IOException;
import java.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

import common_method.common_method_put_api;
import common_method.common_method_utilities;
import io.restassured.path.json.JsonPath;
import request_repository.put_request_repository;


public class Put_tc1 
{	
	@Test
	public static void orchestrator() throws IOException
	{
		String responseBody = "";
		int responseStatuscode=0;
		String baseuri = put_request_repository.baseuri();
		String resource= put_request_repository.resource();
		String requestBody = put_request_repository.Put_request_tc1();
		for (int i=0; i<5; i++)
		{
			responseStatuscode = common_method_put_api.responsestatuscode_extractor(baseuri, resource, requestBody);
			if (responseStatuscode == 200)
			{
				responseBody = common_method_put_api.responsebody_extractor(baseuri, resource, requestBody);
				responseBodyValidator(responseBody);
				break;
			}
			else
			{
				System.out.println("correct status code is not found in the iteration" +i);
			}	
		}
		common_method_utilities.evidenceFileCreator("Put_tc1",requestBody,responseBody);
		Assert.assertEquals(responseStatuscode, 200);
		}

	public static void responseBodyValidator(String responseBody) 
	{
		{
			JsonPath jsp = new JsonPath(responseBody);
			
			// extract response body parameters
			String res_name = jsp.getString("name");
			String res_job = jsp.getString("job");
			String res_updatedAt = jsp.getString("updatedAt");
			String res_date = res_updatedAt.substring(0, 10);
			
			//System.out.println("name : " + res_name + "\njob : " + res_job +  "\nupdatedAt : " + res_date);

			// validate response body parameter
			Assert.assertEquals(res_name, "Pallavi");
			Assert.assertEquals(res_job, "QA");
			
			//extract current date
			String current_date=LocalDate.now().toString();
			//Assert.assertEquals(res_date, current_date);
			//System.out.println( "\nCurrent date : " + current_date);

		
		}
	}
}




