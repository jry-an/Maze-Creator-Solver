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

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		visited = new boolean[maze.sizeR][maze.sizeC];
		direction = Arrays.asList(NORTH, SOUTH, EAST, WEST);

		//loop
		int row = maze.entrance.r;
		int col = maze.entrance.c;
		System.out.println(row + "," + col);


		walk(maze,row,col);
			hunt(maze);
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
			System.out.println("row " + westNeighbourR + " col: " +westNeighbourC);



			visited[r][c] = true;

			Random rand = new Random();
			int walkDirection = direction.get(rand.nextInt(direction.size()));
			System.out.println("walk direction = " + walkDirection);

			System.out.println("checkSpots = " + checkSpots(maze, r, c));

			if (checkSpots(maze, r, c)) {
				switch (walkDirection) {
					//2
					case Maze.NORTH:
						if (r > 0) {
							if (!visited[northNeighbourR][northNeighbourC]) {
								nextR = northNeighbourR;
								nextC = northNeighbourC;
								maze.map[r][c].wall[NORTH].present = false;
//					maze.map[northNeighbourR][northNeighbourC].wall[SOUTH].present = false;
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
								maze.map[r][c].wall[SOUTH].present = false;
//					maze.map[southNeighbourC][southNeighbourC].wall[NORTH].present = false;
								maze.drawFtPrt(current);

							}

							//EAST
						}
						break;
						//0
					case Maze.EAST:
						if (c > 0) {
							if (!visited[eastNeighbourR][eastNeighbourC]) {
								nextR = eastNeighbourR;
								nextC = eastNeighbourC;
								maze.map[r][c].wall[EAST].present = false;
//					maze.map[eastNeighbourC][eastNeighbourR].wall[WEST].present = false;
								maze.drawFtPrt(current);

							}

							//WEST
						}
						break;
						//3
					case Maze.WEST:
						if (c < maze.sizeR - 1) {
							if (!visited[westNeighbourR][westNeighbourC]) {
								nextR = westNeighbourR;
								nextC = westNeighbourC;
								maze.map[r][c].wall[WEST].present = false;
//				maze.map[northNeighbourR][northNeighbourC].wall[EAST].present = false;
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
	}

	private boolean checkSpots(Maze maze,int r,int c){
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
		//check south
		//check east
		//check west
return false;
	}



} // end of class HuntAndKillGenerator
