/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

/**
 * @author Amr Tj. Wallas
 * 
 */
@Entity
public class Update extends Model
{
    @ManyToOne
    public Client client;
    public double longitude;
    public double latitude;
    public double altitude;
    public float bearing;
    public float accuracy;
    public float speed;
    public Date date;

    public Update(Client client, double longitude, double latitude,
	    double altitude, float bearing, float accuracy, float speed,
	    long date)
    {
	this.client = client;
	this.longitude = longitude;
	this.latitude = latitude;
	this.altitude = altitude;
	this.bearing = bearing;
	this.accuracy = accuracy;
	this.speed = speed;
	this.date = new Date(date);
    }
}
