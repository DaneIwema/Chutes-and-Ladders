package ilstu.edu;

import java.util.Scanner;

/**
 * The MainCLass will prompt the user for 2-5 player names and then creates the Game object
 * and calls the simulateGame() method on the object to start the game. 
 * @author miwem
 *
 */
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Scanner input = new Scanner(System.in);
//		System.out.print("Please enter the amount of players (minimum of 2, maximum of 5): ");
//		int playerCount = input.nextInt();
//		if (playerCount < 2 || playerCount >5)
//		do {
//			System.out.println("Invalid number, please enter 2, 3, 4, or 5: ");
//			playerCount = input.nextInt();
//		} while(playerCount < 2 || playerCount >5);
//		input.nextLine();
//		String [] players = new String[5];
//		for (int i = 0; i < playerCount; i++) {
//			System.out.print("Player " + (i+1) + "'s name: ");
//			players[i]= input.nextLine();
//		}
//		System.out.println();
//		Game newGame = new Game(players);
//		newGame.simulateGame();
		Player player = new Player("ANgelica", 10, 20 ,30 ,40);
		System.out.println(player); 
	}
}
