package hu.ait.android.weatherinfo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


/**
 * Created by mayavarghese on 4/10/18.
 */

@Dao
public interface CityDAO {

    //gets objects from the city table
    @Query("SELECT * FROM city")
    List<City> getAll();

    //inserts an city to the table
    @Insert
    long insertCity(City city);

    @Delete
    void delete(City city);

    @Update
    void update(City city);
}
