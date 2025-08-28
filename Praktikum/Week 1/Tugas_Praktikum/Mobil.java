package Tugas_Praktikum;

public class Mobil extends Kendaraan{
    // Atribut Mobil
    int jmlPintu;
    String jenisBahanBakar;
    
    public void bukaPintu(){
        System.out.println("Pintu mobil dibuka.");
    }

    public void aktifkanWiper(){
        System.out.println("Wiper mobil aktif.");
    }

    public void cetakInfo(){
        super.infoProduksi();
        System.out.println("Jumlah Pintu : " + jmlPintu);
        System.out.println("Jenis Bahan Bakar : " + jenisBahanBakar);
    }
}
