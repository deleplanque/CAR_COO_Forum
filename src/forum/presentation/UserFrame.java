package forum.presentation;

import forum.bean.data.User;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3427302352163455760L;
	JPanel window;
	
	public UserFrame(User u) {
		this.setJMenuBar(new MenuBarre(this, u));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setVisible(true);
		this.setSize(1250, 750);
		this.setLocationRelativeTo(null);
		window = new ProfilPanel(u);
		this.add(window);
	}
	
}