import java.util.*;

public class Node {

	public int[][] board; // 0 is blank, 1 is x, 2 is o
	public ArrayList<Node> children;
	public Node parent;
	public int utility; // -100, 50, 100
	public Node favoriteChild;
	public int nextPlayer; // 1 is x, 2 is o
	
	public static int count = 0;


	public Node(int np){
		board = new int[3][3];
		parent = null;
		nextPlayer = np;
		favoriteChild = null;
		children = new ArrayList<Node>();
		utility = 0;
	}

	public void printBoard(int k){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	public void printBoard(){

		int count = 1;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){

				if(board[i][j] == 2 ){
					System.out.print('o');
				} else if(board[i][j] == 1) {
					System.out.print('x');
				} else if(board[i][j] == 0){
					System.out.print(count);
				}
				count++;
			}
			System.out.println();
		}

	}


	public boolean isFull(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}

	public int[][] copyBoard(){
		int[][] newBoard = new int[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}

	public boolean isEmpty(int x, int y){
		return board[x][y] == 0;
	}


	//1 is x, 2 is o
	public int getWinner(){
		if(xWon()){
			return 1;
		} else if(oWon()){
			return 2;
		}
		return 0;
	}

	public boolean isTerminal(){
		//you can never have too many parenthesis
		return ((isFull()) || (getWinner() != 0));
	}

	public boolean xWon(){
		//diagonol win
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
			return true;
		}
		//row/column win
		for (int i = 0; i < 3; ++i) {
			if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1) || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
				return true;
			}
		}
		return false;
	}

	public boolean oWon(){
		//diagonol win
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
			return true;
		}
		//row/column win
		for (int i = 0; i < 3; ++i) {
			if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2) || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2))) {
				return true;
			}
		}
		return false;
	}

	public void setUtility(){
		if(xWon()){
			utility = 100;
		} else if(oWon()){
			utility = -100;
		} else if(isFull()){
			utility = 50;
		}
	}

	public void fillCildren(){
		if(isTerminal()){
			setUtility();
			return;
		}

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(isEmpty(i,j)){

					int temp = 9;
					if(nextPlayer == 1){
						temp = 2;
					} else if(nextPlayer == 2){
						temp = 1;
					} else {
						System.out.println("FATAL ERROR AT NODE FILL CHILDREN 69");
					}

					Node node = new Node(temp);
					node.board = this.copyBoard();
					node.parent = this;
					this.children.add(node);
					node.board[i][j] = this.nextPlayer; //or temp????
				}
			}
		}
		
		Collections.shuffle(children);

		
	}

	//decremented
	public boolean equals(Node n){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(this.board[i][j] != n.board[i][j]){
					return false;
				}
			}
		}
		return true;
	}

	public static Node minimax(Node state){

		Node tempPointer;

		//x, max player
		if(state.nextPlayer == 1){
			tempPointer = max(state);
			//o, min player
		}else if(state.nextPlayer == 2){
			tempPointer = min(state);
		} else {
			System.out.println("FATAL ERROR");
			tempPointer = new Node(-1);
		}

		//		System.out.println("GOAL STATE");
		//		tempPointer.printBoard();

		//might have to traverse to the favorite child??????
		//		while(tempPointer.parent != state){
		//			tempPointer = tempPointer.parent;
		//		}


		System.out.println("moves considered: " + count);
		count = 0;

		return tempPointer;
	}

	public static Node max(Node state){
		count++;

		if(state.isTerminal()){
			state.setUtility();
			return state; // return it's utility
		}

		int v = -99999;

		int currBestChild = 0;

		state.fillCildren();

		if(state.children.size() == 0){
			return state;
		}

		for(int i = 0; i < state.children.size(); i++){

			int tempv = v;
			v = Math.max(v, min(state.children.get(i)).utility);

			if(tempv != v){
				currBestChild = i;
			}
		}

		state.favoriteChild = state.children.get(currBestChild);

		return state.children.get(currBestChild);
	}

	public static Node min(Node state){
		count++; 

		if(state.isTerminal()){
			state.setUtility();
			return state; // return it's utility
		}

		int v = 99999;

		int currBestChild = 0;

		state.fillCildren();

		if(state.children.size() == 0){
			return state;
		}

		for(int i = 0; i < state.children.size(); i++){

			int tempv = v;
			v = Math.min(v, max(state.children.get(i)).utility);

			if(tempv != v){
				currBestChild = i;
			}
		}

		state.favoriteChild = state.children.get(currBestChild);

		return state.children.get(currBestChild);
	}


	public static Node alphaBetaSearch(Node state){
		Node tempPointer;

		//x, max player
		if(state.nextPlayer == 1){
			tempPointer = maxValue(state, -99999, 99999, 100);
			//o, min player
		}else if(state.nextPlayer == 2){
			tempPointer = minValue(state, -99999, 99999, 100);
		} else {
			System.out.println("FATAL ERROR");
			tempPointer = new Node(-1);
		}

		//		System.out.println("GOAL STATE");
		//		tempPointer.printBoard();

		//might have to traverse to the favorite child??????
		//		while(tempPointer.parent != state){
		//			tempPointer = tempPointer.parent;
		//		}


		System.out.println("moves considered: " + count);
		count = 0;

		return tempPointer;
	}

	public static Node maxValue(Node state, int alpha, int beta, int depth){
		count++;
		
		if(depth == 0){
			System.out.println("shouldn't be here");
			return state; // heuristic // will have to go through the state's children and find one with the best heurostic???
			//or should assign the state's utiltiy a heuristic value
		}

		if(state.isTerminal()){
			state.setUtility();
			return state; // return it's utility
		}

		int v = -99999;

		int currBestChild = 0;

		state.fillCildren();

		if(state.children.size() == 0){
			return state;
		}

		for(int i = 0; i < state.children.size(); i++){

			int tempv = v;
			v = Math.max(v, minValue(state.children.get(i), alpha, beta, depth -1).utility);

			if(tempv != v){
				currBestChild = i;
			}
			
			if(state.children.get(currBestChild).utility >= beta){
				state.favoriteChild = state.children.get(currBestChild);
				return state.children.get(currBestChild);
			}
			
			alpha= Math.max(alpha, state.children.get(currBestChild).utility);
			
		}

		state.favoriteChild = state.children.get(currBestChild);

		return state.children.get(currBestChild);
	}

	public static Node minValue(Node state, int alpha, int beta, int depth){
		count++; 

		if(depth == 0){
			System.out.println("shouldn't be here");
			return state; // heuristic // will have to go through the state's children and find one with the best heurostic???
			//or should assign the state's utiltiy a heuristic value
		}
		
		if(state.isTerminal()){
			state.setUtility();
			return state; // return it's utility
		}

		int v = 99999;

		int currBestChild = 0;

		state.fillCildren();

		if(state.children.size() == 0){
			return state;
		}

		for(int i = 0; i < state.children.size(); i++){

			int tempv = v;
			v = Math.min(v, maxValue(state.children.get(i), alpha, beta, depth -1).utility);

			
			if(tempv != v){
				currBestChild = i;
			}
			
			
			if(state.children.get(currBestChild).utility <= alpha){
				state.favoriteChild = state.children.get(currBestChild);
				return state.children.get(currBestChild);
			}
			
			beta= Math.min(beta, state.children.get(currBestChild).utility);
			
		}

		state.favoriteChild = state.children.get(currBestChild);

		return state.children.get(currBestChild);
	}

}




























