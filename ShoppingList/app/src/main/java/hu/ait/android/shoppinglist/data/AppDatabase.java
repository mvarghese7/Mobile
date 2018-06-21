package hu.ait.android.shoppinglist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by mayavarghese on 4/10/18.
 */

//can list all entities here
//version can be changed if the database structure is altered
@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ItemDAO itemDao();

    //same as the getInstance method
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