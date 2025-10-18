package KendaraanOOP;

public class Truk extends Kendaraan {
    double kapasitasMuatan;

    public Truk(String merk, int tahunProduksi, double kapasitasMuatan) {
        super(merk, tahunProduksi);
        this.kapasitasMuatan = kapasitasMuatan;
    }

    @Override
    void jalankan() {
        System.out.println("Truk " + merk + " sedang mengangkut barang.");
    }

    void info() {
        super.info();
        System.out.println("Kapasitas Muatan : " + kapasitasMuatan + " Ton");
    }
}
