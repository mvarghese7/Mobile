package hu.ait.android.weatherinfo.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import hu.ait.android.weatherinfo.data.City;


@Database(entities = {City.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CityDAO cityDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "item-database").build();
        }
        return INSTANCE;
    }

    //called when the app is exited and last activity is closed
    public static void destroyInstance() {
        INSTANCE = null;
    }
}