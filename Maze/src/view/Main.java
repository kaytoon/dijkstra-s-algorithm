package view;
import javax.swing.JFrame;



public class Main {
	 
    private static MazeGraphics myPanel;
    static JFrame myFrame;
	public static void main(String[] args) {
		
		Maze mazeText = new Maze(5, 5, true);
		
		//MazeText mazeText = new MazeText(5, 5, false);
		
		/**
		 * This method will print the maze as a text file to console
		 */
		mazeText.display();
		
		/**
		 * this is a frame for the graphics maze.
		 */
		myFrame = new JFrame("MAZE");
		
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		myFrame.setSize(500, 500);
		myFrame.setLocationRelativeTo(null);
	
		myPanel = new MazeGraphics(10, 10, true);
		
		myFrame.add(myPanel);
		myPanel.generateMaze();
		myPanel.markCorrectPath();
	
	}
	
}




