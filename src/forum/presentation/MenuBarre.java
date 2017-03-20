package forum.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import forum.bean.data.User;

public class MenuBarre extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7548405171149214283L;

	private final ArrayList<JMenu> menu = new ArrayList<JMenu>();
	private UserFrame mainFrame;
	private User u;

	public MenuBarre(UserFrame mainFrame, User u) {
		this.u=u;
		this.mainFrame=mainFrame;
		initJMenuBarre();
	}
	public void initJMenuBarre() {
		/*
		 * MENU
		 */
		int i;
		menu.add(new JMenu("Profil"));
		menu.add(new JMenu("Messagerie"));
		if(u.getRole().equals("admin")){
			menu.add(new JMenu("Admin"));
		}
		
		for (JMenu m : menu) {
			this.add(m);
		}

		/*
		 * MENU Profil
		 */
		i = 0;

		JMenuItem c =  new JMenuItem("Consulter le profil");
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setContentPane(new ProfilPanel(u));
				mainFrame.repaint();
				mainFrame.revalidate();
			}
		});
		c.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		menu.get(i).add(c);

		c = new JMenuItem("Gérer ses amis");
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setContentPane(new FriendPanel(u));
				mainFrame.repaint();
				mainFrame.revalidate();
			}
		});
		c.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));
		menu.get(i).add(c);

		menu.get(i).add(new JSeparator());

		c = new JMenuItem("Se déconnecter");
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectionFrame frame = new ConnectionFrame();
				try {
					System.out.println("User deconnected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				frame.setVisible(true);
				mainFrame.dispose();
			}
		});
		c.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		menu.get(i).add(c);

		/*
		 * MENU Messagerie
		 */
		i = 1;
		
		c = new JMenuItem("Consulter ses discussions");
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setContentPane(new MessagePanel(u));
				mainFrame.repaint();
				mainFrame.revalidate();
			}
		});
		c.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
		menu.get(i).add(c);

		c = new JMenuItem("Gérer ses groupes");
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setContentPane(new GroupPanel(u));
				mainFrame.repaint();
				mainFrame.revalidate();
			}
		});
		c.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		menu.get(i).add(c);
		
		/*
		 * MENU Messagerie
		 */
		if(u.getRole().equals("admin")){
			i = 2;

			c = new JMenuItem("Gérer les utilisateurs");
			c.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainFrame.setContentPane(new AdminPanel(u));
					mainFrame.repaint();
					mainFrame.revalidate();
				}
			});
			c.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
			menu.get(i).add(c);
		}
		
	}
}
