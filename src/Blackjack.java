import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    Scanner scan = new Scanner(System.in);
    private final CardDeck[] bjDeck; // array of 6 CardDeck objects that are shuffled
    private final ArrayList <Card> playerCards; //array list of current cards held by player
    private final ArrayList <Card> dealerCards; //array list of current cards held by dealer
    private int deckPointer; //current index of bjDeck that dealer is in 0-3
    private int cardPointer; //current index of card in current deck 0-51
    private int playerNumValue;
    private int dealerNumValue;
    private int dealerStartValue;
    private int choice;

    public Blackjack(){
        bjDeck = new CardDeck[] {new CardDeck(), new CardDeck(), new CardDeck(), new CardDeck()};
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
        deckPointer = 0;
        cardPointer = 0;
        playerNumValue = 0;
        dealerNumValue = 0;
        dealerStartValue = 0;
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

    private void printHand(ArrayList<Card> cards){
        String hand = "";
        int numVal = 0;
        if(cards.equals(playerCards)){
            hand = "Player Cards: ";
            numVal = playerNumValue;
        }
        if(cards.equals(dealerCards)){
            hand = "Dealer Cards: ";
            if(dealerCards.size() == 2) {
                numVal = dealerStartValue;
            }
            else{
                numVal = dealerNumValue;
            }
        }
        System.out.print(hand);
        for(Card c: cards){
            System.out.print("[" + c + "] ");
        }
        System.out.println("\nNumerical Value: " + numVal + "\n");
    }

    private void printBothHands(){
        printHand(dealerCards);
        printHand(playerCards);
    }


    //deals the player 2 cards for start of round. cards 1 and 3
    private void dealPlayerCard(){
        if(deckPointer<4 && cardPointer<52){
            playerCards.add(bjDeck[deckPointer].getCard(cardPointer));
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
        setNumValue(playerCards);
    }
    //deals dealer 2 cards for start of round. cards 2 and 4
    private void dealDealerCard(){
        if(deckPointer<4 && cardPointer<52){
            dealerCards.add(bjDeck[deckPointer].getCard(cardPointer));
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
            setNumValue(dealerCards);
        }
    }

    private void dealDealerCard(Card customCard){
        dealerCards.add(customCard);
        setNumValue(dealerCards);
    }

    private void dealPlayerCard(Card customCard){
        playerCards.add(customCard);
        setNumValue(playerCards);
    }

    private void setNumValue(ArrayList<Card> cards){
        int value = 0;
        for (Card card : cards) {
            if (card.getNumber() == 1) {
                value += 11;
            }
            else {
                value += Math.min(card.getNumber(), 10);
            }
        }
        if(cards.equals(playerCards)){
            playerNumValue = value;
        }
        else if(cards.equals(dealerCards)){
            dealerNumValue = value;
        }
    }

    private void setDealerStartValue(){
        boolean hasAceIn2 = dealerCards.get(1).getNumber() == 1;
        if(hasAceIn2){
            dealerStartValue = dealerNumValue - 11;
        }
        else if(dealerCards.get(1).getNumber()>10){
                dealerStartValue = dealerNumValue - 10;
            }
        else {
            dealerStartValue = dealerNumValue - dealerCards.get(1).getNumber();
        }
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
        dealPlayerCard();
        dealDealerCard();
        dealPlayerCard();
        dealDealerCard();
        setDealerStartValue();
        printBothHands();
    }

    private void askPlayerOptions(){
        System.out.println("Please type in the number of what action you would like to do.");
        if(playerCards.size() == 2 && playerCards.get(0).getNumber() == playerCards.get(1).getNumber()) {
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
                if(playerNumValue>21){
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
