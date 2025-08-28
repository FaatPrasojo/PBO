package Tugas_Praktikum;

public class PraktikumDemo {
    public static void main(String[] args) {
        
        // Instansiasikan satu buah objek untuk setiap class
    
        // Objek dari Independent class
        Lampu lampuKamar = new Lampu();
        lampuKamar.jenisLampu = "LED";

        Buku buku = new Buku();
        buku.judul = "Harry Potter";
        buku.penulis = "J. K. Rowling";

        // Objek dari class INHERITANCE
        Mobil mobil = new Mobil();
        mobil.merek = "Honda HRV";
        mobil.tahunProduksi = 2025;
        mobil.jmlPintu = 4;
        mobil.jenisBahanBakar = "Bensin";

        Motor sport = new Motor();
        sport.merek = "Kawasaki Ninja ZX-25R";
        sport.tahunProduksi = 2023;
        sport.kapasitasTangki = 15;
        sport.tipeRangka = "Trellis";

        System.out.println("------ OBJEK LAMPU ------");
        lampuKamar.cetakInfo();
        lampuKamar.nyalakan();
        lampuKamar.cetakInfo();
        lampuKamar.matikan();
        System.out.println("=========================");

        System.out.println("------ OBJEK BUKU ------");
        buku.cetakInfo();
        buku.bukaBuku();
        buku.cetakInfo();
        buku.bacaHalaman(57);
        System.out.println("=========================");

        System.out.println("------ OBJEK MOBIL ------");
        mobil.cetakInfo();
        mobil.nyalakanMesin();
        mobil.bukaPintu();
        mobil.aktifkanWiper();
        mobil.matikanMesin();
        System.out.println("=========================");

        System.out.println("------ OBJEK MOTOR ------");
        sport.cetakInfo();
        sport.nyalakanMesin();
        sport.klakson();
        sport.standarGanda();
        sport.matikanMesin();
        System.out.println("=========================");

    }
}
