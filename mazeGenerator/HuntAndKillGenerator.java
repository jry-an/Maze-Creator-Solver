package mazeGenerator;

import maze.Maze;
import maze.Cell;
import java.util.*;

//import maze.Cell;

import java.util.List;

public class HuntAndKillGenerator implements MazeGenerator {


//	1. Choose a starting location.
//	2. Perform a random walk, carving passages to unvisited neighbors, until the current cell has no unvisited neighbors.
//	3.Enter “hunt” mode, where you scan the grid looking for an unvisited cell that is adjacent to a visited cell. If found, carve a passage between the two and let the formerly unvisited cell be the new starting location.
//	4. Repeat steps 2 and 3 until the hunt mode scans the entire grid and finds no unvisited cells.


//	private List<Cell> visited;
private Cell[][] visited;

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub

		Random random = new Random();
		int startingR = random.nextInt(maze.sizeR);
		int startingC = random.nextInt(maze.sizeC);

		//loop
			walk(maze,startingR,startingC);
			hunt(maze);
			//end loop

	} // end of generateMaze()

	//i created this
	private void walk(Maze maze, int r,int c){

	}
	//i created this
	private void hunt(Maze maze) {
	}



} // end of class HuntAndKillGenerator
