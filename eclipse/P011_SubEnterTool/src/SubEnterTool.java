import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class SubEnterTool extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6348463344390438200L;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SubEnterTool().setVisible(true);
	}
	
	
	public SubEnterTool() {
		setTitle("去除回车符(\\n)工具");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		openFileBtn.setSize(100, 30);
		add(openFileBtn);
		
		ta.setLocation(0, 35);
		ta.setSize(400, 250);
		add(ta);
		
		openFileBtn.addActionListener(this);
	}
	
	private final JButton openFileBtn=new JButton("打开文件");
	private final TextArea ta=new TextArea();


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		JFileChooser jfc = new JFileChooser();
		
		if (arg0.getSource()==openFileBtn) {
			
			int resultCode=jfc.showOpenDialog(this);
			
			if (resultCode==JFileChooser.APPROVE_OPTION&&jfc.getSelectedFile()!=null) {
				if (jfc.getSelectedFile().exists()) {
					currentStrContent = getFileContent(jfc.getSelectedFile());
					currentStrContent=currentStrContent.replaceAll("\"", "\\\\\"");
					currentStrContent=currentStrContent.replaceAll("\n", "");
					
					ta.setText(currentStrContent);
				}
			}
		}
	}
	
	
	private String currentStrContent="";
	
	
	public String getFileContent(File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			return new String(bytes,"utf-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
