package baitap;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import CanBo_test.canbo_2;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.security.auth.x500.X500Principal;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.security.spec.DSAGenParameterSpec;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class sinhvien1_jFrame<t> extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textMaSv;
	private JTextField textHoTen;
	private JTextField textGioiTinh;
	private JTextField textNgaySinh;
	private JTextField textDiachi;
	private JTextField textDiemTb;
	private JTextField textEmail;

	/**
	 * Launch the application.
	 */
	
	public ArrayList<student_text> studenList = new ArrayList<>();
	public student_text student_text = new student_text();
	public person person = new person();
	public DefaultTableModel model = new DefaultTableModel();
	private JTextField textTimKiem;
	protected int k;
	public static void main(String[] args)  {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sinhvien1_jFrame frame = new sinhvien1_jFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static Connection connection;
	//public static Date date = Date.valueOf(str);
	
	public void KetNoi() throws Exception{
		 //B1: KET NOI CSDL
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		System.out.println("da xac dinh HQTCSDL");
		//B2: KET NOI VAO CSDL
		String url ="jdbc:sqlserver://DESKTOP-5K500IR:1433;databaseName=Student;user=sa;password=123";
		connection =DriverManager.getConnection(url);
		System.out.println("DaKetNoi");
	}
	//KIEM TRA MA DA TON TAI CHUA
	public boolean ktraMa(String Ma) throws Exception{
		String sql ="SELECT * FROM sinhvien where MaSv=?";
		PreparedStatement pS = connection.prepareStatement(sql);
		pS.setString(1, Ma);
		ResultSet rSet = pS.executeQuery();
		boolean kq= rSet.next();
		rSet.close();
		return kq;
		
	}
	//THEM DU LIEU VAO SQL
	
	public int add_SQL(String MaSv, String HoTen, String GioiTinh,Date NgaySinh,String DiaChi, float DiemTB,String Email) throws SQLException {
		//TAO CAU LENH SQL
		String sql="INSERT INTO sinhvien(MaSv,HoTen,GioiTinh,NgaySinh,DiaChi,DiemTB,Email)" + "VALUES(?,?,?,?,?,?,?)";
		PreparedStatement pS = connection.prepareStatement(sql);
		pS.setString(1, MaSv);
		pS.setString(2, HoTen);
		pS.setString(3, GioiTinh);
		pS.setDate(4,NgaySinh);
		pS.setString(5, DiaChi);
		pS.setFloat(6, DiemTB);
		pS.setString(7, Email);
		return pS.executeUpdate();
		
	}
	public int upDATE_sql(String MaSv, String HoTen, String GioiTinh,Date NgaySinh,String DiaChi, float DiemTB,String Email) throws SQLException {
		String sql=" UPDATE sinhvien SET [MaSV]='?' ,[HoTen]='?',[GioiTinh]='?',[NgaySinh]='?',[DiaChi]='?',[DiemTB]='?',[Email]='?'";
		PreparedStatement pS = connection.prepareStatement(sql);
		pS.setString(1, MaSv);
		pS.setString(2, HoTen);
		pS.setString(3, GioiTinh);
		pS.setDate(4,NgaySinh);
		pS.setString(5, DiaChi);
		pS.setFloat(6, DiemTB);
		pS.setString(7, Email);
		return pS.executeUpdate();
		
	}
	//XOA SV TRONG SQL
	public void Delete_sql(String masv) throws SQLException {
		String sql="DELETE FROM sinhvien WHERE MaSV='";
		PreparedStatement pS = connection.prepareStatement(sql+masv+"'");
		System.out.println("Daxoa: " + masv);
		pS.execute();
	}
	//LUU DATABASE
	public void Luu_DB() throws Exception{
		for (student_text ds : studenList) {
			if(ktraMa(ds.getRollNo())==false && ds.getRollNo() != null) {
				add_SQL(ds.getRollNo(), ds.getName(), ds.getGender(), Date.valueOf(ds.getBirthday()), ds.getAddress(), ds.getMark(), ds.getEmail());
			}
				else {
					System.err.println(ds.getRollNo() + " DaCo!!!");
				}
			
		}
		System.err.println("da luu: " + studenList.size() );
	}
	
	public void ghiSv(ArrayList<student_text> list) {
		try{
			//Mở file ds.txt để ghi
			FileOutputStream f=new FileOutputStream("sinhvien.txt");
			OutputStreamWriter out =new OutputStreamWriter(f);
			PrintWriter pw= new PrintWriter(out);
			int n= list.size();
			for(int i=0;i<n;i++){ //Duyệt qua các sinh viên trong ds
			student_text s=(student_text)list.get(i);
			pw.println(s.getRollNo());
		    pw.println(s.getName());
			pw.println(s.getGender());
			pw.println(s.getBirthday());
			pw.println(s.getAddress());
			pw.println(s.getMark());
			pw.println(s.getEmail());
			
			}
			pw.close();//Đóng file
			
			}catch(Exception tt) {
				System.out.print("loi");
			}
	}
	public void luufile() {
		BufferedWriter ghi = null;
        FileWriter fw = null;
		try{
			//Mở file sinhvien.txt để ghi
			File file = new File("sinhvien.txt");
			
            // if file doesnt exists, then create it
			//neu chua co file , thi tao moi
            if (!file.exists()) {
                file.createNewFile();
            }
				fw = new FileWriter(file.getAbsoluteFile(), true);
	            ghi = new BufferedWriter(fw);
				int n1= studenList.size();
				for(int i=0;i<n1;i++){ //Duyệt qua các sinh viên trong ds
				student_text sv=(student_text)studenList.get(i);
				ghi.write(sv.getRollNo());
				ghi.newLine();
				
				ghi.write(sv.getName());//ghi vào filé
				ghi.newLine();//xuong dong
				ghi.write(sv.getGender());
				ghi.newLine();
				ghi.write(sv.getBirthday());
				ghi.newLine();
				ghi.write(sv.getAddress());
				ghi.newLine();			
				ghi.write(Float.toString(sv.getMark()));
				ghi.newLine();
				ghi.write(sv.getEmail());
				ghi.newLine();
				studenList.add(sv);
			}
			
			
			ghi.close();//Đóng file
			
			}catch(Exception tt) {
					System.out.print("loi");
				}
		
	}
	public void addRowtoJtable() {// them du lieu vao bang
		
		model = (DefaultTableModel) table.getModel();
		
		Object[] rowData= new Object[7];
		for(int i=0;i < studenList.size();i++){
			rowData[0]=studenList.get(i).rollNo;
			rowData[1]=studenList.get(i).name;
			rowData[2]=studenList.get(i).gender;
			rowData[3]=studenList.get(i).birthday;
			rowData[4]=studenList.get(i).address;
			rowData[5]=studenList.get(i).mark;
			rowData[6]=studenList.get(i).email;
			model.addRow(rowData);
			
		}
		
	}
	
	public sinhvien1_jFrame ()  {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					FileInputStream f = new FileInputStream("sinhvien.txt");
					InputStreamReader in = new InputStreamReader(f);
					BufferedReader read = new BufferedReader(in);
					studenList.clear();//Xóa dữ liệu trong ds
					do {
					String rollNo= read.readLine();// Đọc ra masv
					if (rollNo == null) //Nếu đã đọc hết thì thoát ra khỏi vòng lặp
					break;
					String name= read.readLine();// Đọc ra họ tên
					String gender= read.readLine();
					String birthday= read.readLine();
					String address= read.readLine();
					float mark= Float.parseFloat(read.readLine());//Đọc ra đtb
					String email= read.readLine();
					
					student_text sv = new student_text(name, gender, birthday, address, rollNo, mark, email);
							
					studenList.add(sv);//them vao mang
					
					} while (true);
					read.close();//Đóng file sinhvien.txt
				} catch (Exception e2) {
					// TODO: handle exception
				}
				for (int i = 0; i <studenList.size(); i++) {
					studenList.get(i).showIntro();
					}
				try {
					KetNoi();
					Luu_DB();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				addRowtoJtable();//them du lieu vao bang
				

				
				
				
			}
		});
		
		setForeground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 448);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(SystemColor.activeCaption);
		tabbedPane.setBounds(311, 23, 563, 312);
		contentPane.add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		tabbedPane.addTab("SinhVien", null, scrollPane, null);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i  = table.getSelectedRow();
				TableModel model = table.getModel();
				textMaSv.setText(model.getValueAt(i, 0).toString());
				textHoTen.setText(model.getValueAt(i, 1).toString());
				textGioiTinh.setText(model.getValueAt(i, 2).toString());
				textNgaySinh.setText(model.getValueAt(i, 3).toString());
				textDiachi.setText(model.getValueAt(i, 4).toString());
				textDiemTb.setText(model.getValueAt(i, 5).toString());
				textEmail.setText(model.getValueAt(i, 6).toString());
		
				
				
				
			}
		});
		table.setBackground(SystemColor.activeCaption);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"MaSv", "HoTen", "GioiTinh", "NgaySinh", "DiaChi", "DiemTB", "Email"
			}
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(112);
		table.getColumnModel().getColumn(2).setPreferredWidth(52);
		table.getColumnModel().getColumn(5).setPreferredWidth(45);
		table.getColumnModel().getColumn(6).setPreferredWidth(87);
		scrollPane.setViewportView(table);
		
		textMaSv = new JTextField();
		textMaSv.setBounds(94, 24, 144, 20);
		contentPane.add(textMaSv);
		textMaSv.setColumns(10);
		
		textHoTen = new JTextField();
		textHoTen.setBounds(94, 55, 144, 20);
		contentPane.add(textHoTen);
		textHoTen.setColumns(10);
		
		textGioiTinh = new JTextField();
		textGioiTinh.setColumns(10);
		textGioiTinh.setBounds(94, 92, 144, 20);
		contentPane.add(textGioiTinh);
		
		textNgaySinh = new JTextField();
		textNgaySinh.setColumns(10);
		textNgaySinh.setBounds(94, 123, 144, 20);
		contentPane.add(textNgaySinh);
		
		textDiachi = new JTextField();
		textDiachi.setColumns(10);
		textDiachi.setBounds(94, 154, 144, 20);
		contentPane.add(textDiachi);
		
		textDiemTb = new JTextField();
		textDiemTb.setColumns(10);
		textDiemTb.setBounds(94, 185, 144, 20);
		contentPane.add(textDiemTb);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(94, 217, 144, 20);
		contentPane.add(textEmail);
		
		JLabel lblMasv = new JLabel("MaSv");
		lblMasv.setBounds(22, 27, 46, 14);
		contentPane.add(lblMasv);
		
		JLabel lblHoten = new JLabel("HoTen");
		lblHoten.setBounds(22, 58, 46, 14);
		contentPane.add(lblHoten);
		
		JLabel lblGioitinh = new JLabel("GioiTinh");
		lblGioitinh.setBounds(22, 95, 46, 14);
		contentPane.add(lblGioitinh);
		
		JLabel lblDiachi = new JLabel("NgaySinh");
		lblDiachi.setBounds(22, 126, 46, 14);
		contentPane.add(lblDiachi);
		
		JLabel lblDiachi_1 = new JLabel("DiaChi");
		lblDiachi_1.setBounds(22, 157, 46, 14);
		contentPane.add(lblDiachi_1);
		
		JLabel lblDiemtb = new JLabel("DiemTB");
		lblDiemtb.setBounds(22, 188, 46, 14);
		contentPane.add(lblDiemtb);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(22, 220, 46, 14);
		contentPane.add(lblEmail);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					KetNoi();
					studenList.clear();
					
					student_text.setRollNo(textMaSv.getText());			
					student_text.setName(textHoTen.getText());
					student_text.setGender(textGioiTinh.getText());
					student_text.setBirthday(textNgaySinh.getText());
					student_text.setAddress(textDiachi.getText());
					student_text.setMark(Float.parseFloat(textDiemTb.getText()));
					student_text.setEmail(textEmail.getText());
					if(ktraMa(textMaSv.getText())==false){
						studenList.add(student_text);								
						addRowtoJtable();
						luufile();
						Luu_DB();
					}
					else
						 JOptionPane.showMessageDialog(null, "Sinh viên này đã có");
				} catch (Exception e) {
					// TODO: handle exception
				}
//			 	int ktra = 0;
//			 	student_text.setRollNo(textMaSv.getText());
//				int n = studenList.size();
//			 	for (student_text sv : studenList) {
//					
//					if (sv.getRollNo().equalsIgnoreCase(textMaSv.getText())) {
//						
//						ktra = 1;						
//						
//					}
//					else ktra=2;
//														
//				}
//				if (ktra == 2) {// nếu chưa có
//					studenList.clear();
//					
//					//student_text.setRollNo(textMaSv.getText());			
//					student_text.setName(textHoTen.getText());
//					student_text.setGender(textGioiTinh.getText());
//					student_text.setBirthday(textNgaySinh.getText());
//					student_text.setAddress(textDiachi.getText());
//					student_text.setMark(Float.parseFloat(textDiemTb.getText()));
//					student_text.setEmail(textEmail.getText());
//					
//					studenList.add(student_text);								
//					addRowtoJtable();
//					luufile();
//					try {
//						KetNoi();
//						Luu_DB();
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				} else
//					 JOptionPane.showMessageDialog(null, "Sinh viên này đã có");
//				     
//				    
			}
		});

		btnAdd.setBounds(10, 257, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnUpDate = new JButton("UpDate");
		btnUpDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i  = table.getSelectedRow();
				TableModel model = table.getModel();
				
				if(i>-1) {
					//model.setValueAt(textMaSv.getText(),i , 0);
					model.setValueAt(textHoTen.getText(),i , 1);
					model.setValueAt(textGioiTinh.getText(),i , 2);
					model.setValueAt(textNgaySinh.getText(),i , 3);
					model.setValueAt(textDiachi.getText(),i , 4);
					model.setValueAt(textDiemTb.getText(),i , 5);
					model.setValueAt(textEmail.getText(),i , 6);
							
					
				}

			}
		});
		btnUpDate.setBounds(116, 257, 89, 23);
		contentPane.add(btnUpDate);
		
		JButton btnDelete = new JButton("Delete");///xoa sinh vien
		btnDelete.addActionListener(new ActionListener() {
			private int aa;

			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				try {
                     int k = 0;
					aa = JOptionPane.showConfirmDialog(null, "BẠN CÓ MUỐN XÓA SINH VIÊN NÀY?");
					if (aa == JOptionPane.YES_OPTION) {
						DefaultTableModel model = new DefaultTableModel();
						model = (DefaultTableModel) table.getModel();
						int array[] = table.getSelectedRows();
						for (int i = 0; i < array.length; i++) {//xoa 1 dong tren jtable
							model.removeRow(array[i]);
							}
						k = 1;
						
					}
					

				} catch (Exception  e2) {
					System.out.println("2222 ");
				}
				if (aa == JOptionPane.YES_OPTION) {
					String delete_sv = textMaSv.getText();
					for (student_text student_text : studenList) {
						if (student_text.getRollNo().equalsIgnoreCase(delete_sv)) {
							studenList.remove(student_text);
							System.out.println("da xoa ");
							try {
								KetNoi();
								Delete_sql(delete_sv);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				            
							ghiSv(studenList);
//							for (int i = 0; i < studenList.size(); i++) {
//								studenList.get(i).showIntro();
//
//							}

						}

					}
				}
			}
		});
		btnDelete.setBounds(10, 291, 89, 23);
		contentPane.add(btnDelete);
		
		JButton btnSreach = new JButton("srearch");
		btnSreach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String RollnoSearch = textTimKiem.getText();
//				if(textTimKiem.getText() == " ") {
//					JOptionPane.showMessageDialog(null, "Chưa nhập mã sv");
//				}
//				else {
				int cout = 0;
				for (student_text student_text : studenList) { //vong for foreach su dung trong co so du lieu va mang
					if(student_text.getRollNo().equalsIgnoreCase(RollnoSearch)) {
						cout++;
						DefaultTableModel dm = (DefaultTableModel)table.getModel();
						while(dm.getRowCount() > 0)
						{
						    dm.removeRow(0);
						}
						student_text.showIntro();
						model = (DefaultTableModel) table.getModel();
						
						Object[] rowData= new Object[7];
						
							rowData[0]=student_text.rollNo;
							rowData[1]=student_text.name;
							rowData[2]=student_text.gender;
							rowData[3]=student_text.birthday;
							rowData[4]=student_text.address;
							rowData[5]=student_text.mark;
							rowData[6]=student_text.email;
							model.addRow(rowData);
//						addRowtoJtable(studenList);
						
						cout++;
					}
				}
				if(cout == 0)
					
				    JOptionPane.showMessageDialog(null, "Không có sinh viên nào");
			}
//			}
		});
		btnSreach.setBounds(10, 325, 89, 23);
		contentPane.add(btnSreach);
		
		textTimKiem = new JTextField();
		textTimKiem.setBounds(32, 361, 144, 20);
		contentPane.add(textTimKiem);
		textTimKiem.setColumns(10);
		
		JButton btnLoat = new JButton("Load");
		btnLoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				while(dm.getRowCount() > 0)
				{
				    dm.removeRow(0);
				}
				try {
					FileInputStream f = new FileInputStream("sinhvien.txt");
					InputStreamReader in = new InputStreamReader(f);
					BufferedReader read = new BufferedReader(in);
					studenList.clear();//Xóa dữ liệu trong ds
					do {
					String rollNo= read.readLine();// Đọc ra masv
					if (rollNo == null) //Nếu đã đọc hết thì thoát ra khỏi vòng lặp
					break;
					String name= read.readLine();// Đọc ra họ tên
					String gender= read.readLine();
					String birthday= read.readLine();
					String address= read.readLine();
					
					float mark= Float.parseFloat(read.readLine());//Đọc ra đtb
					String email= read.readLine();
					
					student_text sv1 = new student_text(name, gender, birthday, address, rollNo, mark, email);
							
					studenList.add(sv1);//them vao mang
					
					} while (true);
					read.close();//Đóng file sinhvien.txt
				} catch (Exception e2) {
					// TODO: handle exception
				}
//				for (int i = 0; i <studenList.size(); i++) {
//					studenList.get(i).showIntro();
//					
					
//				}
				addRowtoJtable();//them du lieu vao bang
				
				
			}
		});
		btnLoat.setBounds(116, 291, 89, 23);
		contentPane.add(btnLoat);
		
		JButton btnR = new JButton("Reset");
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textMaSv.setText("");
				textHoTen.setText("");
				textGioiTinh.setText("");
				textNgaySinh.setText("");
				textDiachi.setText("");
				textDiemTb.setText("");
				textEmail.setText("");
			}
		});
		btnR.setBounds(116, 325, 89, 23);
		contentPane.add(btnR);
	}
}
