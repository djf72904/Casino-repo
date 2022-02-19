import java.util.Scanner;

public final class BettingSystem {
    private static final Scanner scan = new Scanner(System.in);
    private static int chips = 1000; //current chips held by player
    private static int maxBet = 100; //max bet of current game
    private static int currentBet = 0; //amount bet

    //Empty constructor because BettingSystem is not instantiated
    private BettingSystem(){
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Getters
    -----------------------------------------------------------------------------------------------------------------
     */

    //returns amount of chips player has. universal for entire casino
    public static int getChips(){
        return chips;
    }

    //returns the max bet for given game.
    public static int getMaxBet(){
        return maxBet;
    }

    //returns the player's current entered bet
    public static int getCurrentBet(){
        return currentBet;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Instance Variable manipulation
    -----------------------------------------------------------------------------------------------------------------
     */

    //sets the current bet of the player
    public static void setCurrentBet(int newBet){
        currentBet = newBet;
    }

    //asks user for bet and then sets that as currentBet
    public static void askBet(){
        System.out.println("Please enter bet amount. Max bet is "+maxBet);
        System.out.println("If you would like to quit enter -1");
        int bet = scan.nextInt();
        System.out.println("-----------------------------------------------------------------------------------------");
        while (bet>maxBet || (bet<=0 && bet!=-1)){
            System.out.println("You have entered an invalid bet. Please try again.\nThe max bet is "+maxBet);
            bet = scan.nextInt();
        }
            currentBet = bet;
        if(bet<0){
            System.out.println("Thank you for playing!");
        }
    }

    //sets the max bet allowed for current game
    public static void setMaxBet(int newMax){
        maxBet = newMax;
    }

    //sets the amount of chips a player has
    public static void setChips(int newChips){
        chips = newChips;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Win, lose, and tie methods for games.
    -----------------------------------------------------------------------------------------------------------------
     */

    //win method. gives the user their current bet into chips 1:1
    public static void win(){
        chips+=currentBet;
        System.out.println("You Won!\nYou have earned "+currentBet+" chips\nNew Chip total: "+chips + "\n");
        System.out.println("-----------------------------------------------------------------------------------------");
        currentBet = 0;
    }

    //lose method. takes the user's current bet from total chips 1:1
    public static void lose(){
        chips-=currentBet;
        System.out.println("You Lost!\nYou have lost "+currentBet+" chips.\nNew Chip total: "+chips + "\n");
        System.out.println("-----------------------------------------------------------------------------------------");
        currentBet = 0;
    }

    //tie method. user neither gains nor loses its bet
    public static void tie(){
        System.out.println("It's a tie!\nYou have neither gained or lost chips.\nChip total: "+chips + "\n");
        System.out.println("-----------------------------------------------------------------------------------------");
        currentBet = 0;
    }

}

