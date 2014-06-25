import java.net.*;
import java.nio.charset.Charset;
import java.io.*;

import org.json.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TocHw3
{
  public static void main(String[] args)
  {
	 
    String output  = getUrlContents(args[0],args[1],args[2],args[3]);
    //System.out.println(output);
  }
 
  private static String getUrlContents(String theUrl,String town,String road_name,String year)
  {
    StringBuilder content = new StringBuilder();
 
    try
    {
      // create a url object
      URL url = new URL(theUrl);
 
      // create a urlconnection object
      URLConnection urlConnection = url.openConnection();
      
      int total_price=0;
      int count=0;
      System.out.println("Input:"+theUrl+town+road_name+year);
      JSONArray jsonRealPrice = new JSONArray(new JSONTokener(new InputStreamReader(urlConnection.getInputStream(),Charset.forName("UTF-8"))));
      for(int x=0;x<jsonRealPrice.length();x++)
      {
    	  JSONObject obj = jsonRealPrice.getJSONObject(x);
    	  if(obj.getString("鄉鎮市區").equals(town)&&compare_road(obj,road_name)&&compare_year(obj,year))
    	  {
    		  total_price+=obj.getInt("總價元");
    		  count++;
    	  }    	  
      }
      
      System.out.println("Output:"+(int)(total_price/count));

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return content.toString();
  }
  public static boolean compare_road(JSONObject obj,String string) throws JSONException
  {
	  Pattern pattern=Pattern.compile(".*"+string+".*");
      Matcher matcher =pattern.matcher(obj.getString("土地區段位置或建物區門牌"));
      while(matcher.find())
    	  return true;
      
		  return false;
  
  }
  public static boolean compare_year(JSONObject obj,String string) throws JSONException
  {
	  int last_year = Integer.parseInt(string)*100;
	  if(obj.getInt("交易年月")>=last_year)
	  {
		  return true;
	  }
	  else
		  return false;
  }
}