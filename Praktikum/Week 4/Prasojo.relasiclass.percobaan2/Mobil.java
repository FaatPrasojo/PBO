package Prasojo.relasiclass.percobaan2;

public class Mobil {
    private String nama;
    private int biaya;

    public Mobil(){
    }

    public void setNama(String nama){
        this.nama = nama;
    }
    
    public String getNama(){
        return nama;
    }

    public void setBiaya(int biaya){
        this.biaya = biaya;
    }

    public int getBiaya() {
        return biaya;
    }

    public int hitungBiayaMobil(int hari){
        return biaya * hari; 
    }

}