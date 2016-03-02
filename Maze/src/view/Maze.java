package view;

/**
 *MohamedDahir
 *Assignment 5
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
	private int myWidth;
	private int myDepth;
	private boolean debug;
	private char[][] mazeArray;
	private boolean[][] visited;

	public Maze(int myWidth, int myDepth, boolean theDebug) {
		if(theDebug == false){
			myWidth = 10;
			myDepth = 10;
		}
		this.myWidth = myWidth;
		this.myDepth = myDepth;
		this.debug = theDebug;
		mazeArray = new char[2 * myWidth + 1][2 * myDepth + 1];
		visited = new boolean[myWidth][myDepth];
		initializemazeArray();
		generateMaze();
		markCorrectPath();
	}
/**
 * This method  the the 2-D array 
 */
	private void initializemazeArray() {
		for (int i = 0; i < mazeArray.length; i++) {
			for (int j = 0; j < mazeArray[0].length; j++) {
				if (i % 2 == 0 || j % 2 == 0) {
					mazeArray[i][j] = 'X';
				} else {
					mazeArray[i][j] = ' ';
				}
			}
		}
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}
	/**
	 * This method generates the maze.
	 */
	private void generateMaze() {
		Stack<CellNode> stack = new Stack<CellNode>();
		int curX = 0, curY = 0;
		visited[curX][curY] = true;
		ArrayList<CellNode> neighbors;
		Random rand = new Random();
		while (!allVisited()) {
			if (debug) {
				display();

			}
			neighbors = getUnvisitedNeighbors(curX, curY);
			if (!neighbors.isEmpty()) {

				// randomly select an adjacent unvisited cell
				;
				CellNode loc = neighbors
						.get(rand.nextInt(neighbors.size()));

				// push location of current cell to stack
				stack.push(new CellNode(curX, curY));

				// remove wall between current cell and new cell
				if (loc.getX() == curX) {
					if (loc.getY() > curY) {
						mazeArray[2 * curX + 1][2 * loc.getY()] = ' ';
					} else {
						mazeArray[2 * curX + 1][2 * curY] = ' ';
					}
				} else {
					if (loc.getX() > curX) {
						mazeArray[2 * loc.getX()][2 * curY + 1] = ' ';
					} else {
						mazeArray[2 * curX][2 * curY + 1] = ' ';
					}
				}

				// make chosen cell the current cell and mark it as visited
				curX = loc.getX();
				curY = loc.getY();
				visited[curX][curY] = true;
			} else if (!stack.isEmpty()) {
				CellNode loc = stack.pop();
				curX = loc.getX();
				curY = loc.getY();
			} else {
				// get unvisited cells
				ArrayList<CellNode> unvisited = getAllUnvisited();

				// pick a random unvisited cell
				CellNode loc = unvisited
						.get(rand.nextInt(unvisited.size()));

				// make chosen cell and mark it as visited
				curX = loc.getX();
				curY = loc.getY();
				visited[curX][curY] = true;
			}
		}

		// add entrance and exit
		mazeArray[0][1] = ' ';
		mazeArray[mazeArray.length - 2][mazeArray[0].length - 1] = ' ';
	}
	/**
	 * This method marks the spots the have been visited 
	 * with a plus sign to make sure i don't visit it again
	 */
	public void markCorrectPath() {
		resetVisited();
		Stack<CellNode> stack = new Stack<CellNode>();
		ArrayList<CellNode> neighbors = new ArrayList<CellNode>();
		int curX = 0, curY = 0;
		
		visited[curX][curY] = true;
		while (!(curX == myWidth - 1 && curY == myDepth - 1)) {
			neighbors = getUnvisitedNeighborsWithPath(curX, curY);
			if (!neighbors.isEmpty()) {
				stack.push(new CellNode(curX, curY));
				CellNode loc = neighbors.get(0);
				stack.push(loc);
				curX = loc.getX();
				curY = loc.getY();
				visited[curX][curY] = true;
			} else {
				CellNode loc = stack.pop();
				curX = loc.getX();
				curY = loc.getY();
			}
		}

		while (!stack.isEmpty()) {
			CellNode loc = stack.pop();
			mazeArray[2 * loc.getX() + 1][2 * loc.getY() + 1] = '+';
			
		}
	}
	/**
	 * This method  check if every thing has been visited or not
	 */
	private boolean allVisited() {
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				if (!visited[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method  gets all the unvisited neightboring cells 
	 * and adds it to an arrayList. 
	 */
	private ArrayList<CellNode> getUnvisitedNeighbors(int x, int y) {
		ArrayList<CellNode> list = new ArrayList<CellNode>();
		if (x - 1 >= 0) {
			if (!visited[x - 1][y]) {
				list.add(new CellNode(x - 1, y));
			}
		}
		if (y - 1 >= 0) {
			if (!visited[x][y - 1]) {
				list.add(new CellNode(x, y - 1));
			}
		}
		if (x + 1 < visited.length) {
			if (!visited[x + 1][y]) {
				list.add(new CellNode(x + 1, y));
			}
		}
		if (y + 1 < visited[0].length) {
			if (!visited[x][y + 1]) {
				list.add(new CellNode(x, y + 1));
			}
		}
		return list;
	}

	private ArrayList<CellNode> getUnvisitedNeighborsWithPath(int x, int y) {
		ArrayList<CellNode> list = new ArrayList<CellNode>();
		if (x - 1 >= 0) {
			if (!visited[x - 1][y]) {
				if (mazeArray[2 * x][2 * y + 1] != 'X') {
					list.add(new CellNode(x - 1, y));
				}
			}
		}
		if (y - 1 >= 0) {
			if (!visited[x][y - 1]) {
				if (mazeArray[2 * x + 1][2 * y] != 'X') {
					list.add(new CellNode(x, y - 1));
				}
			}
		}
		if (x + 1 < visited.length) {
			if (!visited[x + 1][y]) {
				if (mazeArray[2 * x + 2][2 * y + 1] != 'X') {
					list.add(new CellNode(x + 1, y));
				}
			}
		}
		if (y + 1 < visited[0].length) {
			if (!visited[x][y + 1]) {
				if (mazeArray[2 * x+1][2 * (y+1)] != 'X') {
					list.add(new CellNode(x, y + 1));
				}
			}
		}
		return list;
	}

	/**
	 * This method gets the visited cells and adds it to an array list. 
	 */
	private ArrayList<CellNode> getAllUnvisited() {
		ArrayList<CellNode> list = new ArrayList<CellNode>();
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				if (!visited[i][j]) {
					list.add(new CellNode(i, j));
				}
			}
		}
		return list;
	}

	private void resetVisited() {
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}

	/**
	 * This method prints out the 2-D array to the console whe called.
	 */
	public void display() {
		for (int i = 0; i < mazeArray.length; i++) {
			for (int j = 0; j < mazeArray[0].length; j++) {
				System.out.print(mazeArray[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

}