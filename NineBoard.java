import java.util.*;

public class NineBoard {

	Scanner scanner = new Scanner(System.in);
	int player;
	int computer;
	int depth;

	NineNode currentGameState;

	public NineBoard(int d){
		this.currentGameState = new NineNode(1, 100);
		this.depth = d;
	}

	public void startGame(){
		System.out.println("Welcome to Nine Board TTT");
		System.out.println("Do you want to be X or O? X will go first");

		this.currentGameState.printBoard();

		String userString = scanner.nextLine();

		if(userString.charAt(0) == 'x' || userString.charAt(0) == 'X'){
			this.player = 1;
			this.computer = 2;
		} else if(userString.charAt(0) == 'o' || userString.charAt(0) == 'O') {
			this.player = 2;
			this.computer = 1;
		}
		
		
		System.out.println("which board?");
		this.currentGameState.currentNode = scanner.nextInt();
		scanner.nextLine();
		

	}
	
	public void playGame(){
		while(this.currentGameState.isTerminal() == false){
			if(this.player == 1){
				playerMove();
				
				System.out.println("******the board is now******");
				System.out.println("current board is " + this.currentGameState.currentNode);
				this.currentGameState.printBoard();
//				System.out.println("****************");
				
				computerMove();
				
				System.out.println("******the board is now******");
				System.out.println("current board is " + this.currentGameState.currentNode);
				this.currentGameState.printBoard();
				///System.out.println("****************");
				
			} else {
				computerMove();
				
				System.out.println("******the board is now******");
				System.out.println("current board is " + this.currentGameState.currentNode);
				this.currentGameState.printBoard();
				//System.out.println("****************");
				
				playerMove();
				
				System.out.println("******the board is now******");
				System.out.println("current board is " + this.currentGameState.currentNode);
				this.currentGameState.printBoard();
//				System.out.println("****************");
				
			}
			
			
		}
	}
	
	public void playerMove(){
		System.out.println("Pick an available spot on the board 1-9");
		String nextMoveString = scanner.nextLine();
		char nextMove = nextMoveString.charAt(0);
		
		int next = Integer.parseInt(nextMoveString);

		int count = 1;

		boolean temp = true;
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(count == next && temp){
					this.currentGameState.nineBoard[(this.currentGameState.currentNode - 1)/3][(this.currentGameState.currentNode - 1)%3].board[i][j] = player;
					//this.currentGameState.board[i][j] = player;
					temp = false;
					this.currentGameState.currentNode = i * 3 + j + 1;
					break;
				}
				count++;
			}
		}

		//System.out.println("The board is now...");
		//this.currentGameState.printBoard();
		this.currentGameState.nextPlayer = computer;

		if(this.currentGameState.isTerminal()){

			if(player == 1){
				if(this.currentGameState.getWinner() == 1){
					System.out.println("you win");
				} else {
					System.out.println("TIE GAME");
				}
			} else if (player == 2){
				if(this.currentGameState.getWinner() == 2){
					System.out.println("you win");
				} else {
					System.out.println("TIE GAME");
				}
			} else {
				System.out.println("FATAL ERROR 3");
			}
			
			System.out.println("******the board is now******");
			this.currentGameState.printBoard();

	//		System.exit(0);


		}



	}
	
	
	public void computerMove(){
		NineNode tempNode = new NineNode(this.currentGameState.nextPlayer, this.currentGameState.currentNode);
		tempNode.nineBoard = this.currentGameState.copyBoard();
		
		NineNode temp1 = new NineNode(1, 2);
		temp1.nineBoard = this.currentGameState.copyBoard();
		int temp2 = 1;
		
		
		
		this.currentGameState = NineNode.alphaBetaSearch(tempNode, depth);
		
		this.currentGameState.nextPlayer = this.player;
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 3; k++){
					for(int l = 0; l < 3; l++){
						if(temp1.nineBoard[i][j].board[k][l] != this.currentGameState.nineBoard[i][j].board[k][l]){
							temp2 = (k) *3 + (l+1);
						}
					}
				}
			}
		}
		
		System.out.println(temp2);
		
		
		
		if(this.currentGameState.isTerminal()){

			if(computer == 1){
				if(this.currentGameState.getWinner() == 1){
					System.out.println("you lose");
				} else {
					System.out.println("TIE GAME");
				}
			} else if (computer == 2){
				if(this.currentGameState.getWinner() == 2){
					System.out.println("you lose");
				} else {
					System.out.println("TIE GAME");
				}
			} else {
				System.out.println("FATAL ERROR 6");
			}

			System.out.println("******the board is now******");
			this.currentGameState.printBoard();
			
		//	System.exit(0);


		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	

}
