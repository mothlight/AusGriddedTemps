package au.edu.monash.ges.temperature;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.Attribute;

public class OpenDapDir
{

//	String baseUrl = "http://opendap.bom.gov.au:8080/thredds/catalog/daily_maximum_temperature_5km/";
//	String baseUrlOpendap = "http://opendap.bom.gov.au:8080/thredds/dodsC/daily_maximum_temperature_5km/";
	TreeMap<String, ArrayList<String>> filesForYears = new TreeMap<String, ArrayList<String>>();
	ArrayList<String> yearUrls = new ArrayList<String>();
	
	String baseUrl;
	String baseUrlOpendap;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		OpenDapDir openDapDir = new OpenDapDir();
//		TreeMap<String, ArrayList<String>> filesUrls = openDapDir.getFilesForYears(1970, 1971);
//		System.out.println(filesUrls.toString());
	}
	
	public OpenDapDir(String baseUrl, String baseUrlOpendap)
	{
		super();
		this.baseUrl = baseUrl;
		this.baseUrlOpendap = baseUrlOpendap;
	}
		
	public TreeMap<String, ArrayList<String>> getFilesForYears(int beginningYear, int endingYear)
	{
		String mainUrl = baseUrl + "catalog.html";
		getYearsDirectories(mainUrl);
		
		for (String yearUrl : yearUrls)
		{
			String yearStr = yearUrl.substring(0, 4);
			Integer yearInt = new Integer(yearStr).intValue();
			if (  (yearInt == beginningYear || yearInt == endingYear)
					|| ( yearInt > beginningYear && yearInt < endingYear  ))
			{
				//System.out.println(yearUrl);
				getFilesForYear(yearUrl);
			}
		}
		
		//System.out.println(filesForYears.toString());
		return filesForYears;
	}
	
	public void getFilesForYear(String urlStr)
	{
		
		String searchUrl = baseUrl + urlStr ;
		try
		{
			URL url = new URL(searchUrl);
			SAXBuilder builder = new SAXBuilder("org.ccil.cowan.tagsoup.Parser");
			Document doc = builder.build(url);

			Element root = doc.getRootElement();
			listChildrenOfYears(root, 0);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void listChildrenOfYears(Element current, int depth)
	{

		//System.out.print(current.getName());

		List elementAttributes = current.getAttributes();
		Iterator<Attribute> attrItr = elementAttributes.iterator();
		while (attrItr.hasNext())
		{
			Attribute attr = attrItr.next();

			String attrName = attr.getName();
			String attrValue = attr.getValue();
			
			//System.out.println(attrName + " " + attrValue);
			if (attrName.equals("href"))
			{
				//System.out.println(attrName + " " + attrValue);	
				if (attrValue.startsWith("catalog.html?dataset=") && attrValue.contains("C-bawap."))
				{
					//System.out.println(attrName + " " + attrValue);
					int startOfYear = attrValue.indexOf("/") + 1;
					String year = attrValue.substring(startOfYear, startOfYear + 4);
					ArrayList<String> yearData = filesForYears.get(year);
					if (yearData == null)
					{
						yearData = new ArrayList<String>();
					}
					String filenameStr = attrValue.replace("catalog.html?dataset=bureau_tern_daily_maximum_temperature_5km/", baseUrlOpendap);
					filenameStr = filenameStr.replace("catalog.html?dataset=bureau_tern_daily_minimum_temperature_5km/", baseUrlOpendap);	
					
					yearData.add(filenameStr);
					filesForYears.put(year, yearData);
				}
			}

		}
		// System.out.println();

		List children = current.getChildren();
		Iterator iterator = children.iterator();
		while (iterator.hasNext())
		{
			Element child = (Element) iterator.next();
			listChildrenOfYears(child, depth + 1);
		}

	}

	public void getYearsDirectories(String urlStr)
	{

		try
		{
			URL url = new URL(urlStr);
			SAXBuilder builder = new SAXBuilder("org.ccil.cowan.tagsoup.Parser");
			Document doc = builder.build(url);

			Element root = doc.getRootElement();
			listChildrenOfYearUrls(root, 0);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void listChildrenOfYearUrls(Element current, int depth)
	{

		//System.out.print(current.getName());

		List elementAttributes = current.getAttributes();
		Iterator<Attribute> attrItr = elementAttributes.iterator();
		while (attrItr.hasNext())
		{
			Attribute attr = attrItr.next();

			String attrName = attr.getName();
			String attrValue = attr.getValue();
			
			//System.out.println(attrName + " " + attrValue);
			if (attrName.equals("href"))
			{
				
				if (attrValue.contains("/catalog.html") && !attrValue.startsWith("/"))
				{
					//System.out.println(attrName + " " + attrValue);	
					yearUrls.add(attrValue);
				}
			}

		}
		// System.out.println();

		List children = current.getChildren();
		Iterator iterator = children.iterator();
		while (iterator.hasNext())
		{
			Element child = (Element) iterator.next();
			listChildrenOfYearUrls(child, depth + 1);
		}

	}

	public String getBaseUrl()
	{
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public String getBaseUrlOpendap()
	{
		return baseUrlOpendap;
	}

	public void setBaseUrlOpendap(String baseUrlOpendap)
	{
		this.baseUrlOpendap = baseUrlOpendap;
	}

}
