package Tugas;

public class TestPeminjaman {
    public static void main(String[] args) {
        Peminjam aswy = new Peminjam();
        Peminjam pras = new Peminjam();

        aswy.id= "101";
        aswy.nama = "Aisya Aswy";
        aswy.member = true;
        aswy.namaGame = "Block Blast!";
        aswy.harga = 20000;
        aswy.lamaSewa = 3;

        pras.id= "102";
        pras.nama = "Prasojo";
        pras.member = false;
        pras.namaGame = "Mobile Legends";
        pras.harga = 15000;
        pras.lamaSewa = 2;

        System.out.println("====--Peminjaman Game--====");
        aswy.tampilData();
        System.out.println("---------------------------");
        pras.tampilData();
        System.out.println("---------------------------");
    }
}
