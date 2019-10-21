package mazeGenerator;

import maze.Maze;
import maze.Cell;

import java.util.ArrayList;
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
		addEdgesToList(maze);

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

				randEdge.getCurrentCell().rootId = maze.map[randEdge.getCurrentCell().r][randEdge.getCurrentCell().c].rootId;
				randEdge.getAdjacentCell().rootId = maze.map[randEdge.getAdjacentCell().r][randEdge.getCurrentCell().c].rootId;


				System.out.println(randEdge.getCurrentCell().rootId + " " + randEdge.getAdjacentCell().rootId);

				if (randEdge.getCurrentCell().rootId != randEdge.getAdjacentCell().rootId){
					System.out.println("edges size = " +edges.size());

					int direction = getDirectionOfAdjacent(maze,maze.map[randEdge.getCurrentCell().r][randEdge.getCurrentCell().c],maze.map[randEdge.getAdjacentCell().r][randEdge.getAdjacentCell().c]);

					if (direction != -1) {
						//remove wall
						maze.map[randEdge.getCurrentCell().r][randEdge.getCurrentCell().c].wall[direction].present = false;
						maze.map[randEdge.getAdjacentCell().r][randEdge.getAdjacentCell().c].wall[Maze.oppoDir[direction]].present = false;

						changeTreeRootId(maze, randEdge.getCurrentCell(), randEdge.getAdjacentCell());

						edges.remove(randEdge);
					} else{
						System.out.println("direction == -1");

					}
				} else{
					edges.remove(randEdge);
				}
			}
		}//end loop

	} // end of generateMaze()

	private void changeTreeRootId(Maze maze, Cell current, Cell adjacent){
		for (int i = 0; i < trees.size(); i++) {
			if (trees.get(i).rootId == adjacent.rootId){
				trees.get(i).rootId = current.rootId;
				maze.map[trees.get(i).r][trees.get(i).c].rootId = current.rootId;
			}
		}

	}

	private Integer getDirectionOfAdjacent(Maze maze,Cell current, Cell adjacent){
		//TODO - fix error, always returning -1
		if (current.r <maze.sizeR -1 ) {
			if (current.neigh[NORTH] == adjacent) {
				return NORTH;
			}
		}
		if (current.r > 0) {
			if (current.neigh[SOUTH] == adjacent) {
				return SOUTH;
			}
		}
		if (current.c <maze.sizeC -1) {
			if (current.neigh[EAST] == adjacent) {
				return EAST;
			}
		}
		if (current.c > 0) {
			if (current.neigh[WEST] == adjacent) {
				return WEST;
			}
		}
		return -1;
	}

	private ArrayList<Integer> checkSurroundingWalls(Maze maze, int r, int c) {
		ArrayList<Integer> availableDirections = new ArrayList<>();
		if (r < maze.sizeR -1){
		if (maze.map[r][c].neigh[NORTH].rootId != maze.map[r][c].rootId) {
			availableDirections.add(NORTH);
			}
		}
		if (r >0) {
			if (maze.map[r][c].neigh[SOUTH].rootId != maze.map[r][c].rootId) {
				availableDirections.add(SOUTH);
			}
		}
		if (c < maze.sizeC -1) {
			if (maze.map[r][c].neigh[EAST].rootId != maze.map[r][c].rootId) {
				availableDirections.add(EAST);
			}
		}
		if(c > 0) {
			if (maze.map[r][c].neigh[WEST].rootId != maze.map[r][c].rootId) {
				availableDirections.add(WEST);
			}
		}

		return availableDirections;
	}

	private void initializeCellId(Maze maze){
		int r;
		int c = 0;
		int id = 0;
		while(c < maze.sizeC) {
			for (r = 0; r < maze.sizeR; r++) {
				maze.map[r][c].rootId = id;
				id++;
			}
			c++;
		}
	}

	private boolean allCellsSameId(Maze maze){
		//compare the first cells id to the rest
		//if there are 2 that are different return false
		int r = 0;
		int c = 0;
		int firstCellId = maze.map[r][c].rootId;
		while(c < maze.sizeC) {
			for (r = 0; r < maze.sizeR; r++) {
				if (maze.map[r][c].rootId != firstCellId){
					return false;
				}
			}
			c++;
		}
		return true;
	}

	private void addEdgesToList(Maze maze){
		ArrayList<Integer> directions;
		int c = 0;
		while(c < maze.sizeC){
			for (int r = 0; r < maze.sizeR; r++) {
				//get valid cells
				directions = checkSurroundingWalls(maze,r,c);
				//get surrounding edges
				for (int i = 0; i < directions.size() ; i++) {
						Edge newEdge = new Edge(new Cell(r, c), new Cell(maze.map[r][c].neigh[directions.get(i)].r, maze.map[r][c].neigh[directions.get(i)].c));
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
