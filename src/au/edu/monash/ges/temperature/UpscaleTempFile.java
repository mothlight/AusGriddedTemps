package au.edu.monash.ges.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dods.dap.Float32PrimitiveVector;
import dods.dap.Float64PrimitiveVector;
import dods.dap.PrimitiveVector;

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

public class UpscaleTempFile
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		UpscaleTempFile readTemp = new UpscaleTempFile();
		readTemp.readTempDataFile();

	}
	
	public void readTempDataFile()
	{
		
		String filename = "TempMaxMinMelbourne-Jan1980.nc";
		NetcdfFile ncfile = null;
		try
		{
			ncfile = NetcdfFile.open(filename);
			
			
			String latitude = "latitude";
			Variable latitudeVar = ncfile.findVariable(latitude);
			Array latitudeData = latitudeVar.read();
			NCdump.printArray(latitudeData, latitude, System.out, null);
			
			 final int NLAT = (int)latitudeData.getSize();			 
			 ArrayFloat.D1 biggerLats = new ArrayFloat.D1(NLAT * 5);
			 
			
			
			int latSize=NLAT;
			int longSize=0;
			int latStart=0;
			int longStart=0;
			int longEnd = 0;
			
			int latCount = 0;
			for (int i = latStart; i < latStart + latSize ; i++)
			{
				biggerLats.set(latCount, ( latitudeData.getFloat(i)));
				latCount ++;
				biggerLats.set(latCount, ( latitudeData.getFloat(i)) - 0.01f);
				latCount ++;
				biggerLats.set(latCount, ( latitudeData.getFloat(i)) - 0.02f);
				latCount ++;
				biggerLats.set(latCount, ( latitudeData.getFloat(i)) - 0.03f);
				latCount ++;
				biggerLats.set(latCount, ( latitudeData.getFloat(i)) - 0.04f);
				latCount ++;
			}
			
			
			String longitude = "longitude";
			Variable longitudeVar = ncfile.findVariable(latitude);
			Array longitudeData = longitudeVar.read();
			NCdump.printArray(longitudeData, longitude, System.out, null);
			
			final int NLON = (int)longitudeData.getSize();
			ArrayFloat.D1 biggerLongs = new ArrayFloat.D1(NLON * 5);
			int longCount = 0;
			for (int i = latStart; i < latStart + latSize ; i++)
			{
				biggerLongs.set(longCount, ( longitudeData.getFloat(i)));
				longCount ++;
				biggerLongs.set(longCount, ( longitudeData.getFloat(i)) - 0.01f);
				longCount ++;
				biggerLongs.set(longCount, ( longitudeData.getFloat(i)) - 0.02f);
				longCount ++;
				biggerLongs.set(longCount, ( longitudeData.getFloat(i)) - 0.03f);
				longCount ++;
				biggerLongs.set(longCount, ( longitudeData.getFloat(i)) - 0.04f);
				longCount ++;
			}
			
			String time = "time";
			Variable timeVar = ncfile.findVariable(time);
			Array timeData = timeVar.read();
			NCdump.printArray(timeData, time, System.out, null);
			
			String temperature_max = "temperature_max";
			Variable temperature_maxVar = ncfile.findVariable(temperature_max);
			Array temperature_maxData = temperature_maxVar.read();
			NCdump.printArray(temperature_maxData, temperature_max, System.out, null);
			
			
			ArrayFloat.D1 biggertemperature_max = new ArrayFloat.D1( (NLON * 5) * (NLAT * 5) );
			int tempCount = 0;
			for (int i = latStart; i < latStart + latSize ; i++)
			{
				biggertemperature_max.set(longCount, ( temperature_maxData.getFloat(i)));
				longCount ++;
				biggertemperature_max.set(longCount, ( temperature_maxData.getFloat(i)));
				longCount ++;
				biggertemperature_max.set(longCount, ( temperature_maxData.getFloat(i)));
				longCount ++;
				biggertemperature_max.set(longCount, ( temperature_maxData.getFloat(i)));
				longCount ++;
				biggertemperature_max.set(longCount, ( temperature_maxData.getFloat(i)));
				longCount ++;
			}
			
			
			
			String temperature_min = "temperature_min";
			Variable temperature_minVar = ncfile.findVariable(temperature_min);
			Array temperature_minData = temperature_minVar.read();
			NCdump.printArray(temperature_minData, temperature_min, System.out, null);
			
			String temperature_mean = "temperature_mean";
			Variable temperature_meanVar = ncfile.findVariable(temperature_mean);
			Array temperature_meanData = temperature_meanVar.read();
			NCdump.printArray(temperature_meanData, temperature_mean, System.out, null);
			
			String morbidity = "morbidity";
			Variable morbidityVar = ncfile.findVariable(morbidity);
			Array morbidityData = morbidityVar.read();
			NCdump.printArray(morbidityData, morbidity, System.out, null);
			
			String mortality = "mortality";
			Variable mortalityVar = ncfile.findVariable(mortality);
			Array mortalityData = mortalityVar.read();
			NCdump.printArray(mortalityData, mortality, System.out, null);
			
			
			
			
			
			
			
			
//			String yrange = "GDAL_Geographics";
//			Variable yrangeVar = ncfile.findVariable(yrange);
//			Array yrangeData = yrangeVar.read();
//			NCdump.printArray(yrangeData, yrange, System.out, null);
//			
//			List<ucar.nc2.Attribute> yattr = yrangeVar.getAttributes();
//			for (ucar.nc2.Attribute attr : yattr)
//			{
//				String attrName = attr.getName();
//				Array  attrValue = attr.getValues();
//				attrValue.getElementType();
//				double value = attrValue.getDouble(0);
//			
//				System.out.println(attrName + "=" + value);
//			}
			
			
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
	
	
	
	
	
	public void writeFile(PrimitiveVector latitudeVector,
			PrimitiveVector longitudeVector, PrimitiveVector timeVector,
			PrimitiveVector tempMaxDayVector, PrimitiveVector tempMinDayVector, String filename) throws Exception
	{
		int latSize=0;
		int longSize=0;
		int latStart=0;
		int longStart=0;
		int longEnd = 0;
		int latEnd = 0;
		
		//for Melbourne
		//  37.7833° S, 144.9667° E

		 final int NLVL = 1;
		 final int NLAT = latitudeVector.getLength();
		 final int NLON = longitudeVector.getLength();
		 final int NREC = 1;
		 final int TIME = timeVector.getLength();
		 
		 ArrayFloat.D1 biggerLats = new ArrayFloat.D1(NLAT * 5);
		 ArrayFloat.D1 biggerLons = new ArrayFloat.D1(NLON * 5);
		 
		int latCount = 0;
		for (int i = latStart; i < latStart + latSize ; i++)
		{
			biggerLats.set(latCount, ((Float32PrimitiveVector) latitudeVector).getValue(i));
			latCount ++;
			biggerLats.set(latCount, ((Float32PrimitiveVector) latitudeVector).getValue(i) + 0.01f);
			latCount ++;
			biggerLats.set(latCount, ((Float32PrimitiveVector) latitudeVector).getValue(i) + 0.02f);
			latCount ++;
			biggerLats.set(latCount, ((Float32PrimitiveVector) latitudeVector).getValue(i) + 0.03f);
			latCount ++;
			biggerLats.set(latCount, ((Float32PrimitiveVector) latitudeVector).getValue(i) + 0.04f);
			latCount ++;
		}
		
		int longCount = 0;
		for (int i = longStart; i < longStart + longSize ; i++)
		{
			
			
			biggerLons.set(longCount, ((Float32PrimitiveVector) longitudeVector).getValue(i));
			longCount ++;
			biggerLons.set(longCount, ((Float32PrimitiveVector) longitudeVector).getValue(i) + 0.01f);
			longCount ++;
			biggerLons.set(longCount, ((Float32PrimitiveVector) longitudeVector).getValue(i) + 0.02f);
			longCount ++;
			biggerLons.set(longCount, ((Float32PrimitiveVector) longitudeVector).getValue(i) + 0.03f);
			longCount ++;
			biggerLons.set(longCount, ((Float32PrimitiveVector) longitudeVector).getValue(i) + 0.04f);
			longCount ++;
		}
		 
		
		 
//		 final int latCenter = 560;
//		 //final int longCenter = 656;
//		 final int longCenter = 660;
//		 
//		 final int latSize = 30;  //y
//		 final int longSize = 30;  //x
//		 
//		 
////		 final int latCenter = 680/2;
////		 final int longCenter = 886/2;
////		 
////		 final int latSize = 670;
////		 final int longSize = 870;
//		 
//		 final int latStart = latCenter - (latSize/2);
//		 final int latEnd = latCenter + (latSize/2);
//		 //final int latEnd = 570;
//		 //final int longStart = 646;
//		 final int longStart = longCenter - (longSize/2);
//		 final int longEnd = longCenter + (longSize/2);
//		 //final int longEnd = 666;
//		 //final int latSize = latEnd - latStart;
//		 //final int longSize = longEnd - longStart;

		// Create the file.
		//String filename = "TempMaxMinMelbourne.nc";
		NetcdfFileWriteable dataFile = null;
		//boolean existingFile = false;
		
		ArrayDouble.D1 time = null;
		ArrayFloat.D4 dataTempMax = null;
		ArrayFloat.D4 dataTempMin = null;
		ArrayFloat.D4 morbidityArray = null;
		ArrayFloat.D4 mortalityArray = null;
		ArrayFloat.D4 dataTempMean = null;
		
		double timeV =0;
		for (int i = 0; i < timeVector.getLength(); i++)
		{
			timeV = ((Float64PrimitiveVector) timeVector).getValue(i);			
		}

		try
		{
//			try
//			{
//				dataFile = NetcdfFileWriteable.openExisting(filename);	
//				existingFile = true;
//				System.out.println("File exists");
//			}
//			catch (IOException e)
//			{
//				System.out.println("File doesn't exist");
//				existingFile = false;
//			}
//			
//			if (!existingFile)
//			{
				// Create new netcdf-3 file with the given filename
				dataFile = NetcdfFileWriteable.createNew(filename, false);

				// add dimensions where time dimension is unlimit
				Dimension lvlDim = dataFile.addDimension("level", NLVL);
				//Dimension latDim = dataFile.addDimension("latitude", NLAT);
				Dimension latDim = dataFile.addDimension("latitude", latSize);
				//Dimension lonDim = dataFile.addDimension("longitude", NLON);
				Dimension lonDim = dataFile.addDimension("longitude", longSize);
				Dimension timeDim = dataFile.addUnlimitedDimension("time");

				ArrayList<Dimension> dims = null;

				// Define the coordinate variables.
				dataFile.addVariable("latitude", DataType.FLOAT, new Dimension[]
				{ latDim });
				dataFile.addVariable("longitude", DataType.FLOAT, new Dimension[]
				{ lonDim });
				dataFile.addVariable("time", DataType.DOUBLE, new Dimension[]
				{ timeDim });

				// Define units attributes for data variables.
				dataFile.addVariableAttribute("latitude", "units", "degrees_north");
				dataFile.addVariableAttribute("longitude", "units", "degrees_east");
				dataFile.addVariableAttribute("time", "units", "days");

				// Define the netCDF variables for the pressure and temperature data.
				dims = new ArrayList<Dimension>();
				dims.add(timeDim);
				dims.add(lvlDim);
				dims.add(latDim);
				dims.add(lonDim);
				// dataFile.addVariable("pressure", DataType.FLOAT, dims);
				dataFile.addVariable("temperature_max", DataType.FLOAT, dims);
				dataFile.addVariable("temperature_min", DataType.FLOAT, dims);
				dataFile.addVariable("temperature_mean", DataType.FLOAT, dims);
				
				dataFile.addVariable("morbidity", DataType.FLOAT, dims);
				dataFile.addVariable("mortality", DataType.FLOAT, dims);

				// Define units attributes for data variables.
				//dataFile.addVariableAttribute("pressure", "units", "hPa");
				dataFile.addVariableAttribute("temperature_max", "units", "celsius");
				dataFile.addVariableAttribute("temperature_min", "units", "celsius");
				dataFile.addVariableAttribute("temperature_mean", "units", "celsius");
				
				dataFile.addVariableAttribute("morbidity", "units", "calculated");
				dataFile.addVariableAttribute("mortality", "units", "calculated");
				
				ArrayFloat.D1 lats = new ArrayFloat.D1(latDim.getLength());
				ArrayFloat.D1 lons = new ArrayFloat.D1(lonDim.getLength());
				
				//Array timeData = Array.factory( DataType.INT, new int[] {1});
				//timeData.setInt(ima, value);
				
				time = new ArrayDouble.D1(timeDim.getLength());
				time = (ArrayDouble.D1)ArrayDouble.D1.factory(DataType.DOUBLE, new int[] {1});
				time.set(0, timeV);
				
				// Create the file. At this point the (empty) file will be written to disk
				dataFile.create();
				
				//for (int i = 0; i < latitudeVector.getLength(); i++)
				 latCount = 0;
				for (int i = latStart; i < latStart + latSize ; i++)
				{
					lats.set(latCount,	((Float32PrimitiveVector) latitudeVector).getValue(i));
					latCount ++;
				}

				//for (int i = 0; i < longitudeVector.getLength(); i++)
				 longCount = 0;
				for (int i = longStart; i < longStart + longSize ; i++)
				{
					lons.set(longCount, ((Float32PrimitiveVector) longitudeVector).getValue(i));
					longCount ++;
				}
				dataFile.write("latitude", lats);
				dataFile.write("longitude", lons);
				dataFile.write("time", time);
				
				for (int i = 0; i < timeVector.getLength(); i++)
				{
					timeV = ((Float64PrimitiveVector) timeVector).getValue(i);
					time.set(i, timeV);
				}
				// This will write our surface pressure and surface temperature data.
				//dataTempMax = new ArrayFloat.D4(NREC, NLVL, NLAT, NLON);
				dataTempMax = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
				//dataTempMin = new ArrayFloat.D4(NREC, NLVL, NLAT, NLON);
				dataTempMin = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
				dataTempMean = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
				
				morbidityArray = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
				mortalityArray = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
				
				int count = 0;
				for (int record = 0; record < NREC; record++)
				{
					for (int lvl = 0; lvl < NLVL; lvl++)
					{
						int latLoopCount = 0;
						for (int lat = 0; lat < NLAT; lat++)
						//for (int lat = 0; lat < latStart + latSize - 1; lat++)
						{
							int lonLoopCount = 0;
							for (int lon = 0; lon < NLON; lon++)
							//for (int lon = 0; lon < longSize + longSize -1; lon++)
							{								
								if (lon < longStart || lon >= longEnd || lat < latStart || lat >= latEnd)
								{
									count++;
									continue;
								}
								//System.out.println("record="+record+",lvl=" + lvl +",lat="+lat+",lon="+lon
								//		+",count="+count+",latLoopCount="+latLoopCount+",lonLoopCount="+lonLoopCount);
								
								// dataPres.set(record, lvl, lat, lon, SAMPLE_PRESSURE + i);
								float tempMax = ((Float32PrimitiveVector) tempMaxDayVector).getValue(count);
								dataTempMax.set(record, lvl, latLoopCount, lonLoopCount, tempMax);								
								
								float tempMin = ((Float32PrimitiveVector) tempMinDayVector).getValue(count);
								dataTempMin.set(record, lvl, latLoopCount, lonLoopCount, tempMin);
								
								//formula is 
								// Tmean = (Tmax + Tmin) / 2
								// Mortality = 0.1 * Tmean
								// Morbidity = 0.01 * Tmean
								
								// new formulas
								// y = ax^c , a = 1.0048157,c = 8.0023051
								// y = a + bx^c, b = 1.43463393 e-13
								
								float a = 1.0048157f;
								float b = 1.43463393e-13f;
								float c = 8.0023051f;
								
								float tMean = (tempMax + tempMin ) / 2;	
								dataTempMean.set(record, lvl, latLoopCount, lonLoopCount, tMean);
								
								//float morbidityValue = (float) (0.1 * tMean);		
								float morbidityValue = a * (float)Math.pow(tMean, c);
								morbidityArray.set(record, lvl, latLoopCount, lonLoopCount, morbidityValue);
								
								//float mortalityValue = (float) (0.01 * tMean);
								float mortalityValue = a  + b * (float)Math.pow(tMean, c);
								mortalityArray.set(record, lvl, latLoopCount, lonLoopCount, mortalityValue);
								
								count++;								
								lonLoopCount ++;
								if (lonLoopCount == longSize)
								{
									latLoopCount ++;	
								}
								
							}
														
						}						
					}
				}
				
				// A newly created Java integer array to be initialized to zeros.
				int[] origin = new int[4];

				dataFile.write("temperature_max", origin, dataTempMax);			
				dataFile.write("temperature_min", origin, dataTempMin);
				dataFile.write("temperature_mean", origin, dataTempMean);
				
				dataFile.write("mortality", origin, mortalityArray);
				dataFile.write("morbidity", origin, morbidityArray);
				
				dataFile.flush();
//			}
//			else
//			{
//				//already opened in the try/catch
//				//add some data (1 days worth)
//			    Variable timeVar = dataFile.findVariable("time");	
//			    
//			    int[] timeVarShape = timeVar.getShape();
//			    int[] origin = new int[1];
//			    Array timeVarReadArray = timeVar.read(origin, timeVarShape);
//			    ArrayDouble.D1 timeArray = (ArrayDouble.D1) timeVarReadArray;
//			    time = (ArrayDouble.D1)Array.factory(DataType.DOUBLE, new int[] {timeVar.getShape()[0] + 1});
//			    for (int i=0;i<timeVar.getShape()[0];i++)
//			    {			    	
//			    	time.set(i, timeArray.get(i));
//			    }
//			    
//			    time.set(timeVar.getShape(0), timeV);
//			    dataFile.write("time", time);
//			    
//				for (int i = 0; i < timeVector.getLength(); i++)
//				{
//					timeV = ((Float64PrimitiveVector) timeVector).getValue(i);
//					time.set(i, timeV);
//				}
//				// This will write our surface pressure and surface temperature data.
//				dataTempMax = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
//				dataTempMin = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
//				dataTempMean = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
//				morbidityArray = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
//				mortalityArray = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
//				
//
//				int count = 0;
//				for (int record = 0; record < NREC; record++)
//				{
//					for (int lvl = 0; lvl < NLVL; lvl++)
//					{
//						int latLoopCount = 0;
//						for (int lat = 0; lat < NLAT; lat++)
//						//for (int lat = 0; lat < latStart + latSize - 1; lat++)
//						{
//							int lonLoopCount = 0;
//							for (int lon = 0; lon < NLON; lon++)
//							//for (int lon = 0; lon < longSize + longSize -1; lon++)
//							{								
//								if (lon < longStart || lon >= longEnd || lat < latStart || lat >= latEnd)
//								{
//									count++;
//									continue;
//								}
//								//System.out.println("record="+record+",lvl=" + lvl +",lat="+lat+",lon="+lon
//								//		+",count="+count+",latLoopCount="+latLoopCount+",lonLoopCount="+lonLoopCount);
//								
//								// dataPres.set(record, lvl, lat, lon, SAMPLE_PRESSURE + i);
//								float tempMax = ((Float32PrimitiveVector) tempMaxDayVector).getValue(count);
//								dataTempMax.set(record, lvl, latLoopCount, lonLoopCount, tempMax);								
//								
//								float tempMin = ((Float32PrimitiveVector) tempMinDayVector).getValue(count);
//								dataTempMin.set(record, lvl, latLoopCount, lonLoopCount, tempMin);
//								
//								//formula is 
//								// Tmean = (Tmax + Tmin) / 2
//								// Mortality = 0.1 * Tmean
//								// Morbidity = 0.01 * Tmean
//								
//								float tMean = (tempMax + tempMin )/2;	
//								dataTempMean.set(record, lvl, latLoopCount, lonLoopCount, tMean);
//								
//								float morbidityValue = (float) (0.1 * tMean);								
//								morbidityArray.set(record, lvl, latLoopCount, lonLoopCount, morbidityValue);
//								
//								float mortalityValue = (float) (0.01 * tMean);
//								mortalityArray.set(record, lvl, latLoopCount, lonLoopCount, mortalityValue);
//								
//								count++;								
//								lonLoopCount ++;
//								if (lonLoopCount == longSize)
//								{
//									latLoopCount ++;	
//								}
//								
//							}
//														
//						}						
//					}
//				}
//				
//				// A newly created Java integer array to be initialized to zeros.
//				origin = new int[4];
//				origin[0] = timeVar.getShape(0)-1;
//
//				dataFile.write("temperature_max", origin, dataTempMax);			
//				dataFile.write("temperature_min", origin, dataTempMin);
//				dataFile.write("temperature_mean", origin, dataTempMean);
//				
//				dataFile.write("mortality", origin, mortalityArray);
//				dataFile.write("morbidity", origin, morbidityArray);
//				
//				dataFile.flush();
//				
//			}
			
		} catch (IOException e)
		{
			e.printStackTrace(System.err);
		} catch (InvalidRangeException e)
		{
			e.printStackTrace(System.err);
		} finally
		{
			if (dataFile != null)
				try
				{
					dataFile.close();
				} catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
		}
		System.out.println("*** SUCCESS writing example file " + filename);
	}
}
