package com.chriskoz.beerstalk;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {
	
	// Main function sets up routes and controller functions for the app
    public static void main(String[] args) {
    	
		VelocityTemplateEngine engine = new VelocityTemplateEngine();
    	
    	staticFileLocation("/public");
    	
    	get("/", (req, res) -> {
    		
    		Map<String, Object> model = new HashMap<String, Object>();
    		return new ModelAndView(model, "index.vm");
    	
    	}, engine);
    	
    	get("/search", (req, res) -> {
    		
    		String searchType = req.queryParams("search_type");
    		BeerList b = new BeerList();
    		BreweryList br = new BreweryList();
    		
    		Map<String, Object> model = new HashMap<String, Object>();
    		model.put("beers", b.search(req.queryParams("query")));
    		model.put("breweries", br.search(req.queryParams("query")));
    		    		
    		return new ModelAndView(model, "search.vm");
    		
    	}, engine);
    	
        
    }
}