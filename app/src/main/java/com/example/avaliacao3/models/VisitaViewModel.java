package com.example.avaliacao3.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.avaliacao3.classes.Database;
import com.example.avaliacao3.classes.Visita;
import com.example.avaliacao3.interfaces.VisitaDAO;

public class VisitaViewModel extends AndroidViewModel {
    private VisitaDAO visitaDAO;

    public VisitaViewModel(@NonNull Application application) {
        super(application);
        visitaDAO = Database.getInstance(application).getVisitaDao();
    }

    public void addVisita(Visita visita) {
        new Thread(() -> visitaDAO.addVisita(visita)).start();
    }
}
