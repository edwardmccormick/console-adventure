package game;


import java.util.Scanner;

public class OrcBattle {

    private int[] heroArray;
    private Hero hero;
    private Orc orc;
    private Scanner scanner;

    public OrcBattle(int[] heroArray) {
        this.heroArray = heroArray;
        this.hero = new Hero(heroArray);
        this.orc = new Orc();
        this.scanner = new Scanner(System.in);
    }

    public void battle() {
        int enemyHP = orc.getEnemyHP();
        int heroHP = hero.getHealthPoints();

        System.out.println("It's an orc! He looks nasty!");

        label:
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1: Attack.");
            System.out.println("2: Drink potion.");
            System.out.println("3: Run.");
            String action = scanner.nextLine();

            switch (action) {
                case "1":
                    int attackValue = 25;
                    orc.changeEnemyHP(attackValue * -1);
                    System.out.printf("You hit the orc for %d points!%n", attackValue);
                    if (orc.getEnemyHP() <= 0) {
                        System.out.println("The orc has 0 health points remaining!");
                        winOrcBattle();
                        break label;
                    } else {
                        System.out.printf("The orc has %d health points remaining.%n", orc.getEnemyHP());
                        int enemyAttackValue = 1; //orc.generateAttackValue();
                        hero.changeHealthPoints(enemyAttackValue * -1);
                        System.out.printf("The orc hits you for %d points!%n", enemyAttackValue);
                        if (hero.getHealthPoints() <= 0) {
                            System.out.println("You have no health points remaining! Oh no!");
                            hero.die();
                            break label;
                        } else {
                            System.out.printf("You have %d health points remaining.%n", hero.getHealthPoints());
                        }
                    }


                    break;
                case "2":

                    hero.usePotion();

                    break;
                case "3":
                    int runSuccess = hero.attemptRun();
                    if (runSuccess == 1) {
                        System.out.println("You got away safely!");
                        System.out.println("You return to the crossroads.");
                        Crossroads crossroads = new Crossroads(hero.getHero());
                        crossroads.choosePath();
                    } else {

                        if (hero.getHealthPoints() == 1) {
                            System.out.println("You got away safely!");
                            System.out.println("You return to the crossroads.");
                            Crossroads crossroads = new Crossroads(hero.getHero());
                            crossroads.choosePath();
                        } else {
                            System.out.println("The orc manages to hit you as you run away! You have lost half of your remaining health points!");
                            hero.changeHealthPoints((hero.getHealthPoints() / 2) * -1);
                            System.out.printf("You now have %d health points remaining.%n", hero.getHealthPoints());
                            System.out.println("You return to the crossroads.");
                            Crossroads crossroads = new Crossroads(hero.getHero());
                            crossroads.choosePath();
                        }
                    }
                    break label;
                default:
                    System.out.println("That is not a valid action. Let's try this again.");
                    break;
            }
        }
    }

    public void winOrcBattle() {
        System.out.println("You beat the orc!");
        System.out.println("You find a potion and 2 gold!");
        hero.changePotionCount(1);
        hero.changeGold(2);
        hero.changeMountainWins();
        while (true) {
            System.out.println("Would you like to stay in the mountains?");
            this.heroArray = hero.getHero();
            System.out.printf("You have %d health points.%n", heroArray[1]);
            System.out.printf("You have %d potions.%n", heroArray[0]);
            System.out.println("1: Stay in the mountains.");
            System.out.println("2: Go back to the crossroads.");
            String action = scanner.nextLine();
            if (action.equals("1")) {
                Mountain mountain = new Mountain();
                mountain.stayOrGo(hero.getHero());
                break;
            } else if (action.equals("2")) {
                Crossroads crossroads = new Crossroads(hero.getHero());
                crossroads.choosePath();
            } else {
                System.out.println("That is not a valid action. Let's try this again.");
                System.out.println("What would you like to do?");
            }
        }

    }
}