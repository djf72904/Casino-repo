public class Card {
    private int number;     //card number
    private char suit;      //card suit

    public Card(int number, char suit) {
        this.number = number;
        this.suit = suit;
    }//end Card Constructor

    //Accessors

    public int getNumber(){
        return this.number;
    }//end getNumber

    public char getSuit(){
        return this.suit;
    }//end getSuit

    //toString
    public String toString() {
        //changes 11 to J, 12 to Q, and 13 to K.
        if (this.number==1)
            return "A"+this.suit;
        else if(this.number==11)
            return "J"+this.suit;
        else if(this.number==12)
            return "Q"+this.suit;
        else if(this.number==13)
            return "K"+this.suit;
        else
            return ""+this.number+this.suit;
    }//end toString
}//end Card
