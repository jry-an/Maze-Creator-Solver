package mazeGenerator;

import maze.Maze;
import java.util.*;

//import maze.Cell;

import java.util.List;

public class HuntAndKillGenerator implements MazeGenerator {


//	1. Choose a starting location.
//	2. Perform a random walk, carving passages to unvisited neighbors, until the current cell has no unvisited neighbors.
//	3.Enter “hunt” mode, where you scan the grid looking for an unvisited cell that is adjacent to a visited cell. If found, carve a passage between the two and let the formerly unvisited cell be the new starting location.
//	4. Repeat steps 2 and 3 until the hunt mode scans the entire grid and finds no unvisited cells.

//	private List<Cell> visited;
private int NORTH = Maze.NORTH;
private int SOUTH = Maze.SOUTH;
private int EAST = Maze.EAST;
private int WEST = Maze.WEST;

	private boolean[][] visited;


	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		visited = new boolean[maze.sizeR][maze.sizeC];


		Random random = new Random();

		//loop
		int row = random.nextInt(4);
		int col = random.nextInt(4);
			walk(maze,row,col);
			hunt(maze);
			//end loop

	} // end of generateMaze()

	//i created this
	private void walk(Maze maze, int r,int c){

		boolean noAvaiableCoord = false;
		int nextR = 0;
		int nextC = 0;

		if (r >= 0 && r < maze.sizeR && c >=0 && c < maze.sizeC ) {

			visited[r][c] = true;

			List<Integer> direction = Arrays.asList(NORTH, SOUTH, EAST, WEST);
			Collections.shuffle(direction);
			int walkDirection = direction.get(0);


			if (walkDirection == NORTH && r > 0 && !visited[r - 1][c]) {
				nextR = r - 1;
				nextC = c;
				maze.map[r - 1][c].wall[NORTH].present = false;

			} else if (walkDirection == SOUTH && r < maze.sizeR) {
				//SOUTH
			} else if (walkDirection == EAST && c > 0) {
				//EAST
			} else if(walkDirection == WEST && c < maze.sizeR) {
				//WEST
			} else {
				noAvaiableCoord = true;
			}

			if (noAvaiableCoord = false) {
				walk(maze,nextR,nextC);
			}

		}
}

	//i created this
	private void hunt(Maze maze) {
	}



} // end of class HuntAndKillGenerator
