package Tugas;

public class Peminjaman {
    private Anggota anggota;
    private Buku buku;
    private String tglPinjam;
    private String tglKembali;

    public Peminjaman(Anggota anggota, Buku buku, String tglPinjam, String tglKembali) {
        this.anggota = anggota;
        this.buku = buku;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
    }

    public void info() {
        System.out.println("--- Detail Peminjaman ---");
        System.out.println("Peminjam\t: " + anggota.getNama());
        System.out.println("Buku yang Dipinjam: \n" + buku.info());
        System.out.println("Tanggal Pinjam\t: " + tglPinjam);
        System.out.println("Tanggal Kembali\t: " + tglKembali);
        System.out.println("-------------------------");
    }
}
