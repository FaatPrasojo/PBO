package KendaraanOOP;

public class Motor extends Kendaraan {
    int kapasitasCC;

    public Motor(String merk, int tahunProduksi, int kapasitasCC){
        super(merk, tahunProduksi);
        this.kapasitasCC = kapasitasCC;
    }

    @Override
    void jalankan(){
        System.out.println("Motor " + merk + " berjalan");
    }

    void info(){
        super.info();
        System.out.println("Kapasitas CC :" + kapasitasCC);
    }
}
