import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {
	private SearchNode solved;
	private int minNumberOfMoves = -2; // -2 = default value, -1 = unsolvable, 0+ = solvable

	public Solver(Board initial) {
		if (initial == null) throw new IllegalArgumentException("can't pass null as an argument!");
		
		PriorityQueue<SearchNode> q, pq1 = new PriorityQueue<SearchNode>(), pq2 = new PriorityQueue<SearchNode>();
		SearchNode minNode = new SearchNode(initial, 0, null);
		pq1.add(minNode);
		
		Board board, swapBoard = initial.twin();
		SearchNode swappedMinNode = new SearchNode(swapBoard, 0, null);
		pq2.add(swappedMinNode);
		boolean flip = false;
		Stack<PriorityQueue<SearchNode>> s = new Stack<PriorityQueue<SearchNode>>();
		
		while(!pq1.isEmpty()) {
			if (flip) {
				s.push(pq1);
				s.push(pq2);
			} else {
				s.push(pq2);
				s.push(pq1);
			}
			
			for(int i = 0; i < 2; i++) {
				q = s.pop();
				minNode = q.remove();
				board = minNode.getBoard();

				if (board.isGoal()) {
					if (q == pq1) {
						minNumberOfMoves = minNode.movesNeeded;
						solved = minNode;
					} else { // nodes[i] = the swapped (fake) board
						minNumberOfMoves = -1;
					}
					break;
				}
				for (SearchNode n : minNode.neighbors()) {
					q.add(n);
				}
			}
			if (minNumberOfMoves != -2) break;
			flip = !flip;
		}
		
	}
	
	private class SearchNode implements Comparable<SearchNode>{
		private Board current;
		private int movesNeeded; // g(n)
		private SearchNode previous;
		private int score; // f(n), the heuristic
		
		public SearchNode(Board t, int n, SearchNode p) {
			current = t;
			movesNeeded = n;
			previous = p;
			score = t.manhattan();
		}
		
		public Iterable<SearchNode> neighbors() {
			Stack<SearchNode> s = new Stack<SearchNode>();
			Iterable<Board> myNeighbors = current.neighbors();
			for(Board n : myNeighbors) {
				if (previous == null || (previous != null && !n.equals(previous.getBoard()))) 
					s.push(new SearchNode(n, movesNeeded+1, this));
			}

			return s;
		}
		
		public Board getBoard() {
			return current;
		}
		
		/*
		 * A*
		 * 
		 * returns an integer that represents the priority using Manhattan distance (i.e. f(n), the heuristic)
		 * and the current number of moves needed (i.e. g(n))
		 */
		public int priority() {
			return movesNeeded + score;
		}

		public int compareTo(SearchNode that) {
			if (priority() < that.priority()) return -1;
			else if (priority() == that.priority()) return 0;
			else return 1;
    	}
	}
	
	public boolean isSolvable() {
		return moves() > -1;
	}
	
	public int moves() {
		return minNumberOfMoves;
	}
	
	public Iterable<Board> solution() {
		if (solved == null) return null;
		SearchNode head = solved;
		
		Deque<Board> s = new ArrayDeque<Board>();
		while(solved != null) {
			s.push(solved.getBoard());
			solved = solved.previous;
		}
		solved = head; // revert the location of solved
		return s;
	}
}
