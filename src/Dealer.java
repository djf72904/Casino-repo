import java.util.ArrayList;

public class Dealer {

    private final ArrayList <Card> dealerCards; //array list of current cards held by dealer
    private int dealerNumValue; //numerical value of cards dealer has
    private int dealerStartValue; //numerical value of cards player sees dealer has at start of blackjack- first card in hand

    //constructor
    public Dealer(){
        dealerCards = new ArrayList<>();
        dealerNumValue = 0;
        dealerStartValue = 0;
    }
    /*
    -----------------------------------------------------------------------------------------------------------------
    Getters
    -----------------------------------------------------------------------------------------------------------------
     */

    //returns dealerNumValue
    public int getDealerNumValue(){
        return dealerNumValue;
    }

    //returns size of dealerCards Array List
    public int getDealerCardSize(){
        return dealerCards.size();
    }

    //returns card of dealerCards at index i
    public Card getCard(int i){
        return dealerCards.get(i);
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Utility Methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //sees if dealer cards have a card that matches parameter
    public boolean containsCard(Card match){
        for(Card c: dealerCards){
            if(c.equals(match)){
                return true;
            }
        }
        return false;
    }

    //finds the index of card that matches parameter. returns -1 if card is not in array
    public int getIndexOfCard(Card match) {
        int index = 0;
        for (Card c : dealerCards) {
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
    Instance variable manipulation
    -----------------------------------------------------------------------------------------------------------------
     */

    //removes last card in dealerCards
    public void deleteCard(){
        dealerCards.remove(dealerCards.size()-1);
    }

    //adds card on end of dealerCards
    public void addCard(Card c){
        dealerCards.add(c);
        setNumValue();
    }

    //sets numerical value of cards in dealerCards. NOTE: face cards are worth 10 and aces are worth 11 in this implementation
    public void setNumValue(){
        dealerNumValue = 0;
        for (Card card: dealerCards){
            if(card.getNumber() ==-1){
                dealerNumValue +=1;
            }
            else if(card.getNumber() == 1){
                dealerNumValue += 11;
            }
            else {
                dealerNumValue += Math.min (card.getNumber(), 10);
            }
        }
    }

    //changes num value of specific card at index
    public void changeCardNumValue(int index, int newValue){
        char suit = dealerCards.get(index).getSuit();
        dealerCards.add(index, new Card(newValue, suit));
        dealerCards.remove(index+1);
        setNumValue();
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Blackjack specific methods
    -----------------------------------------------------------------------------------------------------------------
    */

    //sets dealerStartValue to the value of the first Card in dealerCards in order to keep second card hidden from player
    private void setDealerStartValue(){
        boolean hasAceIn1 = dealerCards.get(0).getNumber() == 1 || dealerCards.get(0).getNumber() == -1;
        boolean hasAceIn2 = dealerCards.get(1).getNumber() == 1 || dealerCards.get(1).getNumber() == -1;

        if(hasAceIn1 && hasAceIn2){
            dealerStartValue = 11;
        }
        else if(hasAceIn2){
            dealerStartValue = dealerNumValue - 11;
        }
        else if(dealerCards.get(1).getNumber()>10){
            dealerStartValue = dealerNumValue - 10;
        }
        else {
            dealerStartValue = dealerNumValue - dealerCards.get(1).getNumber();
        }
    }

    // prints starter hand for dealer with ? as second card and numerical value as dealerStartValue
    public void printBJStartHand(){
        setDealerStartValue();
        System.out.println("Dealer Hand: [" +dealerCards.get(0) + "][?] \nNumerical Value: " + dealerStartValue + "\n");
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Output Methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //prints DealerCards hand along with numerical value of cards
    public void printHand(){
        String hand = "Dealer Cards: ";
        for(Card c: dealerCards){
            hand += "[" + c + "]";
        }
        System.out.println(hand + "\nNumerical Value: " + dealerNumValue + "\n");
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Misc/Testing
    -----------------------------------------------------------------------------------------------------------------
     */

    //main method for testing
    public static void main(String[]args){
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(12, 'H'));
        dealer.addCard(new Card(5, 'C'));
        dealer.printHand();
        System.out.println();
        dealer.printBJStartHand();
    }
}
