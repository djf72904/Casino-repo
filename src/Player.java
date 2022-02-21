import java.util.ArrayList;

public class Player {
    private final ArrayList <Card> playerCards; //array list of current cards held by player
    private int playerNumValue; //numerical value of cards player has

    //constructor
    public Player(){
        playerCards = new ArrayList<>();
        playerNumValue = 0;
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
        setNumValue();
    }

    //sets the numerical value of cards in playerCards. NOTE: face cards are worth 10 and aces are worth 11 in this implementation
    public void setNumValue(){
        playerNumValue = 0;
        for(Card card: playerCards){
            if(card.getNumber() == 1){
                playerNumValue += 11;
            }
            else {
                playerNumValue += Math.min (card.getNumber(), 10);
            }
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
        player.addCard(new Card(1, 'C'));
        player.addCard(new Card(11, 'H'));
        player.printHand();
        if(player.containsCard(new Card(1, 'C'))){
            System.out.println("Has Card");
        }
        else{
            System.out.println("Does not have card");
        }
    }
}

