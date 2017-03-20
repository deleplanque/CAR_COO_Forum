package forum.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import forum.action.LogAction;
import forum.action.UserAction;
import forum.bean.data.User;


public class ConnectionFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1551307968051189232L;
	public static boolean joinDbBool = true;
	private static final Logger LOGGER = Logger.getLogger( ConnectionFrame.class.getName() );
	private static JTextField userAccount = new JTextField();
	private static JPasswordField userPassword = new JPasswordField();
	
	/**
	 * Création de la Frame d'identification composée de :
	 *  - Un panel pour le 'user account'
	 *  - Un panel pour le 'password'
	 *  - Le bouton de validation
	 * */
	public ConnectionFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(400,300);

        GridLayout gl = new GridLayout(3, 1);
		this.setLayout(gl);
		JPanel userAccountPanel = UserAccountPanel();
		//userAccountPanel.set
		this.add(userAccountPanel);
		JPanel userPasswordPanel = UserPasswordPanel();
		this.add(userPasswordPanel);
		JPanel button = ButtonPanel();
		this.add(button);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
	
	public JPanel UserAccountPanel(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		result.add(new JLabel("User account : "));
		result.add(userAccount);
		return result;
	}
	
	public JPanel UserPasswordPanel(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		result.add(new JLabel("password : "));
		result.add(userPassword);
		return result;
	}
	
	public JPanel ButtonPanel(){
		JPanel jpanel = new JPanel();
		JButton validate = new JButton("Ok");
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Bouton appuyé");
				//System.out.println("userAccount : " + userAccount.getText());
				//System.out.println("userPassword : " + userPassword.getText());
				
				if(!userAccount.getText().equals("") && !userPassword.getText().equals("")){
					if(LogAction.getInstance().connexion(userAccount.getText(), userPassword.getText())){
						User u = null;
						u = UserAction.getInstance().getUserByName(userAccount.getText());
						UserFrame frame = new UserFrame(u);
				        frame.setVisible(true);
				        userAccount.setText("");
				        userPassword.setText("");
				        dispose();
					}else{
						LOGGER.log(Level.SEVERE,"User and password don't match !");
					}
				}else if(userAccount.getText().equals("") && userPassword.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Fill your user account and your password");
				}else if(!userAccount.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Fill your password");
				}else if(!userPassword.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Fill your user account");
				}
			}
		});
		jpanel.add(validate);
		return jpanel;
	}
}
