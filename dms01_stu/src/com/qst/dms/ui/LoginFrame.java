package com.qst.dms.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setBounds(52, 74, 58, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(140, 71, 214, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setBounds(52, 150, 58, 15);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 147, 214, 21);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				UserService userService = new UserService();
				user=userService.findUserByName(textField.getText());
				String pwd = new String( passwordField.getText());
				int n;
				if(user == null) {
					// ?????????????????????????????????
					n=JOptionPane.showConfirmDialog(null, 
							"?????????????????????????????????!", "????????????", JOptionPane.ERROR_MESSAGE);
					if(n==0) {
						new RegistFrame();
					}else {
						textField.setText("");
					}
				}else if(!user.getPassword().equals(pwd)) {
					// ???????????????????????????
					JOptionPane.showConfirmDialog(null, 
							"??????????????????????????????", "????????????", JOptionPane.ERROR_MESSAGE);
					//???????????????
					passwordField.setText("");
					
				}else {
					// ???????????????????????????????????????
					setVisible(false);
					new MainFrame();
				}
			}
		});
		btnNewButton.setBounds(171, 290, 97, 23);
		contentPane.add(btnNewButton);
		
		
		//????????????
		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegistFrame();
			}
		});
		btnNewButton_1.setBounds(31, 290, 97, 23);
		contentPane.add(btnNewButton_1);
		
		
		//??????
		JButton btnNewButton_2 = new JButton("\u91CD\u7F6E");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				passwordField.setText("");
			}
		});
		btnNewButton_2.setBounds(312, 290, 97, 23);
		contentPane.add(btnNewButton_2);
	}
}
