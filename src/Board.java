import java.util.ArrayList;
import java.util.Random;

public class Board 
{
	public final int BOARD_SIZE = 8;
	public final int AGGRESSION_CONSTANT = 27;
	private String[][] state;
	private String pos_X;
	private String pos_O;
	private String playerTurn;
	
	ArrayList<Board> children;
	
	public Board()
	{
		state = new String[BOARD_SIZE][BOARD_SIZE];
		state[0][0] = "X";
		state[7][7] = "O";
		pos_X = "A1";
		pos_O = "H8";
		playerTurn = "X"; // can change the default
		children = new ArrayList<Board>();
	}
	
	public Board(String[][] state, String pos_X, String pos_O, String playerTurn)
	{
		this.state = state;
		this.pos_X = pos_X;
		this.pos_O = pos_O;
		this.playerTurn = playerTurn;
		children = new ArrayList<Board>();
	}
	
	// generates possible moves for current X move
	public ArrayList<Board> generateChildrenX()
	{
		int cur_X_letter = getNumber(pos_X.substring(0,1));
		int cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		children = new ArrayList<Board>();
		
		// diagonal up-left
		int max_moves = Math.min(cur_X_letter, cur_X_num);
		
		for (int i = 0; i < max_moves; i++) {
			cur_X_letter-=1;
			cur_X_num-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		
		// diagonal down-right
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_X_letter, BOARD_SIZE - cur_X_num);
		for (int i = 0 ; i < max_moves - 1; i++) {
			cur_X_letter+=1;
			cur_X_num+=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		
		// diagonal up-right
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = Math.min(cur_X_letter, BOARD_SIZE - cur_X_num - 1);
		for (int i = 0 ; i < max_moves; i++) {
			cur_X_letter-=1;
			cur_X_num+=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		
		// diagonal down-left
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_X_letter - 1, cur_X_num);
		for (int i = 0 ; i < max_moves; i++) {
			cur_X_letter+=1;
			cur_X_num-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		
		// horizontal right
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1));
		max_moves = BOARD_SIZE - cur_X_num;
		for (int i = 0 ; i < max_moves; i++) {
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
			cur_X_num+=1;
		}
		
		// horizontal left
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		for (int i = cur_X_num; i > 0; i--) {
			cur_X_num-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		
		// vertical up
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		for (int i = cur_X_letter; i > 0; i--) {
			cur_X_letter-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		
		// vertical down
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = BOARD_SIZE - cur_X_letter;
		for (int i = 0; i < max_moves - 1; i++) {
			cur_X_letter+=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "O");
			newBoardChild.moveX(getLetter(cur_X_letter) + (cur_X_num+1));
			children.add(newBoardChild);
		}
		return children;
	}
	
	// generates possible moves for current O move
	public ArrayList<Board> generateChildrenO()
	{
		int cur_O_letter = getNumber(pos_O.substring(0,1));
		int cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		children = new ArrayList<Board>();
		
		// diagonal up-left
		int max_moves = Math.min(cur_O_letter, cur_O_num);
		
		for (int i = 0; i < max_moves; i++) {
			cur_O_letter-=1;
			cur_O_num-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		
		// diagonal down-right
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_O_letter, BOARD_SIZE - cur_O_num);
		for (int i = 0 ; i < max_moves - 1; i++) {
			cur_O_letter+=1;
			cur_O_num+=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		
		// diagonal up-right
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = Math.min(cur_O_letter, BOARD_SIZE - cur_O_num - 1);
		for (int i = 0 ; i < max_moves; i++) {
			cur_O_letter-=1;
			cur_O_num+=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		
		// diagonal down-left
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_O_letter - 1, cur_O_num);
		for (int i = 0 ; i < max_moves; i++) {
			cur_O_letter+=1;
			cur_O_num-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		
		// horizontal right
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1));
		max_moves = BOARD_SIZE - cur_O_num;
		for (int i = 0 ; i < max_moves; i++) {
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
			cur_O_num+=1;
		}
		
		// horizontal left
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		for (int i = cur_O_num; i > 0; i--) {
			cur_O_num-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		
		// vertical up
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		for (int i = cur_O_letter; i > 0; i--) {
			cur_O_letter-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		
		// vertical down
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = BOARD_SIZE - cur_O_letter;
		for (int i = 0; i < max_moves - 1; i++) {
			cur_O_letter+=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			Board newBoardChild = new Board(this.deepCopyMatrix(), pos_X, pos_O, "X");
			newBoardChild.moveO(getLetter(cur_O_letter) + (cur_O_num+1));
			children.add(newBoardChild);
		}
		return children;
	}
	
	// moves X player to move_X (input)
	public void moveX(String move_X)
	{
		int cur_X_letter = getNumber(pos_X.substring(0,1));
		int cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		int move_X_letter = getNumber(move_X.substring(0,1));
		int move_X_num = Integer.parseInt(move_X.substring(1)) - 1;
		
		state[cur_X_letter][cur_X_num] = "#";
		state[move_X_letter][move_X_num] = "X";
		
		pos_X = move_X;
	}
	
	// moves O player to move_O (input)
	public void moveO(String move_O)
	{
		int cur_O_letter = getNumber(pos_O.substring(0,1));
		int cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		int move_O_letter = getNumber(move_O.substring(0,1));
		int move_O_num = Integer.parseInt(move_O.substring(1)) - 1;
		
		state[cur_O_letter][cur_O_num] = "#";
		state[move_O_letter][move_O_num] = "O";
		
		pos_O = move_O;
	}
	
	// moves X and O player (given inputs move_X and move_O) (this method mainly for
	// debugging and is easily substituted with moveX and moveO)
	public void updateBoard(String move_X, String move_O)
	{		
		// coordinates of the current positions of X and O
		int cur_X_letter = getNumber(pos_X.substring(0,1));
		int cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		int cur_O_letter = getNumber(pos_O.substring(0,1));
		int cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		
		// coordinates of the next move positions of X and O
		int move_X_letter = getNumber(move_X.substring(0,1));
		int move_X_num = Integer.parseInt(move_X.substring(1)) - 1;
		int move_O_letter = getNumber(move_O.substring(0,1));
		int move_O_num = Integer.parseInt(move_O.substring(1)) - 1;
		
		// assignments of the new moves
		state[cur_X_letter][cur_X_num] = "#";
		state[cur_O_letter][cur_O_num] = "#";
		state[move_X_letter][move_X_num] = "X";
		state[move_O_letter][move_O_num] = "O";
		
		pos_X = move_X;
		pos_O = move_O;
	}
	
	public boolean isValidMoveO(String move) 
	{
		if (move.length() != 2)
			return false;
		
		if (!Character.isLetter(move.charAt(0)))
			return false;
		
		if (!Character.isDigit(move.charAt(1)))
			return false;
			
		int cur_O_letter = getNumber(pos_O.substring(0,1));
		int cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		int move_O_letter = getNumber(move.substring(0,1));
		int move_O_num = Integer.parseInt(move.substring(1)) - 1;
		
		// check invalid input (needs to be A-H and 1-8) or the obvious
		if (getLetter(move_O_num) == "" || getNumber(move.substring(0,1)) == -1) {
			//System.out.println("Invalid Input!");
			return false;
		}
		if (cur_O_letter == move_O_letter && cur_O_num == move_O_num) {
			//System.out.println("Invalid Move! (self)");
			return false;
		}
		if (state[move_O_letter][move_O_num] != null) {
			//System.out.println("Invalid Move! (@ move)");
			return false;
		}
		
		// check if valid move (horizontal, vertical, or diagonal)
		boolean horizontal = false;
		boolean vertical = false;
		boolean diagonal = false;
		
		if (cur_O_num == move_O_num) {
			vertical = true;
			//System.out.println("Vertical Move!");
		}
		
		if (cur_O_letter == move_O_letter) {
			horizontal = true;
			//System.out.println("Horizontal Move!");
		}
		
		if (Math.abs(cur_O_letter - move_O_letter) == Math.abs(cur_O_num - move_O_num)) {
			diagonal = true;
			System.out.println("Diagonal Move!");
		}
		
		if (!(horizontal || vertical || diagonal)) {
			System.out.println("Invalid Move!");
			return false;
		}
		
		// check if there are obstacles (horizontal, vertical, or diagonal)
		boolean obstacle = false;
		if (horizontal)
		{
			int difference = cur_O_num - move_O_num;
			if (difference < 0)
			{
				for (int i = cur_O_num + 1; i < move_O_num; i++)
				{
					if (state[cur_O_letter][i] != null) {
						obstacle = true;
						System.out.println("H Obstacle at: " + getLetter(cur_O_letter) + (i+1));
						return false;
					}
				}
			}
			else if (difference > 0)
			{
				for (int i = cur_O_num - 1; i> move_O_num; i--)
				{
					if (state[cur_O_letter][i] != null) {
						obstacle = true;
						System.out.println("H Obstacle at: " + getLetter(cur_O_letter) + (i+1));
						return false;
					}
				}
			}
			else {
				System.out.println("No difference!");
			}
		}
		
		if (vertical)
		{
			int difference = cur_O_letter - move_O_letter;
			if (difference < 0)
			{
				for (int i = cur_O_letter + 1; i < move_O_letter; i++)
				{
					if (state[i][cur_O_num] != null) {
						obstacle = true;
						System.out.println("V Obstacle at: " + getLetter(i) + (cur_O_num+1));
						return false;
					}
				}
			}
			else if (difference > 0)
			{
				for (int i = cur_O_letter - 1; i> move_O_letter; i--)
				{
					if (state[i][cur_O_num] != null) {
						obstacle = true;
						System.out.println("V Obstacle at: " + getLetter(i) + (cur_O_num+1));
						return false;
					}
				}
			}
			else {
				System.out.println("No difference!");
			}
		}
		
		if (diagonal)
		{
			int letterDifference = cur_O_letter - move_O_letter;
			int numDifference = cur_O_num - move_O_num;
			if (letterDifference > 0 && numDifference < 0)
			{
				int j = cur_O_num + 1;
				for (int i = cur_O_letter - 1; i > move_O_letter; i--)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 1 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j++;
				}
			}
			else if (letterDifference < 0 && numDifference > 0)
			{
				int j = cur_O_num - 1;
				for (int i = cur_O_letter + 1; i < move_O_letter; i++)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 2 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j--;
				}
			}
			else if (letterDifference < 0 && numDifference < 0)
			{
				int j = cur_O_num + 1;
				for (int i = cur_O_letter + 1; i < move_O_letter; i++)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 3 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j++;
				}
			}
			else if (letterDifference > 0 && numDifference > 0)
			{
				int j = cur_O_num - 1;
				for (int i = cur_O_letter - 1; i > move_O_letter; i--)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 4 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j--;
				}
			}
			else {
				System.out.println("No difference!");
			}
		}

		return true;
	}
	
	public boolean isValidMoveX(String move) 
	{
		int cur_X_letter = getNumber(pos_X.substring(0,1));
		int cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		int move_X_letter = getNumber(move.substring(0,1));
		int move_X_num = Integer.parseInt(move.substring(1)) - 1;
		
		// check invalid input (needs to be A-H and 1-8) or the obvious
		if (getLetter(move_X_num) == "" || getNumber(move.substring(0,1)) == -1) {
			System.out.println("Invalid Input!");
			return false;
		}
		if (cur_X_letter == move_X_letter && cur_X_num == move_X_num) {
			System.out.println("Invalid Move! (self)");
			return false;
		}
		if (state[move_X_letter][move_X_num] != null) {
			System.out.println("Invalid Move! (@ move)");
			return false;
		}
		
		// check if valid move (horizontal, vertical, or diagonal)
		boolean horizontal = false;
		boolean vertical = false;
		boolean diagonal = false;
		
		if (cur_X_num == move_X_num) {
			vertical = true;
			System.out.println("Vertical Move!");
		}
		
		if (cur_X_letter == move_X_letter) {
			horizontal = true;
			System.out.println("Horizontal Move!");
		}
		
		if (Math.abs(cur_X_letter - move_X_letter) == Math.abs(cur_X_num - move_X_num)) {
			diagonal = true;
			System.out.println("Diagonal Move!");
		}
		
		if (!(horizontal || vertical || diagonal)) {
			System.out.println("Invalid Move!");
			return false;
		}
		
		// check if there are obstacles (horizontal, vertical, or diagonal)
		boolean obstacle = false;
		if (horizontal)
		{
			int difference = cur_X_num - move_X_num;
			if (difference < 0)
			{
				for (int i = cur_X_num + 1; i < move_X_num; i++)
				{
					if (state[cur_X_letter][i] != null) {
						obstacle = true;
						System.out.println("H Obstacle at: " + getLetter(cur_X_letter) + (i+1));
						return false;
					}
				}
			}
			else if (difference > 0)
			{
				for (int i = cur_X_num - 1; i> move_X_num; i--)
				{
					if (state[cur_X_letter][i] != null) {
						obstacle = true;
						System.out.println("H Obstacle at: " + getLetter(cur_X_letter) + (i+1));
						return false;
					}
				}
			}
			else {
				System.out.println("No difference!");
			}
		}
		
		if (vertical)
		{
			int difference = cur_X_letter - move_X_letter;
			if (difference < 0)
			{
				for (int i = cur_X_letter + 1; i < move_X_letter; i++)
				{
					if (state[i][cur_X_num] != null) {
						obstacle = true;
						System.out.println("V Obstacle at: " + getLetter(i) + (cur_X_num+1));
						return false;
					}
				}
			}
			else if (difference > 0)
			{
				for (int i = cur_X_letter - 1; i> move_X_letter; i--)
				{
					if (state[i][cur_X_num] != null) {
						obstacle = true;
						System.out.println("V Obstacle at: " + getLetter(i) + (cur_X_num+1));
						return false;
					}
				}
			}
			else {
				System.out.println("No difference!");
			}
		}
		
		if (diagonal)
		{
			int letterDifference = cur_X_letter - move_X_letter;
			int numDifference = cur_X_num - move_X_num;
			if (letterDifference > 0 && numDifference < 0)
			{
				int j = cur_X_num + 1;
				for (int i = cur_X_letter - 1; i > move_X_letter; i--)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 1 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j++;
				}
			}
			else if (letterDifference < 0 && numDifference > 0)
			{
				int j = cur_X_num - 1;
				for (int i = cur_X_letter + 1; i < move_X_letter; i++)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 2 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j--;
				}
			}
			else if (letterDifference < 0 && numDifference < 0)
			{
				int j = cur_X_num + 1;
				for (int i = cur_X_letter + 1; i < move_X_letter; i++)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 3 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j++;
				}
			}
			else if (letterDifference > 0 && numDifference > 0)
			{
				int j = cur_X_num - 1;
				for (int i = cur_X_letter - 1; i > move_X_letter; i--)
				{
					System.out.println("Checking obstacle at: " + getLetter(i) + (j+1));
					if (state[i][j] != null) {
						obstacle = true;
						System.out.println("D condition 4 Obstacle at: " + getLetter(i) + (j+1));
						return false;
					}
					j--;
				}
			}
			else {
				System.out.println("No difference!");
			}
		}
		return true;
	}
		
	// formats the output
	public String toString()
	{
		String outputString = "";
		outputString+="  1 2 3 4 5 6 7 8\n";
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			String letter = getLetter(i);
			outputString+=letter+" ";
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				if (state[i][j] == null)
					outputString+="- ";
				else
					outputString+=state[i][j]+" ";
			}
			outputString+="\n";
		}
		return outputString;
	}
	
	public void consolePrint_X_first(String player, ArrayList<String> programOldMoves, ArrayList<String> opponentOldMoves, int turns) 
	{
		int counter = 0;
		System.out.println("  1 2 3 4 5 6 7 8       Computer vs. Opponent");
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			String letter = getLetter(i);
			System.out.print(letter + " ");
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				if (state[i][j] == null) {
					System.out.print("- ");
				}
				else {
					System.out.print(state[i][j] + " ");
				}
				if (j == BOARD_SIZE - 1) {
					if (counter < turns + 1) {
						if (player.equals("O")) {
							if (counter == turns)
								System.out.print("          " + (counter+1) + ". " + programOldMoves.get(counter));
							else
								System.out.print("          " + (counter+1) + ". " + programOldMoves.get(counter) + "   " + opponentOldMoves.get(counter));
						}
						else if (player.equals("X")) {
							System.out.print("          " + (counter+1) + ". " + programOldMoves.get(counter) + "   " + opponentOldMoves.get(counter));
						}
						counter++;
					}
				}
			}
			System.out.print("\n");
		}
		
		if (turns > BOARD_SIZE-1) {
			while (counter < turns + 1) {
				if (player.equals("O")) {
					if (counter == turns)
						System.out.print("                            " + (counter+1) + ". " + programOldMoves.get(counter) + "\n");
					else
						System.out.print("                            " + (counter+1) + ". " + programOldMoves.get(counter) + "   " + opponentOldMoves.get(counter) + "\n");
				}
				else if (player.equals("X")) {
					System.out.print("          " + (counter+1) + ". " + programOldMoves.get(counter) + "   " + opponentOldMoves.get(counter) + "\n");
				}
				counter++;
			}
		}
		
	}
	
	public void consolePrint_O_first(String player, ArrayList<String> programOldMoves, ArrayList<String> opponentOldMoves, int turns) 
	{
		int counter = 0;
		System.out.println("  1 2 3 4 5 6 7 8       Opponent vs. Computer");
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			String letter = getLetter(i);
			System.out.print(letter + " ");
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				if (state[i][j] == null) {
					System.out.print("- ");
				}
				else {
					System.out.print(state[i][j] + " ");
				}
				if (j == BOARD_SIZE - 1) {
					if (counter < turns) {
						if (player.equals("O")) {
							if (counter == turns)
								System.out.print("          " + (counter+1) + ". " + opponentOldMoves.get(counter));
							else
								System.out.print("          " + (counter+1) + ". " + opponentOldMoves.get(counter) + "   " + programOldMoves.get(counter));
						}
						else if (player.equals("X")) {
							System.out.print("          " + (counter+1) + ". " + opponentOldMoves.get(counter) + "   " + programOldMoves.get(counter));
						}
						counter++;
					}
				}
			}
			System.out.print("\n");
		}
		
		if (turns > BOARD_SIZE - 1) {
			while (counter < turns) {
				if (player.equals("O")) {
					if (counter == turns)
						System.out.print("                            " + (counter+1) + ". " + opponentOldMoves.get(counter) + "\n");
					else
						System.out.print("                            " + (counter+1) + ". " + opponentOldMoves.get(counter) + "   " + programOldMoves.get(counter) + "\n");
				}
				else if (player.equals("X")) {
					System.out.print("          " + (counter+1) + ". " + opponentOldMoves.get(counter) + "   " + programOldMoves.get(counter) + "\n");
				}
				counter++;
			}
		}
		
	}
	
	// helper method for toString
	public String getLetter(int num)
	{
		String letter = "";
		switch (num)
		{
			case 0:
				letter = "A";
				break;
			case 1:
				letter = "B";
				break;
			case 2:
				letter = "C";
				break;
			case 3:
				letter = "D";
				break;
			case 4:
				letter = "E";
				break;
			case 5:
				letter = "F";
				break;
			case 6:
				letter = "G";
				break;
			case 7:
				letter = "H";
				break;
		}
		return letter;
	}
	
	// helper method for updateBoard
	public int getNumber(String letter)
	{
		int num = -1;
		switch (letter)
		{
			case "A":
				num = 0;
				break;
			case "B":
				num = 1;
				break;
			case "C":
				num = 2;
				break;
			case "D":
				num = 3;
				break;
			case "E":
				num = 4;
				break;
			case "F":
				num = 5;
				break;
			case "G":
				num = 6;
				break;
			case "H":
				num = 7;
				break;
		}
		return num;
	}
	
	// using heuristic of num of current valid moves
	public int evaluationFunction()
	{	
		int defensiveScore = 0;
		int aggressiveScore = 0;
		
		// DEFENSIVE SCORE CALCULATION
		
		int cur_X_letter = getNumber(pos_X.substring(0,1));
		int cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		
		// diagonal up-left
		int max_moves = Math.min(cur_X_letter, cur_X_num);
		
		for (int i = 0; i < max_moves; i++) {
			cur_X_letter-=1;
			cur_X_num-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// diagonal down-right
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_X_letter, BOARD_SIZE - cur_X_num);
		for (int i = 0 ; i < max_moves - 1; i++) {
			cur_X_letter+=1;
			cur_X_num+=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// diagonal up-right
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = Math.min(cur_X_letter, BOARD_SIZE - cur_X_num - 1);
		for (int i = 0 ; i < max_moves; i++) {
			cur_X_letter-=1;
			cur_X_num+=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// diagonal down-left
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_X_letter - 1, cur_X_num);
		for (int i = 0 ; i < max_moves; i++) {
			cur_X_letter+=1;
			cur_X_num-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// horizontal right
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1));
		max_moves = BOARD_SIZE - cur_X_num;
		for (int i = 0 ; i < max_moves; i++) {
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
			cur_X_num+=1;
		}
		
		// horizontal left
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		for (int i = cur_X_num; i > 0; i--) {
			cur_X_num-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// vertical up
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		for (int i = cur_X_letter; i > 0; i--) {
			cur_X_letter-=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// vertical down
		cur_X_letter = getNumber(pos_X.substring(0,1));
		cur_X_num = Integer.parseInt(pos_X.substring(1)) - 1;
		max_moves = BOARD_SIZE - cur_X_letter;
		for (int i = 0; i < max_moves - 1; i++) {
			cur_X_letter+=1;
			if (state[cur_X_letter][cur_X_num] != null) {
				break;
			}
			defensiveScore++;
		}
		
		// AGGRESIVE SCORE CALCULATION
		
		int cur_O_letter = getNumber(pos_O.substring(0,1));
		int cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		
		// diagonal up-left
		max_moves = Math.min(cur_O_letter, cur_O_num);
		
		for (int i = 0; i < max_moves; i++) {
			cur_O_letter-=1;
			cur_O_num-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		// diagonal down-right
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_O_letter, BOARD_SIZE - cur_O_num);
		for (int i = 0 ; i < max_moves - 1; i++) {
			cur_O_letter+=1;
			cur_O_num+=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		// diagonal up-right
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = Math.min(cur_O_letter, BOARD_SIZE - cur_O_num - 1);
		for (int i = 0 ; i < max_moves; i++) {
			cur_O_letter-=1;
			cur_O_num+=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		// diagonal down-left
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = Math.min(BOARD_SIZE - cur_O_letter - 1, cur_O_num);
		for (int i = 0 ; i < max_moves; i++) {
			cur_O_letter+=1;
			cur_O_num-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		// horizontal right
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1));
		max_moves = BOARD_SIZE - cur_O_num;
		for (int i = 0 ; i < max_moves; i++) {
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
			cur_O_num+=1;
		}
		
		// horizontal left
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		for (int i = cur_O_num; i > 0; i--) {
			cur_O_num-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		// vertical up
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		for (int i = cur_O_letter; i > 0; i--) {
			cur_O_letter-=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		// vertical down
		cur_O_letter = getNumber(pos_O.substring(0,1));
		cur_O_num = Integer.parseInt(pos_O.substring(1)) - 1;
		max_moves = BOARD_SIZE - cur_O_letter;
		for (int i = 0; i < max_moves - 1; i++) {
			cur_O_letter+=1;
			if (state[cur_O_letter][cur_O_num] != null) {
				break;
			}
			aggressiveScore++;
		}
		
		aggressiveScore = AGGRESSION_CONSTANT - aggressiveScore;
		return defensiveScore + aggressiveScore;
	}
	
	public String getRandomMoveX()
	{
		Random rand = new Random();
		ArrayList<Board> children = generateChildrenX();
		int random = rand.nextInt(children.size());
		return children.get(random).getPosX();
	}
	
			
	// helper method for generate children
	public String[][] deepCopyMatrix() {
	    if (state == null)
	        return null;
	    String[][] result = new String[state.length][];
	    for (int r = 0; r < BOARD_SIZE; r++) {
	        result[r] = state[r].clone();
	    }
	    return result;
	}
	
	// returns current position of O
	public String getPosO()
	{
		return pos_O;
	}
	
	// returns current position of X
	public String getPosX()
	{
		return pos_X;
	}
	
	// returns the current player's turn
	public String getPlayerTurn()
	{
		return playerTurn;
	}
	
	public boolean noMoreMoves(String playerPos) {
		int cur_letter = getNumber(playerPos.substring(0,1));
		int cur_num = Integer.parseInt(playerPos.substring(1)) - 1;
		int[] xMoves = {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] yMoves = {-1, -1, -1, 0, 0, 1, 1, 1};
		boolean noValidMoves = true;
		
		for(int i = 0; i < 8; i++) {
			if(cur_letter + yMoves[i] >= 0 && cur_letter + yMoves[i] <= 7 && cur_num + xMoves[i] >= 0 && cur_num + xMoves[i] <= 7) { // check if move is within A-H and 1-8
				if (state[cur_letter + yMoves[i]][cur_num + xMoves[i]] == null) // checks if one of the 8 directions is null; null in state[][] would mean possible move 
					noValidMoves = false;
			}
		}
		
		return noValidMoves;
	}
	
	// method to check if  a certain location is within A-H and 1-8
	public boolean isValid(String pos) {
		int pos_letter = getNumber(pos.substring(0,1));
		int pos_num = Integer.parseInt(pos.substring(1)) - 1;

		if (pos_letter < 0 || pos_letter > 7) {
			return false;
		}
		
		if (pos_num < 0 || pos_num > 7) {
			return false;
		}
		
		return true;
	}
}
