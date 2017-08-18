package app;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import db.DbConnection;
import oracle.jdbc.pool.OracleDataSource;

public class Panel{
	
	private JFrame frame;
	OracleDataSource ods = DbConnection.initConnection();
	Connection con = DbConnection.getConnection(ods);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Panel window = new Panel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public Panel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 800, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton bZawodnicy = new JButton();
		Image img1 = new ImageIcon(this.getClass().getResource("/player150.png")).getImage();
		bZawodnicy.setIcon(new ImageIcon(img1));
		bZawodnicy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Zawodnicy frame2 = null;
				try {
					frame2 = new Zawodnicy(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame2.setVisible(true);
			}		
		});
		bZawodnicy.setBounds(20, 20, 150, 150);
		frame.getContentPane().add(bZawodnicy);
		
		JLabel lZawodnicy = new JLabel("Zawodnicy");
		lZawodnicy.setBounds(20, 170, 150, 24);
		lZawodnicy.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lZawodnicy.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lZawodnicy);
		
		JButton bTrenerzy = new JButton();
		Image img2 = new ImageIcon(this.getClass().getResource("/coach150.png")).getImage();
		bTrenerzy.setIcon(new ImageIcon(img2));
		bTrenerzy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Trenerzy frame3 = null;
				try {
					frame3 = new Trenerzy(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame3.setVisible(true);
			}		
		});
		bTrenerzy.setBounds(20, 210, 150, 150);
		frame.getContentPane().add(bTrenerzy);
		
		JLabel lTrenerzy = new JLabel("Trenerzy");
		lTrenerzy.setBounds(20, 360, 150, 24);
		lTrenerzy.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lTrenerzy.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lTrenerzy);
		
		JButton bSedziowie = new JButton();
		Image img3 = new ImageIcon(this.getClass().getResource("/ref150.png")).getImage();
		bSedziowie.setIcon(new ImageIcon(img3));
		bSedziowie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Sedziowie frame4 = null;
				try {
					frame4 = new Sedziowie(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame4.setVisible(true);
			}		
		});
		bSedziowie.setBounds(20, 400, 150, 150);
		frame.getContentPane().add(bSedziowie);
		
		JLabel lSedziowie = new JLabel("Sêdziowie");
		lSedziowie.setBounds(20, 550, 150, 24);
		lSedziowie.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lSedziowie.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lSedziowie);
		
		JButton bDruzyny = new JButton();
		Image img4 = new ImageIcon(this.getClass().getResource("/team150.png")).getImage();
		bDruzyny.setIcon(new ImageIcon(img4));
		bDruzyny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Druzyny frame5 = null;
				try {
					frame5 = new Druzyny(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame5.setVisible(true);
			}		
		});
		bDruzyny.setBounds(200, 20, 150, 150);
		frame.getContentPane().add(bDruzyny);
		
		JLabel lDruzyny = new JLabel("Dru¿yny");
		lDruzyny.setBounds(200, 170, 150, 24);
		lDruzyny.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lDruzyny.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lDruzyny);
		
		JButton bStadiony = new JButton();
		Image img5 = new ImageIcon(this.getClass().getResource("/stadium150.png")).getImage();
		bStadiony.setIcon(new ImageIcon(img5));
		bStadiony.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Stadiony frame6 = null;
				try {
					frame6 = new Stadiony(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame6.setVisible(true);
			}		
		});
		bStadiony.setBounds(200, 210, 150, 150);
		frame.getContentPane().add(bStadiony);
		
		JLabel lStadiony = new JLabel("Stadiony");
		lStadiony.setBounds(200, 360, 150, 24);
		lStadiony.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lStadiony.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lStadiony);
		
		JButton bMecze = new JButton();
		Image img6 = new ImageIcon(this.getClass().getResource("/match150.png")).getImage();
		bMecze.setIcon(new ImageIcon(img6));
		bMecze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Mecze frame7 = null;
				try {
					frame7 = new Mecze(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame7.setVisible(true);
			}		
		});
		bMecze.setBounds(200, 400, 150, 150);
		frame.getContentPane().add(bMecze);
		
		JLabel lMecze = new JLabel("Mecze");
		lMecze.setBounds(200, 550, 150, 24);
		lMecze.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lMecze.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lMecze);
		
		JButton bSezony = new JButton();
		Image img7 = new ImageIcon(this.getClass().getResource("/season150.png")).getImage();
		bSezony.setIcon(new ImageIcon(img7));
		bSezony.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Sezony frame8 = null;
				try {
					frame8 = new Sezony(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame8.setVisible(true);
			}		
		});
		bSezony.setBounds(380, 20, 150, 150);
		frame.getContentPane().add(bSezony);
		
		JLabel lSezony = new JLabel("Sezony");
		lSezony.setBounds(380, 170, 150, 24);

		lSezony.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lSezony.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lSezony);
		
		JButton bUczestnicy = new JButton();
		Image img8 = new ImageIcon(this.getClass().getResource("/part150.png")).getImage();
		bUczestnicy.setIcon(new ImageIcon(img8));
		bUczestnicy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Uczestnicy frame9 = null;
				try {
					frame9 = new Uczestnicy(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame9.setVisible(true);
			}		
		});
		bUczestnicy.setBounds(380, 210, 150, 150);
		frame.getContentPane().add(bUczestnicy);
		
		JLabel lUczestnicy = new JLabel("Uczestnicy");
		lUczestnicy.setBounds(380, 360, 150, 24);
		lUczestnicy.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lUczestnicy.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lUczestnicy);
		
		JButton bTytuly = new JButton();
		Image img9 = new ImageIcon(this.getClass().getResource("/cup150.png")).getImage();
		bTytuly.setIcon(new ImageIcon(img9));
		bTytuly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Tytuly frame10 = null;
				try {
					frame10 = new Tytuly(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame10.setVisible(true);
			}		
		});
		bTytuly.setBounds(380, 400, 150, 150);
		frame.getContentPane().add(bTytuly);
		
		JLabel lTytuly = new JLabel("Tytu³y");
		lTytuly.setBounds(380, 550, 150, 24);
		lTytuly.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lTytuly.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lTytuly);
		
		JButton bKary = new JButton();
		Image img10 = new ImageIcon(this.getClass().getResource("/punish150.png")).getImage();
		bKary.setIcon(new ImageIcon(img10));
		bKary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Kary frame11 = null;
				try {
					frame11 = new Kary(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame11.setVisible(true);
			}		
		});
		bKary.setBounds(560, 20, 150, 150);
		frame.getContentPane().add(bKary);
		
		JLabel lKary = new JLabel("Kary");
		lKary.setBounds(560, 170, 150, 24);
		lKary.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lKary.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lKary);
		
		JButton bFunkcje = new JButton();
		Image img11 = new ImageIcon(this.getClass().getResource("/F150.png")).getImage();
		bFunkcje.setIcon(new ImageIcon(img11));
		bFunkcje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Funkcje frame12 = null;
				try {
					frame12 = new Funkcje(con);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame12.setVisible(true);
			}		
		});
		bFunkcje.setBounds(560, 210, 150, 150);
		frame.getContentPane().add(bFunkcje);
		
		JLabel lFunkcje = new JLabel("FUNKCJE");
		lFunkcje.setBounds(560, 360, 150, 24);
		lFunkcje.setForeground(Color.RED);
		lFunkcje.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lFunkcje.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lFunkcje);
		
		JButton bExit = new JButton();
		Image img12 = new ImageIcon(this.getClass().getResource("/exit150.png")).getImage();
		bExit.setIcon(new ImageIcon(img12));
		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}		
		});
		bExit.setBounds(560, 400, 150, 150);
		frame.getContentPane().add(bExit);
		
		JLabel lExit = new JLabel("Wyjœcie");
		lExit.setBounds(560, 550, 150, 24);
		lExit.setForeground(Color.RED);
		lExit.setFont(new Font("Sans Serif", Font.BOLD, 20));
		lExit.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lExit);
	}
	
	

}
