import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import javax.swing.UIManager;
import java.io.*;

public class MainPanel extends JFrame {

	// list of instance array variables
	private final static String[] list = { "Japanese yen (JPY)", "Euro (EUR)", "US Dollars (USD)", "Australian Dollars (AUD)",
			"Canadian Dollars (CAD)", "South Korean Won (KRW)", "Thai Baht (THB)",
			"United Arab Emirates Dirham (AED)" };
	private JPanel contentPane; // instance variable for containing all component
	private JButton Convertbtn; // instance variable for button
	private JButton Clearbtn; // instance variable for button
	private JLabel InputTitle; // instance jlabel variable for Text field title
	private JTextField textField; // instance variable for taking input data from user
	JLabel counter; // Instance variable for displaying conversion count
	int count = 0; // trace the conversion count
	private JCheckBox Reverse;
	private JLabel AmountTitle; //instance jlabel variable for result title
	private JLabel IndexTitle;
	private JComboBox<String> comboBox;
	private JLabel convertresult; //instance jlabel variable for display result
	private String[] name = new String[20]; //instance array variables for hold the currency name from file
	private String[] symbol = new String[20];//instance array variables for hold the currency symbol from file
	private double[] amount = new double [20];//instance array variables for hold the currency factor from file
	
	private int a=0;
	
	JMenuBar setupMenu() { // it contain menu and display them

		JMenuBar menuBar = new JMenuBar(); // object of menubar
		setJMenuBar(menuBar);

		JMenu file = new JMenu("File"); // creating a menu object file
		JMenu help = new JMenu("Help"); // creating a menu object help

		JMenuItem loaditem = new JMenuItem("Load");  // creating an sub menu/menu item object load
		// adding an icon into the load submenu/menuitem
		loaditem.setIcon(new ImageIcon("C:/Users/DELL/Desktop/APLab/CurrencyConverterFinal/src/load.png"));
		loaditem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		loaditem.addActionListener(new LoadListener());

		JMenuItem exititem = new JMenuItem("Exit");
		exititem.setIcon(new ImageIcon("C:/Users/DELL/Desktop/APLab/CurrencyConverterFinal/src/exiticon.png"));
		exititem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		JMenuItem aboutitem = new JMenuItem("About");
		aboutitem.setIcon(new ImageIcon("C:/Users/DELL/Desktop/APLab/CurrencyConverterFinal/src/abouticon.png"));	
		aboutitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		aboutitem.addActionListener(e -> {// lambda expression, it takes parameter and return the following value
			JOptionPane.showMessageDialog(this, "This is Currency Converter\nPound to various currencies and vice-verse\n@Narendra Roy 2020 ");
		});

		menuBar.add(file);
		menuBar.add(help);
		file.add(loaditem);
		file.add(exititem);
		help.add(aboutitem);

		return menuBar; // Return the menubar to converter class and it is created in frame

	}

	 MainPanel() {
		
		ActionListener listener = new ConvertListener();
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CURRENCY CONVERTER", TitledBorder.TRAILING, TitledBorder.BOTTOM, null,Color.BLUE));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<String>(list);
		comboBox.setToolTipText("SELECT CURRENCY");
		//comboBox.setBackground(Color.LIGHT_GRAY);
		comboBox.setBounds(418, 46, 241, 33);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(comboBox);
		
		//1st Panel
		JPanel currencypanel = new JPanel();
		currencypanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		currencypanel.setBounds(11, 11, 347, 156);
		currencypanel.setBackground(SystemColor.activeCaption);
		contentPane.add(currencypanel);
		currencypanel.setLayout(null);
		
		InputTitle = new JLabel("ENTER CURRENCY IN POUND");
		InputTitle.setBounds(70, 28, 354, 33);
		InputTitle.setFont(new Font("Source Sans Pro Semibold", Font.BOLD | Font.ITALIC, 20));
		currencypanel.add(InputTitle);
		
		textField = new JTextField();
		textField.setToolTipText("ENTER AMOUNT TO CONVERT");
		textField.setForeground(new Color(0, 0, 255));
		textField.setBounds(100, 61, 170, 27);
		textField.setFont(new Font("Tahoma", Font.BOLD, 20));
		currencypanel.add(textField);
		
		//3rd Panel
		JPanel dispanel = new JPanel();
		dispanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		dispanel.setBounds(11, 197, 347, 126);
		dispanel.setBackground(new Color(46, 139, 87));
		//panel_1.setBackground(Color.blue);
		contentPane.add(dispanel);
		dispanel.setLayout(null);
		
		AmountTitle = new JLabel("CONVERTED AMOUNT");
		AmountTitle.setForeground(Color.ORANGE);
		AmountTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		AmountTitle.setBounds(61, 0, 260, 33);
		dispanel.add(AmountTitle);
		
		convertresult = new JLabel("----");
		convertresult.setForeground(new Color(238, 130, 238));
		convertresult.setFont(new Font("Tahoma", Font.BOLD, 20));
		convertresult.setBounds(135, 28, 115, 33);
		dispanel.add(convertresult);
		
		counter = new JLabel(String.valueOf("Conversion Count: " + count));
		counter.setForeground(SystemColor.text);
		counter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		counter.setBounds(86, 75, 319, 33);
		dispanel.add(counter);
		
		
		IndexTitle = new JLabel("CONVERT INTO");
		IndexTitle.setBounds(468, 11, 355, 33);
		IndexTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(IndexTitle);
		
		
		Reverse = new JCheckBox("Reverse Conversion");
		Reverse.setToolTipText("CLICK HERE REVERSE CONVERSION");
		Reverse.setForeground(Color.BLACK);
		Reverse.setBackground(SystemColor.inactiveCaption);
		Reverse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Reverse.setBounds(100, 112, 170, 27);
		currencypanel.add(Reverse);
		
		Convertbtn = new JButton("CONVERT");
		Convertbtn.setToolTipText("CLICK HERE TO CONVERT");
		Convertbtn.addActionListener(listener);
		
		Convertbtn.setForeground(Color.blue);
		//btnNewButton.setBackground(new Color(85, 107, 47));
		Convertbtn.setBackground(Color.ORANGE);
		Convertbtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		Convertbtn.setBounds(382, 282, 143, 41);
		contentPane.add(Convertbtn);
		
		Clearbtn = new JButton("CLEAR");
		Clearbtn.setToolTipText("CLICK HERE TO RESET ALL FIELD");
		Clearbtn.setForeground(new Color(230, 230, 250));
		Clearbtn.setBackground(new Color(178, 34, 34));
		Clearbtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		Clearbtn.setBounds(552, 282, 143, 41);
		Clearbtn.addActionListener(e -> { //lamda expressions
			
			textField.setText(null); // clear textfield
			convertresult.setText("---");
			counter.setText("Conversion Count:");
			count = 0;
		});
		
		contentPane.add(Clearbtn);
	}
	
	private class ConvertListener implements ActionListener {

		@Override // using overriding feature for this method to be called from the parent class
		public void actionPerformed(ActionEvent event) {

			
			if((comboBox.getSelectedItem()).equals("error"))
			{
				JOptionPane.showMessageDialog(null, "Error found in that Index", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
			try {
				String text = textField.getText().trim(); // get value from the text field and remove whitespace

				// if the text field value is empty then the following code will be execute
				if (text.isEmpty() == false) {

					double value = Double.parseDouble(text); // convert the string value into double

					// the factor applied during the conversion
					double factor=0;	

					// if the reverse check box is not selected then the following code will be
					// executed
					if(a == 0)
						
					{
						String symbols = null;
						if (Reverse.isSelected() == false) {
						// Setup the correct factor/offset values depending on required conversion
						// combo box for select an index for converting the value
						switch (comboBox.getSelectedIndex()) {

						
						case 0://JPY
							factor = 137.52;
							symbols = "¥";
							break;
						
						case 1: // EURO
							factor = 1.09;
							symbols = "€";
							break;

						case 2: //USD
							factor = 1.29;
							symbols = "$";
							break;

						case 3://AUD
							factor = 1.78;
							symbols = "A$";
							break;

						case 4://CAD
							factor = 1.70;
							symbols = "C$";
							break;

						case 5: //KRW
							factor = 1537.75;
							symbols = "₩";
							break;

						case 6://THB
							factor = 40.52;
							symbols = "฿";
							break;

						case 7://AED
							factor = 4.75;
							symbols="إ د";
							break;
						
						}
					} else {
						switch (comboBox.getSelectedIndex()) {

						case 0: // JPY-POUND
							factor = 0.0072;
							symbols="£";
							break;

						case 1: // EURO-POUND
							factor = 0.91;
							symbols="£";
							break;

						case 2:// USD-POUND
							factor = 0.74;
							symbols="£";
							break;

						case 3:// AUD-POUND
							factor = 0.56;
							symbols="£";
							break;

						case 4: // CAD-POUND
							factor = 0.58;
							symbols="£";
							break;

						case 5:// KRW-POUND
							factor = 0.00067;
							symbols="£";
							break;

						case 6:// THB-POUND
							factor = 0.025;
							symbols="£";
							break;
							
						case 7:// AED-POUND
							factor = 0.20;
							symbols="£";
							break;
						}

					}
						double result = factor * value; // store calculation into result
						String finalresult = new DecimalFormat("0.00").format(result); // it display the result in decimal
																						// format
						convertresult.setText(symbols+finalresult);// display the decimal format result into label
						
					}
					else {
						
						if(Reverse.isSelected()) {
							factor = 1/amount[comboBox.getSelectedIndex()];
							double result = factor * value; // store calculation into result
						}
						else
							factor = amount[comboBox.getSelectedIndex()];
						
						double result = factor * value; // store calculation into result
						String finalresult = new DecimalFormat("0.00").format(result); // it display the result in decimal
																						// format
						convertresult.setText(symbol[comboBox.getSelectedIndex()]+finalresult);// display the decimal format result into label		
					}
					counter.setText("Conversion Count: " + Integer.toString(++count));
				} else // if the textfield is empty then it display message
				{
					JOptionPane.showMessageDialog(null, "Null Value is not accepted", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException a) {
				JOptionPane.showMessageDialog(null, "Please Enter Numeric Value"); // it display error message to user
			}
			}		
		}
			
		}
	
	private class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent x) {
			
			comboBox.removeAllItems();
			a=1;
			File file;
			JFileChooser jfc = new JFileChooser();
			int status = jfc.showOpenDialog(null);
			
			if(status ==JFileChooser.APPROVE_OPTION)
			{
				file = jfc.getSelectedFile();
				try
				{
					BufferedReader br = new BufferedReader(new FileReader(file));
					//Scanner scan = new Scanner(file);
					Object[] line = br.lines().toArray();
					
					//while(i<line.length)
					for(int i=0;i<line.length;i++)
					{
						String s = line[i].toString();
						String[]Break = s.split(",");
						if(Break.length == 3)
						{
							try 
							{
								amount[i] = Double.parseDouble(Break[1].trim());
								name[i] = Break[0].trim();
								symbol[i]= Break[2].trim();								
							}
							catch(NumberFormatException ex)
							{
								name[i] = "error";
							}
							
						}
						else {
							name[i] = "error";
						}
						comboBox.addItem(name[i]);
					}
				}
				catch(FileNotFoundException a)
				{
					
				}
			}						
		}
			
		}
	}
	

