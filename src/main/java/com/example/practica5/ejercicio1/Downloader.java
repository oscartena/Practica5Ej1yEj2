package com.example.practica5.ejercicio1;


import javafx.collections.ListChangeListener;

public class Downloader implements ListChangeListener<String> {

    @Override
    public void onChanged(Change<? extends String> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (String addedUrl : change.getAddedSubList()) {
                    System.out.println("Se ha iniciado la descarga del archivo " + addedUrl);
                }
            }
        }
    }
}