package com.chriskoz.beerstalk;

import java.util.ArrayList;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

public class BeerList extends APIProxy{
	
	private ArrayList<JSONObject> list;
	private String baseUrl;
	
	public BeerList(){
		this.baseUrl = BeerList.api_root + "/beers?" + "token=" + BeerList.api_token;
	}
	
	public ArrayList<JSONObject> search(String query){
		
		// Make a request to the beers endpoint and loop until all results are retrieved.  This is needed to 
		// search the descriptions
		int curPage = 1;
		int numPages = 0;
		int numResults = 0;
		JSONResource response = new JSONResource();
		JSONArray beers = new JSONArray();
		
		do{
			
			// Make the request
			try {
				response = new Resty().json(this.baseUrl + "&page=" + curPage);
				
				// If first iteration, set the total number of results and pages
				if(curPage == 1){
					numPages = (int)response.get("pages");
					numResults = (int)response.get("total");
					list = new ArrayList<JSONObject>(numResults);
					System.out.println("NUMBER OF ITEMS: " + numResults);
				}
				
				// Update the loop counters
				curPage++;
				
				// Isolate the beers section of the response
				beers = response.object().getJSONArray("beers");

			} catch (Exception e) {
				e.printStackTrace();
			}
				
			// Add these results to the array
			for(int i=0; i<beers.length(); i++){
				JSONObject tempBeer;
				try {
					tempBeer = beers.getJSONObject(i);
					
					// Add to the list if there is no query or only add if it matches the search
					if(query == null){
						
						list.add(tempBeer);
						
					}else if(tempBeer.getString("name").toLowerCase().contains(query.toLowerCase()) || 
						tempBeer.getString("description").toLowerCase().contains(query.toString())){
						
						list.add(tempBeer);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}while(curPage <= numPages);
		
		// Sort the final list by ABV
		list.sort(new ABVComparator());
		
		return list;
			
	}
	
}
