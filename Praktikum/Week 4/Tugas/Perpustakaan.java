package Tugas;

public class Perpustakaan {
    private Buku[] listBuku = new Buku[100];
    private Anggota[] listAnggota = new Anggota[100];
    private Peminjaman[] listPeminjaman = new Peminjaman[100];
    private int jmlBuku = 0;
    private int jmlAnggota = 0;
    private int jmlPeminjaman = 0;

    public void tambahBuku(Buku buku) {
        if (jmlBuku < listBuku.length) {
            listBuku[jmlBuku++] = buku;
        }
    }

    public void tambahAnggota(Anggota anggota) {
        if (jmlAnggota < listAnggota.length) {
            listAnggota[jmlAnggota++] = anggota;
        }
    }

    public void prosesPeminjaman(Anggota anggota, Buku buku, String tglPinjam, String tglKembali) {
        if (jmlPeminjaman < listPeminjaman.length) {
            listPeminjaman[jmlPeminjaman++] = new Peminjaman(anggota, buku, tglPinjam, tglKembali);
        }
    }

    public void tampilkanDataPeminjaman() {
        System.out.println("======= DATA PEMINJAMAN PERPUSTAKAAN =======");
        for (int i = 0; i < jmlPeminjaman; i++) {
            listPeminjaman[i].info();
        }
        System.out.println("==========================================");
    }
}
