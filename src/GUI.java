import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;

public class GUI extends JFrame {

	private String[] ISPNames = { "Aya", "Tarassul", "Zad", "SCS", "Run net" };
	private JPanel contentPane;
	private JTextField FromTextBox;
	private JTextField ToTextBox;
	private JComboBox<String> MESTextBox;
	private JTextField SubjectTextBox;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JLabel lblMailExchangeServer;
	private JLabel lblSubject;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		;
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("b1.jpg"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 651);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel(new ImageIcon(myPicture));
		lblNewLabel.setBounds(0, 0, 884, 630);
		contentPane.add(lblNewLabel);

		final JTextPane BodyTextPane = new JTextPane();
		BodyTextPane.setBorder(BorderFactory.createLineBorder(new Color(128, 0,
				0), 4));
		lblNewLabel.add(BodyTextPane);
		BodyTextPane.setBounds(10, 308, 864, 227);

		ToTextBox = new JTextField();
		lblNewLabel.add(ToTextBox);
		ToTextBox.setBounds(168, 117, 164, 20);
		ToTextBox.setColumns(10);

		FromTextBox = new JTextField();
		lblNewLabel.add(FromTextBox);
		FromTextBox.setBounds(168, 87, 164, 20);
		FromTextBox.setColumns(10);

		MESTextBox = new JComboBox<String>(ISPNames);
		lblNewLabel.add(MESTextBox);
		MESTextBox.setBounds(168, 148, 164, 20);
		MESTextBox.setSelectedIndex(0);

		SubjectTextBox = new JTextField();
		lblNewLabel.add(SubjectTextBox);
		SubjectTextBox.setBounds(168, 179, 164, 20);
		SubjectTextBox.setColumns(10);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (FromTextBox.getText().isEmpty()
							|| ToTextBox.getText().isEmpty()
							|| SubjectTextBox.getText().isEmpty()
							|| BodyTextPane.getText().isEmpty()) {
						throw new MyException("Please fill the textboxes");
					}
					String smtpHost = "";
					if (MESTextBox.getItemAt(MESTextBox.getSelectedIndex())
							.equals("Aya")) {
						smtpHost = "smtp.aya.sy";
					} else if (MESTextBox.getItemAt(
							MESTextBox.getSelectedIndex()).equals("Tarassul")) {
						smtpHost = "mgw0" + new Random().nextInt(5)
								+ ".tarassul.sy";
					} else if (MESTextBox.getItemAt(
							MESTextBox.getSelectedIndex()).equals("Zad")) {
						smtpHost = "mail.zad.sy";
					} else if (MESTextBox.getItemAt(
							MESTextBox.getSelectedIndex()).equals("SCS")) {
						smtpHost = "smtpscs.scs-net.org";
					} else if (MESTextBox.getItemAt(
							MESTextBox.getSelectedIndex()).equals("Run net")) {
						smtpHost = "mail.runnet.sy";
					} else {
						smtpHost = "mgw0" + new Random().nextInt(5)
								+ ".tarassul.sy";
					}
					LogicSmtp lsmtp = new LogicSmtp(smtpHost, (short) 25);
					Message msg = new Message(FromTextBox.getText(), ToTextBox
							.getText(), SubjectTextBox.getText(), BodyTextPane
							.getText());
					lsmtp.sendMail(msg);
					JOptionPane.showMessageDialog(GUI.this,
							"Your message has been successfully sent",
							"Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (MyException exc) {
					JOptionPane.showMessageDialog(GUI.this, exc.msg);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(GUI.this, exc.getMessage());
				}
				// progressBar.setVisible(false);
				// progressBar.setIndeterminate(false);
			}
		});
		lblNewLabel.add(btnNewButton);
		btnNewButton.setBounds(168, 546, 89, 23);

		lblFrom = new JLabel("From");
		lblFrom.setForeground(new Color(128, 0, 0));
		lblFrom.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblFrom.setBounds(23, 89, 125, 14);
		lblNewLabel.add(lblFrom);

		lblTo = new JLabel("To");
		lblTo.setForeground(new Color(128, 0, 0));
		lblTo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTo.setBounds(23, 120, 125, 14);
		lblNewLabel.add(lblTo);

		lblMailExchangeServer = new JLabel("Your ISP");
		lblMailExchangeServer.setForeground(new Color(128, 0, 0));
		lblMailExchangeServer.setFont(new Font("Times New Roman", Font.PLAIN,
				15));
		lblMailExchangeServer.setBounds(23, 151, 135, 14);
		lblNewLabel.add(lblMailExchangeServer);

		lblSubject = new JLabel("Subject");
		lblSubject.setForeground(new Color(128, 0, 0));
		lblSubject.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblSubject.setBounds(23, 182, 125, 14);
		lblNewLabel.add(lblSubject);

		JLabel lblBody = new JLabel("Body");
		lblBody.setForeground(new Color(128, 0, 0));
		lblBody.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblBody.setBounds(10, 277, 64, 20);
		lblNewLabel.add(lblBody);

		JLabel lblSmtpClient = new JLabel("SMTP Client");
		lblSmtpClient.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblSmtpClient.setBounds(322, 11, 233, 34);
		lblNewLabel.add(lblSmtpClient);

		JButton button = new JButton("Clear Everything");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FromTextBox.setText("");
				ToTextBox.setText("");
				MESTextBox.setSelectedIndex(0);
				SubjectTextBox.setText("");
				BodyTextPane.setText("");
			}
		});
		lblNewLabel.add(button);
		button.setBounds(642, 543, 127, 23);
	}
}
