package Tugas_Praktikum;

public class Lampu {
    // Atribut
    String jenisLampu;
    boolean menyala;

    // Method

    public void nyalakan(){
        menyala = true;
        System.out.println("Lampu dinyalakan.");
    }

    public void matikan(){
        menyala = false;
        System.out.println("Lampu dimatikan.");
    }
    
    public void cetakInfo(){
        System.out.println("Jenis Lampu : " + jenisLampu);
        System.out.println(" Status : " + (menyala ? "Menyala" : "Mati"));
    }
}