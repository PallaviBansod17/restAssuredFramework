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


public class dataDriven {
	public static ArrayList<String> getDataExcel (String testSheetName, String testCaseName) throws IOException
	{
		ArrayList<String> arrayData= new ArrayList<String>();
		//step1 locate excel file by creating the object of fileInputStream
		FileInputStream fis = new FileInputStream("C:\\Users\\integral\\Desktop\\test_data.xlsx");//fis is reference variable
		
		//step2 create the object of XXSFWorkbook to open the excel file
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		
		//step 3 access the desired sheet
		//step 3.1 fetch the name of sheet available in the excel file
		int countOfSheet = workBook.getNumberOfSheets();
		
		//step 3.2 fetch the name of sheet and compare against desired sheet name
		for (int i=0; i<countOfSheet; i++)
		{
			String sheetname= workBook.getSheetName(i);//get sheet name from every index thats why i
			if (sheetname.equalsIgnoreCase(testSheetName))//if sheet name is desired sheet name the go inside that sheet
				//our expected test SheetName is in testSheetName variable
			{
				//step 4 access the sheet and iterates through rows to fetch the column in which test case name column is found
				XSSFSheet Sheet =workBook.getSheetAt(i);//sheet is variable of XSSFSheet(its not object)
				//to read rows and columns we need entire sheet not just name
				
				//step 4.1 creates iterator for rows
				Iterator<Row> Rows = Sheet.iterator();// sheet is parent of row
				Row firstRow = Rows.next();//give you row
				
				//step 4.2 creates iterator for cells
				Iterator<Cell> Cells = firstRow.cellIterator();//row is a parent of cell
				int j = 0;
				int tc_column = 0;
				
				//step 4.3 read cell values for row number 1 to compare against the test case name
				while (Cells.hasNext())//cell me value exist krti hai ya nhi ye check krega
				{
					Cell cellvalue = Cells.next();//we are at 1 , we didn't get data at 1 so we move next cell
					if (cellvalue.getStringCellValue().equalsIgnoreCase("tc_name"))//if cellvalue=tc_name then tc_column=j, otherwise j+1 and iterates again
					{
						tc_column = j;
					}
					j++;
				}
				//step 5 fetch the data for designated test case
				while (Rows.hasNext())
				{
					Row dataRow = Rows.next();
					if (dataRow.getCell(tc_column).getStringCellValue().equalsIgnoreCase(testCaseName))//tc_name k cell ko read krna hai
					{
						Iterator<Cell> dataCellValue = dataRow.cellIterator();//we are at tc1---iterates cell
						
						while(dataCellValue.hasNext())
						{
							Cell dataOfCell = dataCellValue.next();
							//Method1:to extract cell value using try and catch
							try//in that block we write code to execute
							{
								String testData = dataOfCell.getStringCellValue();
								//System.out.println(testData);
								arrayData.add(testData);
							}
							catch (IllegalStateException e) //here we first catch exception name, code jb chalega to konse exception ko catch krna hai
							//catch krke {} me dalna hai
							{
								int inttestData= (int) dataOfCell.getNumericCellValue();
								String stringtestData=Integer.toString(inttestData);//convert to string with(toString)
								arrayData.add(stringtestData);
							}
							/*Method2:to extract cell value by if and else
							CellType dataType =dataOfCell.getCellType();//getCellType tell you datatype of values of cell
							
							if (dataType.toString() == "NUMERIC")
							{
								int inttestData=(int) dataOfCell.getNumericCellValue();
								String stringtestData= Integer.toString(inttestData);//convert to string with (toString)
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

