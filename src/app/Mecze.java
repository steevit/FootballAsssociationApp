package app;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

public class Mecze extends JFrame {
	
	Connection con;
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JLabel lID;
	private JTextField tID;
	private JTextField tTeam1;
	private JTextField tTeam2;
	private JTextField tSID;
	private JTextField tDate;
	private JTextField tRID;
	private JTextField tZID;
	private JTextField tSearch;
	private JComboBox<String> cBoxSelect;
	private JComboBox<String> cBoxSort;

	
	/**
	 * Create the application.
	 * @throws ClassNotFoundException 
	 */
	public Mecze(Connection con1) throws ClassNotFoundException {	
		con = con1;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(255, 85, 700, 460);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				try 
				{
					int row = table.getSelectedRow();
					String ID_=(table.getModel().getValueAt(row, 0)).toString();
					
					String query = 
							"select id_meczu, id_druzyny1, id_druzyny2, id_stadionu, to_char(data, 'YYYY-MM-DD') AS Data, id_sedziego, id_sezonu  from Mecze where id_meczu='"+ID_+"'";
					PreparedStatement pst = con.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next())
					{
						tID.setText(rs.getString("id_meczu"));
						tTeam1.setText(rs.getString("id_druzyny1"));
						tTeam2.setText(rs.getString("id_druzyny2"));
						tSID.setText(rs.getString("id_stadionu"));
						tDate.setText(rs.getString("Data"));
						tRID.setText(rs.getString("id_sedziego"));
						tZID.setText(rs.getString("id_sezonu"));
					}
					
					pst.close();
				} catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		lID = new JLabel("ID");
		lID.setBounds(10, 100, 100, 20);
		contentPane.add(lID);
		lID.setVisible(false);
		
		tID = new JTextField();
		tID.setBounds(110, 100, 115, 20);
		contentPane.add(tID);
		tID.setColumns(10);
		tID.setVisible(false);
		
		JLabel lTeam1 = new JLabel("ID Dru¿yny 1");
		lTeam1.setBounds(10, 130, 100, 20);
		contentPane.add(lTeam1);
		
		tTeam1 = new JTextField();
		tTeam1.setBounds(110, 130, 115, 20);
		contentPane.add(tTeam1);
		tTeam1.setColumns(10);
		
		JLabel lTeam2 = new JLabel("ID Dru¿yny 2");
		lTeam2.setBounds(10, 160, 100, 20);
		contentPane.add(lTeam2);
		
		tTeam2 = new JTextField();
		tTeam2.setBounds(110, 160, 115, 20);
		contentPane.add(tTeam2);
		tTeam2.setColumns(10);
		
		JLabel lSID = new JLabel("ID Stadionu");
		lSID.setBounds(10, 190, 100, 20);
		contentPane.add(lSID);
		
		tSID = new JTextField();
		tSID.setBounds(110, 190, 115, 20);
		contentPane.add(tSID);
		tSID.setColumns(10);
		
		JLabel lDate = new JLabel("Data");
		lDate.setBounds(10, 220, 100, 20);
		contentPane.add(lDate);
		
		tDate = new JTextField();
		tDate.setBounds(110, 220, 115, 20);
		contentPane.add(tDate);
		tDate.setColumns(10);
		
		JLabel lRID = new JLabel("ID Sêdziego");
		lRID.setBounds(10, 250, 100, 20);
		contentPane.add(lRID);
		
		tRID = new JTextField();
		tRID.setBounds(110, 250, 115, 20);
		contentPane.add(tRID);
		tRID.setColumns(10);
		
		JLabel lZID = new JLabel("ID Sezonu");
		lZID.setBounds(10, 280, 100, 20);
		contentPane.add(lZID);
		
		tZID = new JTextField();
		tZID.setBounds(110, 280, 115, 20);
		contentPane.add(tZID);
		tZID.setColumns(10);
		
		JButton bRefresh = new JButton("Odœwie¿");
		bRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				refreshTable();
			}
		});
		bRefresh.setBounds(65, 330, 120, 25);
		contentPane.add(bRefresh);
		
		JButton bSave = new JButton("Zapisz");
		bSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String query = "{call P_MECZE.P_DODAJ(?,?,?,?,?,?)}";
					CallableStatement cst = con.prepareCall(query);
					cst.setInt(1, Integer.parseInt(tTeam1.getText()));
					cst.setInt(2, Integer.parseInt(tTeam2.getText()));
					cst.setInt(3, Integer.parseInt(tSID.getText()));
					cst.setDate(4, java.sql.Date.valueOf(tDate.getText()));
					cst.setInt(5, Integer.parseInt(tRID.getText()));
					cst.setInt(6, Integer.parseInt(tZID.getText()));
					cst.execute();
					
					JOptionPane.showMessageDialog(null, "Dane zapisane");
					
					cst.close();
				} catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				refreshTable();
			}
		});
		bSave.setBounds(65, 365, 120, 25);
		contentPane.add(bSave);
		
		JButton btnUpdate = new JButton("Uaktualnij");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String query = "{call P_MECZE.P_EDYTUJ("+tID.getText()+","+Integer.parseInt(tTeam1.getText())+","+Integer.parseInt(tTeam2.getText())+","+Integer.parseInt(tSID.getText())+",'"+java.sql.Date.valueOf(tDate.getText())+"',"+Integer.parseInt(tRID.getText())+","+Integer.parseInt(tZID.getText())+")}";
					CallableStatement cst = con.prepareCall(query);
					
					cst.execute();
					
					JOptionPane.showMessageDialog(null, "Dane uaktualnione");
					
					cst.close();
				} catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				refreshTable();
			}
		});
		btnUpdate.setBounds(65, 400, 120, 25);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Usuñ");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int action = JOptionPane.showConfirmDialog(null, "Czy napewno chcesz usun¹æ te dane?","Usuñ",JOptionPane.YES_NO_OPTION);
				if(action==0)
				{
					try 
					{
						String query = "{call P_MECZE.P_USUN("+tID.getText()+")}";
						CallableStatement cst = con.prepareCall(query);
						
						cst.execute();
						
						JOptionPane.showMessageDialog(null, "Dane usuniête");
						
						cst.close();
					} catch (Exception e1) 
					{
						e1.printStackTrace();
					}
					refreshTable();
				}
			}
		});
		btnDelete.setBounds(65, 435, 120, 25);
		contentPane.add(btnDelete);
		
		tSearch = new JTextField();
		tSearch.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyReleased(KeyEvent e) 
			{
				try 
				{
					String selection=(String)cBoxSelect.getSelectedItem();
					String query = "select id_meczu, id_druzyny1, d1.nazwa, id_druzyny2, d2.nazwa, m.id_stadionu, s.nazwa, to_char(data, 'YYYY-MM-DD') AS Data, m.id_sedziego, imie, nazwisko, m.id_sezonu, rok from Mecze m, Druzyny d1, Druzyny d2, Stadiony s, Sedziowie se, Sezony sez where m.id_druzyny1=d1.id_druzyny AND m.id_druzyny2=d2.id_druzyny AND m.id_stadionu=s.id_stadionu AND m.id_sedziego=se.id_sedziego AND m.id_sezonu=sez.id_sezonu AND "+selection+"=? ";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, tSearch.getText());
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));				
					pst.close();
				} catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		tSearch.setBounds(488, 36, 174, 25);
		contentPane.add(tSearch);
		tSearch.setColumns(10);
		
		cBoxSelect = new JComboBox<String>();
		cBoxSelect.setModel(new DefaultComboBoxModel<String>(new String[] {"id_meczu", "id_druzyny1", "d1.nazwa", "id_druzyny2", "d2.nazwa", "m.id_stadionu","s.nazwa", "m.id_sedziego", "imie", "nazwisko", "m.id_sezonu", "rok"}));
		cBoxSelect.setBounds(304, 36, 160, 25);
		contentPane.add(cBoxSelect);
		
		JLabel lSort = new JLabel("Sortowanie po:");
		lSort.setBounds(20, 30, 160, 20);
		contentPane.add(lSort);
		
		cBoxSort = new JComboBox<String>();
		cBoxSort.setModel(new DefaultComboBoxModel<String>(new String[] {"id_meczu", "id_druzyny1", "d1.nazwa", "id_druzyny2", "d2.nazwa", "m.id_stadionu","s.nazwa", "Data", "m.id_sedziego", "imie", "nazwisko", "m.id_sezonu", "rok"}));
		cBoxSort.setBounds(20, 55, 160, 25);
		contentPane.add(cBoxSort);
		cBoxSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String selection=(String)cBoxSort.getSelectedItem();
					String query = "select id_meczu, id_druzyny1, d1.nazwa, id_druzyny2, d2.nazwa, m.id_stadionu, s.nazwa, to_char(data, 'YYYY-MM-DD') AS Data, m.id_sedziego, imie, nazwisko, m.id_sezonu, rok from Mecze m, Druzyny d1, Druzyny d2, Stadiony s, Sedziowie se, Sezony sez where m.id_druzyny1=d1.id_druzyny AND m.id_druzyny2=d2.id_druzyny AND m.id_stadionu=s.id_stadionu AND m.id_sedziego=se.id_sedziego AND m.id_sezonu=sez.id_sezonu ORDER BY "+selection;
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
				}catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		
		refreshTable();
	}


	public void refreshTable()
	{
		try 
		{
			String query = "select id_meczu, id_druzyny1, d1.nazwa, id_druzyny2, d2.nazwa, m.id_stadionu, s.nazwa, to_char(data, 'YYYY-MM-DD') AS Data, m.id_sedziego, imie, nazwisko, m.id_sezonu, rok from Mecze m, Druzyny d1, Druzyny d2, Stadiony s, Sedziowie se, Sezony sez where m.id_druzyny1=d1.id_druzyny AND m.id_druzyny2=d2.id_druzyny AND m.id_stadionu=s.id_stadionu AND m.id_sedziego=se.id_sedziego AND m.id_sezonu=sez.id_sezonu";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		} catch (Exception e1) 
		{
			e1.printStackTrace();
		}
	}
	
}
