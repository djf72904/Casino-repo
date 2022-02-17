import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private final Card [] sortedDeck = new Card[52];     //array of card objects in box order
    private Card [] shuffledDeck;   // array of card objects shuffled

    //constructor
    public CardDeck(){
        //Set cards for hearts
        for(int x=0; x<13; x++)
        {
            sortedDeck[x] = new Card(x+1,'H');
        }
        //Set cards for Clubs
        for(int x=0; x<13; x++)
        {
            sortedDeck[x + 13] = new Card(x+1,'C');
        }
        //Set cards for Diamonds
        for(int x=0; x<13; x++)
        {
            sortedDeck[x + 26] = new Card(x + 1, 'D');
        }
        //Set cards for Spades
        for(int x=0; x<13; x++)
        {
            sortedDeck[x + 39] = new Card(x + 1, 'S');
        }
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Getters
    -----------------------------------------------------------------------------------------------------------------
     */

    //returns card in shuffled deck at index i
    public Card getCard(int i){
        return shuffledDeck[i];
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Instance variable manipulation
    -----------------------------------------------------------------------------------------------------------------
     */

    //creates a shuffled deck out of the sorted deck
    public void setShuffledDeck() {
        shuffledDeck = sortedDeck.clone();
        List<Card> cardList = Arrays.asList(shuffledDeck);
        Collections.shuffle(cardList);
        cardList.toArray(shuffledDeck);
    }

    /*
    -----------------------------------------------------------------------------------------------------------------
    Output methods
    -----------------------------------------------------------------------------------------------------------------
     */

    //prints a sorted deck of cards
    public void printSorted(){
        for(Card c: sortedDeck)
        {
            System.out.print(c+"\t");
            //enters down after 13 cards are printed
            if(c.getNumber()==13)
                System.out.println();
        }
    }

    //prints a shuffled deck of cards
    public void printShuffled(){
        int count = 1; //keeps track of how many cards in row
        for (Card c : shuffledDeck) {
            System.out.print(c + "\t");
            count++;
            //enters down after 1 cards are printed
            if (count % 13 == 1)
                System.out.println();
        }
    }
}

