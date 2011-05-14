/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Client;
import models.MyUpdate;
import models.Update;

/**
 * @author Amr Tj. Wallas
 * 
 */
@CRUD.For(Update.class)
public class Statistics extends CRUD
{
    /**
     * The value of one latitude degree in meters.
     */
    public final static double LATITUDE_DEG_IN_M = 111318.8449;
    /**
     * The value of one latitude degree per 1m distance.
     */
    public final static double ONE_M_LATITUDE_IN_DEG = 1.0 / LATITUDE_DEG_IN_M;
    /**
     * The value of PI according to google.
     */
    public final static double PI = 3.14159265;
    /**
     * The value of the radius of the earth in meters.
     */
    public final static double EARTH_RADIUS = 6378100;

    /**
     * renders the map page and passes to it the list of updates in the database
     */
    public static void map()
    {
	//TODO Filter by device ID
	long currDate = new Date().getTime();
	List<Client> clients = Client.find("lastUpdate >= ? AND lastUpdate <= ?", new Date(currDate-1000), new Date(currDate)).fetch();
	if(clients.isEmpty())
	{
	   Date date = Update.find("SELECT MAX(date) FROM Update").first();
	   clients = Client.find("lastUpdate >= ?", new Date(date.getTime()-60000)).fetch();
	   //System.out.println(clients.size());
	    long timeDiff = currDate-date.getTime()-60000;
	    List<Update> updates = new ArrayList<Update>();
	    for (Client c : clients)
	    {
		updates.add(c.updates.get(c.updates.size()-1));
	    }
	    render(updates,timeDiff);
	}
	List<Update> updates = new ArrayList<Update>();
	for (Client c : clients)
	{
	    updates.add(c.updates.get(c.updates.size()-1));
	}
	render(updates);
    }
    
    public static void pollUpdates()
    {
	long currDate = new Date().getTime();
	List<Client> clients = Client.find("lastUpdate >= ? AND lastUpdate <= ? ORDER BY lastUpdate ASC", new Date(currDate-1000), new Date(currDate)).fetch();
	Update currUp;
	if(clients.isEmpty())
	{
	   Date date = Update.find("SELECT MAX(date) FROM Update").first();
	   clients = Client.find("lastUpdate >= ? ORDER BY lastUpdate ASC", new Date(date.getTime()-60000)).fetch();
	   //System.out.println(clients.size());
	    List<MyUpdate> updates = new ArrayList<MyUpdate>();
	    for (Client c : clients)
	    {
		currUp = c.updates.get(c.updates.size()-1);
		updates.add(new MyUpdate(currUp.client.device_id, currUp.speed, currUp.longitude, currUp.latitude,currUp.date));
	    }
	    renderJSON(updates);
	}
	List<MyUpdate> updates = new ArrayList<MyUpdate>();
	for (Client c : clients)
	{
	    currUp = c.updates.get(c.updates.size()-1);
	    updates.add(new MyUpdate(currUp.client.device_id, currUp.speed, currUp.longitude, currUp.latitude,currUp.date));
	}
	renderJSON(updates);
	//List<Update> updates = Update.find("SELECT client.device_id, speed, longitude, latitude FROM Update").fetch();
	//renderJSON(updates);
    }

    /**
     * Adds new Updates into the database given an HTTP post with the params
     * 
     * @param device_id
     * @param longitude
     * @param latitude
     * @param altitude
     * @param bearing
     * @param accuracy
     * @param speed
     * @param date
     * @return <code>True</code> If the update is not redundant.
     */
    public static boolean newInfo(long device_id, double longitude,
	    double latitude, double altitude, float bearing, float accuracy,
	    float speed, long date)
    {
	Client c = Client.findById(device_id);
	if (c == null)
	    c = new Client(device_id).save();

	boolean redundant = isRedundant(c, longitude, latitude, bearing, speed,
		date);

	if (!redundant)
	    c.addUpdate(longitude, latitude, altitude, bearing, accuracy,
		    speed, date);
	return !redundant;
    }

    /**
     * Checks whether a given update is redundant or not according to the given
     * parameters.
     * 
     * @param c
     * @param longitude
     * @param latitude
     * @param bearing
     * @param speed
     * @param date
     * @return <code>True</code> if the update is redundant.
     */
    public static boolean isRedundant(Client c, double longitude,
	    double latitude, float bearing, float speed, long date)
    {
	// TODO Does this client ACTUALLY represents a vehicle?
	// TODO is it a two way road?
	List<Update> updates = Update
		.find("date >= ? AND date <= ? AND speed >= ? AND speed <= ? AND client.device_id != ? ORDER BY date DESC",
			new Date(date - 10000), new Date(date + 10000),
			speed - 2.77777778f, speed + 2.77777778f, c.device_id)
		.fetch();
	if (updates.isEmpty())
	    return false;
	for (Update u : updates)
	{
	    if (getDistance(u.longitude, u.latitude, longitude, latitude) <= 1.4)
		if (Math.abs(bearing - u.bearing) <= 90)
		    return true;
	}
	return false;
    }

    /**
     * Gets Meters per 1 Degree of Longitude at a given latitude.
     * 
     * @param latitude
     *            latitude in decimal degrees.
     * @return double: Meters per 1 Degree of Longitude at a given latitude.
     */
    public static double getLongDegInMeters(double latitude)
    {
	return (LATITUDE_DEG_IN_M * Math.cos(latitude));
	// return (1.0 / longDegInMeters);
    }

    /**
     * Gets the distance in meters between two geographic points.
     * 
     * @param long1
     *            longitude of point1 in decimal degrees.
     * @param lat1
     *            latitude of point1 in decimal degrees.
     * @param long2
     *            longitude of point2 in decimal degrees.
     * @param lat2
     *            latitude of point2 in decimal degrees.
     * @return double: The distance between the two points in meters.
     */
    public static double getDistance(double long1, double lat1, double long2,
	    double lat2)
    {
	double la1 = lat1 * LATITUDE_DEG_IN_M;
	double la2 = lat2 * LATITUDE_DEG_IN_M;
	double lo1 = long1 * getLongDegInMeters(lat1);
	double lo2 = long2 * getLongDegInMeters(lat2);
	return Math.sqrt((Math.pow(lo2 - lo1, 2)) + (Math.pow(la2 - la1, 2)));
    }
}