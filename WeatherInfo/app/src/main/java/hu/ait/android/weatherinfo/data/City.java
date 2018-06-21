package hu.ait.android.weatherinfo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by mayavarghese on 4/5/18.
 */

@Entity
public class City implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long cityId;

    @ColumnInfo(name = "item_name")
    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;

    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}