import java.util.ArrayList;

public class Dealer {

    private final ArrayList <Card> dealerCards; //array list of current cards held by dealer
    private int dealerNumValue;

    public Dealer(){
        dealerCards = new ArrayList<>();
        dealerNumValue = 0;
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
        dealerCards.add(C);
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







    public static void main(String[]args){

    }
}
