package Tugas;

public class Anggota {

    private String noKtp;
    private String nama;
    private double limitPinjaman;
    private double jumlahPinjaman;

    public Anggota(String noKtp, String nama, double limitPinjaman) {
        this.noKtp = noKtp;
        this.nama = nama;
        this.limitPinjaman = limitPinjaman;
        this.jumlahPinjaman = 0;
    }

    public String getNama() {
        return nama;
    }

    public double getLimitPinjaman() {
        return limitPinjaman;
    }

    public double getJumlahPinjaman() {
        return jumlahPinjaman;
    }

    public void pinjam(double uang) {
        if (uang + jumlahPinjaman > limitPinjaman) {
            System.out.println("Maaf, jumlah pinjaman melebihi limit.");
        } else {
            jumlahPinjaman += uang;
        }
    }

    public void angsur(double uang) {
        if (uang < jumlahPinjaman * 0.1) {
            System.out.println("Maaf, angsuran harus 10% dari jumlah pinjaman");
        } else if (uang > jumlahPinjaman) {
            System.out.println("Maaf, pembayaran Anda melebihi total pinjaman saat ini.");
        } else {
            jumlahPinjaman -= uang;
        }
    }
}
