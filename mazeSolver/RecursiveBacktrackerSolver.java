package mazeSolver;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the recursive backtracking maze solving algorithm.
 */
public class RecursiveBacktrackerSolver implements MazeSolver {

	private static int NORTH = Maze.NORTH;
	private static  int SOUTH = Maze.SOUTH;
	private static int EAST = Maze.EAST;
	private static int WEST = Maze.WEST;
	private List<Cell> path;

	private int cellsExplored;

	@Override
	public void solveMaze(Maze maze) {
		cellsExplored = 0;
		path = new ArrayList<>();

		explore(maze,maze.entrance.r,maze.entrance.c,path);

	} // end of solveMaze()

	@Override
	public boolean isSolved() {
		return false;
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		return cellsExplored;
	} // end of cellsExplored()

	private void explore(Maze maze, int r, int c, List<Cell> path){
		Cell unvisited;

	}

	private List<Cell> getNextValidCell(Maze maze, int r , int c){
		List<Cell> possibleCells = new ArrayList<>();
		if (!maze.map[r][c].wall[NORTH].present){
			possibleCells.add(maze.map[r][c].neigh[NORTH]);
		}
		if (!maze.map[r][c].wall[SOUTH].present){
			possibleCells.add(maze.map[r][c].neigh[SOUTH]);
		}
		if (!maze.map[r][c].wall[EAST].present){
			possibleCells.add(maze.map[r][c].neigh[EAST]);
		}
		if (!maze.map[r][c].wall[WEST].present){
			possibleCells.add(maze.map[r][c].neigh[WEST]);
		}
		return possibleCells;

	}

//	private void checkPath(Maze maze){
//		if(path.contains(new Cell(maze.entrance.r, maze.entrance.c)) && path.contains(new Cell(maze.exit.r, maze.exit.c))){
//			isSolved = true;
//		}
//	}

} // end of class RecursiveBackTrackerSolver
