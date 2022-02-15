import java.util.Scanner;

public class GameSystem {

    public static void main (String[]args){
        start();
    }





    private static void start(){
        Scanner scan = new Scanner(System.in);
        Blackjack bj = new Blackjack(); //Creation of blackjack game
        String gameList = "List of Games:\n(1)Blackjack";

        System.out.println("\nWelcome to the Casino! Please enter which game you would like to play. (Type in number)");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(gameList);

        int gameChoice = scan.nextInt();

        while(gameChoice>0) {
            switch (gameChoice) {
                case 1: {
                    bj.play();
                }
                case 2: {

                }
            }
            System.out.println("\nThank you for playing. If you would like to play another game enter the number. \nIf not, enter -1.");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(gameList);
            gameChoice = scan.nextInt();
        }

        System.out.println("Thanks for visiting the Casino! Come again!");
    }

}
