package com.example.avaliacao3;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = {Cliente.class, Cidade.class, Visita.class}, version = 1)
@TypeConverters(Conversor.class)
public abstract class Database extends RoomDatabase {
    private static Database INSTANCE;
    public abstract ClienteDAO getClienteDao();
    public abstract CidadeDAO getCidadeDao();
    public abstract VisitaDAO getVisitaDao();

    public static Database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
