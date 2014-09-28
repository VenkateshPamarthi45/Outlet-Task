package com.couponduniatask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	
	Context context;
	public static final String filename = "final.json";
	public static final String TAG = MainActivity.class.getSimpleName(); 
	// outlet json url
    private static final String url = "http://api.coupondunia/link.json";  
    private List<Outlet> outletList = new ArrayList<Outlet>();
    private ListView listView;
    private CustomListAdapter adapter;
	private static final String TAG_NAME = "OutletName";
	private static final String TAG_LATITUDE = "Latitude";
	private static final String TAG_LONGITUDE = "Longitude";
	private static final String TAG_IMAGEURL = "LogoURL";
	private static final String TAG_OFFERS = "NumCoupons";
	private static final String TAG_NEIGHBOURHOODNAME = "NeighbourhoodName";
	public double userLatitude, userLongitude;
	public JSONObject obj;
	public TextView latitudetxt, longitudetxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GPSTracker gpsTracker = new GPSTracker(this);
		
        String stringLatitude = String.valueOf(gpsTracker.latitude);
        userLatitude = gpsTracker.latitude;

        String stringLongitude = String.valueOf(gpsTracker.longitude);
        userLongitude = gpsTracker.longitude;
        
		latitudetxt = (TextView) findViewById(R.id.lati_value);
		longitudetxt = (TextView) findViewById(R.id.long_value);
		
		// Displaying user latitude and longitude
		latitudetxt.setText(stringLatitude);
		longitudetxt.setText(stringLongitude);
		
		listView = (ListView) findViewById(android.R.id.list);
        adapter = new CustomListAdapter(MainActivity.this, outletList);
        listView.setAdapter(adapter);

     /* calling method for sending request
        sendingJSONRequest();*/
		
        try {
			obj = new JSONObject(loadJSONFromAsset());
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		handleJSONResponse(obj);
	}

	public String loadJSONFromAsset() {
	    String json = null;
	    try {
	        InputStream is = getAssets().open("final.json");
	        int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        json = new String(buffer, "UTF-8");

	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;
	}
	
	public void sendingJSONRequest()
	{
		// Creating volley request obj
		String tag_json_obj = "json_obj_req";	   
		         
		        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
		                url, null,
		                new Response.Listener<JSONObject>() {
		 
		                    @Override
		                    public void onResponse(JSONObject response) {
		                        Log.d(TAG, response.toString());
		                        handleJSONResponse(response);
		                    }
		                }, new Response.ErrorListener() {
		 
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                        VolleyLog.d(TAG, "Error: " + error.getMessage());
		                    }
		                });
		 
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        
	}
	public void handleJSONResponse(JSONObject res)
	{
		try {
            
	 	       Log.v("JsonOutletSearch", "1");
	 	      
	 	             JSONObject orgin = res.getJSONObject("data");
	 	            for (int i = 0; i < orgin.length(); i++) 
	 	            {
	 	            	String objitemstrg = String.valueOf(i); 
		 	            JSONObject tst = orgin.getJSONObject(objitemstrg);
		 	           	
		 	            Outlet outletobj = new Outlet();
		 	            ArrayList<String> categ = new ArrayList<String>();
		 	            
		 	            outletobj.setTitle(tst.getString(TAG_NAME));
		 	            outletobj.setThumbnailUrl(tst.getString(TAG_IMAGEURL));
		 	            outletobj.setOffers(((Number) tst.get(TAG_OFFERS)).intValue());
		 	           		 	           
		 	            outletobj.setLatitude(Double.parseDouble(tst.getString(TAG_LATITUDE)));
		 	            outletobj.setLongitude(Double.parseDouble(tst.getString(TAG_LONGITUDE)));
		 	            
		 	            outletobj.setDistance(calculateDistance(userLatitude, userLongitude,  outletobj.getLatitude(),outletobj.getLongitude()));
		 	            outletobj.setLocation(tst.getString(TAG_NEIGHBOURHOODNAME));
		 	            
			 	        // categories is json array
	                    JSONArray categoriesArry = tst.getJSONArray("Categories");
	                    for(int j = 0; j < categoriesArry.length(); j++ )
	                    {
	                    	JSONObject cateObj = categoriesArry.getJSONObject(j);
                            categ.add(cateObj.getString("Name"));
	                    }
	 	                outletobj.setGenre(categ);	 	                
	 	                outletList.add(outletobj);
	 	            }
	 	        Collections.sort(outletList, new DistanceComparator());	
				 } catch(JSONException e) {
				
				 	 Log.v("JsonOutletSearch", "fail");
				     e.printStackTrace();
				 }
		adapter.notifyDataSetChanged();
	}
	
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		
		 double R = 6378.1; // Radius of the earth in km
	     double dLat = deg2rad(lat2-lat1);  // deg2rad below
	     double dLon = deg2rad(lon2-lon1);
	     double a =
	       Math.sin(dLat/2) * Math.sin(dLat/2) +
	       Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
	       Math.sin(dLon/2) * Math.sin(dLon/2)
	       ;
	     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	     double d = R * c; // Distance in km
	     return d * 1000;
	}
	private double deg2rad(double d) {

		   return d * (Math.PI/180);
		}
}
