/**
 * 
 */
package controllers;

import java.util.List;

import models.Client;
import models.Update;

/**
 * @author Amr Tj. Wallas
 * 
 */
@CRUD.For(Update.class)
public class Statistics extends CRUD
{
    /**
     * renders the map page and passes to it the list of updates in the database
     */
    public static void map()
    {
	List<Update> updates = Update.findAll();
	render(updates);
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
     * @return <code>True</code> If the client is a new client to the system
     */
    public static boolean newInfo(long device_id, double longitude,
	    double latitude, double altitude, float bearing, float accuracy,
	    float speed, long date)
    {
	boolean neu = false;
	Client c = Client.findById(device_id);
	if (c == null)
	{
	    c = new Client(device_id).save();
	    neu = true;
	}
	c.addUpdate(longitude, latitude, altitude, bearing, accuracy, speed,
		date);
	return neu;
    }
}
