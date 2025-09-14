package Tugas;
import java.util.Scanner;

public class TestKoperasi {
    public static void main(String[] args) {
        Scanner prasojo = new Scanner(System.in); 
        
        Anggota donny = new Anggota("111333444", "Donny", 5000000);
        
        System.out.println("Nama Anggota: " + donny.getNama());
        System.out.println("Limit Pinjaman: " + donny.getLimitPinjaman());
        
        System.out.print("\nMasukkan jumlah uang yang akan dipinjam: ");
        double jumlahPinjam = prasojo.nextDouble();
        donny.pinjam(jumlahPinjam);
        System.out.println("Jumlah pinjaman saat ini: " + donny.getJumlahPinjaman());

        while (donny.getJumlahPinjaman() > 0) {
            System.out.println("------------------------------------------");
            System.out.println("Jumlah pinjaman saat ini: Rp " + donny.getJumlahPinjaman());
            System.out.print("Masukkan jumlah angsuran: ");
            double jumlahAngsuran = prasojo.nextDouble();
            donny.angsur(jumlahAngsuran);
        }
        
        System.out.println("------------------------------------------");
        System.out.println("Selamat! Pinjaman Anda telah lunas.");
        System.out.println("Jumlah pinjaman saat ini: Rp " + donny.getJumlahPinjaman());
        
        prasojo.close();
    }
}