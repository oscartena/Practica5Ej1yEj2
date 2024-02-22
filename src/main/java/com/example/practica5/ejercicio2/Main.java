package com.example.practica5.ejercicio2;

import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Main {

    public static void main(String[] args) {
        ObservableList<String> urls = FXCollections.observableArrayList();
        Downloader downloader = new Downloader();
        urls.addListener(downloader);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Introduce una URL (o 'exit' para salir): ");
            String url = sc.nextLine();

            if ("exit".equalsIgnoreCase(url)) {
                break;
            }

            urls.add(url);
        }

        sc.close();
    }
}
