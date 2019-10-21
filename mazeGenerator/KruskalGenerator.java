package mazeGenerator;

import maze.Maze;
import maze.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KruskalGenerator implements MazeGenerator {

	private static int NORTH = Maze.NORTH;
	private static  int SOUTH = Maze.SOUTH;
	private static int EAST = Maze.EAST;
	private static int WEST = Maze.WEST;
	private ArrayList<Edge> edges;
	private ArrayList<Cell> trees;

	@Override
	public void generateMaze(Maze maze) {
		Random rand = new Random();
		edges = new ArrayList<>();
		trees = new ArrayList<>();

		initializeCellId(maze);
		addEdgesToMap(maze);

		//repeat until all cells the same root id
		while(!allCellsSameId(maze)){
		/*
		1. pick random wall
		2. if the cells next to the wall != same rootCell
			- break down wall
			- change all cells with that id, in that tree to the same as original id
		3. repeat 1 and 2
		 */

			//get random cell
			if (edges.size() > 0) {
				Edge randEdge = edges.get(rand.nextInt(edges.size()));

				if (randEdge.getCurrentCell().rootCell != randEdge.getAdjacentCell().rootCell){
					int direction = getDirectionOfAdjacent(randEdge.getCurrentCell(),randEdge.getAdjacentCell());

					//remove wall
					randEdge.getCurrentCell().wall[direction].present = false;
					randEdge.getAdjacentCell().wall[Maze.oppoDir[direction]].present = false;

					changeTreeRootId(maze,randEdge.getCurrentCell(), randEdge.getAdjacentCell());

					edges.remove(randEdge);
				}
			}


			int randomWall = -1;
			ArrayList<Integer> listofAvailableWalls = checkSurroundingWalls(maze,randR,randC);
			if (!listofAvailableWalls.isEmpty()){
				 randomWall = listofAvailableWalls.get(rand.nextInt(listofAvailableWalls.size()));
			}

		}//end loop

	} // end of generateMaze()

	private void changeTreeRootId(Maze maze, Cell current, Cell adjacent){
		for (int i = 0; i < trees.size(); i++) {
			if (trees.get(i).rootCell == adjacent.rootCell){
				trees.get(i).rootCell = current.rootCell;
				maze.map[trees.get(i).r][trees.get(i).c].rootCell = current.rootCell;
			}
		}

	}

	private Integer getDirectionOfAdjacent(Cell current, Cell adjacent){
		if (current.neigh[NORTH] == adjacent){
			return NORTH;
		}
		if (current.neigh[SOUTH] == adjacent){
			return SOUTH;
		}
		if (current.neigh[EAST] == adjacent){
			return EAST;
		}
		if (current.neigh[WEST] == adjacent){
			return WEST;
		}
		return -1;
	}

	private ArrayList<Integer> checkSurroundingWalls(Maze maze, int r, int c) {
		ArrayList<Integer> availableDirections = new ArrayList<>();
		if (r < maze.sizeR -1){
		if (maze.map[r][c].neigh[NORTH].rootCell != maze.map[r][c].rootCell) {
			availableDirections.add(NORTH);
			}
		}
		if (r >0) {
			if (maze.map[r][c].neigh[SOUTH].rootCell != maze.map[r][c].rootCell) {
				availableDirections.add(SOUTH);
			}
		}
		if (c < maze.sizeC -1) {
			if (maze.map[r][c].neigh[EAST].rootCell != maze.map[r][c].rootCell) {
				availableDirections.add(EAST);
			}
		}
		if(c > 0) {
			if (maze.map[r][c].neigh[WEST].rootCell != maze.map[r][c].rootCell) {
				availableDirections.add(WEST);
			}
		}

		return availableDirections;
	}

	private void initializeCellId(Maze maze){
		int r;
		int c = 0;
		int id = 0;
		for (r = 0; r < maze.sizeR; r++) {
				maze.map[r][c].rootCell = new Cell(r,c);
			c++;
		}
	}

	private boolean allCellsSameId(Maze maze){
		//compare the first cells id to the rest
		//if there are 2 that are different return false
		int r = 0;
		int c = 0;
		Cell firstCellId = maze.map[r][c].rootCell;
		while(c < maze.sizeC) {
			for (r = 0; r < maze.sizeR; r++) {
				if (maze.map[r][c].rootCell != firstCellId){
					return false;
				}
			}
			c++;
		}
		return true;
	}

	private void addEdgesToMap(Maze maze){
		ArrayList<Integer> directions;
		int c = 0;
		while(c < 50){
			for (int r = 0; r < maze.sizeR; r++) {
				//get valid cells
				directions = checkSurroundingWalls(maze,r,c);
				//get surrounding edges
				for (int i = 0; i < directions.size() ; i++) {
					Edge newEdge = new Edge(new Cell(r,c), new Cell(maze.map[r][c].neigh[i].r,maze.map[r][c].neigh[i].c));
					edges.add(newEdge);
				}
			}
			c++;
		}
	}


	protected class Edge{
		private Cell currentCell, adjacentCell;

		public Edge(Cell currentCell, Cell adjacentCell) {
			this.currentCell = currentCell;
			this.adjacentCell = adjacentCell;
		}

		public Cell getCurrentCell() {
			return currentCell;
		}

		public Cell getAdjacentCell() {
			return adjacentCell;
		}

		public void setAdjacentCell(Cell adjacentCell) {
			this.adjacentCell = adjacentCell;
		}
	}

} // end of class KruskalGenerator
