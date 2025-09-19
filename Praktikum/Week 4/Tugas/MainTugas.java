package Tugas;

public class MainTugas {
    public static void main(String[] args) {
        
        Perpustakaan perpus = new Perpustakaan();

        Buku buku1 = new Buku("Pemrograman Berorientasi Objek", "John Doe", "Penerbit Informatika");
        Buku buku2 = new Buku("Dasar-dasar Jaringan Komputer", "Jane Smith", "Penerbit Andi");
        
        perpus.tambahBuku(buku1);
        perpus.tambahBuku(buku2);
        
        Anggota anggota1 = new Anggota("A001", "Budi Santoso");
        Anggota anggota2 = new Anggota("A002", "Citra Lestari");
        
        
        perpus.tambahAnggota(anggota1);
        perpus.tambahAnggota(anggota2);

        perpus.prosesPeminjaman(anggota1, buku1, "18-09-2025", "25-09-2025");
        perpus.prosesPeminjaman(anggota2, buku2, "19-09-2025", "26-09-2025");

        perpus.tampilkanDataPeminjaman();
    }
}
