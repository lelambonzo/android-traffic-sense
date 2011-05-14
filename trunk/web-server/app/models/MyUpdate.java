/**
 * 
 */
package models;

import java.util.Date;

/**
 * @author Amr Tj. Wallas
 *
 */
public class MyUpdate
{
    long device_id;
    float speed;
    double longitude,latitude;
    long date;
    
    public MyUpdate(long device_id, float speed, double longitude, double latitude, Date date)
    {
	this.device_id=device_id;
	this.speed=speed;
	this.longitude=longitude;
	this.latitude=latitude;
	this.date = date.getTime();
    }
}
