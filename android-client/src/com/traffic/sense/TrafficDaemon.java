package com.traffic.sense;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Amr Tj. Wallas
 */
public class TrafficDaemon extends Activity
{
    public static TextView tv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	tv = new TextView(this);

	Location l = new Location("manual_fix");
	l.setAltitude(12345.6);
	l.setLatitude(5432.1);
	l.setLongitude(6023.4);
	l.setSpeed(60.8f);
	tv.setText("Altitude: " + l.getAltitude() + "\nLatitude: "
		+ l.getLatitude() + "\nLongitude: " + l.getLongitude()
		+ "\nSpeed: " + l.getSpeed());

	/*
	 * LocationManager lm = (LocationManager)
	 * getSystemService(LOCATION_SERVICE); LocationListener ll = new
	 * MyLocationListener();
	 * lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	 */
	setContentView(tv);
    }
}
/*
 * class MyLocationListener implements LocationListener {
 * 
 * @Override public void onLocationChanged(Location location) { if (location !=
 * null) { TrafficDaemon.tv.setText("Altitude: " + location.getAltitude() +
 * "\nLatitude: " + location.getLatitude() + "\nLongitude: " +
 * location.getLongitude() + "\nSpeed: " + location.getSpeed()); }
 * 
 * }
 * 
 * @Override public void onProviderDisabled(String provider) { // TODO
 * Auto-generated method stub
 * 
 * }
 * 
 * @Override public void onProviderEnabled(String provider) { // TODO
 * Auto-generated method stub
 * 
 * }
 * 
 * @Override public void onStatusChanged(String provider, int status, Bundle
 * extras) { // TODO Auto-generated method stub
 * 
 * }
 * 
 * }
 */