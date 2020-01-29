package com.plter.colorball;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ColorBallMain extends JFrame{

	private static final long serialVersionUID = 1L;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ColorBallMain().setVisible(true);
	}
	
	
	private JLabel label;
	private JLabel[] labels=new JLabel[7];
	private JButton reGenBtn;
	private final ArrayList<Integer> allRedBalls = new ArrayList<Integer>();
	private final ActionListener reGenBtnActionListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			genColorBall();
		}
	};
	
	public ColorBallMain() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(450, 300);
		setLayout(null);
		setTitle("双色球模拟器");
		
		for (int i = 0; i < labels.length; i++) {
			label=new JLabel();
			labels[i]=label;
			
			label.setSize(35, 35);
			label.setFont(new Font(null, 0, 25));
			label.setLocation(i*45+10, 10);
			
			if (i<6) {
				label.setBorder(BorderFactory.createLineBorder(Color.RED));
			}else{
				label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			}
			
			add(label);
		}
		
		reGenBtn=new JButton("重新生成");
		reGenBtn.setSize(80, 35);
		reGenBtn.setLocation(7*45+10,10);
		add(reGenBtn);
		
		reGenBtn.addActionListener(reGenBtnActionListener);
	}
	
	
	public void genColorBall(){
		reGenAllRedBalls();
		
		for (int i = 0; i < 6; i++) {
			labels[i].setText(allRedBalls.remove((int)(Math.random()*allRedBalls.size())).toString());
		}
		
		labels[6].setText(new Integer((int)(Math.random()*16)+1).toString());
	}
	
	public void reGenAllRedBalls(){
		allRedBalls.clear();
		for (int i = 1; i <= 33; i++) {
			allRedBalls.add(i);
		}
	}

}
