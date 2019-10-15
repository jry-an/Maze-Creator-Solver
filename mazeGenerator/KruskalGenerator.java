package mazeGenerator;

import maze.Maze;
import maze.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KruskalGenerator implements MazeGenerator {

	private static int NORTH = Maze.NORTH;
	private static  int SOUTH = Maze.SOUTH;
	private static int EAST = Maze.EAST;
	private static int WEST = Maze.WEST;
	private List<Integer> wallDirection;
	private Random rand;

	@Override
	public void generateMaze(Maze maze) {
		rand = new Random();
		wallDirection = Arrays.asList(NORTH, SOUTH, EAST, WEST);
		initializeCellId(maze);

		//repeat until all cells the same root id
	while(!allCellsSameId(maze)){
		/*
		1. pick random wall
		2. if the cells next to the wall != same rootId
			- break down wall
			- change all cells with that id to with og cell
		3. repeat 1 and 2
		 */

		//get random cell

		int randR = rand.nextInt(maze.sizeR);
		int randC = rand.nextInt(maze.sizeC);
		ArrayList<Integer> listofAvailableWalls = checkSurroundingWalls(maze,randR,randC);
		int randomWall = listofAvailableWalls.get(rand.nextInt(listofAvailableWalls.size()));

		switch (randomWall){
			case Maze.NORTH:	//r+1
				if (randR < maze.sizeR) {
						maze.map[randR][randC].wall[NORTH].present = false;
						maze.map[randR][randC].neigh[NORTH].rootId = maze.map[randR][randC].rootId;
						//TODO - recurse through neighbour tree, converting ID
				}
				break;
			case Maze.SOUTH:	//r-1
				if (randR > 0){
						maze.map[randR][randC].wall[SOUTH].present = false;
						maze.map[randR][randC].neigh[SOUTH].rootId = maze.map[randR][randC].rootId;
						//TODO - recurse through neighbour tree, converting ID
				}
				break;

				case Maze.EAST:	//c+1
					if (randC < maze.sizeC) {
							maze.map[randR][randC].wall[EAST].present = false;
							maze.map[randR][randC].neigh[EAST].rootId = maze.map[randR][randC].rootId;
							//TODO - recurse through neighbour tree, converting ID
					}
					break;

			case Maze.WEST:	//c-1
				if (randC > 0){
						maze.map[randR][randC].wall[WEST].present = false;
						maze.map[randR][randC].neigh[WEST].rootId = maze.map[randR][randC].rootId;
						//TODO - recurse through neighbour tree, converting ID
				}

				break;





		}

//		if (maze.map[randR][randC].neigh[NORTH].rootId != maze.map[randR][randC].rootId){
//
//		}

	}//end loop


	} // end of generateMaze()

	private ArrayList<Integer> checkSurroundingWalls(Maze maze, int r, int c) {
		ArrayList<Integer> availableDirections = new ArrayList<>();
		if (maze.map[r][c].neigh[NORTH].rootId != maze.map[r][c].rootId){
			availableDirections.add(NORTH);
		}
		if (maze.map[r][c].neigh[SOUTH].rootId != maze.map[r][c].rootId){
			availableDirections.add(SOUTH);
		}
		if (maze.map[r][c].neigh[EAST].rootId != maze.map[r][c].rootId){
			availableDirections.add(EAST);
		}
		if (maze.map[r][c].neigh[WEST].rootId != maze.map[r][c].rootId){
			availableDirections.add(WEST);
		}

		return availableDirections;
	}

	private void initializeCellId(Maze maze){
		int r = 0;
		int c = 0;
		int id = 0;
		while(r < maze.sizeR && c < maze.sizeC) {
			for (r = 0; r < maze.sizeR; r++) {
				maze.map[r][c].rootId = id;
			}
			c++;
			id++;
		}
	}

	private boolean allCellsSameId(Maze maze){
		//compare the first cells id to the rest
		//if there are 2 that are different return false

		int r = 0;
		int c = 0;
		int firstCellId = maze.map[r][c].rootId
				;
		while(r < maze.sizeR && c < maze.sizeC) {
			for (r = 0; r < maze.sizeR; r++) {
				if (maze.map[r][c].rootId != firstCellId){
					return false;
				}
			}
			c++;
		}
		return true;
	}

} // end of class KruskalGenerator
