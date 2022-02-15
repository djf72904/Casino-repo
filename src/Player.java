import java.util.ArrayList;

public class Player {
    private final ArrayList <Card> playerCards; //array list of current cards held by player
    private int playerNumValue;

    public Player(){
        playerCards = new ArrayList<>();
        playerNumValue = 0;
    }
    //Getters
    public int getPlayerNumValue(){
        return playerNumValue;
    }

    //other
    public void addCard(Card c){
        playerCards.add(c);
        setNumValue();
    }

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

    public void printHand(){
        String hand = "Player Cards: ";

        for(Card c: playerCards){
            hand += "[" + c + "]";
        }
        System.out.println(hand + "\nNumerical Value " + playerNumValue + "\n");
    }

    public static void main(String[]args){
        Player player = new Player();
        player.addCard(new Card(1, 'C'));
        player.addCard(new Card(11, 'H'));
        player.printHand();
    }
}

