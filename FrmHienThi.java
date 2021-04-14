import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FrmHienThi extends JFrame {

	private JPanel contentPane;
	private JTextField txta;
	private JTextField txtb;
	private JTextField txtkq;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmHienThi frame = new FrmHienThi();
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
	public FrmHienThi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 289);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel("a=");
		lblUserName.setBounds(44, 42, 92, 16);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("b=");
		lblPassword.setBounds(44, 77, 78, 16);
		contentPane.add(lblPassword);
		
		JLabel lblKtQu = new JLabel("K\u1EBFt qu\u1EA3");
		lblKtQu.setBounds(41, 118, 56, 16);
		contentPane.add(lblKtQu);
		
		txta = new JTextField();
		txta.setBounds(113, 39, 291, 22);
		contentPane.add(txta);
		txta.setColumns(10);
		
		txtb = new JTextField();
		txtb.setBounds(113, 71, 291, 22);
		contentPane.add(txtb);
		txtb.setColumns(10);
		
		txtkq = new JTextField();
		txtkq.setBounds(112, 115, 292, 22);
		contentPane.add(txtkq);
		txtkq.setColumns(10);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Lấy giá trị của a, b
				int a=Integer.parseInt(txta.getText());
				int b=Integer.parseInt(txtb.getText());
				Integer s=a+b;
				txtkq.setText(s.toString());
			}
		});
		button.setBounds(60, 172, 97, 25);
		contentPane.add(button);
		
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Lấy giá trị của a, b
				int a=Integer.parseInt(txta.getText());
				int b=Integer.parseInt(txtb.getText());
				Integer s=a-b;
				txtkq.setText(s.toString());
			}
		});
		button_1.setBounds(174, 172, 97, 25);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("*");
		button_2.setBounds(290, 172, 97, 25);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("/");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Lấy giá trị của a, b
				int a=Integer.parseInt(txta.getText());
				int b=Integer.parseInt(txtb.getText());
				if(b==0)
					JOptionPane.showMessageDialog(null, "Về lớp 1 mà học");
				else
				{
				Double s=a/(double)b;
				txtkq.setText(s.toString());
				}
			}
		});
		button_3.setBounds(422, 172, 97, 25);
		contentPane.add(button_3);
	}
}
