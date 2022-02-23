import java.util.*;
import java.util.concurrent.TimeUnit;

public class Blackjack {
    Scanner scan = new Scanner(System.in);
    private final Player player; //player object of player class
    private final Dealer dealer; //dealer object using blackjack specific methods

    private final CardDeck[] bjDeck; // array of 4 CardDeck objects that are shuffled in respective decks
    private final Card[] bjMixedDeck; //array of all 4 card decks shuffled into each other -- true shuffle
    private final int MAX_CARDS = 208; //constant amount of cards in the 4 combined decks
    private int deckPointer; //current index of bjDeck that dealer is in 0-3
    private int cardPointer; //current index of card in current deck 0-51

    private int indexOfAce; //index of an ace in hand

    private int choice; //player choice of which blackjack play they would like to make
    private int amountHit; // amount of times player has hit
    private boolean hasDD; //true if player has double downed. false if player has not

    //constructor
    public Blackjack(){
        player = new Player();
        dealer = new Dealer();

        bjDeck = new CardDeck[] {new CardDeck(), new CardDeck(), new CardDeck(), new CardDeck()};
        bjMixedDeck = new Card[MAX_CARDS];
        deckPointer = 0;
        cardPointer = 0;

        indexOfAce = -1;

        choice = 0;
        amountHit = 0;
        hasDD = false;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Utility methods for testing
    -----------------------------------------------------------------------------------------------------------------
    */

    //utility method that prints entire shuffled bj deck
    private void printBjDeck(){
        for(CardDeck cd: bjDeck){
            cd.printShuffled();
        }
    }

    //utility method for testing that prints true shuffled bj deck mixedBJDeck
    private void printMixedBjDeck(){
        int count = 0;
        for (Card c: bjMixedDeck){
            System.out.print("[" + c + "]" +" ");
            count++;
            //enters down after 26 cards
            if(count % 26 == 0){
                System.out.println();
            }
        }
    }

    //utility method for testing. Can insert custom card
    private void dealDealerCard(Card customCard){
        dealer.addCard(customCard);
        dealer.setNumValue();
    }
    //utility method for testing. Can insert custom card
    private void dealPlayerCard(Card customCard){
        player.addCard(customCard);
        updatePointers();
        player.setNumValue();
        playerAceDowngrade();
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Utility methods for abstraction
    -----------------------------------------------------------------------------------------------------------------
     */

    //delays next action by 1 second
    private void delayAction(){
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //shuffles all 4 decks in bjDeck
    private void shuffleDeck(){
        for(CardDeck cd: bjDeck) {
            cd.setShuffledDeck();
        }
    }

    //fills mixedBjDeck with all cards from bjDeck but ture shuffled
    private void shuffleMixedBjDeck() {
        //copies bjDeck into mixedBJDeck
        shuffleDeck();
        for (int x = 0; x < MAX_CARDS; x++) {
            bjMixedDeck[x] = bjDeck[deckPointer].getCard(cardPointer);
            updatePointers();
        }

        //shuffles mixedBJDeck
        List<Card> fullCardList = Arrays.asList(bjMixedDeck);
        Collections.shuffle(fullCardList);
        fullCardList.toArray(bjMixedDeck);
    }

    //updates deck and card pointers for transferring bjDeck into bjMixedDeck
    private void updatePointers(){
        if (cardPointer < 51) {
            cardPointer++;
        } else if (deckPointer < 3) {
            deckPointer++;
            cardPointer = 0;
        } else {
            deckPointer = 0;
            cardPointer = 0;
        }
    }

    //updates card pointer to keep track of card when going through bjMixedDeck
    private void updateCardPointer(){
        if(cardPointer<MAX_CARDS-1){
            cardPointer++;
        }
        else{
            cardPointer = 0;
            shuffleMixedBjDeck();
        }
    }

    //returns true if both starting cards are same number and player can split into 2 hands
    private boolean canSplit(){
        return player.getPlayerCardSize() == 2 && player.getCard(0).getNumber() == player.getCard(1).getNumber();
    }

    //checks to see if player numerical value has surpassed 21
    private boolean playerGoneOver(){
        return player.getPlayerNumValue()>21;
    }

    //checks to see if dealer numerical value has surpassed 21
    private boolean dealerGoneOver(){
        return dealer.getDealerNumValue()>21;
    }

    //checks to see if dealer cards are greater than player cards
    private boolean dealerIsGreater(){
        return dealer.getDealerNumValue()>player.getPlayerNumValue();
    }

    //Reset hands after round
    private void resetHands(){
        hasDD = false;
        amountHit = 0;
        for(int x=player.getPlayerCardSize()-1; x>=0; x--) {
            player.deleteCard();
        }
        for(int x=dealer.getDealerCardSize()-1; x>=0; x--){
            dealer.deleteCard();
        }
    }

    //Checks user input to be sure it stays in bounds
    private void choiceCheck(){
        while(!(choice>=1 && choice<=4)) {
            System.out.println("Invalid Entry. Please enter a valid number");
            choice = scan.nextInt();
        }
    }

    //when choice = 1 and player hits
    private void playerHit(){
        dealPlayerCard();
        amountHit++;
        if(playerGoneOver()){
            printBothHands();
            BettingSystem.lose();
        }
        else{
            printBothStarterHands();
        }
    }

    //when choice = 2 and player double downs
    private void playerDoubleDown(){
        dealPlayerCard();
        BettingSystem.setCurrentBet(BettingSystem.getCurrentBet()*2);
        System.out.println("New Bet: " + BettingSystem.getCurrentBet() +"\n");
        if(!playerGoneOver()) {
            printBothStarterHands();
        }
        else{
            printBothHands();
            delayAction();
            BettingSystem.lose();
        }
    }

    //gives the dealer a card
    private void dealerHit() {
        delayAction();
        dealDealerCard();
        printBothHands();
    }


    //compares both numerical values and decides who wins
    private void compareHands(){
        if(dealer.getDealerNumValue()>player.getPlayerNumValue() && !dealerGoneOver()){
            delayAction();
            BettingSystem.lose();
        }
        else if(dealer.getDealerNumValue()<player.getPlayerNumValue() || dealerGoneOver()){
            delayAction();
            BettingSystem.win();
        }
        else{
            delayAction();
            BettingSystem.tie();
        }
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Ace Checks
    -----------------------------------------------------------------------------------------------------------------=
    */

    //checks deck that is inputted to see if there is an ace in any position
    private boolean playerHasAce(){
        boolean hasAce = false;
        indexOfAce = -1;
        if(player.containsCard(new Card(1, 'H'))){
            hasAce = true;
            int indexOfAce = player.getIndexOfCard(new Card(1, 'H'));
        }
        else if(player.containsCard(new Card(1, 'S'))){
            hasAce = true;
            int indexOfAce = player.getIndexOfCard(new Card(1, 'S'));
        }
        else if(player.containsCard(new Card(1, 'C'))){
            hasAce = true;
            int indexOfAce = player.getIndexOfCard(new Card(1, 'C'));
        }
        else if(player.containsCard(new Card(1, 'D'))){
            hasAce = true;
            int indexOfAce = player.getIndexOfCard(new Card(1, 'D'));
        }
        return hasAce;
    }
    private boolean dealerHasAce(){
        boolean hasAce = false;
        if(dealer.containsCard(new Card(1, 'H'))){
            hasAce = true;
        }
        else if(dealer.containsCard(new Card(1, 'S'))){
            hasAce = true;
        }
        else if(dealer.containsCard(new Card(1, 'C'))){
            hasAce = true;
        }
        else if(dealer.containsCard(new Card(1, 'D'))){
            hasAce = true;
        }
        return hasAce;
    }


    //turns ace from 11 to 1
    private void playerAceDowngrade(){
        if(playerHasAce() && player.getPlayerNumValue()>21){
            player.changeCardNumValue(indexOfAce, -1);
            player.setNumValue();
        }
    }
    private void dealerAceDowngrade(){

    }


    /*
    -----------------------------------------------------------------------------------------------------------------
    Card dealing methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //deals the player a card. if starting cards, cards 1 and 3 are dealt to player
    private void dealPlayerCard() {
        player.addCard(bjMixedDeck[cardPointer]);
        updateCardPointer();
        player.setNumValue();
        playerAceDowngrade();
    }

    //deals the dealer a card. if starting cards, cards 2 and 4 are dealt to player
    private void dealDealerCard() {
        dealer.addCard(bjMixedDeck[cardPointer]);
        updateCardPointer();
        dealer.setNumValue();
    }

    //deals cards to player and dealer at start of round
    private void dealStarters(){
        for(int x=0; x<2; x++){
            dealPlayerCard(new Card(1, 'H'));
            dealDealerCard();
        }
        printBothStarterHands();
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Misc methods/Output methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //prints both bj hands for start, hiding the dealer's second card
    private void printBothStarterHands(){
        delayAction();
        dealer.printBJStartHand();
        player.printHand();
    }

    //prints both hands with full transparency
    private void printBothHands(){
        delayAction();
        dealer.printHand();
        player.printHand();
        System.out.println("----------------------------------------------------------------------------------------\n");
    }

    //asks player what blackjack play they would like to make. double down and split are available
    private void askPlayerOptions(){
        delayAction();
        System.out.println("Please type in the number of what action you would like to do.");
        if(canSplit()) {
            System.out.println("\t----------------------------------------------------------");
            System.out.println("\t|    (1)Hit    (2)Double Down    (3)Stand    (4)Split    |");
            System.out.println("\t----------------------------------------------------------");
        }
        else if(amountHit>0){
            System.out.println("\t-----------------------------");
            System.out.println("\t|    (1)Hit    (3)Stand     |");
            System.out.println("\t-----------------------------");
        }
        else{
            System.out.println("\t-----------------------------------------------");
            System.out.println("\t|    (1)Hit    (2)Double Down    (3)Stand     |");
            System.out.println("\t-----------------------------------------------");
        }
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Player and Dealer Turns
    -----------------------------------------------------------------------------------------------------------------
     */

    //player is given options to hit, stand, double down, and split if possible
    private void playerTurn() {
        choice = 0;
        while (!playerGoneOver() && choice !=3 && !hasDD) {
            askPlayerOptions();
            choice = scan.nextInt();
            choiceCheck();
            if (choice == 1) {
                playerHit();
            } else if (choice == 2) {
                playerDoubleDown();
                hasDD = true;
            }
        }
    }

    //dealer hits until 17 or more or bust is reached
    private void dealerTurn(){
        int count = 0;
        System.out.println("\nDealer Turn");
        System.out.println("-----------------------------------------------------------------------------------------\n");
        printBothHands();
        while (!dealerIsGreater()) {
            if (!dealerGoneOver() && dealer.getDealerNumValue() < 17) {
                dealerHit();
                count++;
            }
            else{
                break;
            }
        }
        if(count == 0) {
            printBothHands();
            compareHands();
        }
        else{
            compareHands();
        }
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Play method that runs all of blackjack
    -----------------------------------------------------------------------------------------------------------------
     */

    public void play(){
        BettingSystem.setMaxBet(500);
        shuffleMixedBjDeck();

        if(BettingSystem.getChips()>0){
            System.out.println("\nWelcome to the blackjack table! I assume you know the rules.\nIf not...look em up!");
            System.out.println("---------------------------------------------------------------------------------------");
            BettingSystem.askBet();

            while(BettingSystem.getChips()>0  && (BettingSystem.getCurrentBet() != -1)){
                dealStarters();
                playerTurn();
                if(!playerGoneOver()) {
                    dealerTurn();
                }
                resetHands();
                BettingSystem.askBet();
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

}
