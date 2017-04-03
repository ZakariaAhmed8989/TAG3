package tag2ver4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Zack
 */
public class GameController {

    Scanner userInput = new Scanner(System.in);
    Room[][] roomMatrix = new RoomFactory().createRooms();
    Boundery b = new Boundery();
    BackpackController bpc = new BackpackController();
    boolean gamerun;
    private boolean invalidInput = true;
    String name;
    String helpInput;
    Player n1 = new Player(name, 100, roomMatrix[0][0], 1, 0);

    public void runGame(Player n1) {

        // choose to go inside the roomMatrix or not this works              
        b.chooseRoomStart();

        Scanner rumA = new Scanner(System.in);
        String enterchoice = rumA.next();

        //n1.setLocation(roomMatrix[0][0]);
        if (enterchoice.equals("1")) {
            System.out.println(n1.getLocation());
            gamerun = true;
        }

        if (enterchoice.equals("0")) {
            gameOver(n1);
            System.exit(0);
            gamerun = false;

        }
        if (!(enterchoice.equals("0") || enterchoice.equals("1"))) {
            System.out.println("Say what!");
            runGame(n1);
        }

        gamerun = true;

        boolean game;

        // initalise room
        game = true;
        while (game) {
            while (gamerun) {

                // You win the game:
                if (n1.getRoomNr() == 9) {
                    System.out.println(n1.getName() + ", You won");
                    System.out.println("You collected " + n1.getPlayerGold() + " gold coins" + "\n"
                            + "--------------------------------------------------------------------");
                    printMethod(n1);
                    restartGame(n1);
                }
                invalidInput = true;

                while (invalidInput) {
                    // You lose the game:  
                    if (n1.getHealth() < 1) {

                        System.out.println("You Died. Game Over");
                        restartGame(n1);

                    }
                    b.chooseDirection();
                    // you are inside the game and should be able to move around in the dungeon
                    
                    String brugerInput = userInput.nextLine();
                    
                    
                      if (brugerInput.equalsIgnoreCase("help")) {
                        Help(n1);
                    }

                    if (brugerInput.equalsIgnoreCase("search")) {

                        bpc.Search(n1, n1.getLocation());
                        bpc.addToBackpack(n1, n1.getLocation());

                    }
                    
                    // ROOM NORTH
                    if (brugerInput.equalsIgnoreCase("n")) {
                        if (n1.getLocation().getNorth() != null) {
                            direction(n1, n1.getLocation().getNorth());
                        } // IF NO ROOM TO THAT DIRECTION
                        else {
                            System.out.println("No room north for room " + n1.getRoomNr() + "\n"
                                    + "--------------------------------------------------------------------");
                        }
                    }

                    // ROOM SOUTH
                    if (brugerInput.equalsIgnoreCase("s")) {
                        if (n1.getLocation().getSouth() != null) {
                            direction(n1, n1.getLocation().getSouth());
                        } // IF NO ROOM TO THAT DIRECTION
                        else {
                            System.out.println("No room south for room " + n1.getRoomNr() + "\n"
                                    + "--------------------------------------------------------------------");
                        }
                    }

                    // ROOM EAST
                    if (brugerInput.equalsIgnoreCase("e")) {
                        if (n1.getLocation().getEast() != null) {
                            direction(n1, n1.getLocation().getEast());
                        } // IF NO ROOM TO THAT DIRECTION
                        else {
                            System.out.println("No room east for room " + n1.getRoomNr() + "\n"
                                    + "--------------------------------------------------------------------");
                        }
                    }
                    // ROOM WEST
                    if (brugerInput.equalsIgnoreCase("w")) {
                        if (n1.getLocation().getWest() != null) {
                            direction(n1, n1.getLocation().getWest());
                        } // // IF NO ROOM TO THAT DIRECTION
                        else {
                            System.out.println("No room west for room " + n1.getRoomNr() + "\n"
                                    + "--------------------------------------------------------------------");
                        }
                    }
                    
                }
            }
        }

    }

    private void direction(Player n1, Room nextRoom) {
        invalidInput = false;

        // sætter vores ny position for spilleren
        n1.setLocation(nextRoom);

        n1.setRoomNr(n1.getLocation().getCurrentRoom());

        int gold = nextRoom.getGold();
        int health = n1.getHealth();
        n1.setPlayerGold(n1.getPlayerGold() + gold);
        nextRoom.setGold(0);

        n1.setHealth(health - 10);

        System.out.println("" + n1.getLocation() + "\n");
        System.out.println("You found " + gold + " gold coins" + "\n");
        //System.out.println("Your health is " + n1.getHealth() + "\n");

    }


    public void Help(Player n1) {
        
        
        b.helpMenu(n1);
        invalidInput = false;
        Scanner scanHelp = new Scanner(System.in);
        helpInput = scanHelp.nextLine();
        helpInput.toLowerCase();
        
        switch(helpInput)
        {
            case "exit":
            invalidInput = true;
            System.out.println("You are still in room " + n1.getRoomNr());
            break;
            
            case "gold":
            System.out.println("You have collected:  " + n1.getPlayerGold() + " gold pieces");
            Help(n1);
            break;
            
            case "health":
            System.out.println("Your health is at:  " + n1.getHealth() + " %");
            Help(n1);
            break;
            
            case "quit":
            gameOver(n1);
            System.exit(0);
            break;
            
            case "sack":
            bpc.printBackPack(n1);
            bpc.rucksackOptions(n1);
            break;
            
            case "search":
             bpc.Search(n1, n1.getLocation());
             Help(n1);
            break;
        }
    }

    public void restartGame(Player n1) {

        Scanner exitopt = new Scanner(System.in);
        System.out.println("Do you wanna play again? Type 'Yes' for yes og 'No' for no");
        String startover = exitopt.nextLine();

        if (startover.equalsIgnoreCase("no")) {
            System.out.println("Thank you for playing  " + n1.getName() + " !");
            System.exit(0);
        }

        if (startover.equalsIgnoreCase("yes")) {
            n1.setHealth(100);
            n1.setLocation(roomMatrix[0][0]);
            n1.setRoomNr(1);
            System.out.println(n1.getRoomNr() + "  " + n1.getLocation());
            gamerun = true;

        } else {
            restartGame(n1);
        }

    }

    public void gameOver(Player n1) {

        System.out.println("Bye  " + n1.getName() + "  Game Over");

    }

    public static void printMethod(Player n1) {
        try {
            String file = "C:\\Users\\Ejer\\Desktop\\Datamatiker\\TagGame2\\TagGame2\\TagGameNew\\src\\tag2ver4\\HighScores.txt";
            String text = "High scores:" + n1.name + "  " + n1.getPlayerGold();
            try (BufferedWriter out = new BufferedWriter(new FileWriter(file, true))) {
                out.write(text);
            }
        } catch (IOException out) {
            System.out.print(out);
        }

    }

}