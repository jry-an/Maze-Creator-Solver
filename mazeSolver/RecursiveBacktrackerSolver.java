package mazeSolver;

import maze.Cell;
import maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements the recursive backtracking maze solving algorithm.
 */
public class RecursiveBacktrackerSolver implements MazeSolver {

	private static int NORTH = Maze.NORTH;
	private static  int SOUTH = Maze.SOUTH;
	private static int EAST = Maze.EAST;
	private static int WEST = Maze.WEST;

	private int cellsExplored;
	private boolean[][] visited;
	private boolean solved;

	@Override
	public void solveMaze(Maze maze) {
		cellsExplored = 0;
		solved = false;
		visited = new boolean[maze.sizeR][maze.sizeC];
		List<Cell> path = new ArrayList<>();

		explore(maze,maze.entrance.r,maze.entrance.c,path);

	} // end of solveMaze()

	@Override
	public boolean isSolved() {
		return solved;
	} // end if isSolved()

	@Override
	public int cellsExplored() {
		return cellsExplored;
	} // end of cellsExplored()

	private void explore(Maze maze, int r, int c, List<Cell> path) {
		if (!isSolved()) {
			Random rand = new Random();
			path.add(path.size(), new Cell(r, c));
			maze.drawFtPrt(new Cell(r, c));
			//add to cells explored
			cellsExplored++;
			//mark current cell as visited
			visited[r][c] = true;
			//print visited cell
			System.out.println(r + " " + c);
			//check if current cell is the exit cell
			if (visited[maze.exit.r][maze.exit.c]){
				solved = true;
			}

			//get random coordinate from the surround cell (cell must be visitable)
			while (getNextValidCell(maze, r, c).size() > 0 && !isSolved() || maze.map[r][c].tunnelTo != null && !visited[maze.map[r][c].tunnelTo.r][maze.map[r][c].tunnelTo.c]) {
				//do if current cell is not exit
				if (maze.map[r][c] != maze.map[maze.exit.r][maze.exit.c]) {
					if (maze.map[r][c].tunnelTo != null && !visited[maze.map[r][c].tunnelTo.r][maze.map[r][c].tunnelTo.c]) {
						explore(maze, maze.map[r][c].tunnelTo.r, maze.map[r][c].tunnelTo.c, path);
					}
					//do if there is a valid location
					if (getNextValidCell(maze, r, c).size() > 0) {
						//if is tunnel && tunnel dest is not visited - explore to tunnel dest
						//if is not tunnel
						if (maze.map[r][c].tunnelTo == null || (maze.map[r][c].tunnelTo != null && visited[maze.map[r][c].tunnelTo.r][maze.map[r][c].tunnelTo.c])) {
							int unvisited = getNextValidCell(maze, r, c).get(rand.nextInt(getNextValidCell(maze, r, c).size()));
							explore(maze, maze.map[r][c].neigh[unvisited].r, maze.map[r][c].neigh[unvisited].c, path);

						}
						//if a tunnel and tunnel dest isn't visited
							//go to tunnel
						//else

//						if(maze.map[r][c].tunnelTo == null || visited[maze.map[r][c].tunnelTo.r][maze.map[r][c].tunnelTo.c]) {
//							int unvisited = getNextValidCell(maze, r, c).get(rand.nextInt(getNextValidCell(maze, r, c).size()));
//							explore(maze, maze.map[r][c].neigh[unvisited].r, maze.map[r][c].neigh[unvisited].c, path);
//
//						} else if (maze.map[r][c].tunnelTo != null && !visited[maze.map[r][c].tunnelTo.r][maze.map[r][c].tunnelTo.c]) {
//							explore(maze, maze.map[r][c].tunnelTo.r, maze.map[r][c].tunnelTo.c, path);
//						}
					}
				}
			}
		}
	}

	//returns a list of possible moves
	private List<Integer> getNextValidCell(Maze maze, int r , int c){
		List<Integer> possibleCells = new ArrayList<>();
		if (!maze.map[r][c].wall[NORTH].present && !visited[maze.map[r][c].neigh[NORTH].r][maze.map[r][c].neigh[NORTH].c]){
			possibleCells.add(NORTH);
		}
		if (!maze.map[r][c].wall[SOUTH].present && !visited[maze.map[r][c].neigh[SOUTH].r][maze.map[r][c].neigh[SOUTH].c]){
			possibleCells.add(SOUTH);
		}
		if (!maze.map[r][c].wall[EAST].present && !visited[maze.map[r][c].neigh[EAST].r][maze.map[r][c].neigh[EAST].c]){
			possibleCells.add(EAST);
		}
		if (!maze.map[r][c].wall[WEST].present && !visited[maze.map[r][c].neigh[WEST].r][maze.map[r][c].neigh[WEST].c]){
			possibleCells.add(WEST);
		}
		return possibleCells;
	}

} // end of class RecursiveBackTrackerSolver