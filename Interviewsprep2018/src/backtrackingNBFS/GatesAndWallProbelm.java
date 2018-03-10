package backtrackingNBFS;

public class GatesAndWallProbelm {
/* matrix that is filled with ‘O’, ‘G’, and 
 * ‘W’ where ‘O’ represents open space, ‘G’ represents guards and ‘W’ represents walls in a Bank.
 * Replace all of the O’s in the matrix with their shortest distance from a guard, without being able to go 
 * through any walls. Also, replace the guards with 0 and walls with -1 in output matrix.
 * Expected Time complexity is O(MN) for a M x N matrix.
 * 
	 O ==> Open Space
	 G ==> Guard
	 W ==> Wall
	 
	 Input: 
  O  O  O  O  G
  O  W  W  O  O
  O  O  O  W  O
  G  W  W  W  O
  O  O  O  O  G

Output:  
  3  3  2  1  0
  2 -1 -1  2  1
  1  2  3 -1  2
  0 -1 -1 -1  1
  1  2  2  1  0
  
  The idea is to do BFS. We first enqueue all cells containing the guards and loop 
  till queue is not empty. For each iteration of the loop, we dequeue the front cell 
  from the queue and for each of its four adjacent cells, if cell is an open area and 
  its distance from guard is not calculated yet, we update its distance and enqueue it. 
  Finally after BFS procedure is over, we print the distance matrix.
  
  
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
