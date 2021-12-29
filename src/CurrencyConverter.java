
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CurrencyConverter {

	public static void main(String[] args) {
				
					MainPanel frame = new MainPanel();
					frame.setJMenuBar(frame.setupMenu());
					
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setTitle("CURRENCY CONVERTER");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:/Users/DELL/Desktop/APLab/CurrencyConverterFinal/src/currencyicon.png"));

					frame.setBounds(100, 100, 718, 424);
					frame.setLocation(300,130);
	}
}