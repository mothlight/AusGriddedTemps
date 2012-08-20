package au.edu.monash.ges.temperature;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Enumeration;

import dods.dap.*;
import dods.dap.parser.ParseException;
import dods.util.geturl.gui.StatusWindow;
import dods.util.Getopts;
import dods.util.InvalidSwitch;
import dods.util.OptSwitch;

public class OpenDapFile
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}
	
	public DataDDS getFileReturn(String nextUrl)
	{
		boolean accept_deflate = false;
		DConnect url = null;
		try
		{
			url = new DConnect(nextUrl, accept_deflate);
		} catch (java.io.FileNotFoundException e)
		{
			e.printStackTrace();
		}

		DataDDS dds = null;
		try
		{
			dds = url.getData(null);
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DDSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DODSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		boolean verbose = true;
//		boolean dump_data = true;
		//processData(url, dds, verbose, dump_data, accept_deflate);
		
		
		////process(dds);
		return dds;

	}	

	public void getFile(String nextUrl)
	{
		boolean accept_deflate = false;
		DConnect url = null;
		try
		{
			url = new DConnect(nextUrl, accept_deflate);
		} catch (java.io.FileNotFoundException e)
		{
			e.printStackTrace();
		}

		DataDDS dds = null;
		try
		{
			dds = url.getData(null);
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DDSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DODSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		boolean verbose = true;
//		boolean dump_data = true;
		//processData(url, dds, verbose, dump_data, accept_deflate);
		process(dds);

	}
	
	public void process(DataDDS ddsMax, DataDDS ddsMin, String filename)
	{		
		try
		{
			BaseType latitudeBaseMax = ddsMax.getVariable("latitude");
			DArray latitudeInstanceMax = (DArray) latitudeBaseMax;
			PrimitiveVector latitudeVectorMax = latitudeInstanceMax.getPrimitiveVector();
			BaseType longitudeBaseMax = ddsMax.getVariable("longitude");
			DArray longitudeInstanceMax = (DArray) longitudeBaseMax;
			PrimitiveVector longitudeVectorMax = longitudeInstanceMax.getPrimitiveVector();
			BaseType timeBaseMax = ddsMax.getVariable("time");			
			DArray timeInstanceMax = (DArray) timeBaseMax;
			PrimitiveVector timeVectorMax = timeInstanceMax.getPrimitiveVector();
			DGrid temp_max_dayBase = (DGrid)ddsMax.getVariable("temp_max_day");	
			BaseType temp_max_dayTrueBaseType = null;
			try
			{
				temp_max_dayTrueBaseType = temp_max_dayBase.getVar(0);
			} 
			catch (NoSuchVariableException e)
			{
				e.printStackTrace();
			}			
			PrimitiveVector temp_max_dayVector = ((DVector) temp_max_dayTrueBaseType).getPrimitiveVector();
			
			
			//BaseType latitudeBaseMin = ddsMin.getVariable("latitude");
			//DArray latitudeInstanceMin = (DArray) latitudeBaseMin;
			//PrimitiveVector latitudeVectorMin = latitudeInstanceMin.getPrimitiveVector();
			//BaseType longitudeBaseMin = ddsMin.getVariable("longitude");
			//DArray longitudeInstanceMin = (DArray) longitudeBaseMin;
			//PrimitiveVector longitudeVectorMin = longitudeInstanceMin.getPrimitiveVector();
			//BaseType timeBaseMin = ddsMin.getVariable("time");			
			//DArray timeInstanceMin = (DArray) timeBaseMin;
			//PrimitiveVector timeVectorMin = timeInstanceMin.getPrimitiveVector();
			DGrid temp_min_dayBase = (DGrid)ddsMin.getVariable("temp_min_day");	
			BaseType temp_min_dayTrueBaseType = null;
			try
			{
				temp_min_dayTrueBaseType = temp_min_dayBase.getVar(0);
			} 
			catch (NoSuchVariableException e)
			{
				e.printStackTrace();
			}			
			PrimitiveVector temp_min_dayVector = ((DVector) temp_min_dayTrueBaseType).getPrimitiveVector();
			
			
			WriteNetCDF writeNetCDF = new WriteNetCDF();
			writeNetCDF.setFilename(filename);
			
			try
			{
				writeNetCDF.writeFile(latitudeVectorMax, longitudeVectorMax,  timeVectorMax, temp_max_dayVector, temp_min_dayVector);
			} 
			catch (Exception e)
			{				
				e.printStackTrace();
			} 
			
		} catch (NoSuchVariableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public void process(DataDDS dds)
	{
		
		try
		{
			BaseType latitudeBase = dds.getVariable("latitude");
			//System.out.println(base.toString());
			//DArray latitudeInstance = (DArray) base;			
			DArray latitudeInstance = (DArray) latitudeBase;
			PrimitiveVector latitudeVector = latitudeInstance.getPrimitiveVector();
//			int length = latitudeVector.getLength();
//			for (int i=0;i<length;i++)
//			{
//				System.out.print(((Float32PrimitiveVector) primitiveVector).getValue(i) +", ");
//			}
//			System.out.println();
			
			
			BaseType longitudeBase = dds.getVariable("longitude");
			DArray longitudeInstance = (DArray) longitudeBase;
			PrimitiveVector longitudeVector = longitudeInstance.getPrimitiveVector();
			
			BaseType timeBase = dds.getVariable("time");			
//			DGrid timeGrid = (DGrid) timeBase;
//			BaseType timeTrueBaseType = null;
//			try
//			{
//				timeTrueBaseType = timeGrid.getVar(0);
//			} 
//			catch (NoSuchVariableException e)
//			{
//				e.printStackTrace();
//			}
			DArray timeInstance = (DArray) timeBase;
			// Grab the primitive Vector
			PrimitiveVector timeVector = timeInstance.getPrimitiveVector();
			
			DGrid temp_max_dayBase = (DGrid)dds.getVariable("temp_max_day");	
			BaseType temp_max_dayTrueBaseType = null;
			// Now grab the variable
			try
			{
				temp_max_dayTrueBaseType = temp_max_dayBase.getVar(0);
			} 
			catch (NoSuchVariableException e)
			{
				e.printStackTrace();
			}			
			PrimitiveVector temp_max_dayVector = ((DVector) temp_max_dayTrueBaseType).getPrimitiveVector();
			
			WriteNetCDF writeNetCDF = new WriteNetCDF();
			
			try
			{
				writeNetCDF.writeFile(latitudeVector, longitudeVector,  timeVector, temp_max_dayVector);
			} catch (Exception e)
			{				
				e.printStackTrace();
			} 
			
		} catch (NoSuchVariableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}