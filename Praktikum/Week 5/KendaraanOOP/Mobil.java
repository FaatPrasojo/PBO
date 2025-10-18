package KendaraanOOP;

public class Mobil extends Kendaraan{
    int jumlahPintu;

    public Mobil(String merk, int tahunProduksi, int jumlahPintu){
        super(merk, tahunProduksi);
        this.jumlahPintu = jumlahPintu;
    }

    @Override
    void jalankan(){
        System.out.println("Mobil " + merk + " berjalan...");
    }

    void info(){
        super.info();
        System.out.println("Jumlah Pintu : " + jumlahPintu);
    }
}
