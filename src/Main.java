import java.util.Random;

public class Main {

    public static int bossHealth = 3000;

    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 400, 250, 300, 400, 250, 300, 280};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 10, 20, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky","Berserk","Thor"};
    public static int roundNumber = 0;
    static Random random = new Random();

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }

    }


    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        thorsEffect();
        bossHits();
        medicsWork();
        heroesHit();
        luckyEvade();
        golemsWork();
        blocking();
        printStatistics();
        bossDamageDefault();
    }

    private static void thorsEffect() {
        boolean stun = random.nextBoolean();
        if (heroesHealth[7] > 0 && stun){
            bossDamage = 0;
            System.out.println("Thors Effect is successfully completed");
        }
    }

    private static void blocking() {
        int blocking = random.nextInt(20);
        if (heroesHealth[6] > 0){
            heroesDamage[6] += blocking;
            heroesHealth[6] += blocking;
        }
    }

    private static void luckyEvade() {
        boolean randomEvade = random.nextBoolean();
        if (randomEvade) {
            heroesHealth[5] += bossDamage;
            System.out.println("Boss is missed");
        }
    }


    private static void golemsWork() {
        bossDamage -= bossDamage / 5;
    }

    private static void medicsWork() {
        int randomHealing = random.nextInt(31);
        if (heroesHealth[3] >= 0) {
            for (int i = 0; i < heroesAttackType.length; i++) {
                if (heroesHealth[i] != heroesHealth[3] && heroesHealth[i] <= 100) {
                    heroesHealth[i] += randomHealing;
                    System.out.println("Medic healthing: " + heroesAttackType[i] + " " + randomHealing);
                    break;
                }
            }
        }
    }


    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int healthOfCurrentHero : heroesHealth) {
            if (healthOfCurrentHero > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        if (roundNumber == 0) {
            System.out.println("BEFORE START -------------");
        } else {
            System.out.println("ROUND " + roundNumber + " -------------");
        }

        System.out.println("Boss health: " + bossHealth + "; damage: "
                + bossDamage + "; defence: "
                + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " +
                    heroesHealth[i] + "; damage: " + heroesDamage[i]);

        }
    }

    private static void bossDamageDefault() {
        bossDamage = 50;
    }
}