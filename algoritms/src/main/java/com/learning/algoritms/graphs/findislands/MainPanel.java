package com.learning.algoritms.graphs.findislands;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private FindIslands islands = new FindIslands();

	public MainPanel() {

        this.setBackground(new java.awt.Color(0, 0, 0));
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setBounds(0, 0, Constans.SIZE_X, Constans.SIZE_Y);
        
        this.generateIslands(Constans.ISLAND_AMOUNT);
        
        this.setVisible(true);
	}

	public void generateIslands(int matrixSize) {
		islands.generateRandomIslands(matrixSize);
	}

	public void searchIslands() {
		this.islands.processIslands();
	}
	
	public boolean isSearchinIslands() {
		return this.islands.isSearchingIslands();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Constans.SIZE_X, Constans.SIZE_Y);
		
		if(this.islands.isIslandsCompleted()) {
			if(!this.islands.isViewing()) {
				this.islands.paintIslands(g);
			} else {
				this.islands.paintSelectedIsland(g);
			}
		}
	}
	
	public void togleViewOptions() {
		this.islands.setViewing(!this.islands.isViewing());
	}
	
	public boolean isViewing() {
		return this.islands.isViewing();
	}

	
	public void moveLeft() {
		this.islands.reduceIndex();
	}

	public void moveRight() {
		this.islands.increaseIndex();
	}
}
