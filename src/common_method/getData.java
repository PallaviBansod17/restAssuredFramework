package common_method;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class getData {
	public static ArrayList<String> getDataExcel (String testSheetName, String testCaseName) throws IOException
	{
		ArrayList<String> arrayData= new ArrayList<String>();
		//step1 locate excel file by creating the object of fileInputStream
		FileInputStream fis = new FileInputStream("C:\\Users\\integral\\Desktop\\test_data.xlsx");
		
		//step2 create the object of XXSFWorkbook to open the excel file
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		
		//step 3 access the desired sheet
		//step 3.1 fetch the name of sheet available in the excel file
		int countOfSheet = workBook.getNumberOfSheets();
		
		//step 3.2 fetch the name of sheet and compare against desired sheet name
		for (int i=0; i<countOfSheet; i++)
		{
			String sheetname= workBook.getSheetName(i);
			if (sheetname.equalsIgnoreCase(testSheetName))
		
			{
				//step 4 access the sheet and iterates through rows to fetch the column in which test case name column is found
				XSSFSheet Sheet =workBook.getSheetAt(i);
				
				//step 4.1 creates iterator for rows
				Iterator<Row> Rows = Sheet.iterator();
				Row firstRow = Rows.next();
				
				//step 4.2 creates iterator for cells
				Iterator<Cell> Cells = firstRow.cellIterator();
				int j = 0;
				int tc_column = 0;
				
				//step 4.3 read cell values for row number 1 to compare against the test case name
				while (Cells.hasNext())
				{
					Cell cellvalue = Cells.next();
					if (cellvalue.getStringCellValue().equalsIgnoreCase("tc_name"))
					{
						tc_column = j;
					}
					j++;
				}
				//step 5 fetch the data for designated test case
				while (Rows.hasNext())
				{
					Row dataRow = Rows.next();
					if (dataRow.getCell(tc_column).getStringCellValue().equalsIgnoreCase(testCaseName))
					{
						Iterator<Cell> dataCellValue = dataRow.cellIterator();
						
						while(dataCellValue.hasNext())
						{
							Cell dataOfCell = dataCellValue.next();
							//Method1:to extract cell value using try and catch
							try
							{
								String testData = dataOfCell.getStringCellValue();
								//System.out.println(testData);
								arrayData.add(testData);
							}
							catch (IllegalStateException e)
							
							{
								int inttestData= (int) dataOfCell.getNumericCellValue();
								String stringtestData=Integer.toString(inttestData);
								arrayData.add(stringtestData);
							}
							/*Method2:to extract cell value by if and else
							CellType dataType =dataOfCell.getCellType();
							
							if (dataType.toString() == "NUMERIC")
							{
								int inttestData=(int) dataOfCell.getNumericCellValue();
								String stringtestData= Integer.toString(inttestData);
								arrayData.add(stringtestData);
							}
							else if (dataType.toString()=="STRING")
							{
								String testData = dataOfCell.getStringCellValue();
								arrayData.add(testData);
							}*/
							/*Method3:extract the data by converting into string first
							String testData= dataOfCell.toString().replaceAll("\\.\\d+$","");
							arrayData.add(testData);
							System.out.println(testData);*/
							
							/*Method4:Extract the data by using DataFormatter
							DataFormatter format = new DataFormatter();
							String testData=format.formatCellValue(dataOfCell);
							arrayData.add(testData);
							System.out.println(testData);*/
							
						}
					}
				}
						
				
			 }
		}
		return arrayData;
	}

}
