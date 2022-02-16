import java.util.ArrayList;

public class Dealer {

    private final ArrayList <Card> dealerCards; //array list of current cards held by dealer
    private int dealerNumValue;
    private int dealerStartValue;

    public Dealer(){
        dealerCards = new ArrayList<>();
        dealerNumValue = 0;
        dealerStartValue = 0;
    }
    //Getters
    public int getDealerNumValue(){
        return dealerNumValue;
    }

    public int getDealerCardSize(){
        return dealerCards.size();
    }

    public Card getCard(int i){
        return dealerCards.get(i);
    }

    //other
    public void addCard(Card c){
        dealerCards.add(c);
        setNumValue();
    }

    public void setNumValue(){
        dealerNumValue = 0;
        for (Card card: dealerCards){
            if(card.getNumber() == 1){
                dealerNumValue += 11;
            }
            else {
                dealerNumValue += Math.min (card.getNumber(), 10);
            }
        }
    }

    public void printHand(){
        String hand = "Dealer Cards: ";
        for(Card c: dealerCards){
            hand += "[" + c + "]";
        }
        System.out.println(hand + "\nNumerical Value: " + dealerNumValue + "\n");
    }


    //Blackjack specific methods
    // prints starter hand for dealer with ? as second card
    public void printBJStartHand(){
        setDealerStartValue();
        System.out.println("Dealer Hand: [" +dealerCards.get(0) + "][?] \nNumerical Value: " + dealerStartValue + "\n");
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


    public static void main(String[]args){
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(12, 'H'));
        dealer.addCard(new Card(5, 'C'));
        dealer.printHand();
        System.out.println();
        dealer.printBJStartHand();
    }
}
