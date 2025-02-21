package com.example.avaliacao3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
