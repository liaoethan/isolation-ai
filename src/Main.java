import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	// important constant values (can adjust if needed)
	private static final int TIME_LIMIT = 9999;
	private static final int NUM_OF_RAND_TURNS = 3;
	private static final int STARTING_DEPTH = 5;
	
	public static void main(String args[])
	{
		// initialization of the starting board
		Board board = new Board();
		
		// important local variables
		Scanner sc = new Scanner(System.in);
		boolean gameFinished = false;
		int turn = 0;
		ArrayList<String> programOldMoves = new ArrayList<String>(); 
		ArrayList<String> opponentOldMoves = new ArrayList<String>(); 
		
		// prompt user if the opponent or the AI is starting
		System.out.print("\nWho is going first? (X or O): ");
		String firstPlayer = sc.nextLine();
		
		// checks the input in the console
		if (firstPlayer.equals("X"))
		{
			System.out.println("\nStarting board: \n" + board);
			startTime = System.currentTimeMillis();
			
			// assuming the game starts with the program going first
			while (!gameFinished) 
			{
				System.out.print("\nThe program is deciding on a move(WAIT UP TO 10 SECONDS)... \n");

				startTime = System.currentTimeMillis();
				currentTime = 0;
				
				// the program decides on a move
				String inputProgram = null ;
				if (turn < NUM_OF_RAND_TURNS) {
					inputProgram = board.getRandomMoveX();
				}
				else {
					int depth = STARTING_DEPTH;
					boolean cont = true;
					
					// run the algorithm (with iterative deepening)
					while (cont) {
						
						try {
							int minimaxVal = minimax(board, depth, "X", Integer.MAX_VALUE*-1, Integer.MAX_VALUE);
							inputProgram = bestestMove.getPosX();
						}
						catch (RuntimeException ex) {
							cont = false;
						}
						depth++;
					}
				}
				
				// prints out the move of the AI
				if (inputProgram == null)
					inputProgram = board.getRandomMoveX();
				board.moveX(inputProgram);
				programOldMoves.add(inputProgram);
				System.out.println("Computer's Move: " + inputProgram);
				System.out.println();
				board.consolePrint_X_first("O", programOldMoves, opponentOldMoves, turn);
				
				// checks if there is a winner
				if (board.noMoreMoves(board.getPosX())) {
					System.out.println("O WINS!");
					break;
				}
				if (board.noMoreMoves(board.getPosO())) {
					System.out.println("X WINS!");
					break;
				}
				
				// OPPONENT'S TURN
				System.out.print("\nEnter opponent's move: ");
				
				boolean validInput = false;
				
				// input validation
				while (!validInput) 
				{
					String inputOpponent = sc.nextLine();
					if (board.isValidMoveO(inputOpponent)) {
						board.moveO(inputOpponent);			
						opponentOldMoves.add(inputOpponent);
						System.out.println();
						board.consolePrint_X_first("X", programOldMoves, opponentOldMoves, turn);
						validInput = true;
					}
					else {
						System.out.println("Invalid Move! Re-Input!");
						System.out.print("Enter opponent's move: ");
					}
				}
				
				// checks if winner
				if (board.noMoreMoves(board.getPosX())) {
					System.out.println("O WINS!");
					break;
				}
				if (board.noMoreMoves(board.getPosO())) {
					System.out.println("X WINS!");
					break;
				}
				
				turn++;
			}
		}
		else if (firstPlayer.equals("O")) // assumes game starts with the opponent going first
		{
			System.out.println("\nStarting board: \n" + board);
			startTime = System.currentTimeMillis();
			
			// assuming the game starts with the program going first
			while (!gameFinished) 
			{
				System.out.print("\nEnter opponent's move: ");
				
				boolean validInput = false;
				
				// input validation
				while (!validInput) 
				{
					String inputOpponent = sc.nextLine();
					if (board.isValidMoveO(inputOpponent)) {
						board.moveO(inputOpponent);			
						opponentOldMoves.add(inputOpponent);
						System.out.println();
						board.consolePrint_O_first("O", programOldMoves, opponentOldMoves, turn);
						validInput = true;
					}
					else {
						System.out.println("Invalid Move! Re-Input!");
						System.out.print("Enter opponent's move: ");
					}
				}
				
				// checks if winner
				if (board.noMoreMoves(board.getPosX())) {
					System.out.println("O WINS!");
					break;
				}
				if (board.noMoreMoves(board.getPosO())) {
					System.out.println("X WINS!");
					break;
				}
				
				turn++;
				
				System.out.print("\nThe program is deciding on a move (WAIT UP TO 10 SECONDS)... \n");

				startTime = System.currentTimeMillis();
				currentTime = 0;
				
				String inputProgram = null ;
				if (turn < NUM_OF_RAND_TURNS) {
					inputProgram = board.getRandomMoveX();
				}
				else {
					int depth = STARTING_DEPTH;
					boolean cont = true;
					
					while (cont) {
						
						try {
							int minimaxVal = minimax(board, depth, "X", Integer.MAX_VALUE*-1, Integer.MAX_VALUE);
							inputProgram = bestestMove.getPosX();
						}
						catch (RuntimeException ex) {
							cont = false;
						}
						depth++;
					}
				}
				
				if (inputProgram == null)
					inputProgram = board.getRandomMoveX();
				board.moveX(inputProgram);
				programOldMoves.add(inputProgram);
				System.out.println("Computer's Move: " + inputProgram);
				System.out.println();
				board.consolePrint_O_first("O", programOldMoves, opponentOldMoves, turn);
				
				// checks if winner
				if (board.noMoreMoves(board.getPosX())) {
					System.out.println("O WINS!");
					break;
				}
				if (board.noMoreMoves(board.getPosO())) {
					System.out.println("X WINS!");
					break;
				}
			}
		}
	}
	
	// important static variables
	public static Board bestestMove = null;
	public static long currentTime = 0;
	public static long startTime = 0;
	
	public static int minimax(Board node, int depth, String player, int alpha, int beta)
	{
		boolean gameOver = false;
		Board bestMove = null;
		
		if (player == "X")
			gameOver = node.noMoreMoves(node.getPosX());
		else if (player == "O")
			gameOver = node.noMoreMoves(node.getPosO());	
		
		if (currentTime > TIME_LIMIT) {
			throw new RuntimeException();
		}
		
		if (depth == 0 || gameOver) {
			long endTime = System.currentTimeMillis();
			currentTime = endTime - startTime;
			return node.evaluationFunction();
		}
		
		if (player == "X") {
			int maxEval = Integer.MAX_VALUE * -1;
			ArrayList<Board> children = node.generateChildrenX();
			for (int i = 0; i < children.size(); i++) {
				int eval = minimax(children.get(i), depth-1, "O", alpha, beta);
				if (eval > maxEval) {
					bestMove = children.get(i);
					maxEval = Math.max(maxEval, eval);
				}
				alpha = Math.max(alpha, eval);
				if (beta <= alpha)
					break;
			}
			bestestMove = bestMove;
			long endTime = System.currentTimeMillis(); 
			currentTime = endTime - startTime;
			return maxEval;
		}
		else // player == "O"
		{
			int minEval = Integer.MAX_VALUE;
			ArrayList<Board> children = node.generateChildrenO();
			for (int i = 0; i < children.size(); i++) {
				int eval = minimax(children.get(i), depth-1, "X", alpha, beta);
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta, eval);
				if (beta <= alpha)
					break;
			}
			long endTime = System.currentTimeMillis();
			currentTime = endTime - startTime;
			return minEval;
		}
	}
}
