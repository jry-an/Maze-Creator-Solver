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

	private void explore(Maze maze, int r, int c, List<Cell> path){
		Random rand = new Random();
		path.add(path.size(),new Cell(r,c));
		maze.drawFtPrt(new Cell(r,c));
		cellsExplored++;
		visited[r][c] = true;

		if (maze.map[r][c] != maze.map[maze.exit.r][maze.exit.c]) {
			Cell unvisited = getNextValidCell(maze, r, c).get(rand.nextInt(getNextValidCell(maze, r, c).size()));
			if (getNextValidCell(maze,r,c).size() == 0){
				explore(maze,path.get(path.size()-1).r, path.get(path.size()-1).c,path);
			} else{
				explore(maze,unvisited.r,unvisited.c,path);
			}
		} else {
			solved = true;
		}


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

} // end of class RecursiveBackTrackerSolver
