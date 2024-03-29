package mazeGenerator;

import maze.Maze;
import maze.Cell;

import java.util.ArrayList;
import java.util.Random;

public class KruskalGenerator implements MazeGenerator {

	/**
	 * PLEASE NOTE THAT THE CELL CLASS HAS BEEN EDITED (added rootId int)
	 */

	private static int NORTH = Maze.NORTH;
	private static  int SOUTH = Maze.SOUTH;
	private static int EAST = Maze.EAST;
	private static int WEST = Maze.WEST;
	private ArrayList<Edge> edges;

	@Override
	public void generateMaze(Maze maze) {
		Random rand = new Random();
		edges = new ArrayList<>();
		initializeCellId(maze);
		addEdgesToList(maze);

		//repeat until edges is empty
		while(!edges.isEmpty()){
		/*
		1. pick random edge
		2. if the cells next to the wall != same rootCell
			- break down wall
			- change all cells with that id, in that tree to the same as original id
		3. repeat 1 and 2
		 */


			//get random edge
				Edge randEdge = edges.get(rand.nextInt(edges.size()));
				//initialize randEdge  rootId
				randEdge.getCurrentCell().rootId = maze.map[randEdge.getCurrentCell().r][randEdge.getCurrentCell().c].rootId;
				randEdge.getAdjacentCell().rootId = maze.map[randEdge.getAdjacentCell().r][randEdge.getAdjacentCell().c].rootId;
				//check if current cell and adjacent cell have same id
				if (randEdge.getCurrentCell().rootId != randEdge.getAdjacentCell().rootId){
					//get direction of adjacent cell in comparison to current cell
					int direction = getDirectionOfAdjacent(maze,maze.map[randEdge.getCurrentCell().r][randEdge.getCurrentCell().c], maze.map[randEdge.getAdjacentCell().r][randEdge.getAdjacentCell().c]);
					System.out.println(direction);
					//-1 if no direction found
					if (direction != -1) {
						//remove wall
						maze.map[randEdge.getCurrentCell().r][randEdge.getCurrentCell().c].wall[direction].present = false;
						maze.map[randEdge.getAdjacentCell().r][randEdge.getAdjacentCell().c].wall[Maze.oppoDir[direction]].present = false;

						//merge trees
						changeTreeRootId(maze, randEdge.getCurrentCell(), randEdge.getAdjacentCell());

						//remove edge from list of edges
						edges.remove(randEdge);
					} else{
						System.out.println("direction == -1, failed to find direction");
					}
				} else {
					edges.remove(randEdge);
				}
		}//end loop

	} // end of generateMaze()
	//change all the root id that are same ad adjacent to current rootId
	private void changeTreeRootId(Maze maze, Cell current, Cell adjacent){
		int c = 0;
		int oldAdjRoot = maze.map[adjacent.r][adjacent.c].rootId;
		maze.map[adjacent.r][adjacent.c].rootId = maze.map[current.r][current.c].rootId;

		while(c < maze.sizeC){
			for (int r = 0; r < maze.sizeR; r++) {
				if (maze.map[r][c].rootId == oldAdjRoot){
					maze.map[r][c].rootId = maze.map[current.r][current.c].rootId;
				}
				//when cell is a tunnel, go to cell and change rootId of tunnel exit to tunnel entrance rootId
				//change all with same rootId as previous id
				if (maze.map[r][c].tunnelTo != null){
					int tunnelR = maze.map[r][c].tunnelTo.r;
					int tunnelC = maze.map[r][c].tunnelTo.c;
					if (maze.map[r][c].rootId != maze.map[tunnelR][tunnelC].rootId){
						maze.map[tunnelR][tunnelC].rootId = maze.map[r][c].rootId;
					}
				}
			}
			c++;
		}

	}
	//get the direction of the adjacent cell in comaprision to current cell
	private Integer getDirectionOfAdjacent(Maze maze,Cell current, Cell adjacent){
		if (current.r <maze.sizeR ) {
			if (maze.map[current.r][current.c].neigh[NORTH] == maze.map[adjacent.r][adjacent.c]) {
				return NORTH;
			}
		}
		if (current.r > 0) {
			if (maze.map[current.r][current.c].neigh[SOUTH] == maze.map[adjacent.r][adjacent.c]) {
				return SOUTH;
			}
		}
		if (current.c <maze.sizeC) {
			if (maze.map[current.r][current.c].neigh[EAST] == maze.map[adjacent.r][adjacent.c]) {
				return EAST;
			}
		}
		if (current.c > 0) {
			if (maze.map[current.r][current.c].neigh[WEST] == maze.map[adjacent.r][adjacent.c]) {
				return WEST;
			}
		}
		return -1;
	}

	//gets the 4 surrounding neighbours rootId, if not the same add to list to return
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

	//give every cell a unique id
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

	//initialize edges and add to list of edges
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
	}

} // end of class KruskalGenerator
