package arraysexamples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PracticeFinalBeforG {
	public static void main(String[] args) {
		wallsNGateExample();
	}
	
	static void wallsNGateExample(){
		int[][] rooms = {
							{Integer.MAX_VALUE, -1, 0, Integer.MAX_VALUE},
							{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1},
							{Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1},
							{0, -1, Integer.MAX_VALUE, Integer.MAX_VALUE}
						};
		
		
		bfsWalk(rooms);
		System.out.println("Rooms with distances ");
		for(int[] room: rooms) {
			System.out.println(Arrays.toString(room));
		}
		
	}
	
	static void bfsWalk(int[][] rooms) {
		Queue<Node> q = new LinkedList<>();
		
		for(int i=0; i<rooms.length; i++) {
			for(int j=0; j<rooms[0].length; j++) {
				if(rooms[i][j]==0) {
					q.offer(new Node(i,j,0));
				}
			}
		}
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			int r = n.r;
			int c = n.c;
			int curDist = n.dist;
			if(!isValidPos(r, c, rooms) || rooms[r][c] < curDist) {
				continue;
			}
			
			rooms[r][c] = curDist;
			
			for(int d=0; d<DIRECTIONS.length; d++) {
				q.offer(new Node(r+DIRECTIONS[d][0], c+DIRECTIONS[d][1], curDist+1));
			}
		}
	}
	
	static final int[][] DIRECTIONS = {{0,-1},{-1,0},{0,1},{1,0}};
	
	static boolean isValidPos(int i, int j, int[][] grid) {
		if(i<0 || i>= grid.length || j<0 || j>= grid[0].length) {
			return false;
		}
		return true;
	}
	
	static class Node{
		int r;
		int c;
		int dist;
		Node(int r, int c, int dist){
			this.r = r;
			this.c = c;
			this.dist = dist;
		}
	}
}
