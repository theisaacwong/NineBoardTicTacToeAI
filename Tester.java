import java.util.*;

public class Tester {

	public static void main(String[] args){
		int playAgain = 1;
		Scanner scanner = new Scanner(System.in);
		while(playAgain == 1){
			System.out.println("do you want to play nine board TTT or basic TTT? enter 1 for basic, enter 2 for nineboard");
			int i = new Scanner(System.in).nextInt();
			//new Scanner(System.in).nextLine();

			if(i == 1){
				TTTBoard tttboard = new TTTBoard();
				tttboard.startGame();
				tttboard.playGame();
			}

			else {
				NineBoard nineboard = new NineBoard(10);
				nineboard.startGame();
				nineboard.playGame();
			}

			System.out.println("Do you want to play again (1/0)");
			//		new Scanner(System.in).nextLine();

			playAgain = scanner.nextInt();
		}

	}
}
