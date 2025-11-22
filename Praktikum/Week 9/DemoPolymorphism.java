class Manusia {
    void bernafas() {
        System.out.println("Manusia sedang bernafas...");
    }

    void makan() {
        System.out.println("Manusia sedang makan...");
    }
}

class Dosen extends Manusia {
    @Override
    void makan() {
        System.out.println("Dosen makan di ruang dosen.");
    }

    void lembur() {
        System.out.println("Dosen sedang lembur menyiapkan materi kuliah.");
    }
}

class Mahasiswa extends Manusia {
    @Override
    void makan() {
        System.out.println("Mahasiswa makan di kantin kampus.");
    }

    void tidur() {
        System.out.println("Mahasiswa tidur setelah belajar semalaman.");
    }
}

public class DemoPolymorphism {
    public static void main(String[] args) {
        Manusia manusia;

        manusia = new Dosen();
        manusia.bernafas();
        manusia.makan();  

        System.out.println();

        manusia = new Mahasiswa();
        manusia.bernafas();
        manusia.makan(); 

        System.out.println();

        ((Dosen) new Dosen()).lembur();
        ((Mahasiswa) new Mahasiswa()).tidur();
    }
}
