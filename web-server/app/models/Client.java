/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.jpa.GenericModel;

/**
 * @author Amr Tj. Wallas
 * 
 */
@Entity
public class Client extends GenericModel
{
    @Id
    public long device_id;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<Update> updates;

    public Client(long device_id)
    {
	this.device_id = device_id;
	this.updates = new ArrayList<Update>();
    }

    public void addUpdate(double longitude, double latitude, double altitude,
	    float bearing, float accuracy, float speed)
    {
	this.updates.add(new Update(this, longitude, latitude, altitude,
		bearing, accuracy, speed));
	this.save();
    }
}
