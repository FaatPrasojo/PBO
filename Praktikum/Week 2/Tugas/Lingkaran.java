package Tugas;

public class Lingkaran {
    public int phi = 22/7;
    public int r;

    public double hitungLuas(){
        double luas = phi *r^2;
        return luas;
    }

    public double hitungKeliling(){
        double Keliling = phi * (r*2);
        return Keliling;
    }

    public void tampilData(){
        System.out.println("Jari-Jari Lingkaran\t: " + r);
        System.out.println("Luas Lingkarant\t\t:" + hitungLuas());
        System.out.println("Keliling Lingkaran\t: " + hitungLuas());
    }
}
