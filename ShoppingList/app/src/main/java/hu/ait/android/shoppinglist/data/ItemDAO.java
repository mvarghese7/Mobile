package hu.ait.android.shoppinglist.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.ait.android.shoppinglist.data.Item;

/**
 * Created by mayavarghese on 4/10/18.
 */

@Dao
public interface ItemDAO {

    //gets objects from the item table
    @Query("SELECT * FROM item")
    List<Item> getAll();

    //inserts an item to the table
    @Insert
    long insertItem(Item item);

    @Delete
    void delete(Item item);

    @Update
    void update(Item item);
}

