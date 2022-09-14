package com.learning.algoritms.graphs.findislands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class MainWindow extends JFrame implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private MainPanel panel;
	private Timer timer;
	boolean r = false;

	public MainWindow() {
		this.setName("main-window");
		this.setBounds(Constans.LOCATION_X, Constans.LOCATION_Y, Constans.SIZE_X, Constans.SIZE_Y);
		this.setResizable(false);  
        
        panel = new MainPanel();
        this.add(panel);
        
        this.addMouseListener(this);
        this.addKeyListener(this);

        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.startTimer();
	}
	
	private void startTimer() {
		
	    timer = new Timer(38, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaintObjects();
			}
	    });
	    timer.start();
	}
	
	public void repaintObjects() {
		this.panel.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// Press escape to close the program 
		if(e.getKeyCode() == 27) {
			System.exit(0);
		} else if(e.getKeyCode() == 82) { // Press r to reset
			this.panel.generateIslands(Constans.ISLAND_AMOUNT);
		} else if(e.getKeyCode() == 10) { // Press enter to start searching
			System.out.println("Mouse Clicket");
			if(!this.panel.isSearchinIslands()) {
				panel.searchIslands();
			}
		} else if(e.getKeyCode() == 86) { // Press v to enable review screen(navigate trough all the islands)
			this.panel.togleViewOptions();
		}

		// When view screen is enabled you can use right and left arrows
		if(this.panel.isViewing()) {
			if(e.getKeyCode() == 39 ) { // Press right arrow to navigate in the list of islands 
				this.panel.moveRight();
			} else if(e.getKeyCode() == 37) { // Press left arrow to navigate in the list of islands 
				this.panel.moveLeft();
			}
		}
		
		//System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
