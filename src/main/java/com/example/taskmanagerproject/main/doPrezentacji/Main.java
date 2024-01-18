package com.example.taskmanagerproject.main.doPrezentacji;

public class Main {
    public static void main(String[] args) {
        Osoba osoba = new Osoba("Jan", "Kowalski", 25);

        System.out.println("Imię: " + osoba.getImie());
        System.out.println("Nazwisko: " + osoba.getNazwisko());
        System.out.println("Wiek: " + osoba.getWiek());

        // Zmiana wieku za pomocą settera
        osoba.setWiek(30);

        // Próba zmiany wieku na wartość ujemną (zabezpieczenie enkapsulacji)
        osoba.setWiek(-5);

        // Wyświetlenie zmienionego wieku
        System.out.println("Nowy wiek: " + osoba.getWiek());
    }
}