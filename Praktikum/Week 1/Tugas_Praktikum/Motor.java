package Tugas_Praktikum;

public class Motor extends Kendaraan {
    // Atribut Motor
    int kapasitasTangki;
    String tipeRangka;

    // Method
    public void standarGanda(){
        System.out.println("Motor diparkir dengan standar ganda.");
    }

    public void klakson(){
        System.out.println("Tinn!!.. Motor membunyikan klakson.");
    }

    public void cetakInfo(){
        super.infoProduksi();
        System.out.println("Kapasitas Tangki : " + kapasitasTangki);
        System.out.println("Tipe Rangka : " + tipeRangka);
    }
}