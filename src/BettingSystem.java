import java.util.Scanner;

public final class BettingSystem {
    private static Scanner scan = new Scanner(System.in);
    private static int chips = 1000; //current chips held by player
    private static int maxBet = 100; //max bet of current game
    private static int currentBet = 0; //amount bet

    //Empty constructor because BettingSystem is not instantiated
    private BettingSystem(){
    }

    //Accessors
    public static int getChips(){
        return chips;
    }//end getChips

    public static int getMaxBet(){
        return maxBet;
    }//end getMaxBet

    public static int getCurrentBet(){
        return currentBet;
    }//end getCurrentBet

    //Mutators
    public static void setCurrentBet(int newBet){
        currentBet = newBet;
    }//end setCurrentBet

    //asks user for bet and then sets that as currentBet
    public static void askBet(){
        System.out.println("Please enter bet amount. Max bet is "+maxBet);
        int bet = scan.nextInt();
        while (bet>maxBet || bet<=0){
            System.out.println("You have entered an invalid bet. Please try again.\nThe max bet is "+maxBet);
            bet = scan.nextInt();
        }
        currentBet = bet;
    }

    public static void setMaxBet(int newMax){
        maxBet = newMax;
    }//end setMaxBet

    public static void setChips(int newChips){
        chips = newChips;
    }//end setChips

    //Win and lose methods for games
    public static void win(){
        chips+=currentBet;
        System.out.println("You Won!\nYou have earned "+currentBet+" chips\nNew Chip total: "+chips);
        currentBet = 0;
    }//end win

    public static void lose(){
        chips-=currentBet;
        System.out.println("You Lost!\n You have lost "+currentBet+" chips.\nNew Chip total: "+chips);
        currentBet = 0;
    }//end lose

}

