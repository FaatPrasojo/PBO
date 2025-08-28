package SepedaDemo;

public class SepedaDemo {
    public static void main(String[] args) {
        // Buat dua buah objek sepeda
        Sepeda spd1 = new Sepeda();
        Sepeda spd2 = new Sepeda();
        SepedaGunung spd3 = new SepedaGunung();


        // Panggil method di dalam objek sepeda
        spd1.setMerek("Polygone");
        spd1.tambahKecepatan(10);
        spd1.gantiGear(2);
        spd1.cetakStatus();

        spd2.setMerek("Wiim Cycle");
        spd2.tambahKecepatan(10);
        spd2.gantiGear(2);
        spd2.tambahKecepatan(10);
        spd2.gantiGear(3);
        spd2.cetakStatus();

        spd3.setMerek(null);
        spd3.tambahKecepatan(5);
        spd3.gantiGear(7);
        spd3.seTipeSuspensi("Gas suspension");
        spd3.cetakStatus();
    }
}