package common_method;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class common_method_utilities {

	public static void evidenceFileCreator(String fileName, String request, String response) throws IOException {
		File newTextFile = new File("D:\\restAssuredEvidence\\" + fileName + ".txt");
		if (newTextFile.createNewFile()) 
		{
			FileWriter datawriter = new FileWriter(newTextFile);
			datawriter.write("requestBody is : \n" + request + "\n\n");
			datawriter.write("responstBody is : \n" + response);
			datawriter.close();
			System.out.println("requestBody and responseBody data saved in :" + newTextFile.getName());
		} 
		else 
		{
			System.out.println(newTextFile.getName() + " already exist take backup of it");
		}
	}
}
