import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    Scanner scan = new Scanner(System.in);
    private final CardDeck[] bjDeck; // array of 6 CardDeck objects that are shuffled
    private final Player player;
    private final Dealer dealer;
    private int deckPointer; //current index of bjDeck that dealer is in 0-3
    private int cardPointer; //current index of card in current deck 0-51
    private int choice;

    public Blackjack(){
        bjDeck = new CardDeck[] {new CardDeck(), new CardDeck(), new CardDeck(), new CardDeck()};
        player = new Player();
        dealer = new Dealer();
        deckPointer = 0;
        cardPointer = 0;
        choice = 0;
    }

    //Utility methods
    //prints entire shuffled bj deck
    private void printBjDeck(){
        for(CardDeck cd: bjDeck){
            cd.printShuffled();
        }
    }

    //shuffles all 4 decks in bjDeck
    private void shuffleDeck(){
        for(CardDeck cd: bjDeck) {
            cd.setShuffledDeck();
        }
    }

    private void printBothHands(){
        if(dealer.getDealerCardSize() == 2){
            dealer.printBJStartHand();
        }
        else{
            dealer.printHand();
        }
        player.printHand();
    }


    //deals the player 2 cards for start of round. cards 1 and 3
    private void dealPlayerCard(){
        if(deckPointer<4 && cardPointer<52){
            player.addCard(bjDeck[deckPointer].getCard(cardPointer));
            if(cardPointer<51){
                cardPointer++;
            }
            else if(deckPointer<3){
                deckPointer++;
                cardPointer=0;
            }
            else{
                shuffleDeck();
                deckPointer = 0;
                cardPointer = 0;
            }
        }
            player.setNumValue();
    }
    //deals dealer 2 cards for start of round. cards 2 and 4
    private void dealDealerCard(){
        if(deckPointer<4 && cardPointer<52){
            dealer.addCard(bjDeck[deckPointer].getCard(cardPointer));
            if(cardPointer<51){
                cardPointer++;
            }
            else if(deckPointer<3){
                deckPointer++;
                cardPointer=0;
            }
            else{
                shuffleDeck();
                deckPointer = 0;
                cardPointer = 0;
            }
            dealer.setNumValue();
        }
    }

    private void dealDealerCard(Card customCard){
        dealer.addCard(customCard);
        dealer.setNumValue();
    }

    private void dealPlayerCard(Card customCard){
        player.addCard(customCard);
        player.setNumValue();
    }

    private boolean hasAce(ArrayList<Card> cards){
        for (Card card : cards) {
            if (card.getNumber() == 1) {
                return true;
            }
        }
        return false;
    }


    /*
    private void aceDowngrade(ArrayList<Card> cards){
        if(hasAce(cards) && getNumValue(cards)>21){
            if(cards.equals(dealerNumValue)){
                dealerNumValue -= 11;
            }
            if(cards.equals)
        }
    }
     */

    //deals cards to player and dealer at start of round
    private void dealStarters(){
        for(int x=0; x<2; x++){
            dealPlayerCard();
            dealDealerCard();
        }
        printBothHands();
    }

    private boolean canSplit(){
        return player.getPlayerCardSize() == 2 && player.getCard(0).getNumber() == player.getCard(1).getNumber();
    }

    private void askPlayerOptions(){
        System.out.println("Please type in the number of what action you would like to do.");
        if(canSplit()) {
            System.out.println("\t----------------------------------------------------------");
            System.out.println("\t|    (1)Hit    (2)Double Down    (3)Stand    (4)Split    |");
            System.out.println("\t----------------------------------------------------------");
        }
        else{
            System.out.println("\t-----------------------------------------------");
            System.out.println("\t|    (1)Hit    (2)Double Down    (3)Stand     |");
            System.out.println("\t-----------------------------------------------");
        }
    }

    private void playerTurn() {


        choice = scan.nextInt();
        while(!(choice>0 && choice< 5)) {
            System.out.println("Please enter a valid number");
            choice = scan.nextInt();
        }
            if (choice == 1) {
                dealPlayerCard();
                printBothHands();
                if(player.getPlayerNumValue()>21){
                    BettingSystem.lose();
                }
                else if(choice!=3){
                    playerTurn();
                }
                else{
                    System.out.println("Dealer Turn");
                    System.out.println("-----------");
                    dealerTurn();
                }
            }
            if(choice == 2){
                BettingSystem.setCurrentBet(BettingSystem.getCurrentBet()*2);
                dealPlayerCard();
                printBothHands();
                playerTurn();
            }
            if(choice == 3) {
                printBothHands();
            }
        }

        private void dealerTurn(){

        }


    public void play(){
        BettingSystem.setMaxBet(500);
        shuffleDeck();

        if(BettingSystem.getChips()>0){
            System.out.println("\nWelcome to the blackjack table! I assume you know the rules.\nIf not...look em up!");
            System.out.println("---------------------------------------------------------------------------------------");
            while(BettingSystem.getChips()>0){
                BettingSystem.askBet();
                dealStarters();
                playerTurn();
            }
        }
        else{
            System.out.println("It seems you don't have enough chips for this game...please return later.");
        }
    }

    public static void main(String[]args){
        Blackjack bj = new Blackjack();
        bj.play();
    }

}// end Blackjack
