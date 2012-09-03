package au.edu.monash.ges.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dods.dap.*;

import ucar.ma2.Array;
import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayFloat;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Dimension;
import ucar.nc2.NCdump;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.Variable;

public class ReadTreeCoverNC
{
	
	private String filename = "treecover_990.nc";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		ReadTreeCoverNC readTree = new ReadTreeCoverNC();
		readTree.read5kmlatlong();

	}
	
	public void read5kmlatlong()
	{
		NetcdfFile ncfile = null;
		try
		{
			ncfile = NetcdfFile.open("/home/kerryn/Desktop/Temp/treecover_30pts_lat.nc");
			String xrange = "Band1";
			Variable xrangeVar = ncfile.findVariable(xrange);
			Array xrangeData = xrangeVar.read();
			NCdump.printArray(xrangeData, xrange, System.out, null);
			
			String yrange = "GDAL_Geographics";
			Variable yrangeVar = ncfile.findVariable(yrange);
			Array yrangeData = yrangeVar.read();
			NCdump.printArray(yrangeData, yrange, System.out, null);
			
			List<ucar.nc2.Attribute> yattr = yrangeVar.getAttributes();
			for (ucar.nc2.Attribute attr : yattr)
			{
				String attrName = attr.getName();
				Array  attrValue = attr.getValues();
				attrValue.getElementType();
				double value = attrValue.getDouble(0);
			
				System.out.println(attrName + "=" + value);
			}
			
			
			//[Northernmost_Northing = -37.51903101958847, Southernmost_Northing = -38.49309598467985, Easternmost_Easting = 145.56979076552813, Westernmost_Easting = 144.4949604592204, spatial_ref = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0],UNIT[\"degree\",0.0174532925199433],AUTHORITY[\"EPSG\",\"4326\"]]", GeoTransform = "144.495 0.0335884 0 -37.519 0 -0.0335884 ", grid_mapping_name = "Geographics Coordinate System", long_name = "Grid_latitude"]
			
//			String zrange = "z_range";
//			Variable zrangeVar = ncfile.findVariable(zrange);
//			Array zrangeData = zrangeVar.read();
//			NCdump.printArray(zrangeData, zrange, System.out, null);
//			
//			String spacing = "spacing";
//			Variable spacingVar = ncfile.findVariable(spacing);
//			Array spacingData = spacingVar.read();
//			NCdump.printArray(spacingData, spacing, System.out, null);
//			
//			String dimension = "dimension";
//			Variable dimensionVar = ncfile.findVariable(dimension);
//			Array dimensionData = dimensionVar.read();
//			NCdump.printArray(dimensionData, dimension, System.out, null);
//			
//			String z = "z";
//			Variable zVar = ncfile.findVariable(z);
//			Array zData = zVar.read();
//			NCdump.printArray(zData, z, System.out, null);
			
//			int count = 0;
//			//for (int x=0;x<94;x++)
//			for (int x=0;x<30;x++)  // 106.92 km
//			{
//				//for (int y=0;y<108;y++)
//				for (int y=0;y<30;y++) // 93.06 km
//				{
//					double item = zData.getDouble(count);
//					long percent = Math.round(item * 100);
//					if (percent < 0)
//					{
//						percent = 0;
//					}
//					String percentStr = new Integer((int)percent).toString();
//					if (percentStr.length() == 1)
//					{
//						percentStr = "0" + percentStr;
//					}
//					if (percentStr.equals("00"))
//					{
//						percentStr = "  ";
//					}
//				
//					//System.out.println(x + " " + y + " " + item);
//					System.out.print( " " + percentStr);
//					count ++;
//				}
//				System.out.println();
//			}
			
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public void read5km()
	{
		NetcdfFile ncfile = null;
		try
		{
			ncfile = NetcdfFile.open("treecover_30pts.nc");
			String xrange = "x_range";
			Variable xrangeVar = ncfile.findVariable(xrange);
			Array xrangeData = xrangeVar.read();
			NCdump.printArray(xrangeData, xrange, System.out, null);
			
			String yrange = "y_range";
			Variable yrangeVar = ncfile.findVariable(yrange);
			Array yrangeData = yrangeVar.read();
			NCdump.printArray(yrangeData, yrange, System.out, null);
			
			String zrange = "z_range";
			Variable zrangeVar = ncfile.findVariable(zrange);
			Array zrangeData = zrangeVar.read();
			NCdump.printArray(zrangeData, zrange, System.out, null);
			
			String spacing = "spacing";
			Variable spacingVar = ncfile.findVariable(spacing);
			Array spacingData = spacingVar.read();
			NCdump.printArray(spacingData, spacing, System.out, null);
			
			String dimension = "dimension";
			Variable dimensionVar = ncfile.findVariable(dimension);
			Array dimensionData = dimensionVar.read();
			NCdump.printArray(dimensionData, dimension, System.out, null);
			
			String z = "z";
			Variable zVar = ncfile.findVariable(z);
			Array zData = zVar.read();
			NCdump.printArray(zData, z, System.out, null);
			
			int count = 0;
			//for (int x=0;x<94;x++)
			for (int x=0;x<30;x++)  // 106.92 km
			{
				//for (int y=0;y<108;y++)
				for (int y=0;y<30;y++) // 93.06 km
				{
					double item = zData.getDouble(count);
					long percent = Math.round(item * 100);
					if (percent < 0)
					{
						percent = 0;
					}
					String percentStr = new Integer((int)percent).toString();
					if (percentStr.length() == 1)
					{
						percentStr = "0" + percentStr;
					}
					if (percentStr.equals("00"))
					{
						percentStr = "  ";
					}
				
					//System.out.println(x + " " + y + " " + item);
					System.out.print( " " + percentStr);
					count ++;
				}
				System.out.println();
			}
			
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public void read()
	{
		NetcdfFile ncfile = null;
		try
		{
			ncfile = NetcdfFile.open(filename);
			String xrange = "x_range";
			Variable xrangeVar = ncfile.findVariable(xrange);
			Array xrangeData = xrangeVar.read();
			//NCdump.printArray(xrangeData, xrange, System.out, null);
			
			String yrange = "y_range";
			Variable yrangeVar = ncfile.findVariable(yrange);
			Array yrangeData = yrangeVar.read();
			//NCdump.printArray(yrangeData, yrange, System.out, null);
			
			String zrange = "z_range";
			Variable zrangeVar = ncfile.findVariable(zrange);
			Array zrangeData = zrangeVar.read();
			//NCdump.printArray(zrangeData, zrange, System.out, null);
			
			String spacing = "spacing";
			Variable spacingVar = ncfile.findVariable(spacing);
			Array spacingData = spacingVar.read();
			//NCdump.printArray(spacingData, spacing, System.out, null);
			
			String dimension = "dimension";
			Variable dimensionVar = ncfile.findVariable(dimension);
			Array dimensionData = dimensionVar.read();
			//NCdump.printArray(dimensionData, dimension, System.out, null);
			
			String z = "z";
			Variable zVar = ncfile.findVariable(z);
			Array zData = zVar.read();
			//NCdump.printArray(zData, z, System.out, null);
			
			int count = 0;
			//for (int x=0;x<94;x++)
			for (int x=0;x<108;x++)  // 106.92 km
			{
				//for (int y=0;y<108;y++)
				for (int y=0;y<94;y++) // 93.06 km
				{
					double item = zData.getDouble(count);
					long percent = Math.round(item * 100);
					String percentStr = new Integer((int)percent).toString();
					if (percentStr.length() == 1)
					{
						percentStr = "0" + percentStr;
					}
					if (percentStr.equals("00"))
					{
						percentStr = "  ";
					}
				
					//System.out.println(x + " " + y + " " + item);
					System.out.print( " " + percentStr);
					count ++;
				}
				System.out.println();
			}
			
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

}
