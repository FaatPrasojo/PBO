package SourceCode;

public class GameTest {
    public static void main(String[] args) {
        
        Player p1 = new Player("Prasojo", 150, 40, 65);
        Monster m1 = new Monster("Baby Dragon", 175, 15, "Dragon");
        BossMonster m2 = new BossMonster("Toothless", 350, 35, "Dragon");

        // Character[] characters = { p1, m1, m2 };
        // for (Character c : characters) {
        //     c.attack(p1); // semua karakter menyerang player
        // }

        System.out.println("----- PERTARUNGAN DIMULAI -----");

        int round = 1;

        while (p1.getHealth() > 0 && (m1.getHealth() > 0 || m2.getHealth() > 0)) {
            System.out.println("\n--- Ronde " + round + " ---");

            // Player menyerang monster
            if (m1.getHealth() > 0) {
                p1.attack(m1);
            } else if(m2.getHealth() > 0) {
                p1.attack(m2);
                p1.attack(m2);
            }

            // Player diserang monster
            if (m1.getHealth() > 0) {
                m1.attack(p1);
            } 
            if (m2.getHealth() > 0){
                m2.attack(p1);
            }

            if (p1.getHealth() < 50 && p1.getHealth() > 0) {
                p1.heal();
            }
            
            System.out.println("Status HP Saat Ini:");
            System.out.println("-> " + p1.getName() + ": " + p1.getHealth());
            System.out.println("-> " + m1.getName() + ": " + m1.getHealth());
            System.out.println("-> " + m2.getName() + ": " + m2.getHealth());

            if (p1.getHealth() <= 0) {
                System.out.println("\n----- PERTARUNGAN SELESAI -----");
                System.out.println("Maaf, " + p1.getName() + " telah dikalahkan!");
            } else if (m1.getHealth() <= 0 || m2.getHealth() <= 0){
                System.out.println("\n----- PERTARUNGAN SELESAI -----");
                System.out.println("Selamat, " + p1.getName() + " berhasil mengalahkan semua monster!");
            }
            round++;
        }   
    }
}
