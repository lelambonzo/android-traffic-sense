package com.traffic.sense;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The main traffic sensing Android app
 * 
 * @author Amr Tj. Wallas
 */
public class TrafficDaemon extends Activity
{
    public static TextView tv;
    public static Socket s;
    public static PrintWriter out;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setManualLocation();
	setContentView(tv);

	/* Use the LocationManager class to obtain GPS locations */

	//LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	//LocationListener mlocListener = new MyLocationListener();

	//mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
	//	mlocListener);

	try
	{
	    connect("10.0.2.2", 27960);
	    send("CMD_HELLO");
	    send(tv.getText().toString());
	    send("CMD_QUIT");
	} catch (UnknownHostException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Sets GPS location manually for testing purposes.
     */
    public void setManualLocation()
    {
	tv = new TextView(this);

	Location l = new Location("manual_fix");
	l.setAltitude(12345.6);
	l.setLatitude(5432.1);
	l.setLongitude(6023.4);
	l.setSpeed(60.8f);
	tv.setText("Altitude: " + l.getAltitude() + "\nLatitude: "
		+ l.getLatitude() + "\nLongitude: " + l.getLongitude()
		+ "\nSpeed: " + l.getSpeed());

    }

    /**
     * Connects the Android Client to a given server
     * 
     * @param name
     *            The name of the remote server
     * @param port
     *            Port number to connect to at the remote server.
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void connect(String name, int port)
	    throws UnknownHostException, IOException
    {

	s = new Socket(name, port);
	out = new PrintWriter(s.getOutputStream(), true);
    }

    /**
     * Sends a string message to the server.
     * 
     * @param msg
     *            The message to be sent.
     * @throws IOException
     */
    public static void send(String msg) throws IOException
    {
	if (!s.isClosed() && msg != null)
	{
	    out.println(msg);
	    if (msg.contains("CMD_QUIT"))
	    {
		out.close();
		s.close();
		Log.i("ServerConnection","Client Disconnected.");
	    }
	}
    }

    class MyLocationListener implements LocationListener

    {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onLocationChanged(android.location
	 * .Location)
	 */
	@Override
	public void onLocationChanged(Location loc)
	{
	    // TODO Auto-generated method stub
	    loc.getLatitude();
	    loc.getLongitude();
	    String Text = "My current location is: " + "Latitud = "
		    + loc.getLatitude() + "Longitud = " + loc.getLongitude();
	    
	    Log.i("GeoLocation", Text);

	    //Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT)
		//    .show();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String
	 * )
	 */
	@Override
	public void onProviderDisabled(String provider)
	{
	    // TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider)
	{
	    // TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	    // TODO Auto-generated method stub

	}

    }/* End of Class MyLocationListener */
}