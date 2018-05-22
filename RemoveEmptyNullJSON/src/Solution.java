// Giuseppe Stramandinoli

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String json = "{\r\n" + 
				"	\"first_name\": \"Jane\",\r\n" + 
				"	\"last_name\": \"Smith\",\r\n" + 
				"	\"email\": \"jane.smith@wyng.com\",\r\n" + 
				"	\"gender\": null,\r\n" + 
				"	\"invitations\": [{\r\n" + 
				"		\"from\": \"\",\r\n" + 
				"		\"code\": null\r\n" + 
				"	}],\r\n" + 
				"	\"company\": {\r\n" + 
				"		\"name\": \"\",\r\n" + 
				"		\"industries\": []\r\n" + 
				"	},\r\n" + 
				"	\"address\": {\r\n" + 
				"		\"city\": \"New York\",\r\n" + 
				"		\"state\": \"NY\",\r\n" + 
				"		\"zip\": \"10011\",\r\n" + 
				"		\"street\": \" \"\r\n" + 
				"	}\r\n" + 
				"}";
		
		
		try {
			// Create map of string
			Map<String, Object> array = parse(json);
			
			// convert map to jsonObject
			JSONObject j = new JSONObject(array);
			
			array = parse(j.toString());
			j = new JSONObject(array);
			
			System.out.print(j.toString(2));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Initial method for parsing
	public static Map<String, Object> parse(String jsonStr) 
	{
		// Intitialize result map
		Map<String, Object> result = null;
		
		// If json string is null, return null
		if (null != jsonStr) 
		{
			
			try 
			{
				// Convert json string to a json object
				JSONObject jsonObject = new JSONObject(jsonStr);
				
				// Parse json Object and save to result map
				result = parseJSONObject(jsonObject);
				
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	// Method to parse value
	private static Object parseValue(Object inputObject) throws JSONException 
	{	
		//Initialize Object
		Object outputObject = null;

		// if object is null, return null
		if (null != inputObject) 
		{
			if (inputObject instanceof JSONArray) 
			{
				// if object is a Json array, parse the array
				// return the json array
				outputObject = parseJSONArray((JSONArray) inputObject);
			} 
			else if (inputObject instanceof JSONObject) 
			{
				// if object is a Json object, parse the jsonobject
				// return the jsonobject
				outputObject = parseJSONObject((JSONObject) inputObject);
			} 
			else
			{	
				// else, object is not an array or json object
				// return the value
				outputObject = inputObject;
			}
		}
		return outputObject;
	}

	// Method to parse array
	private static List<Object> parseJSONArray(JSONArray jsonArray) throws JSONException 
	{
		// Intialize list
		List<Object> valueList = null;

		// if list is null, return null
		if (null != jsonArray) 
		{
			valueList = new ArrayList<Object>();

			// start loop to get the name/values of the array
			for (int i = 0; i < jsonArray.length(); i++) 
			{
				// temp object
				Object itemObject = jsonArray.get(i);
				
				// if object is null, do not add it to list
				if (null != itemObject) 
				{
					// if object is a string, check to see if value is empty or null
					if (itemObject instanceof String) 
					{
						String temp = (String) itemObject;
						
						// if value is not null or empty, send to the 
						// parseValue method to determine if the value is 
						// an array, object or value
						if ((!temp.equals(" ")) && !temp.equals("")) 
						{
							valueList.add(parseValue(itemObject));
						}
					}
				}
			}
		}
		return valueList;
	}

	// Method to parse objects
	@SuppressWarnings("unchecked")
	private static Map<String, Object> parseJSONObject(JSONObject jsonObject) throws JSONException 
	{
		// Initialize map
		Map<String, Object> valueObject = null;
		
		// if object is null, return null
		if (null != jsonObject) 
		{	
			valueObject = new HashMap<String, Object>();
			
			// get keys of json object to iterate through object
			Iterator<String> keyIter = jsonObject.keys();
			
			while (keyIter.hasNext())
			{
				// get key and value and save it to temp string and object variables
				String keyStr = keyIter.next();
				Object itemObject = jsonObject.get(keyStr);
				
				if (null != itemObject && !JSONObject.NULL.equals(itemObject)) 
				{
					// check if object is a string object
					if (itemObject instanceof String) 
					{
						// check is string is empty or null
						// Add to map if not
						String temp = (String) itemObject;
						if (!temp.equals(" ") && !temp.equals("") && !temp.equals("null")) 
						{
							valueObject.put(keyStr, parseValue(itemObject));
						}												
					} 
					// check if object is json array
					else if (itemObject instanceof JSONArray) 
					{
						// check if array is null
						// Add to map if not
						JSONArray temp = (JSONArray) itemObject;
						if (!temp.isNull(0)) 
							valueObject.put(keyStr, parseValue(itemObject));
						
					} 
					// check if object is json object
					else if (itemObject instanceof JSONObject) 
					{
						// check if json object is null
						// Add to map if not
						JSONObject temp = (JSONObject) itemObject;
						if (!(temp.length() == 0))
							valueObject.put(keyStr, parseValue(itemObject));
					} 
					else
					{
						// see if object is null
						// add to map if not
						Object temp = itemObject;
						if(!(temp == null))
						{
							valueObject.put(keyStr, parseValue(itemObject));
						}
					}
				}

			}
		}
		
		return valueObject;
	}
}