package au.edu.monash.ges.temperature;
import java.util.ArrayList;
import java.util.TreeMap;
import dods.dap.*;
import dods.dap.parser.ParseException;


public class ProcessOpenDapFiles
{
	
	String baseUrlMax = "http://opendap.bom.gov.au:8080/thredds/catalog/daily_maximum_temperature_5km/";
	String baseUrlOpendapMax = "http://opendap.bom.gov.au:8080/thredds/dodsC/daily_maximum_temperature_5km/";
	
	String baseUrlMin = "http://opendap.bom.gov.au:8080/thredds/catalog/daily_minimum_temperature_5km/";
	String baseUrlOpendapMin = "http://opendap.bom.gov.au:8080/thredds/dodsC/daily_minimum_temperature_5km/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ProcessOpenDapFiles processOpenDapFiles = new ProcessOpenDapFiles();
		processOpenDapFiles.process();
	}
	
	public void process()
	{
		String filename = "TempMaxMinMelbourne-Jan1980-2010.nc";
		OpenDapDir openDapDirMax = new OpenDapDir(baseUrlMax, baseUrlOpendapMax);
		OpenDapDir openDapDirMin = new OpenDapDir(baseUrlMin, baseUrlOpendapMin);
		TreeMap<String, ArrayList<String>> filesUrlsMax = openDapDirMax.getFilesForYears(1980, 1990);
		TreeMap<String, ArrayList<String>> filesUrlsMin = openDapDirMin.getFilesForYears(1980, 1990);
		//System.out.println(filesUrlsMax.toString());
		
		OpenDapFile openDapFile = new OpenDapFile();
		
		String[] years = {"1982","1983","1984","1985","1986","1987","1988","1989"};
		
		for (String year : years)
		{
			ArrayList<String> dataForMaxTemps = filesUrlsMax.get(year);
			ArrayList<String> dataForMinTemps = filesUrlsMin.get(year);
			
			int numberOfFiles = dataForMaxTemps.size();
			
			int start = 0;
			//int end = 32;
			for (int i=start;i<numberOfFiles;i++)
			{
				DataDDS maxDDS = null;
				DataDDS minDDS = null;
				maxDDS = openDapFile.getFileReturn(dataForMaxTemps.get(i));
				minDDS = openDapFile.getFileReturn(dataForMinTemps.get(i));
				System.out.println(year + " " + i);
				openDapFile.process(maxDDS, minDDS, filename);
				//openDapFile.getFile(dataFor1970Min.get(i));
			}
		}
		
		
		
	}

}
