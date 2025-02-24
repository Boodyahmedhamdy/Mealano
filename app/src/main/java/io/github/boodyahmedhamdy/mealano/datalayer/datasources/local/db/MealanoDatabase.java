package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.converters.Converters;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.MealsDao;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.PlansDao;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;

@Database(entities = {MealEntity.class, PlanEntity.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MealanoDatabase extends RoomDatabase {
    private static final String TAG = "MealanoDatabase";

    public abstract MealsDao mealsDao();
    public abstract PlansDao plansDao();
    private static MealanoDatabase instance;

    public static MealanoDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MealanoDatabase.class,
                            "mealano_db"
                    ).build();
            Log.i(TAG, "database was created for the first time");
        }
        return instance;
    }


}
