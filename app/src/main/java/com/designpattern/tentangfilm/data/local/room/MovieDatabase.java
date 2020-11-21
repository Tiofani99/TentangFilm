package com.designpattern.tentangfilm.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.designpattern.tentangfilm.data.local.entity.Movie;

@Database(entities = {Movie.class},
        version = 1,
        exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "db_movie";
    private static volatile MovieDatabase INSTANCE;

    public static MovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract MovieDao movieDao();
}
