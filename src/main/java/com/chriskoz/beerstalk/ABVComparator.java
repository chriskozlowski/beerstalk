package com.chriskoz.beerstalk;

import java.util.Comparator;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

public class ABVComparator implements Comparator<JSONObject>{
	@Override
    public int compare(JSONObject o1, JSONObject o2) {
        try {
			if(o1.getDouble("abv") < o2.getDouble("abv")){
			    return 1;
			} else {
			    return -1;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        return 0;
    }
}