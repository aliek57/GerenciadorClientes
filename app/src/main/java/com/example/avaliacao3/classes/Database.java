package com.example.avaliacao3.classes;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.avaliacao3.interfaces.CidadeDAO;
import com.example.avaliacao3.interfaces.ClienteDAO;
import com.example.avaliacao3.interfaces.VisitaDAO;

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
