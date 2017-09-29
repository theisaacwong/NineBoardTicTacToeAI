import java.util.*;


public class NineNode {

	public Node[][] nineBoard;
	public ArrayList<NineNode> children;
	public NineNode parent;
	public int utility;
	public NineNode favoriteChild;
	public int nextPlayer;
	public int currentNode = 100;

	public static int count = 0;

	public NineNode(int np, int currNode){
		nineBoard = new Node[3][3];
		parent = null;
		nextPlayer = np;
		favoriteChild = null;
		children = new ArrayList<NineNode>();
		utility = 0;


		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				this.nineBoard[i][j] = new Node('n');
			}
		}
		
		this.currentNode = currNode;
	}


	public void printBoard(){

		//a test to make sure the printing below worked
		//		for(int i = 0; i < 3; i++){
		//			for(int j = 0; j < 3; j++){
		//				for(int k = 0; k < 3; k++){
		//					for(int l = 0; l < 3; l++){
		//						nineBoard[i][j].board[k][l] = (1 + k * 3 + l);
		//					}
		//				}
		//			}
		//		}

		for(int i = 0; i < 3; i++){

			System.out.print(nineBoard[i][0].board[0][0] + "" + nineBoard[i][0].board[0][1] + "" + nineBoard[i][0].board[0][2]); System.out.print("   ");
			System.out.print(nineBoard[i][1].board[0][0] + "" + nineBoard[i][1].board[0][1] + "" + nineBoard[i][1].board[0][2]); System.out.print("   ");
			System.out.print(nineBoard[i][2].board[0][0] + "" + nineBoard[i][2].board[0][1] + "" + nineBoard[i][2].board[0][2]); System.out.print("\n");

			System.out.print(nineBoard[i][0].board[1][0] + "" + nineBoard[i][0].board[1][1] + "" + nineBoard[i][0].board[1][2]); System.out.print("   ");
			System.out.print(nineBoard[i][1].board[1][0] + "" + nineBoard[i][1].board[1][1] + "" + nineBoard[i][1].board[1][2]); System.out.print("   ");
			System.out.print(nineBoard[i][2].board[1][0] + "" + nineBoard[i][2].board[1][1] + "" + nineBoard[i][2].board[1][2]); System.out.print("\n");

			System.out.print(nineBoard[i][0].board[2][0] + "" + nineBoard[i][0].board[2][1] + "" + nineBoard[i][0].board[2][2]); System.out.print("   ");
			System.out.print(nineBoard[i][1].board[2][0] + "" + nineBoard[i][1].board[2][1] + "" + nineBoard[i][1].board[2][2]); System.out.print("   ");
			System.out.print(nineBoard[i][2].board[2][0] + "" + nineBoard[i][2].board[2][1] + "" + nineBoard[i][2].board[2][2]); System.out.print("\n\n");

		}

	}

	public boolean isFull(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(nineBoard[i][j].isFull() == false){
					return false;
				}
			}
		}
		return true;
	}

	public Node[][] copyBoard(){
		Node[][] newBoard = new Node[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				newBoard[i][j] = new Node('n');
				newBoard[i][j].nextPlayer = nineBoard[i][j].nextPlayer;
				newBoard[i][j].board = nineBoard[i][j].copyBoard();
			}
		}
		return newBoard;
	}

	public int getWinner(){

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){

				if(nineBoard[i][j].xWon()){
					return 1;
				} else if(nineBoard[i][j].oWon()){
					return 2;
				}
			}
		}
		return 0;
	}

	public boolean isTerminal(){
		return ((isFull()) || (getWinner() != 0));
	}

	public void setUtility(){
		if(getWinner() == 1){
			//x wins
			utility = 10000;
		} else if(getWinner() == 2){
			//o wins
			utility = -10000;
		} else if(isFull()){
			//tie
			utility = 100;
		}else{
			//heuristic
			try{
				utility = getHeuristic() * 50;
			}catch(Exception e){
				this.utility = 500;
			}
			
		}
	}
	
	public int getHeuristic(){
		int points = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j]==1){
					points = points +1;
				}
			}
		}
		for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
			if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j]==2){
				points = points - 1;
			}
		}
		}
		
		//Checks rows
		for(int i = 0; i < 3; i++){
			for(int j = 0; j<3; j++){
				if(i<2){
					if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i+1][j] == 1 && 
							this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j]==1){
						points = points + 10;
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j<3; j++){
				if(i<2){
					if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i+1][j] == 2 && 
							this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j]== 2){
						points = points - 10;
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j<3; j++){
				if(j<2){
					if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j+1] == 1 && 
							this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j]==1){
						points = points + 10;
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j<3; j++){
				if(j<2){
					if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j+1] == 2 && 
							this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j]==2){
						points = points + 10;
					}
				}
			}
		}
		
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[0][0] == 1 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 1){
			points = points + 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[2][2] == 1 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 1){
			points = points + 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[0][2] == 1 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 1){
			points = points + 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[2][0] == 1 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 1){
			points = points + 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[0][0] == 2 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 2){
			points = points - 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[2][2] == 2 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 2){
			points = points - 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[0][2] == 2 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 2){
			points = points - 10;
		}
		if(this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[2][0] == 2 &&
				this.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[1][1] == 2){
			points = points - 10;
		}
		return points;
	}


	public void fillChildren(){
		if(this.isTerminal()){
			this.setUtility();
			return;
		}
		
		
		
		
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].isEmpty(i, j)){
					int temp = 9;

					if(nextPlayer == 1){
						temp = 2;
					} else if(nextPlayer == 2){
						temp = 1;
					} else {
						System.out.println("FATAL ERROR AT FILLCHILDREN() 1");
					}
					
					//create a new 9node with focus at the new node
					NineNode node = new NineNode(temp, i * 3 + j + 1);
					node.nineBoard = this.copyBoard();
					node.parent = this;
					//fill in the move made at the prev node, not new focus node
					node.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)%3].board[i][j] = nextPlayer;
					//node.nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)/3].fillCildren();
					children.add(node);
					Collections.shuffle(children);
					
				}
			}
		}
		
		//nineBoard[(this.currentNode-1)/3][(this.currentNode - 1)/3].fillCildren();
		
		
		
		
	}
	
	//dont use, only left to show how making this method progressed, and our past struggles. 
	public void fillChildrenn(){
		if(isTerminal()){
			setUtility();
			return;
		}

		int tempCount = 1;

		for(int i = 0; i < 3; i ++){
			for(int j = 0; j < 3; j++){
				if(tempCount == this.currentNode){
					for(int k = 0; k < 3; k++){
						for(int l = 0; l < 3; l++){
							if(nineBoard[i][j].isEmpty(k, l)){

								int temp = 9;

								if(nextPlayer == 1){
									temp = 2;
								} else if(nextPlayer == 2){
									temp = 1;
								} else {
									System.out.println("FATAL ERROR AT FILLCHILDREN() 1");
								}

								
//								//starting here
//								nineBoard[i][j].fillCildren();
								
								Node node = new Node(temp);
								node.board = nineBoard[i][j].copyBoard();
								node.parent = nineBoard[i][j];
								nineBoard[i][j].children.add(node);
								node.board[k][l] = nextPlayer;
								
								node.fillCildren();


								this.currentNode = k * 3 + l + 1;//

							}
						}
					}

					Collections.shuffle(nineBoard[i][j].children);

				}
				tempCount++;
			}
		}

	}

	public static NineNode alphaBetaSearch(NineNode state, int depth){

		
		
		NineNode tempPointer;

		//x, max player
		if(state.nextPlayer == 1){
			System.out.println("maxing \t****Please Wait ****");
			tempPointer = maxValue(state, -999999, 999999, depth);
		} else if(state.nextPlayer == 2){
			System.out.println("min-ing \t****Please Wait ****");
			tempPointer = minValue(state, -999999, 999999, depth);
		} else {
			System.out.println("FATAL ERROR AT ALPHABETASEARCH 1");
			tempPointer = new NineNode(-1, state.currentNode);
		}

		
		
		System.out.println("moves considered: " + count);
		count = 0;

		return tempPointer;
	}

	public static NineNode maxValue(NineNode state, int alpha, int beta, int depth){
		count++;

		if(depth == 0){
			//System.out.println("using heuristic");
			state.setUtility();
			return state;
		}

		if(state.isTerminal()){
			state.setUtility();
			return state;
		}

		int v = -999999;

		int currBestChild = 0;

		state.fillChildren();

		if(state.children.size() == 0){
			//System.out.println("using heuristic");
			state.setUtility();
			return state;
		}


		Collections.shuffle(state.children);

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

	public static NineNode minValue(NineNode state, int alpha, int beta, int depth){
		count++; 

		if(depth == 0){
			//System.out.println("using heuristic");
			state.setUtility();
			return state;
		}
		
		if(state.isTerminal()){
			//System.out.println("using heuristic");
			state.setUtility();
			return state;
		}

		int v = 999999;

		int currBestChild = 0;

		state.fillChildren();

		if(state.children.size() == 0){
			//System.out.println("using heuristic");
			state.setUtility();
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
