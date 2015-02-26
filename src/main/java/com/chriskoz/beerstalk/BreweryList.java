package com.chriskoz.beerstalk;

import java.util.ArrayList;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

public class BreweryList extends APIProxy{
	
	private ArrayList<JSONObject> list;
	private String baseUrl;
	
	public BreweryList(){
		this.baseUrl = BeerList.api_root + "/breweries?" + "token=" + BreweryList.api_token;
	}
	
	public ArrayList<JSONObject> search(String query){
		
		JSONResource response = new JSONResource();
		JSONArray breweries = new JSONArray();
		
		// Make the request to the API
		try {
			String url = this.baseUrl + "&query=" + query;
			System.out.println(url);
			response = new Resty().json(url);
			
			// Extract the beers section of the response
			breweries = response.object().getJSONArray("breweries");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		list = new ArrayList<JSONObject>();
		
		// Add these results to the array
		for(int i=0; i<breweries.length(); i++){
			try {
				list.add(breweries.getJSONObject(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return list;
			
	}
	
}
