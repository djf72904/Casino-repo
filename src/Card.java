public class Card {
    private int number;     //card number
    private char suit;      //card suit

    //constructor
    public Card(int number, char suit) {
        this.number = number;
        this.suit = suit;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Getters
    -----------------------------------------------------------------------------------------------------------------
     */

    //returns number of Card
    public int getNumber(){
        return this.number;
    }

    //returns suit of Card
    public char getSuit(){
        return this.suit;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Utility methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //equals method that checks if two cards are equal in number and suit
    public boolean equals(Card c){
        return c.getNumber() == this.number && c.getSuit() == this.suit;
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Output methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //allows the card to be printed out with number or letter and suit
    public String toString() {
        //changes 11 to J, 12 to Q, and 13 to K.
        if (this.number==1 || this.number==-1)
            return "A"+this.suit;
        else if(this.number==11)
            return "J"+this.suit;
        else if(this.number==12)
            return "Q"+this.suit;
        else if(this.number==13)
            return "K"+this.suit;
        else
            return ""+this.number+this.suit;
    }
}
