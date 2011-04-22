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
    public int cell_id;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<Update> updates;

    public Client(int cellId)
    {
	this.cell_id = cellId;
	this.updates = new ArrayList<Update>();
    }
}
