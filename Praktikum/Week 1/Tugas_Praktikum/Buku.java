package Tugas_Praktikum;

public class Buku {
    
    // Atribut
    String judul;
    String penulis;
    int noHal;

    // Method
    public void bukaBuku(){
        System.out.println("Membuka buku '" + judul + "'...");
    }

    public void bacaHalaman(int noHal){
        System.out.println("Membaca halaman " + noHal + ".");
    }

    public void cetakInfo(){
        System.out.println("Judul : " + judul);
        System.out.println("Penulis : " + penulis);
    }
}
