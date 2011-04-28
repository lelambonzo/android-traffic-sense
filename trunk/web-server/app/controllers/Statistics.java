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
    public static void map()
    {
	List<Update> updates = Update.findAll();
	render(updates);
    }

    public static boolean newInfo(long device_id, double longitude,
	    double latitude, double altitude, float bearing, float accuracy,
	    float speed)
    {
	boolean neu = false;
	Client c = Client.findById(device_id);
	if (c == null)
	{
	    c = new Client(device_id).save();
	    neu = true;
	}
	c.addUpdate(longitude, latitude, altitude, bearing, accuracy, speed);
	return neu;
    }
}
