package com.learning.algoritms.graphs.findislands;

public class Matrix {

	private int matrixSize;
	private Node[][] nodes;
	
	public Matrix() {
	}

	public Matrix(int matrixSize) {
		this.matrixSize = matrixSize;
		nodes = new Node[matrixSize][matrixSize];
	}
	
	public Node<Boolean> getNode(int x, int y) {
		return nodes[x][y];
	}

	public void setNode(int x, int y, Node node) {
		nodes[x][y] = node;
	}

	public int getMatrixSize() {
		return matrixSize;
	}

	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize;
	}

	public Node[][] getNodes() {
		return nodes;
	}

	public void setNodes(Node[][] nodes) {
		this.nodes = nodes;
	}
	
}
