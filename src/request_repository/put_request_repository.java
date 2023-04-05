package request_repository;

import java.io.IOException;
import java.util.ArrayList;

import common_method.getData;

public class put_request_repository {
	public static String baseuri()
	{
		String baseuri="https://reqres.in/";
		return baseuri;
	}
	public static String resource ()
	{
		String resource = "api/users/2";
		return resource;
	}
	public static String Put_request_tc1() throws IOException
	{
		ArrayList<String> data= getData.getDataExcel("put_data", "tc1");
		//System.out.println(data);
		String Name = data.get(2);
		String Job = data.get(3);
		String requestBody= "{\r\n"
				+ "    \"name\": \""+Name+"\",\r\n"
				+ "    \"job\": \""+Job+"\"\r\n"
				+ "}";
		return requestBody;
	}

}
