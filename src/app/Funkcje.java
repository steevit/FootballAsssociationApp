package app;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;



public class Funkcje extends JFrame {
	
	private static final long serialVersionUID = 1L;

	Connection con = null;
	

	/**
	 * Create the application.
	 * @throws ClassNotFoundException 
	 */
	public Funkcje(Connection con1) throws ClassNotFoundException {
		con = con1;	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 250);
		this.getContentPane().setLayout(null);
		
		JButton bKary = new JButton();
		Image img1 = new ImageIcon(this.getClass().getResource("/F150.png")).getImage();
		bKary.setIcon(new ImageIcon(img1));
		bKary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				IleKar frame2 = null;
				try {
					frame2 = new IleKar(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame2.setVisible(true);
			}		
		});
		bKary.setBounds(20, 20, 150, 150);
		this.getContentPane().add(bKary);
		
		JLabel lKary = new JLabel("ILE_KAR");
		lKary.setBounds(20, 170, 150, 24);
		lKary.setForeground(Color.RED);
		lKary.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lKary.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lKary);
		
		JButton bTytuly = new JButton();
		Image img2 = new ImageIcon(this.getClass().getResource("/F150.png")).getImage();
		bTytuly.setIcon(new ImageIcon(img2));
		bTytuly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				CzyZdobylTytul frame3 = null;
				try {
					frame3 = new CzyZdobylTytul(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame3.setVisible(true);
			}		
		});
		bTytuly.setBounds(200, 20, 150, 150);
		this.getContentPane().add(bTytuly);
		
		JLabel lTytuly = new JLabel("CZY_ZDOBYL_TYTUL");
		lTytuly.setBounds(200, 170, 150, 24);
		lTytuly.setForeground(Color.RED);
		lTytuly.setFont(new Font("Sans Serif", Font.BOLD, 14));
		lTytuly.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lTytuly);
		
		JButton bMecze = new JButton();
		Image img4 = new ImageIcon(this.getClass().getResource("/F150.png")).getImage();
		bMecze.setIcon(new ImageIcon(img4));
		bMecze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				IleMeczy frame5 = null;
				try {
					frame5 = new IleMeczy(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame5.setVisible(true);
			}		
		});
		bMecze.setBounds(380, 20, 150, 150);
		this.getContentPane().add(bMecze);
		
		JLabel lMecze = new JLabel("ILE_MECZY");
		lMecze.setBounds(380, 170, 150, 24);
		lMecze.setForeground(Color.RED);
		lMecze.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lMecze.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lMecze);
	}
		
	
}
