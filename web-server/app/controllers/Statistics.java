/**
 * 
 */
package controllers;

import java.util.List;

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
}
