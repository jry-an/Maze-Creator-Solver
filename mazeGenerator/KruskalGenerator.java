package mazeGenerator;

import maze.Maze;
import maze.Cell;

import java.util.ArrayList;

public class KruskalGenerator implements MazeGenerator {

	private int NORTH = Maze.NORTH;
	private int SOUTH = Maze.SOUTH;
	private int EAST = Maze.EAST;
	private int WEST = Maze.WEST;

	@Override
	public void generateMaze(Maze maze) {
		initializeCellId(maze);

		// TODO Auto-generated method stub

	} // end of generateMaze()

	private void initializeCellId(Maze maze){
		int r = 0;
		int c = 0;
		int id = 0;
		while(r < maze.sizeR && c < maze.sizeC) {
			for (r = 0; r < maze.sizeR; r++) {
				maze.map[r][c].id = id;
			}
			c++;
			id++;
		}
	}

} // end of class KruskalGenerator
