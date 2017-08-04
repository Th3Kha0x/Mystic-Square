import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private final int[][] blocks;
	private final int N;
	private int blankRow = -1;
	private int blankCol = -1;

	public Board(int[][] blocks) {
		int row = blocks.length;
		int col = blocks[0].length;
		
		N = row;
		this.blocks = new int[row][col];
		
		// Copy the initial board values
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if (blocks[i][j] == 0) {
					blankRow = i;
					blankCol = j;
				}
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}
	public int dimension() { return N; }
	public int hamming() {
		int score = 0;
		for (int i = 0, counter = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == N-1 && j == N-1) continue;
				if (blocks[i][j] != ++counter) score++;
			}
		}
		return score;
	}
	public int manhattan() {
		int blockVal = -1, goalBlockRow = -1, goalBlockCol = -1;
		int score = 0;
		int test[] = new int[N*N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blockVal = blocks[i][j];
				if (blockVal == 0) continue; 
				goalBlockRow = (blockVal-1) / N;
				goalBlockCol = (blockVal-1) % N;
				test[(goalBlockRow*N)+goalBlockCol+1] = Math.abs(goalBlockRow - i) + Math.abs(goalBlockCol - j);
				score += Math.abs(goalBlockRow - i) + Math.abs(goalBlockCol - j);
			}
		}
		
		return score;
	}
	public boolean isGoal() {
		for (int i = 0, counter = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == N-1 && j == N-1) continue;
				if (blocks[i][j] != ++counter) return false;
			}
		}
		return true;
	}
	public Board twin() {
		int rand1 = -1, rand2 = -1;
		int blankBlockNum = (blankRow*N) + blankCol;
		while(rand1 == rand2 || rand1 == blankBlockNum || rand2 == blankBlockNum) {
			rand1 = StdRandom.uniform(N*N);
			rand2 = StdRandom.uniform(N*N);
		}
		int twin[][] = deepCopy();
		int rand1Row = rand1 / N, rand1Col = rand1 % N;
		int rand2Row = rand2 / N, rand2Col = rand2 % N;

		int swap = twin[rand1Row][rand1Col];
		twin[rand1Row][rand1Col] = twin[rand2Row][rand2Col];
		twin[rand2Row][rand2Col] = swap;
		return new Board(twin);
	}
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		
		Board that = (Board) y;
		if (this.N != that.N) return false;

		int[][] theseBlocks = this.getBlocks();
		int[][] thoseBlocks = that.getBlocks();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (theseBlocks[i][j] != thoseBlocks[i][j]) return false;
			}
		}
		return true;
	}
	
	private int[][] getBlocks(){
		return blocks;
	}
	
	/*
	 * In Java, primitive types (int, boolean, ...) are actually assigned to memory addresses as values,
	 * however, assigning arrays sets the pointer value to the array. If b is an array of ints, int[] a = b
	 * means that a is only a pointer reference to b, and a is not a standalone separate array entity.
	 * Therefore, a new array must be instantiated, and each primitive value has to be copied.
	 */
	private int[][] deepCopy() {
		int[][] copiedBlocks = new int[N][N];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				copiedBlocks[row][col] = blocks[row][col];
			}
		}
		return copiedBlocks;
	}
	
	public Iterable<Board> neighbors() {
		Stack<Board> neighbors = new Stack<Board>();
		
		if(blankRow+1 < N) {
			int neighborBlocks[][] = deepCopy();
			neighborBlocks[blankRow][blankCol] = neighborBlocks[blankRow+1][blankCol];
			neighborBlocks[blankRow+1][blankCol] = 0; // 0 = blank
			neighbors.push(new Board(neighborBlocks));
		}
		if(blankRow-1 >= 0) {
			int neighborBlocks[][] = deepCopy();
			neighborBlocks[blankRow][blankCol] = neighborBlocks[blankRow-1][blankCol];
			neighborBlocks[blankRow-1][blankCol] = 0; // 0 = blank
			neighbors.push(new Board(neighborBlocks));
		}
		if(blankCol+1 < N) {
			int neighborBlocks[][] = deepCopy();
			neighborBlocks[blankRow][blankCol] = neighborBlocks[blankRow][blankCol+1];
			neighborBlocks[blankRow][blankCol+1] = 0; // 0 = blank
			neighbors.push(new Board(neighborBlocks));
		}
		if(blankCol-1 >= 0) {
			int neighborBlocks[][] = deepCopy();
			neighborBlocks[blankRow][blankCol] = neighborBlocks[blankRow][blankCol-1];
			neighborBlocks[blankRow][blankCol-1] = 0; // 0 = blank
			neighbors.push(new Board(neighborBlocks));
		}
		
		return neighbors;
	}
	public String toString() {
		String out = N + "\n";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				out = out + Integer.toString(blocks[i][j]) + " ";
			}
			out += "\n";
		}
		return out;
	}
	public static void main(String[] args) {
		int[][] testboard = new int[3][3];
		int counter = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				testboard[i][j] = ++counter;
			}
		}
		testboard[2][2] = 0;
		
		// custom mods
//		testboard[0][0] = 8;
//		testboard[0][1] = 1;
//		testboard[0][2] = 3;
//		testboard[1][0] = 4;
//		testboard[1][1] = 0;
//		testboard[1][2] = 2;
//		testboard[2][0] = 7;
//		testboard[2][1] = 6;
//		testboard[2][2] = 5;

		
		Board b = new Board(testboard);
		StdOut.println(b);
		StdOut.println(b.hamming());
		StdOut.println(b.manhattan());
		
//		Board c = b.twin();
//		StdOut.println(c);
//		StdOut.println(c.hamming());
//		StdOut.println(c.manhattan());

	}
			
}
