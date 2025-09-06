package Tugas;

public class TestBarang {
    public static void main(String[] args) {
        Barang brg1 = new Barang();

        brg1.kode = "101";
        brg1.namaBarang = "Kaca Mata";
        brg1.hargaDasar = 20000;
        brg1.diskon = 25;
        brg1.hitungHarga();

        System.out.println("====--Data Barang--====");
        brg1.tampilData();
    }
}
