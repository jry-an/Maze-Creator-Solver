package mazeGenerator;

import maze.Cell;
import maze.Maze;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HuntAndKillGenerator implements MazeGenerator {

//	1. Choose a starting location.
//	2. Perform a random walk, carving passages to unvisited neighbors, until the current cell has no unvisited neighbors.
//	3.Enter “hunt” mode, where you scan the grid looking for an unvisited cell that is adjacent to a visited cell. If found, carve a passage between the two and let the formerly unvisited cell be the new starting location.
//	4. Repeat steps 2 and 3 until the hunt mode scans the entire grid and finds no unvisited cells.

	private static int NORTH = Maze.NORTH;
	private static  int SOUTH = Maze.SOUTH;
	private static int EAST = Maze.EAST;
	private static int WEST = Maze.WEST;

	private boolean[][] visited;
	private List<Integer> direction;
	private Random rand = new Random();


	@Override
	public void generateMaze(Maze maze) {
		//initialize visited 2D array + direction list
		visited = new boolean[maze.sizeR][maze.sizeC];
		direction = Arrays.asList(NORTH, SOUTH, EAST, WEST);
		initializeVisited(maze);

		//set starting walk to entrance
		int row = maze.entrance.r;
		int col = maze.entrance.c;
		//loop until every cell in visited 2D array is visited
		while(!allVisited(maze)) {
			walk(maze, row, col);
			Cell nextWalk = hunt(maze);
			//set walk row and col to result of hunt
			if (nextWalk != null) {
				row = nextWalk.r;
				col = nextWalk.c;
			}
		}//end loop

		System.out.println("0,0 visited status: " + visited[0][0]);
	} // end of generateMaze()

	//i created this
	private void walk(Maze maze, int r,int c) {

		int nextR = r;
		int nextC = c;

		//cell is in bound of maze map
		if (r >= 0 && r < maze.sizeR && c >= 0 && c < maze.sizeC) {

			//set current cell to visited
			visited[r][c] = true;

			//pick a random direction to walk to
			int walkDirection = direction.get(rand.nextInt(direction.size()));
//			System.out.println("walk direction = " + walkDirection);
//			System.out.println("checkSpots = " + walkCheckSpots(maze, r, c));

			//if there is a spot neighbour coord available continue
			if (walkCheckSpots(maze, r, c)) {
				switch (walkDirection) {
					//2
					case Maze.NORTH:
						if (r < maze.sizeR - 1) {
							int northNeighbourR = maze.map[r][c].neigh[NORTH].r;
							int northNeighbourC = maze.map[r][c].neigh[NORTH].c;
							if (!visited[northNeighbourR][northNeighbourC]) {
								nextR = northNeighbourR;
								nextC = northNeighbourC;
								maze.map[r][c].wall[NORTH].present = false;
								maze.map[r][c].neigh[NORTH].wall[SOUTH].present = false;
								System.out.println(r + "," + c);
							}
						}
						break;
						//5
					case Maze.SOUTH:
						if (r > 0) {
							int southNeighbourR = maze.map[r][c].neigh[SOUTH].r;
							int southNeighbourC = maze.map[r][c].neigh[SOUTH].c;
							if (!visited[southNeighbourR][southNeighbourC]) {
								nextR = southNeighbourR;
								nextC = southNeighbourC;
								maze.map[r][c].wall[SOUTH].present = false;
								maze.map[r][c].neigh[SOUTH].wall[NORTH].present = false;

								System.out.println(r + "," + c);

							}
						}
						break;
						//0
					case Maze.EAST:
						if (c < maze.sizeR - 1) {
							int eastNeighbourR = maze.map[r][c].neigh[EAST].r;
							int eastNeighbourC = maze.map[r][c].neigh[EAST].c;

							if (!visited[eastNeighbourR][eastNeighbourC]) {
								nextR = eastNeighbourR;
								nextC = eastNeighbourC;
								maze.map[r][c].wall[EAST].present = false;
								maze.map[r][c].neigh[EAST].wall[WEST].present = false;

								System.out.println(r + "," + c);

							}
						}
						break;
						//3
					case Maze.WEST:
						if (c > 0) {
							int westNeighbourR = maze.map[r][c].neigh[WEST].r;
							int westNeighbourC = maze.map[r][c].neigh[WEST].c;
							if (!visited[westNeighbourR][westNeighbourC]) {
								nextR = westNeighbourR;
								nextC = westNeighbourC;
								maze.map[r][c].wall[WEST].present = false;
								maze.map[r][c].neigh[WEST].wall[EAST].present = false;
								System.out.println(r + "," + c);
							}
						}
						break;
				}
				//once moved cell, call walk again. this is recursed until there are no possible neighbours
				walk(maze, nextR, nextC);
			}
		}
	}

	//i created this
	private Cell hunt(Maze maze) {
		System.out.println("HUNT START!");

		int startRow = 0;
		int r;
		int c = 0;
		boolean cellFound = false;

		//find cell that is unvisited && has a neighbour that is
		//break wall between them
		//return walk from that cell

		while(!cellFound && c < maze.sizeC) {
				for (r = startRow; r < maze.sizeR; r++) {
					if (!cellFound) {

						int foundCell = huntCheckSpots(maze, r, c);
						if (foundCell != -1  && !visited[r][c]) {
							cellFound = true;
							//NORTH
							if (foundCell == NORTH) {
								int northNeighbourR = maze.map[r][c].neigh[NORTH].r;
								int northNeighbourC = maze.map[r][c].neigh[NORTH].c;
								if (visited[northNeighbourR][northNeighbourC] && !visited[r][c]) {
									maze.map[r][c].neigh[NORTH].wall[SOUTH].present = false;
									maze.map[r][c].wall[NORTH].present = false;
									System.out.println(r + "," + c);
									return new Cell(northNeighbourR, northNeighbourC);
								}
							} //SOUTH
							else if (foundCell == SOUTH ) {
								int southNeighbourR = maze.map[r][c].neigh[SOUTH].r;
								int southNeighbourC = maze.map[r][c].neigh[SOUTH].c;
								if (visited[southNeighbourR][southNeighbourC] && !visited[r][c]) {
									maze.map[r][c].neigh[SOUTH].wall[NORTH].present = false;
									maze.map[r][c].wall[SOUTH].present = false;
									System.out.println(r + "," + c);
									return new Cell(southNeighbourR, southNeighbourC);
								}
							} //EAST
							else if (foundCell == EAST) {
								int eastNeighbourR = maze.map[r][c].neigh[EAST].r;
								int eastNeighbourC = maze.map[r][c].neigh[EAST].c;
									if (visited[eastNeighbourR][eastNeighbourC]  && !visited[r][c]) {
										maze.map[r][c].neigh[EAST].wall[WEST].present = false;
										maze.map[r][c].wall[EAST].present = false;
										System.out.println(r + "," + c);
											return new Cell(eastNeighbourR, eastNeighbourC);
									}
							} //WEST
							else if (foundCell == WEST) {
								int westNeighbourR = maze.map[r][c].neigh[WEST].r;
								int westNeighbourC = maze.map[r][c].neigh[WEST].c;
								if (visited[westNeighbourR][westNeighbourC] && !visited[r][c]) {
									maze.map[r][c].neigh[WEST].wall[EAST].present = false;
									maze.map[r][c].wall[WEST].present = false;
										System.out.println(r + "," + c);
									return new Cell(westNeighbourR, westNeighbourC);
								}
							}
						}
					}
				}
			c++;
		}
		return null;
	}
	private int huntCheckSpots(Maze maze,int r,int c){
		for (int cell = 0; cell < 4; cell++) {
			int directionToTravel = direction.get(cell);
			//check north
			switch (directionToTravel) {
				case Maze.NORTH:
					if (r < maze.sizeR - 1) {
						if (visited[r + 1][c] && !visited[r][c]) {
							return NORTH;
						}
					}
					break;
				case Maze.SOUTH:
					if (r > 0) {
						if (visited[r - 1][c] && !visited[r][c]) {
							return SOUTH;
						}
					}
					break;

				case Maze.EAST:
					if (c < maze.sizeC - 1) {
						if (visited[r][c + 1] && !visited[r][c]) {
							return EAST;
						}
					}
					break;

				case Maze.WEST:

					if (c > 0 ) {
						if (visited[r][c-1] && !visited[r][c]) {
							return WEST;
						}
					}
					break;
			}
		}
		return -1;
	}

	private boolean walkCheckSpots(Maze maze,int r,int c){
		if (r < maze.sizeR-1){
			if( !visited[r +1][c]){
				return true;
			}
		}
		if (r > 0) {
			if (!visited[r - 1][c]){
				return true;
			}
		}
		if (c < maze.sizeC-1){
			if (!visited[r][c + 1]){
				return true;
			}
		}
		if (c > 0) {
			if( !visited[r][c-1]){
				return true;
			}
		}
return false;
	}

	private boolean allVisited(Maze maze) {
		int c = 0;
		while (c < maze.sizeC) {
			for (int r = 0; r < maze.sizeR; r++) {
					if (!visited[r][c]) {
						return false;
					}
			}
			c++;
		}
		return true;
	}

	private void initializeVisited(Maze maze){
		int c = 0;
		while(c<maze.sizeC) {
			for (int r = 0; r < maze.sizeR; r++) {
				visited[r][c] = false;
			}
			c++;
		}
	}
} // end of class HuntAndKillGenerator
