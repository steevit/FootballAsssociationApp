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

public class Tytuly extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JLabel lID;
	private JTextField tID;
	private JTextField tTID1;
	private JTextField tTID2;
	private JTextField tSearch;
	private JComboBox<String> cBoxSelect;
	private JComboBox<String> cBoxSort;
	Connection con = null;
	

	/**
	 * Create the application.
	 * @throws ClassNotFoundException 
	 */
	public Tytuly(Connection con1) throws ClassNotFoundException {
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
							"select id_sezonu,id_druzyny_mistrzowskiej, id_druzyny_wicemistrzowskiej from Tytuly where id_sezonu='"+ID_+"'";
					PreparedStatement pst = con.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next())
					{
						tID.setText(rs.getString("id_sezonu"));
						tTID1.setText(rs.getString("id_druzyny_mistrzowskiej"));
						tTID2.setText(rs.getString("id_druzyny_wicemistrzowskiej"));
					}
					
					pst.close();
				} catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		lID = new JLabel("ID Sezonu");
		lID.setBounds(10, 130, 100, 20);
		contentPane.add(lID);
		lID.setVisible(true);
		
		tID = new JTextField();
		tID.setBounds(110, 130, 115, 20);
		contentPane.add(tID);
		tID.setColumns(10);
		tID.setVisible(true);
		
		
		JLabel lTID1 = new JLabel("ID Dru¿yny 1");
		lTID1.setBounds(10, 160, 100, 20);
		contentPane.add(lTID1);
		
		tTID1 = new JTextField();
		tTID1.setBounds(110, 160, 115, 20);
		contentPane.add(tTID1);
		tTID1.setColumns(10);
		
		JLabel lTID2 = new JLabel("ID Dru¿yny 2");
		lTID2.setBounds(10, 190, 100, 20);
		contentPane.add(lTID2);
		
		tTID2 = new JTextField();
		tTID2.setBounds(110, 190, 115, 20);
		contentPane.add(tTID2);
		tTID2.setColumns(10);
		
		
		JButton bRefresh = new JButton("Odœwie¿");
		bRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				refreshTable();
			}
		});
		bRefresh.setBounds(65, 295, 120, 25);
		contentPane.add(bRefresh);
		
		JButton bSave = new JButton("Zapisz");
		bSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String query = "{call P_TYTULY.P_DODAJ(?,?,?)}";
					CallableStatement cst = con.prepareCall(query);
					cst.setString(1, tID.getText());
					cst.setString(2, tTID1.getText());
					cst.setString(3, tTID2.getText());
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
		bSave.setBounds(65, 330, 120, 25);
		contentPane.add(bSave);
		
		
		JButton btnDelete = new JButton("Usuñ");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int action = JOptionPane.showConfirmDialog(null, "Czy napewno chcesz usun¹æ te dane?","Usuñ",JOptionPane.YES_NO_OPTION);
				if(action==0)
				{
					try 
					{
						String query = "{call P_TYTULY.P_USUN("+tID.getText()+")}";
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
		btnDelete.setBounds(65, 365, 120, 25);
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
					String query = "select t.id_sezonu, rok, id_druzyny_mistrzowskiej, d1.nazwa, id_druzyny_wicemistrzowskiej, d2.nazwa from Tytuly t, Sezony s, Druzyny d1, Druzyny d2 where t.id_sezonu=s.id_sezonu AND id_druzyny_mistrzowskiej=d1.id_druzyny AND id_druzyny_wicemistrzowskiej=d2.id_druzyny AND "+selection+"=? ";
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
		cBoxSelect.setModel(new DefaultComboBoxModel<String>(new String[] {"t.id_sezonu", "rok", "id_druzyny_mistrzowskiej", "d1.nazwa", "id_druzyny_wicemistrzowskiej", "d2.nazwa"}));
		cBoxSelect.setBounds(304, 36, 160, 25);
		contentPane.add(cBoxSelect);
		
		JLabel lSort = new JLabel("Sortowanie po:");
		lSort.setBounds(20, 30, 160, 20);
		contentPane.add(lSort);
		
		cBoxSort = new JComboBox<String>();
		cBoxSort.setModel(new DefaultComboBoxModel<String>(new String[] {"t.id_sezonu", "rok", "id_druzyny_mistrzowskiej", "d1.nazwa", "id_druzyny_wicemistrzowskiej", "d2.nazwa"}));
		cBoxSort.setBounds(20, 55, 160, 25);
		contentPane.add(cBoxSort);
		cBoxSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String selection=(String)cBoxSort.getSelectedItem();
					String query = "select t.id_sezonu, rok, id_druzyny_mistrzowskiej, d1.nazwa, id_druzyny_wicemistrzowskiej, d2.nazwa from Tytuly t, Sezony s, Druzyny d1, Druzyny d2 where t.id_sezonu=s.id_sezonu AND id_druzyny_mistrzowskiej=d1.id_druzyny AND id_druzyny_wicemistrzowskiej=d2.id_druzyny ORDER BY "+selection;
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
			String query = "select t.id_sezonu, rok, id_druzyny_mistrzowskiej, d1.nazwa, id_druzyny_wicemistrzowskiej, d2.nazwa from Tytuly t, Sezony s, Druzyny d1, Druzyny d2 where t.id_sezonu=s.id_sezonu AND id_druzyny_mistrzowskiej=d1.id_druzyny AND id_druzyny_wicemistrzowskiej=d2.id_druzyny";
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
