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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

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
    public static TelephonyManager tm;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	tv = new TextView(getApplicationContext());
	tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	tv.setText("I currently have no Location Data.");
	setContentView(tv);

	/* Use the LocationManager class to obtain GPS locations */

	LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	LocationListener mlocListener = new MyLocationListener();

	mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
		mlocListener);
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
		Log.i("ServerConnection", "Client Disconnected.");
	    }
	}
    }

    /**
     * Location Listener class that will perform actions according to location
     * updates.
     * 
     * @author Amr Tj. Wallas
     * 
     */
    class MyLocationListener implements LocationListener

    {

	/**
	 * First called when the GPS location is changed. (New location fix
	 * obtained)
	 * 
	 * @param loc
	 *            The new Obtained location fix.
	 */
	@Override
	public void onLocationChanged(Location loc)
	{
	    String txt = "Latitude:" + loc.getLatitude() + "\nLongtitude:"
		    + loc.getLongitude();
	    Log.i("GeoLocation", "My current location is:\n " + txt);
	    tv.setText("My current location is:\n" + txt);
	    String msg = loc.getLongitude() + "\n" + loc.getLatitude() + "\n"
		    + loc.getAltitude() + "\n" + loc.getBearing() + "\n"
		    + loc.getAccuracy() + "\n" + loc.getSpeed() + "\n"
		    + loc.getTime();
	    msg = tm.getDeviceId() + "\n" + msg; /* DeviceID is null in emulator */
	    try
	    {
		connect("10.0.2.2", 27960);
		send("CMD_HELLO");
		send(msg);
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
	 * Called when the GPS provider is disabled or connection to it lost
	 * 
	 * @param provider
	 *            The GPS provider
	 */
	@Override
	public void onProviderDisabled(String provider)
	{
	    // TODO Auto-generated method stub

	}

	/**
	 * Called when the GPS provider is enabled or connection to it is
	 * established.
	 * 
	 * @param provider
	 *            The GPS provider
	 */
	@Override
	public void onProviderEnabled(String provider)
	{
	    // TODO Auto-generated method stub

	}

	/**
	 * Called when the GPS status is changed.
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	    // TODO Auto-generated method stub

	}

    }/* End of Class MyLocationListener */
}/* End of Activity Class */