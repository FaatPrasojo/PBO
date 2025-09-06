package Percobaan;

public class TestMahasiswa {
    public static void main(String[] args) {
        Mahasiswa mhs1=new Mahasiswa();
        Mahasiswa pras = new Mahasiswa();
        Mahasiswa aswy = new Mahasiswa();

        System.out.println("=====--Data Mahasiswa--====");
        mhs1.nim=101;
        mhs1.nama="Lestari";
        mhs1.alamat="Jl. Vinolia No 1A";
        mhs1.kelas="1A";
        mhs1.tampilBiodata();
        System.out.println("--------------------------");
        pras.nim=102;
        pras.nama="Prasojo";
        pras.alamat="Sukun, Kota Malang";
        pras.kelas="2D";
        pras.tampilBiodata();
        System.out.println("--------------------------");
        aswy.nim=103;
        aswy.nama="Aisya";
        aswy.alamat="Suhat, Kota Malang";
        aswy.kelas="2D";
        aswy.tampilBiodata();

    }
}
