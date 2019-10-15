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
		int randomWall = wallDirection.get(rand.nextInt(wallDirection.size()));

		switch (randomWall){
			case Maze.NORTH:	//r+1
				if (randR < maze.sizeR) {
					if (maze.map[randR][randC].rootId != maze.map[randR][randC].neigh[NORTH].rootId){
						maze.map[randR][randC].wall[NORTH].present = false;
						maze.map[randR][randC].neigh[NORTH].rootId = maze.map[randR][randC].rootId;
						//TODO - recurse through neighbour tree, converting ID
					}
				}
				break;
			case Maze.SOUTH:	//r-1
				if (randR > 0){
					if (maze.map[randR][randC].rootId != maze.map[randR][randC].neigh[SOUTH].rootId){
						maze.map[randR][randC].wall[SOUTH].present = false;
						maze.map[randR][randC].neigh[SOUTH].rootId = maze.map[randR][randC].rootId;
						//TODO - recurse through neighbour tree, converting ID
					}
				}
				break;

				case Maze.EAST:	//c+1
					if (randC < maze.sizeC) {
						if (maze.map[randR][randC].rootId != maze.map[randR][randC].neigh[EAST].rootId){
							maze.map[randR][randC].wall[EAST].present = false;
							maze.map[randR][randC].neigh[EAST].rootId = maze.map[randR][randC].rootId;
							//TODO - recurse through neighbour tree, converting ID
						}
					}
					break;

			case Maze.WEST:	//c-1
				if (randC > 0){
					if (maze.map[randR][randC].rootId != maze.map[randR][randC].neigh[WEST].rootId){
						maze.map[randR][randC].wall[WEST].present = false;
						maze.map[randR][randC].neigh[WEST].rootId = maze.map[randR][randC].rootId;
						//TODO - recurse through neighbour tree, converting ID
					}
				}

				break;





		}

//		if (maze.map[randR][randC].neigh[NORTH].rootId != maze.map[randR][randC].rootId){
//
//		}

	}//end loop


	} // end of generateMaze()

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
