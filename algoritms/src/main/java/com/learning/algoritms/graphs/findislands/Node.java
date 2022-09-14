package com.learning.algoritms.graphs.findislands;

public class Node<T> {

	private int id;
	private boolean selected;
	private T value;

	public Node() {
		this.selected = false;
	}

	public Node(T value, int matrixSize, int currentX, int currentY) {
		this.value = value;
		this.selected = false;
		this.id = Utils.calculateId(matrixSize, currentX, currentY);
	}

	public int getX(int matrixSize) {
		return Utils.reverseXFromId(this.id, matrixSize);
	}

	public int getY(int x, int matrixSize) {
		return Utils.reverseYFromId(this.id, x, matrixSize);
	}

	@Override
	public boolean equals(Object o) {

		if(!(o instanceof Node))
			return false;

		if(o == this)
			return true;

		Node<T> node = (Node<T>)o;

		if(node.getId() == this.id) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean visited) {
		this.selected = visited;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
