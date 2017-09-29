import java.util.*;

public class TTTBoard {

	Scanner scanner = new Scanner(System.in);
	int player;
	int computer;
	//x goes first
	Node currentGameState = new Node(1);


	public TTTBoard(){

		//		//if player is x
		//		if(plyr == 1){
		//			this.player = 1;
		//			this.computer = 2;
		//		} else {
		//			this.player = 2; 
		//			this.computer = 1;
		//		}

		//x goes first
		this.currentGameState = new Node(1);

	}

	public void startGame(){
		System.out.println("welcome to Tic Tac Toe");
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
	}

	public void playGame(){
		while(this.currentGameState.isTerminal() == false){
			if(this.player == 1){
				playerMove();
				
				System.out.println("******the board is now******");
				this.currentGameState.printBoard();
//				System.out.println("****************");
				
				computerMove();
				
				System.out.println("******the board is now******");
				this.currentGameState.printBoard();
				///System.out.println("****************");
				
			} else {
				computerMove();
				
				System.out.println("******the board is now******");
				this.currentGameState.printBoard();
				//System.out.println("****************");
				
				playerMove();
				
				System.out.println("******the board is now******");
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
					this.currentGameState.board[i][j] = player;
					temp = false;
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
				if(this.currentGameState.xWon()){
					System.out.println("you win");
				} else {
					System.out.println("TIE GAME");
				}
			} else if (player == 2){
				if(this.currentGameState.oWon()){
					System.out.println("you win");
				} else {
					System.out.println("TIE GAME");
				}
			} else {
				System.out.println("FATAL ERROR 3");
			}
			
			System.out.println("******the board is now******");
			this.currentGameState.printBoard();

//			System.exit(0);


		}



	}
	public void computerMove(){
		Node tempNode = new Node(this.currentGameState.nextPlayer);
		tempNode.board = this.currentGameState.copyBoard();


		//tempNode = Node.minimax(tempNode);
		
		
		Node temp1 = new Node('n');
		temp1.board = tempNode.copyBoard();
		
			currentGameState = Node.minimax(tempNode);
			//currentGameState = Node.alphaBetaSearch(tempNode);
		
		
		int temp2 = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(temp1.board[i][j] != this.currentGameState.board[i][j]){
					temp2 = i * 3 + j + 1;
				}
			}
		}
		
		System.out.println(temp2);
		
		
		
		
		this.currentGameState.nextPlayer = player;
		//currentGameState.printBoard();
		
		if(this.currentGameState.isTerminal()){

			if(computer == 1){
				if(this.currentGameState.xWon()){
					System.out.println("you lose");
				} else {
					System.out.println("TIE GAME");
				}
			} else if (computer == 2){
				if(this.currentGameState.oWon()){
					System.out.println("you lose");
				} else {
					System.out.println("TIE GAME");
				}
			} else {
				System.out.println("FATAL ERROR 6");
			}

			System.out.println("******the board is now******");
			this.currentGameState.printBoard();
			
	//		System.exit(0);


		}






	}




}
