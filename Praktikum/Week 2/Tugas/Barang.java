package Tugas;

public class Barang {
    public String kode;
    public String namaBarang;
    public int hargaDasar;
    public float diskon;

    public int hitungHarga(){
        double hargaJual = hargaDasar - ((diskon/100)*hargaDasar);
        return (int) hargaJual;
    }

    public void tampilData(){
        System.out.println("Kode Barang\t: " + kode);
        System.out.println("Nama Barang\t: " + namaBarang);
        System.out.println("Harga Dasar\t: " + hargaDasar);
        System.out.printf("Diskon\t\t: %.0f%%\n", diskon);
        System.out.println("Harga Jual\t: " + hitungHarga());
    }
}
