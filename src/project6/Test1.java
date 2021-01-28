package project6;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

public class Test1
{
	private String userUrl;
	
	public String getUserUrl()
	{
		return userUrl;
	}

	public void setUserUrl(String userUrl)
	{
		this.userUrl = userUrl;
	}
	
	public static String getInput()
	{
		Scanner scan = new Scanner(System.in);
//		System.out.println("Please enter 1 for http or 2 for https.");
//		int userchoice = scan.nextInt();
		System.out.println("Please enter a web address to crawl. ex. http://www.google.com");
		String userUrl = scan.next();
		scan.close();
//		StringBuilder sb = new StringBuilder(userChoice);
//		sb.insert(0, "http://");
//		userChoice = sb.toString();
		return userUrl;
	}
	
	public static void traverse(List<String> pendingList,List<String> traversedList) throws Exception
	{
    	String currentUrl = null;
    		currentUrl = pendingList.get(0);

    	try
    	{
    	
	    	URL url = new URL(currentUrl);
	    	String inputLine;
	    	URLConnection urlConnection = url.openConnection();
	    	urlConnection.setConnectTimeout(200);
	    	BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	    	
	    	
	    	while ((inputLine = in.readLine()) != null)
	    	{
	    		Pattern p = Pattern.compile("a href=\"(.*?)\"", Pattern.DOTALL);
	    		Matcher m = p.matcher(inputLine);
	    		while (m.find())
	    	    {
	    			String result = null;
	    			StringBuilder sb = new StringBuilder(m.group(1));
					
	    			if(!m.group(1).contains("@") )
	    			{
	    				if(!m.group(1).contains("http"))
	        			{
	        				sb.insert(0, currentUrl);
	        			}
	    				
	    				result = sb.toString();
	    				
	    				if(pendingList.contains(result) || traversedList.contains(result))
	    				{
	    					result = null;
	    				}
	    			}	
	    			
	    			if(!(result == null))
	    			{
	    				pendingList.add(result);
	    			}
	    	    }	
	    	}
	    	in.close();
	    	
    	}
    	catch(Exception e)
    	{
    		StringBuilder sb = new StringBuilder(currentUrl);
    		sb.insert(0,"(Dead Link) ");
    		currentUrl = sb.toString();
    	}
    	pendingList.remove(0);
		traversedList.add(currentUrl);
	}
	
	public static void main(String[] args) throws Exception
	{
		List<String> traversedList = new ArrayList<String>();
    	List<String> pendingList = new ArrayList<String>();
		
//    	String userchoice = getInput();
//    	System.out.println(s);
    	
//    	String userchoice = "http://www.sonyclie.org/";
    	String userchoice = "http://www.xcomufo.com/";
    	pendingList.add(userchoice);
    	int i=0;
    	while(!pendingList.isEmpty() && i<200)
    	{
    		traverse(pendingList,traversedList);
    		i++;
    	}
    	
    	long total = pendingList.stream().count();
    	System.out.println("Number of Pending Links:" + total);
//    	for (String str : pendingList)
//        {
//    		System.out.println(str);
//    	}
    	
    	System.out.println("");
    	
    	long total2 = traversedList.stream().count();
    	System.out.println("Number of Traversed Links:" + total2);
//    	for (String str : traversedList)
//        {
//    		System.out.println(str);
//    	}
	}
}