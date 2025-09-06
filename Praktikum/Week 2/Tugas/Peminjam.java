package Tugas;

public class Peminjam {
    public String id;
    public String nama;
    public boolean member;
    public String namaGame;
    public int harga;
    public int lamaSewa;

    public double hargaSewa(){
        double totalHarga = lamaSewa * harga;
        if (member) {
            totalHarga = lamaSewa * (harga * 0.75);
        }
        return totalHarga;
    }

    public void tampilData(){
        System.out.println("Id\t\t: " + id);
        System.out.println("Nama\t\t: " + nama);
        System.out.println("Total Harga\t: "+ "Rp" + hargaSewa());
        System.out.println(nama + " " + ((member) ? "adalah Member" : "bukan Member"));
    }
}