package app;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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


public class Uczestnicy extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JTextField tSID;
	private JTextField tTID;
	private JTextField tSearch;
	private JComboBox<String> cBoxSelect;
	private JComboBox<String> cBoxSort;
	Connection con = null;
	

	/**
	 * Create the application.
	 * @throws ClassNotFoundException 
	 */
	public Uczestnicy(Connection con1) throws ClassNotFoundException {
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
		scrollPane.setViewportView(table);
		
		
		JLabel lSID = new JLabel("ID Sezonu");
		lSID.setBounds(10, 130, 100, 20);
		contentPane.add(lSID);
		
		tSID = new JTextField();
		tSID.setBounds(110, 130, 115, 20);
		contentPane.add(tSID);
		tSID.setColumns(10);
		
		JLabel lTID = new JLabel("ID Dru¿yny");
		lTID.setBounds(10, 160, 100, 20);
		contentPane.add(lTID);
		
		tTID = new JTextField();
		tTID.setBounds(110, 160, 115, 20);
		contentPane.add(tTID);
		tTID.setColumns(10);
		
		
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
					String query = "{call P_UCZESTNICY.P_DODAJ(?,?)}";
					CallableStatement cst = con.prepareCall(query);
					cst.setString(1, tSID.getText());
					cst.setString(2, tTID.getText());
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
						String query = "{call P_UCZESTNICY.P_USUN("+tSID.getText()+","+tTID.getText()+")}";
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
					String query = "select u.id_sezonu, rok, u.id_druzyny, nazwa from Uczestnicy u, Sezony s, Druzyny d where u.id_sezonu=s.id_sezonu AND u.id_druzyny=d.id_druzyny AND "+selection+"=? ";
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
		cBoxSelect.setModel(new DefaultComboBoxModel<String>(new String[] {"u.id_sezonu", "rok", "u.id_druzyny", "nazwa"}));
		cBoxSelect.setBounds(304, 36, 160, 25);
		contentPane.add(cBoxSelect);
		
		JLabel lSort = new JLabel("Sortowanie po:");
		lSort.setBounds(20, 30, 160, 20);
		contentPane.add(lSort);
		
		cBoxSort = new JComboBox<String>();
		cBoxSort.setModel(new DefaultComboBoxModel<String>(new String[] {"u.id_sezonu", "rok", "u.id_druzyny", "nazwa"}));
		cBoxSort.setBounds(20, 55, 160, 25);
		contentPane.add(cBoxSort);
		cBoxSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String selection=(String)cBoxSort.getSelectedItem();
					String query = "select u.id_sezonu, rok, u.id_druzyny, nazwa from Uczestnicy u, Sezony s, Druzyny d where u.id_sezonu=s.id_sezonu AND u.id_druzyny=d.id_druzyny ORDER BY "+selection;
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
			String query = "select u.id_sezonu, rok, u.id_druzyny, nazwa from Uczestnicy u, Sezony s, Druzyny d where u.id_sezonu=s.id_sezonu AND u.id_druzyny=d.id_druzyny";
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
