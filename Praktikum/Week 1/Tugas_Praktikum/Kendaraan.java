package Tugas_Praktikum;

public class Kendaraan {
    //Atribut
    String merek;
    int tahunProduksi;

    // 3 Method
    public void nyalakanMesin(){
        System.out.println("Mesin " + merek + " dinyalakan...");
    }

    public void matikanMesin(){
        System.out.println("Mesin " + merek + " dimatikan...");
    }

    public void infoProduksi(){
        System.out.println("Mesin :\t" + merek );
        System.out.println("Tahun Produksi :\t" + tahunProduksi);
    }

}