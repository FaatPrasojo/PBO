package Prasojo.relasiclass.percobaan1;

public class Processor {
    private String merk;
    private double cache;

    public Processor() {
    }

    public Processor(String merk, double cache) {
        this.merk = merk;
        this.cache = cache;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setCache(double cache) {
        this.cache = cache;
    }

    public String getMerk() {
        return merk;
    }

    public double getCache() {
        return cache;
    }

    public void info() {
        System.out.printf("Merk Prosesor = %s/n", merk);
        System.out.printf("Cache Prosesor = %.2f/n", cache);
    }
}