package com.learning.algoritms.graphs.findislands;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FindIslands implements Runnable {

	private HashSet<Integer> visited = new HashSet<>();
	private Queue<Integer> queue = new LinkedList<Integer>();

	private List<List<Integer>> islands = new LinkedList<>();
	private List<Integer> currentIsland = new LinkedList<>();

	private Matrix nodes;
	private boolean islandsCompleted = false; // to stop drawing when program is creating the matrix 
	private boolean searchingIslands = false; // to stop actions when program is analyzing the matrix (searching islands) 
	
	private int islandIndex = 0;
	private boolean viewing = false;

	public void generateRandomIslands(int matrixSize) {
		this.visited = new HashSet<>();
		this.queue = new LinkedList<Integer>();
		this.islands = new LinkedList<>();
		this.currentIsland = new LinkedList<>();

		this.islandsCompleted = false;

		this.nodes = new Matrix(matrixSize);
		Node<Boolean> node;

		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				node = new Node(Math.round( Math.random()) == 1? true:false, matrixSize, i, j);
				
				this.nodes.setNode(i, j, node);
			}
		}

		this.islandsCompleted = true;
	}

	// Start the process in a thread so the program can show the updates in real time
	public void processIslands() {
		new Thread(this).start();
	}

	// Thread to iterate the matrix
	@Override
	public void run() {
		this.searchingIslands = true; 
		int matrixSize = this.nodes.getMatrixSize();

		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				
				this.nodes.getNodes()[i][j].setSelected(true);

				this.visitNeighbours(i, j);

				try {
					// Stop thread so we can see the process
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				this.nodes.getNodes()[i][j].setSelected(false);
				if(!this.currentIsland.isEmpty()) {
					this.islands.add(this.currentIsland);
					
					this.currentIsland = new LinkedList<>();
				}
			}
		}
		this.searchingIslands = false;
	}

	// Recursive method that handles the island in progress
	public void visitNeighbours(int x, int y) {

		Node<Boolean> currentNode = this.nodes.getNodes()[x][y];
		if(this.visited.contains(currentNode.getId())) {
			return;
		}

		if((boolean) currentNode.getValue()
				&& !this.visited.contains(currentNode.getId())) {
			// Set the initial node as visited
			this.visited.add(currentNode.getId());
			// Save section of current island so we can have save all the nodes for the current island 
			this.currentIsland.add(currentNode.getId());

			// These if are used to search nodes in the matrix top, down, right and left, if a node is found then add it to queue
			// if any node is found, then mark the nodes as visited
			int xyPointer = x - 1;
			if(xyPointer >= 0 
				&& (boolean)this.nodes.getNodes()[xyPointer][y].getValue() 
				&& !this.visited.contains(this.nodes.getNodes()[xyPointer][y].getId())) {

				if(!this.queue.contains(this.nodes.getNodes()[xyPointer][y].getId())) {
					this.queue.add(this.nodes.getNodes()[xyPointer][y].getId());
				}

			} else if(xyPointer >= 0) {
				this.visited.add(this.nodes.getNodes()[xyPointer][y].getId());
			}

			xyPointer = x + 1;
			if(xyPointer < this.nodes.getMatrixSize() 
				&& (boolean)this.nodes.getNodes()[xyPointer][y].getValue() 
				&& !this.visited.contains(this.nodes.getNodes()[xyPointer][y].getId())) {

				if(!this.queue.contains(this.nodes.getNodes()[xyPointer][y].getId())) {
					this.queue.add(this.nodes.getNodes()[xyPointer][y].getId());
				}

			} else if(xyPointer < this.nodes.getMatrixSize()) {
				this.visited.add(this.nodes.getNodes()[xyPointer][y].getId());
			}

			xyPointer = y - 1;
			if(xyPointer >= 0 
				&& (boolean)this.nodes.getNodes()[x][xyPointer].getValue() 
				&& !this.visited.contains(this.nodes.getNodes()[x][xyPointer].getId())) {

				if(!this.queue.contains(this.nodes.getNodes()[x][xyPointer].getId())) {
					this.queue.add(this.nodes.getNodes()[x][xyPointer].getId());
				}

			} else if(xyPointer >= 0) {
				this.visited.add(this.nodes.getNodes()[x][xyPointer].getId());
			}

			xyPointer = y + 1;
			if(xyPointer < this.nodes.getMatrixSize() 
				&& (boolean)this.nodes.getNodes()[x][xyPointer].getValue() 
				&& !this.visited.contains(this.nodes.getNodes()[x][xyPointer].getId())) {
			
				if(!this.queue.contains(this.nodes.getNodes()[x][xyPointer].getId())) {
					this.queue.add(this.nodes.getNodes()[x][xyPointer].getId());
				}

			}  else if(xyPointer < this.nodes.getMatrixSize()) {
				this.visited.add(this.nodes.getNodes()[x][xyPointer].getId());
			}

		} else {
			this.visited.add(this.nodes.getNodes()[x][y].getId());
		}
		// If queue is not empty it means we found new nodes in the previous if
		if(!this.queue.isEmpty()) {
			int id = this.queue.poll();
			
			int queued_x = Utils.reverseXFromId(id, this.nodes.getMatrixSize());
			int queued_y = Utils.reverseYFromId(id, queued_x, this.nodes.getMatrixSize());

			this.visitNeighbours(queued_x, queued_y);
		}
	}

	/*
	 * Paint islands
	 * blue color   = do not belong to island and is already visited
	 * green color  = it belongs to island that is already visited
	 * black color  = it belongs to island that is not visited
	 * orange color = do not belong to island and is not visited
	 * red color    = is the current node that we select in order to search its neighbors 
	 */
	public void paintIslands(Graphics g) {
		int islandSize = this.getIslandSize();
		int squareSize = Constans.SIZE_X / islandSize;

		for (int i = 0; i < islandSize; i++) {
			for (int j = 0; j < islandSize; j++) {
			
				Node<Boolean> currentNode = this.getNode(i, j);

				if(this.visited.contains(currentNode.getId()) && !currentNode.getValue()) {
					g.setColor(Color.BLUE);
					g.fillRect(
							j * squareSize, 
							i * squareSize, 
							squareSize,
							squareSize);
				} else if(this.visited.contains(currentNode.getId()) && currentNode.getValue()) {
					g.setColor(Color.GREEN);
					g.fillRect(
							j * squareSize, 
							i * squareSize, 
							squareSize,
							squareSize);
				} else if((boolean)currentNode.getValue()) {
					g.setColor(Color.BLACK);
					g.fillRect(
							j * squareSize, 
							i * squareSize, 
							squareSize, 
							squareSize);
				} else {
					g.setColor(Color.ORANGE);
					g.drawRect(
							j * squareSize, 
							i * squareSize, 
							squareSize,
							squareSize);	
				}

				if((boolean)currentNode.isSelected()) {
					g.setColor(Color.RED);
					g.fillRect(
							j * squareSize, 
							i * squareSize, 
							squareSize, 
							squareSize);
				}
				//g.setColor(Color.YELLOW);
				//g.drawString(currentNode.getId() + "", (j * squareSize) + squareSize/2, ((i+1) * squareSize) - squareSize/2);
			}
		}
	}

	/*
	 * Paint islands when view option is active (this option is activated in keyPresed method from mainWindow)
	 * black color  = it belongs to an island that is not selected
	 * orange color = all the squares that do not belongs to islands
	 * red color    = is the current node that we select in order to search its neighbors 
	 * green color  = it belongs to all the squares that belongs to the selected island
	 */
	public void paintSelectedIsland(Graphics g) {
		int islandSize = this.getIslandSize();
		int squareSize = Constans.SIZE_X / islandSize;

		List<Integer> selectedList;

		for (int i = 0; i < islandSize; i++) {
			for (int j = 0; j < islandSize; j++) {
			
				Node<Boolean> currentNode = this.getNode(i, j);

				if((boolean)currentNode.getValue()) {
					g.setColor(Color.BLACK);
					g.fillRect(
							j * squareSize, 
							i * squareSize, 
							squareSize, 
							squareSize);
				} else {
					g.setColor(Color.ORANGE);
					g.drawRect(
							j * squareSize, 
							i * squareSize, 
							squareSize,
							squareSize);	
				}

				if((boolean)currentNode.isSelected()) {
					g.setColor(Color.RED);
					g.fillRect(
							j * squareSize, 
							i * squareSize, 
							squareSize, 
							squareSize);
				}
			}
		}
		
		if(this.islands.size() > 0) {
			selectedList = this.islands.get(this.islandIndex);
			
			for(int currentIndex: selectedList) {
				// Calculate the square location inside the matrix
				int x = Utils.reverseXFromId(currentIndex, this.nodes.getMatrixSize());
				int y = Utils.reverseYFromId(currentIndex, x, this.nodes.getMatrixSize());
				
				g.setColor(Color.GREEN);
				g.fillRect(
						y * squareSize, 
						x * squareSize, 
						squareSize,
						squareSize);
			}
		}
	}
	
	public void increaseIndex() {
		if(this.islands.size() == 0) {
			this.islandIndex = 0;
		} else {

			this.islandIndex++;
	
			if(this.islandIndex > this.islands.size() - 1) {
				this.islandIndex = 0;
			}
		}
		System.out.println("left: " + this.islandIndex);
	}
	
	public void reduceIndex() {
		if(this.islands.size() == 0) {
			this.islandIndex = 0;
			return;
		} else {
			this.islandIndex--;

			if(this.islandIndex < 0) {
				this.islandIndex = this.islands.size() - 1;
			}	
		}
		System.out.println("left: " + this.islandIndex);
	}

	public boolean isIslandsCompleted() {
		return this.islandsCompleted;
	}

	public boolean isSearchingIslands() {
		return this.searchingIslands;
	}

	public int getIslandSize() {
		return this.nodes.getMatrixSize();
	}
	
	public Node<Boolean> getNode(int x, int y) {
		return this.nodes.getNode(x, y);
	}

	public HashSet<Integer> getVisited() {
		return this.visited;
	}

	public void setVisited(HashSet<Integer> visited) {
		this.visited = visited;
	}

	public Queue<Integer> getQueue() {
		return this.queue;
	}

	public void setQueue(Queue<Integer> queue) {
		this.queue = queue;
	}

	public boolean isViewing() {
		return viewing;
	}

	public void setViewing(boolean viewing) {
		this.viewing = viewing;
	}
}
