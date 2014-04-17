package com.routeapp.routewithwaypoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	final String log_Tag= "Monitor Location";
	LocationListener _gpsProvider;
	LocationListener _networkProvider;
	GoogleMap map;
	Polyline line;
	List<String> waypoints=new ArrayList<String>()  ;
	String points;
	ArrayList<LatLng> routeoptions=new ArrayList<LatLng>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LatLng ltlng=new LatLng(33.6147197,73.0554959);
		LatLng ltlng2=new LatLng(33.745089,73.111725);
		StringBuilder sb=new StringBuilder();
		routeoptions.add(ltlng);
		routeoptions.add(new LatLng(33.5946014,73.0507746));
		routeoptions.add(new LatLng(33.600989,73.047978));
		routeoptions.add(new LatLng(33.593401,73.0669355));
		routeoptions.add(new LatLng(33.631379,73.072622));
		routeoptions.add(new LatLng(33.6115345,73.0650674));
		routeoptions.add(new LatLng(33.662652,73.085617));
		routeoptions.add(new LatLng(31.7028064,74.0178135));
		routeoptions.add(new LatLng(33.7069327,73.0877042));
		routeoptions.add(new LatLng(33.7167149,73.1017403));
		routeoptions.add(new LatLng(33.603191,73.028017));
		routeoptions.add(new LatLng(33.745089,73.111725));
		
		
		
		for(int i=0;i<(routeoptions.size()-5);i++)
		{
			waypoints.add(routeoptions.get(i).latitude+","+routeoptions.get(i).longitude);
		}
		
		
		String points=TextUtils.join("|", waypoints);
		
		findDirections(33.6147197,73.0554959,33.745089,73.111725,points, "Driving");
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints)
	{
	    Polyline newPolyline;
	    GoogleMap mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); 
	    PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);

	    for(int i = 0 ; i < directionPoints.size() ; i++) 
	    {          
	        rectLine.add(directionPoints.get(i));
	    }
	    newPolyline = mMap.addPolyline(rectLine);
	    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(directionPoints.get(0).latitude,directionPoints.get(0).longitude)));
	    mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
	   
	}


	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong,String waypts ,String mode)
	{
	    Map<String, String> map = new HashMap<String, String>();
	    map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
	    map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
	    map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
	    map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
	    map.put(GetDirectionsAsyncTask.WAYPOINTS, waypts);
	    map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

	    GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
	    asyncTask.execute(map); 
	   
	}
	
	public void findDirections(ArrayList<LatLng> list,String mode)
	{
	   handleGetDirectionsResult(list);
	   
	}
	
}