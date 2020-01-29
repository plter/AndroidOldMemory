import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GenPasswd extends JFrame implements ActionListener {

	
	public static void main(String[] args) {
		
		new GenPasswd().setVisible(true);
		
	}
	
	
	public GenPasswd() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("密码生成工具");
		
		setSize(220, 100);
		
		btn=new JButton("生成密码");
		btn.setSize(100, 30);
		add(btn);
		btn.addActionListener(this);
		
		tf=new JTextField();
		tf.setSize(200, 30);
		tf.setLocation(0, 35);
		tf.setEditable(false);
		add(tf);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		char[] chars=new char[14];
		for (int i = 0; i < chars.length; i++) {
			chars[i]=getRandomChar();
		}
		
		tf.setText(new String(chars));
	}
	
	private char getRandomChar(){
		return WORDS.charAt((int) (Math.random()*WORDS.length()));
	}
	
	private JButton btn;
	private JTextField tf;
	private final String WORDS="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890@#%";
	
}
