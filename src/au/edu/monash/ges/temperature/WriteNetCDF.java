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
import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.Variable;

public class WriteNetCDF
{

	private String filename = "DefaultMinMaxFilename.nc";
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		final int NLVL = 1;
//		final int NLAT = 6;
//		final int NLON = 12;
//		final int NREC = 2;
//		double time_in = 22222;

		// WriteNetCDF writeNetCDF = new WriteNetCDF();
		// try
		// {
		// writeNetCDF.writeFile(NLVL, NLAT, NLON, NREC, time_in);
		// } catch (Exception e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public void writeFile(PrimitiveVector latitudeVector,
			PrimitiveVector longitudeVector, PrimitiveVector timeVector,
			PrimitiveVector tempMaxDayVector) throws Exception
	{

		 final int NLVL = 1;
		 final int NLAT = latitudeVector.getLength();
		 final int NLON = longitudeVector.getLength();
		 final int NREC = 1;
		 final int TIME = timeVector.getLength();

		//final float SAMPLE_PRESSURE = 900.0f;
		// final float SAMPLE_TEMP = 9.0f;
		// final float START_LAT = 25.0f;
		// final float START_LON = -125.0f;

		// Create the file.
		//String filename = "pres_temp_4D.nc";
		NetcdfFileWriteable dataFile = null;
		boolean existingFile = false;
		
		ArrayDouble.D1 time = null;
		ArrayFloat.D4 dataTemp = null;
		
		double timeV =0;
		for (int i = 0; i < timeVector.getLength(); i++)
		{
			timeV = ((Float64PrimitiveVector) timeVector).getValue(i);			
		}

		try
		{
			try
			{
				dataFile = NetcdfFileWriteable.openExisting(filename);	
				existingFile = true;
				System.out.println("File exists");
			}
			catch (IOException e)
			{
				System.out.println("File doesn't exist");
				//e.printStackTrace();
				existingFile = false;
			}
			
			if (!existingFile)
			{
				// Create new netcdf-3 file with the given filename
				dataFile = NetcdfFileWriteable.createNew(filename, false);

				// add dimensions where time dimension is unlimit
				Dimension lvlDim = dataFile.addDimension("level", NLVL);
				Dimension latDim = dataFile.addDimension("latitude", NLAT);
				Dimension lonDim = dataFile.addDimension("longitude", NLON);
				Dimension timeDim = dataFile.addUnlimitedDimension("time");
				//Dimension timeDim = dataFile.addDimension("time", TIME);

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

				// Define the netCDF variables for the pressure and temperature
				// data.
				dims = new ArrayList<Dimension>();
				dims.add(timeDim);
				dims.add(lvlDim);
				dims.add(latDim);
				dims.add(lonDim);
				// dataFile.addVariable("pressure", DataType.FLOAT, dims);
				dataFile.addVariable("temperature", DataType.FLOAT, dims);

				// Define units attributes for data variables.
				//dataFile.addVariableAttribute("pressure", "units", "hPa");
				dataFile.addVariableAttribute("temperature", "units", "celsius");
				
				ArrayFloat.D1 lats = new ArrayFloat.D1(latDim.getLength());
				ArrayFloat.D1 lons = new ArrayFloat.D1(lonDim.getLength());
				
				//Array timeData = Array.factory( DataType.INT, new int[] {1});
				//timeData.setInt(ima, value);
				
				time = new ArrayDouble.D1(timeDim.getLength());
				time = (ArrayDouble.D1)ArrayDouble.D1.factory(DataType.DOUBLE, new int[] {1});
				time.set(0, timeV);
				
				// Create the file. At this point the (empty) file will be written
				// to disk
				dataFile.create();
				
				for (int i = 0; i < latitudeVector.getLength(); i++)
				{
					lats.set(i,	((Float32PrimitiveVector) latitudeVector).getValue(i));
				}

				for (int i = 0; i < longitudeVector.getLength(); i++)
				{
					lons.set(i, ((Float32PrimitiveVector) longitudeVector).getValue(i));
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
				dataTemp = new ArrayFloat.D4(NREC, NLVL, NLAT, NLON);
				//ArrayFloat.D4 dataPres = new ArrayFloat.D4(NREC, lvlDim.getLength(), latDim.getLength(), lonDim.getLength());
				int count = 0;
				for (int record = 0; record < NREC; record++)
				{
					for (int lvl = 0; lvl < NLVL; lvl++)
						for (int lat = 0; lat < NLAT; lat++)
							for (int lon = 0; lon < NLON; lon++)
							{
								// dataPres.set(record, lvl, lat, lon, SAMPLE_PRESSURE + i);
								float temp = ((Float32PrimitiveVector) tempMaxDayVector).getValue(count);
								dataTemp.set(record, lvl, lat, lon, temp);
								count++;
							}
				}
//				// A newly created Java integer array to be initialized to zeros.
				int[] origin = new int[4];
				// dataFile.write("pressure", origin, dataPres);
				dataFile.write("temperature", origin, dataTemp);			
				dataFile.flush();
								
			}
			else
			{
				

				
				//already opened in the try/catch
				//dataFile = NetcdfFileWriteable.createNew(filename, false);
				
				//List<Variable> dataFileVariables = dataFile.getVariables();
				//time = new ArrayDouble.D1(TIME);
				
				//add some data (1 days worth)
			    Variable timeVar = dataFile.findVariable("time");	
			    
			    int[] timeVarShape = timeVar.getShape();
			    int[] origin = new int[1];
			    //Array timeVarReadArray = timeVar.read(origin, timeVarShape).reduce();
			    Array timeVarReadArray = timeVar.read(origin, timeVarShape);
  		        //ArrayDouble.D0 timeArray = (ArrayDouble.D0) timeVarReadArray;
			    
			    ArrayDouble.D1 timeArray = (ArrayDouble.D1) timeVarReadArray;
			    
			    //System.out.println(timeVar.getShape(0));
			      
			    time = (ArrayDouble.D1)Array.factory(DataType.DOUBLE, new int[] {timeVar.getShape()[0] + 1});
			    for (int i=0;i<timeVar.getShape()[0];i++)
			    {			    	
			    	//time.set(i, timeArray.get());
			    	time.set(i, timeArray.get(i));
			    }
			    
			    //ArrayDouble.D1 timeData = (ArrayDouble.D1)timeVar.read();
			    //System.out.println(timeData.getShape());
			    //
			    //timeVarShape[0] = timeVarShape[0] + 1;
			    //timeData.reshape(timeVarShape);
			    time.set(timeVar.getShape(0), timeV);
			    dataFile.write("time", time);
			    			    
				for (int i = 0; i < timeVector.getLength(); i++)
				{
					timeV = ((Float64PrimitiveVector) timeVector).getValue(i);
					time.set(i, timeV);
				}
				// This will write our surface pressure and surface temperature data.
				dataTemp = new ArrayFloat.D4(NREC, NLVL, NLAT, NLON);
				//ArrayFloat.D4 dataPres = new ArrayFloat.D4(NREC, lvlDim.getLength(), latDim.getLength(), lonDim.getLength());
				int count = 0;
				for (int record = 0; record < NREC; record++)
				{
					for (int lvl = 0; lvl < NLVL; lvl++)
						for (int lat = 0; lat < NLAT; lat++)
							for (int lon = 0; lon < NLON; lon++)
							{
								// dataPres.set(record, lvl, lat, lon, SAMPLE_PRESSURE + i);
								float temp = ((Float32PrimitiveVector) tempMaxDayVector).getValue(count);
								dataTemp.set(record, lvl, lat, lon, temp);
								count++;
							}
				}
////				// A newly created Java integer array to be initialized to zeros.
				origin = new int[4];
				origin[0] = timeVar.getShape(0)-1;
//				// dataFile.write("pressure", origin, dataPres);
				dataFile.write("temperature", origin, dataTemp);			
				dataFile.flush();
				
			}
			
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
	
	
	public void writeFile(PrimitiveVector latitudeVector,
			PrimitiveVector longitudeVector, PrimitiveVector timeVector,
			PrimitiveVector tempMaxDayVector, PrimitiveVector tempMinDayVector) throws Exception
	{
		
		//for Melbourne
		//  37.7833° S, 144.9667° E

		 final int NLVL = 1;
		 final int NLAT = latitudeVector.getLength();
		 final int NLON = longitudeVector.getLength();
		 final int NREC = 1;
		 final int TIME = timeVector.getLength();
		 
		 final int latCenter = 560;
		 //final int longCenter = 656;
		 final int longCenter = 660;
		 
		 final int latSize = 30;  //y
		 final int longSize = 30;  //x
		 
		 
//		 final int latCenter = 680/2;
//		 final int longCenter = 886/2;
//		 
//		 final int latSize = 670;
//		 final int longSize = 870;
		 
		 final int latStart = latCenter - (latSize/2);
		 final int latEnd = latCenter + (latSize/2);
		 //final int latEnd = 570;
		 //final int longStart = 646;
		 final int longStart = longCenter - (longSize/2);
		 final int longEnd = longCenter + (longSize/2);
		 //final int longEnd = 666;
		 //final int latSize = latEnd - latStart;
		 //final int longSize = longEnd - longStart;

		// Create the file.
		//String filename = "TempMaxMinMelbourne.nc";
		NetcdfFileWriteable dataFile = null;
		boolean existingFile = false;
		
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
			try
			{
				dataFile = NetcdfFileWriteable.openExisting(filename);	
				existingFile = true;
				System.out.println("File exists");
			}
			catch (IOException e)
			{
				System.out.println("File doesn't exist");
				existingFile = false;
			}
			
			if (!existingFile)
			{
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
				int latCount = 0;
				for (int i = latStart; i < latStart + latSize ; i++)
				{
					lats.set(latCount,	((Float32PrimitiveVector) latitudeVector).getValue(i));
					latCount ++;
				}

				//for (int i = 0; i < longitudeVector.getLength(); i++)
				int longCount = 0;
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
			}
			else
			{
				//already opened in the try/catch
				//add some data (1 days worth)
			    Variable timeVar = dataFile.findVariable("time");	
			    
			    int[] timeVarShape = timeVar.getShape();
			    int[] origin = new int[1];
			    Array timeVarReadArray = timeVar.read(origin, timeVarShape);
			    ArrayDouble.D1 timeArray = (ArrayDouble.D1) timeVarReadArray;
			    time = (ArrayDouble.D1)Array.factory(DataType.DOUBLE, new int[] {timeVar.getShape()[0] + 1});
			    for (int i=0;i<timeVar.getShape()[0];i++)
			    {			    	
			    	time.set(i, timeArray.get(i));
			    }
			    
			    time.set(timeVar.getShape(0), timeV);
			    dataFile.write("time", time);
			    
				for (int i = 0; i < timeVector.getLength(); i++)
				{
					timeV = ((Float64PrimitiveVector) timeVector).getValue(i);
					time.set(i, timeV);
				}
				// This will write our surface pressure and surface temperature data.
				dataTempMax = new ArrayFloat.D4(NREC, NLVL, latSize, longSize);
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
								
								float tMean = (tempMax + tempMin )/2;	
								dataTempMean.set(record, lvl, latLoopCount, lonLoopCount, tMean);
								
								float morbidityValue = (float) (0.1 * tMean);								
								morbidityArray.set(record, lvl, latLoopCount, lonLoopCount, morbidityValue);
								
								float mortalityValue = (float) (0.01 * tMean);
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
				origin = new int[4];
				origin[0] = timeVar.getShape(0)-1;

				dataFile.write("temperature_max", origin, dataTempMax);			
				dataFile.write("temperature_min", origin, dataTempMin);
				dataFile.write("temperature_mean", origin, dataTempMean);
				
				dataFile.write("mortality", origin, mortalityArray);
				dataFile.write("morbidity", origin, morbidityArray);
				
				dataFile.flush();
				
			}
			
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

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}	

}
