import java.util.ArrayList;

public class Player {
    private final ArrayList <Card> playerCards; //array list of current cards held by player
    private int playerNumValue; //numerical value of cards player has

    private final ArrayList<Card> split1; //array list of first split deck
    private final ArrayList<Card> split2; //array list of first split deck
    private final ArrayList<Card> split3; //array list of first split deck

    //constructor
    public Player(){
        playerCards = new ArrayList<>();
        playerNumValue = 0;

        split1 = new ArrayList<>();
        split2 = new ArrayList<>();
        split3 = new ArrayList<>();
    }
    /*
    -----------------------------------------------------------------------------------------------------------------
    Getters
    -----------------------------------------------------------------------------------------------------------------
    */

    //returns playerNumValue
    public int getPlayerNumValue(){
        return playerNumValue;
    }

    //returns size of playerCards Array List
    public int getPlayerCardSize(){
        return playerCards.size();
    }

    //returns card of playerCards at index i
    public Card getCard(int i){
        return playerCards.get(i);
    }

    //sees if player cards have a card that matches parameter
    public boolean containsCard(Card match){
        for(Card c: playerCards){
            if(c.equals(match)){
                return true;
            }
        }
        return false;
    }

    //finds the index of card that matches parameter. returns -1 if card is not in array
    public int getIndexOfCard(Card match) {
        int index = 0;
        for (Card c : playerCards) {
            if (c.equals(match)) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Instance Variables manipulation
    -----------------------------------------------------------------------------------------------------------------
     */

    //deletes last card in playerCards
    public void deleteCard(){
        playerCards.remove(playerCards.size()-1);
    }

    //adds card on end of playerCards
    public void addCard(Card c){
        playerCards.add(c);
    }

    //sets the numerical value of cards in playerCards. NOTE: face cards are worth 10 and aces are worth 11 in this implementation
    public void setNumValue(){
        playerNumValue = 0;
        for(Card card: playerCards){
            if(card.getNumber() == -1){
                playerNumValue += 1;
            }
            else if(card.getNumber() == 1){
                playerNumValue += 11;
            }
            else {
                playerNumValue += Math.min (card.getNumber(), 10);
            }
        }
    }

    //changes num value of specific card at index
    public void changeCardNumValue(int index, int newValue){
        char suit = playerCards.get(index).getSuit();
        playerCards.add(index, new Card(newValue, suit));
        playerCards.remove(index+1);
        setNumValue();
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Blackjack specific methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //initializes 1st split deck
    public void initSplit1(){
        split1.add(playerCards.get(1));
        playerCards.remove(1);
    }

    //initializes 2nd split deck
    public void initSplit2(){
        if(playerCards.get(0).equals(playerCards.get(1))){
            split2.add(playerCards.get(1));
            playerCards.remove(1);
        }
        else{
            split2.add(split1.get(1));
            split1.remove(1);
        }
    }

    //initializes 3rd split deck
    public void initSplit3(){
        if(playerCards.get(0).equals(playerCards.get(1))){
            split3.add(playerCards.get(1));
            playerCards.remove(1);
        }
        else if(split1.get(0).equals(split1.get(1))){
            split3.add(split1.get(1));
            split1.remove(1);
        }
        else{
            split3.add(split2.get(1));
            split2.remove(1);
        }
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Output methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //prints playerCards hand along with Numerical Value of cards
    public void printHand(){
        String hand = "Player Cards: ";

        for(Card c: playerCards){
            hand += "[" + c + "]";
        }
        System.out.println(hand + "\nNumerical Value: " + playerNumValue + "\n");
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Misc/Testing
    -----------------------------------------------------------------------------------------------------------------
     */

    //main method used for testing
    public static void main(String[]args){
        Player player = new Player();
        player.addCard(new Card(2, 'C'));
        player.addCard(new Card(11, 'H'));
        player.printHand();
        if(player.containsCard(new Card(1, 'C'))){
            System.out.println("Has Card");
            System.out.println("index of Ace : " + player.getIndexOfCard(new Card(1, 'C')));
        }
        else{
            System.out.println("Does not have card");
            System.out.println("index of Ace : " + player.getIndexOfCard(new Card(1, 'C')));
        }
    }
}

