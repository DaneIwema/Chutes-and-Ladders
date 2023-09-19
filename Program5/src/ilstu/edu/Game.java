package ilstu.edu;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * The game class creates the game and contains the classes to run the simulation.
 * @author miwem
 *
 */
public class Game {
	
	private LinkedList<Integer> cards;
	private Stack<Integer> faceDownCards;
	private Stack<Integer> faceUpCards;
	private Queue<Player> players;
	
	/**
	 * The Game constructor takes an array of names and creates player objects for each name
	 * and adds them to a queue in the order they were created. 
	 * @param players
	 */
	public Game(String [] players) {
		this.cards = new LinkedList<Integer>();
		this.faceDownCards = new Stack<Integer>();
		this.faceUpCards = new Stack<Integer>();
		this.players = new LinkedList<Player>();
		
		//methods populateCards() and shuffle() are called to ready the playing cards for dealing.
		populateCards();
		shuffle();
		
		//the players are created and dealt their random cards.
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null)
				break;
			this.players.add(new Player(players[i], this.faceDownCards.pop(), this.faceDownCards.pop(),this.faceDownCards.pop(),this.faceDownCards.pop()));
		}
	}
	
	/**
	 * The populateCards() method will create cards and add them to the cards linked list if both the cards linked list and
	 * the faceUpCards stack are empty, otherwise it will empty the faceDownCards stack and put them back into the 
	 * cards linked list.
	 * 
	 */
	private void populateCards() {
		if (cards.isEmpty() && faceUpCards.isEmpty()) {
			
			//new cards are created for the start of the game
			for (int i = 1; i <= 10; i++)
				cards.add(i);
			for (int i = 11; i <= 20; i++)
				cards.add(i);
			for (int i = 21; i <= 30; i++)
				cards.add(i);
			for (int i = 31; i <= 40; i++)
				cards.add(i);
		}
		else {
			
			//old cards are recycled to the cards linked list but saves the top card for the next turn.
			int temp = faceUpCards.pop();
			while (!faceUpCards.isEmpty())
				cards.add(faceUpCards.pop());
			faceUpCards.push(temp);
		}
	}
	
	/**
	 * the shuffle() method is a recursive method that calls itself until the cards linked list is empty.
	 * Each call will take the size of the cards list and reviece a random number based on that size and
	 * push it to the faceDownCards stack while removing it from the cards linked list.
	 */
	private void shuffle() {
		if (cards.isEmpty()) //Base case of the recursive method, if the linked list is empty then stop the method.
			return;
		
		//This will create a new SecureRandom object and get a random number corresponding to the size of the
		//of the cards linkedlist, remove that item from the linked list while also pushing it to the faceDownCards stack.
		SecureRandom rand = new SecureRandom();
		faceDownCards.push(cards.remove(rand.nextInt(cards.size())));
		shuffle();
	}
	
	/**
	 * The simulateGame() method is called when you want to start a game. it containts the game loop
	 * need to run the game and will cycle through each player in the queue and return them to the end of the queue
	 * after their turn is completed. 
	 */
	public void simulateGame() {
		
		//This is the game loop and will continue to loop until a winner is found
		while (!players.peek().drawCard(faceDownCards.pop())){
			faceUpCards.add(players.peek().throwCard());
			
			//The players hands and the face up card are shown because it is the end of a turn
			//and the current player is then added to the back of the queue.
			for (int i = 0; i < players.size(); i++) {
				System.out.println(players.peek());
				players.add(players.poll());
			}
			players.add(players.poll());
			System.out.println("The card facing up is " + getCardString(faceUpCards.peek()) + "\n");
			
			//This is used incase we run out of face down cards and recalls the populateCards()
			//and shuffle() methods to recycle the cards and have them shuffled again.
			if (faceDownCards.peek() == null) {
				populateCards();
				shuffle();
			}
			
			// This is the start of a new players turn, and if the face up card can make the next player win,
			//the loop will break and the player will pull the face up card and win.
			if (players.peek().decideCard(faceUpCards.peek()))
				break;
		}
	}
	
	/**
	 * the getCardString class will take an integer representing a card, and create the string associated with the card
	 * meaning that a 1 is the ace of clubs and 2 is the 2 of clubs and so on. 
	 * @param card - int representing the card
	 * @return cardString - the string built using the card int. 
	 */
	public String getCardString(int card) {
		StringBuilder cardString = new StringBuilder();
		
		//Tests if the card number is a one so the ace is shown, if it can evenly be divided by 10 then 10 is displayed,
		//otherwise the number is shown.
		if (card % 10 == 1)
			cardString.append("Ace");
		else if (card % 10 == 0)
			cardString.append(10);
		else
			cardString.append(card % 10);
		
		//goes through and checks if the card divided by 10 is the associated suit, or if the the card is a multiple of 10
		//and assigns the suit.
		if (card / 10 == 0 || card == 10)
			cardString.append(" of Clubs");
		else if (card / 10 == 1 || card == 20)
			cardString.append(" of Hearts");
		else if (card / 10 == 2 || card == 30)
			cardString.append(" of Spades");
		else if (card / 10 == 3 || card == 40)
			cardString.append(" of Diamonds");
		return cardString.toString();
	}
}