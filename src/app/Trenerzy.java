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


public class Trenerzy extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JLabel lID;
	private JTextField tID;
	private JTextField tName;
	private JTextField tSurname;
	private JTextField tNationality;
	private JTextField tBirthDate;
	private JTextField tSearch;
	private JComboBox<String> cBoxSelect;
	private JComboBox<String> cBoxSort;
	Connection con = null;
	

	/**
	 * Create the application.
	 * @throws ClassNotFoundException 
	 */
	public Trenerzy(Connection con1) throws ClassNotFoundException {
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
							"select id_trenera,imie,nazwisko,narodowosc,to_char(data_ur, 'YYYY-MM-DD') AS Data_urodzenia from Trenerzy where id_trenera='"+ID_+"'";
					PreparedStatement pst = con.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next())
					{
						tID.setText(rs.getString("id_trenera"));
						tName.setText(rs.getString("imie"));
						tSurname.setText(rs.getString("nazwisko"));
						tNationality.setText(rs.getString("narodowosc"));
						tBirthDate.setText(rs.getString("Data_urodzenia"));
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
		
		
		JLabel lName = new JLabel("Imiê");
		lName.setBounds(10, 130, 100, 20);
		contentPane.add(lName);
		
		tName = new JTextField();
		tName.setBounds(110, 130, 115, 20);
		contentPane.add(tName);
		tName.setColumns(10);
		
		JLabel lSurname = new JLabel("Nazwisko");
		lSurname.setBounds(10, 160, 100, 20);
		contentPane.add(lSurname);
		
		tSurname = new JTextField();
		tSurname.setBounds(110, 160, 115, 20);
		contentPane.add(tSurname);
		tSurname.setColumns(10);
		
		JLabel lNationality = new JLabel("Narodowoœæ");
		lNationality.setBounds(10, 190, 100, 20);
		contentPane.add(lNationality);
		
		tNationality = new JTextField();
		tNationality.setBounds(110, 190, 115, 20);
		contentPane.add(tNationality);
		tNationality.setColumns(10);
		
		JLabel lBirthDate = new JLabel("Data urodzenia");
		lBirthDate.setBounds(10, 220, 100, 20);
		contentPane.add(lBirthDate);
		
		tBirthDate = new JTextField();
		tBirthDate.setBounds(110, 220, 115, 20);
		contentPane.add(tBirthDate);
		tBirthDate.setColumns(10);
		
		
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
					String query = "{call P_TRENERZY.P_DODAJ(?,?,?,?)}";
					CallableStatement cst = con.prepareCall(query);
					cst.setString(1, tName.getText());
					cst.setString(2, tSurname.getText());
					cst.setString(3, tNationality.getText());
					cst.setDate(4, java.sql.Date.valueOf(tBirthDate.getText()));
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
		
		JButton btnUpdate = new JButton("Uaktualnij");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String query = "{call P_TRENERZY.P_EDYTUJ("+tID.getText()+",'"+tName.getText()+"','"+tSurname.getText()+"','"+tNationality.getText()+"','"+java.sql.Date.valueOf(tBirthDate.getText())+"')}";
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
		btnUpdate.setBounds(65, 365, 120, 25);
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
						String query = "{call P_TRENERZY.P_USUN("+tID.getText()+")}";
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
		btnDelete.setBounds(65, 400, 120, 25);
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
					String query = "select id_trenera, imie, nazwisko, narodowosc, to_char(data_ur, 'YYYY-MM-DD') AS Data_urodzenia from Trenerzy where "+selection+"=? ";
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
		cBoxSelect.setModel(new DefaultComboBoxModel<String>(new String[] {"id_trenera", "imie", "nazwisko", "narodowosc"}));
		cBoxSelect.setBounds(304, 36, 160, 25);
		contentPane.add(cBoxSelect);
		
		JLabel lSort = new JLabel("Sortowanie po:");
		lSort.setBounds(20, 30, 160, 20);
		contentPane.add(lSort);
		
		cBoxSort = new JComboBox<String>();
		cBoxSort.setModel(new DefaultComboBoxModel<String>(new String[] {"id_trenera", "imie", "nazwisko", "narodowosc","Data_urodzenia"}));
		cBoxSort.setBounds(20, 55, 160, 25);
		contentPane.add(cBoxSort);
		cBoxSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String selection=(String)cBoxSort.getSelectedItem();
					String query = "select id_trenera, imie, nazwisko, narodowosc, to_char(data_ur, 'YYYY-MM-DD') AS Data_urodzenia from Trenerzy ORDER BY "+selection;
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
			String query = "select id_trenera, imie, nazwisko, narodowosc, to_char(data_ur, 'YYYY-MM-DD') AS Data_urodzenia from Trenerzy";
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
