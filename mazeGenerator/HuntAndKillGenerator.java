package mazeGenerator;

import maze.Cell;
import maze.Maze;
import java.util.*;
import java.util.List;

public class HuntAndKillGenerator implements MazeGenerator {


//	1. Choose a starting location.
//	2. Perform a random walk, carving passages to unvisited neighbors, until the current cell has no unvisited neighbors.
//	3.Enter “hunt” mode, where you scan the grid looking for an unvisited cell that is adjacent to a visited cell. If found, carve a passage between the two and let the formerly unvisited cell be the new starting location.
//	4. Repeat steps 2 and 3 until the hunt mode scans the entire grid and finds no unvisited cells.

private int NORTH = Maze.NORTH;
private int SOUTH = Maze.SOUTH;
private int EAST = Maze.EAST;
private int WEST = Maze.WEST;

	private boolean[][] visited;
	private List<Integer> direction;
	private Random rand = new Random();


	@Override
	public void generateMaze(Maze maze) {
		visited = new boolean[maze.sizeR][maze.sizeC];
		direction = Arrays.asList(NORTH, SOUTH, EAST, WEST);

		int row = maze.entrance.r;
		int col = maze.entrance.c;
		//loop
		while(!maze.validate()) {
			walk(maze, row, col);
			hunt(maze);
		}
			//end loop

	} // end of generateMaze()

	//i created this
	private void walk(Maze maze, int r,int c) {

		int nextR = r;
		int nextC = c;

		if (r >= 0 && r < maze.sizeR && c >= 0 && c < maze.sizeC) {
			Cell current = new Cell(r, c);
			Cell north = new Cell(r - 1, c);
			Cell south = new Cell(r + 1, c);
			Cell east = new Cell(r, c - 1);
			Cell west = new Cell(r, c + 1);

			maze.map[r][c].neigh[NORTH] = north;
			int northNeighbourR = maze.map[r][c].neigh[NORTH].r;
			int northNeighbourC = maze.map[r][c].neigh[NORTH].c;

			maze.map[r][c].neigh[SOUTH] = south;
			int southNeighbourR = maze.map[r][c].neigh[SOUTH].r;
			int southNeighbourC = maze.map[r][c].neigh[SOUTH].c;


			maze.map[r][c].neigh[EAST] = east;
			int eastNeighbourR = maze.map[r][c].neigh[EAST].r;
			int eastNeighbourC = maze.map[r][c].neigh[EAST].c;


			maze.map[r][c].neigh[WEST] = west;
			int westNeighbourR = maze.map[r][c].neigh[WEST].r;
			int westNeighbourC = maze.map[r][c].neigh[WEST].c;

			visited[r][c] = true;

			int walkDirection = direction.get(rand.nextInt(direction.size()));
			System.out.println("walk direction = " + walkDirection);
			System.out.println("checkSpots = " + walkCheckSpots(maze, r, c));

			if (walkCheckSpots(maze, r, c)) {
				switch (walkDirection) {
					//2
					case Maze.NORTH:
						if (r > 0) {
							if (!visited[northNeighbourR][northNeighbourC]) {
								nextR = northNeighbourR;
								nextC = northNeighbourC;
								maze.map[r][c].wall[SOUTH].present = false;
								maze.drawFtPrt(current);
							}
						}
						break;
						//5
					case Maze.SOUTH:
						if (r < maze.sizeR - 1) {
							if (!visited[southNeighbourR][southNeighbourC]) {
								nextR = southNeighbourR;
								nextC = southNeighbourC;
								maze.map[r][c].wall[NORTH].present = false;
								maze.drawFtPrt(current);
							}
						}
						break;
						//0
					case Maze.EAST:
						if (c > 0) {
							if (!visited[eastNeighbourR][eastNeighbourC]) {
								nextR = eastNeighbourR;
								nextC = eastNeighbourC;
								maze.map[r][c].wall[WEST].present = false;
								maze.drawFtPrt(current);
							}
						}
						break;
						//3
					case Maze.WEST:
						if (c < maze.sizeR - 1) {
							if (!visited[westNeighbourR][westNeighbourC]) {
								nextR = westNeighbourR;
								nextC = westNeighbourC;
								maze.map[r][c].wall[EAST].present = false;
								maze.drawFtPrt(current);
							}
						}
						break;
				}
				walk(maze, nextR, nextC);
			}
		}
	}

	//i created this
	private void hunt(Maze maze) {
		System.out.println("HUNT START!");

		int startRow = 0;
		int r = 0;
		int c = 0;
		boolean cellFound = false;

		while(!cellFound) {
				for (r = startRow; r < maze.sizeR; r++) {
					if (!cellFound) {
						System.out.println("checking " + r + " " + c);

						Cell foundCell = huntCheckSpots(maze, r, c);
						if (visited[0][12]){
							System.out.println("exit is visited");

						}

						if (foundCell != null  && !visited[r][c]) {
							System.out.println("found cell " + foundCell.r + " " + foundCell.c);
							cellFound = true;
							visited[r][c] = true;

							if (r >= 0 && foundCell.r > 0) {
								maze.map[foundCell.r][foundCell.c].neigh[NORTH] = new Cell(r - 1, c);
								if (visited[foundCell.r - 1][foundCell.c] && !visited[r][c]) {
									maze.drawFtPrt(foundCell);
									maze.validate();
									maze.map[r][c].wall[SOUTH].present = false;
									visited[r][c] = true;
									break;
								}
							} else if (r < maze.sizeR - 1 && foundCell.r < maze.sizeR - 1) {
								maze.map[foundCell.r][foundCell.c].neigh[SOUTH] = new Cell(r + 1, c);
								if (visited[foundCell.r + 1][foundCell.c] && !visited[r][c]) {
									maze.drawFtPrt(foundCell);
									maze.map[r][c].wall[NORTH].present = false;
									visited[r][c] = true;
									break;
								}
							} else if (c >= 0 && foundCell.c > 0) {
								maze.map[foundCell.r][foundCell.c].neigh[EAST] = new Cell(r, c - 1);
								if (visited[foundCell.r][foundCell.c - 1]  && !visited[r][c]) {
									maze.drawFtPrt(foundCell);
									maze.map[r][c].wall[WEST].present = false;
									visited[r][c] = true;
									break;
								}
							} else if (c < maze.sizeC - 1 && foundCell.c < maze.sizeC - 1) {
								if (visited[foundCell.r][foundCell.c + 1]  && !visited[r][c]) {
									maze.drawFtPrt(foundCell);
									maze.map[r][c].wall[EAST].present = false;
									visited[r][c] = true;
									break;
								}
							}

						}
					}
				}
			c++;
		}
	}
	private Cell huntCheckSpots(Maze maze,int r,int c){

		for (int cell = 0; cell < 4; cell++) {
			int directionToTravel = direction.get(cell);
			//check north
			switch (directionToTravel) {
				case Maze.NORTH:
					if (r > 0) {
						if (visited[r - 1][c]) {
							return new Cell(r - 1, c);
						}
					}
					break;
				case Maze.SOUTH:
					if (r < maze.sizeR - 1) {
						if (visited[r + 1][c]) {
							return new Cell(r + 1, c);
						}
					}
					break;

				case Maze.EAST:
					if (c > 0) {
						if (visited[r][c - 1]) {
							return new Cell(r, c - 1);
						}
					}
					break;

				case Maze.WEST:

					if (c < maze.sizeC - 1) {
						if (visited[r][c + 1]) {
							return new Cell(r, c + 1);
						}
					}
					break;
			}
		}
		return null;
	}

	private boolean walkCheckSpots(Maze maze,int r,int c){
		//check north
		if (r > 0) {
			if (!visited[r - 1][c]){
				return true;
			}
		}
			if (r < maze.sizeR-1){
					if( !visited[r +1][c]){
						return true;
					}
			}

		if (c > 0) {
				if( !visited[r][c-1]){
					return true;
				}
			}

		if (c < maze.sizeC-1){
			if (!visited[r][c + 1]){
				return true;
			}
		}
return false;
	}



} // end of class HuntAndKillGenerator
