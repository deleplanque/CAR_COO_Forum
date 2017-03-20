package forum.presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import forum.action.LogAction;
import forum.action.UserAction;
import forum.bean.data.User;
import forum.persistance.dao.UnitOfWork;

public class AdminPanel extends JPanel implements ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2433081632363071228L;
	User u;
	User userSelected;
	
	JPanel adminPanel = new JPanel();
	JPanel usersUpdate = new JPanel();
	JPanel manageUser = new JPanel();
	
	ArrayList<User> userList = new ArrayList<User>();
	
	JList<User> users = new JList<User>();
	DefaultListModel<User> lmodel = new DefaultListModel<User>();
	
	public AdminPanel(User u) {
		this.u=u;
		
		GridLayout gl = new GridLayout(3, 1);
		adminPanel.setLayout(gl);
		adminPanel.add(createUser());
		adminPanel.add(new JSeparator());
		manageUser = manageUser();
		adminPanel.add(manageUser);
		this.add(adminPanel);
	}
	
	private void lmodel(){
		lmodel.clear();
		for (int i = 0; i < userList.size(); i++) {
			lmodel.addElement(userList.get(i));
		}
	}
	
	private JPanel createUser(){
		JPanel result = new JPanel();

		JPanel form = new JPanel();
		
		GridLayout gl = new GridLayout(6, 2);
		form.setLayout(gl);
		
		JLabel nameAccount = new JLabel("nom de compte : ");
		JLabel mail = new JLabel("mail : ");
		JLabel password = new JLabel("mot de passe : ");
		JLabel cPassword = new JLabel("confirmation mdp : ");
		JLabel lastname = new JLabel("nom");
		JLabel firstname = new JLabel("prenom");

		final JTextField nameAccountT = new JTextField();
		final JTextField mailT = new JTextField();
		final JTextField passwordT = new JTextField();
		final JTextField cPasswordT = new JTextField();
		final JTextField lastnameT = new JTextField();
		final JTextField firstnameT = new JTextField();
		
		form.add(nameAccount);form.add(nameAccountT);
		form.add(mail);form.add(mailT);
		form.add(password);form.add(passwordT);
		form.add(cPassword);form.add(cPasswordT);
		form.add(lastname);form.add(lastnameT);
		form.add(firstname);form.add(firstnameT);
		
		result.add(form);
		
		JButton inscription = new JButton("Creation");
		inscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameA = nameAccountT.getText();
				String ma = mailT.getText();
				String pwd = passwordT.getText();
				String cpwd = cPasswordT.getText();
				String ln = lastnameT.getText();
				String fn = firstnameT.getText();
				if(nameA!=null && ma!=null && pwd!=null && cpwd!=null && ln!=null && fn!=null && pwd.equals(cpwd)){
					System.out.println(LogAction.getInstance().inscription(nameA,ma,pwd,cpwd,ln,fn));
					adminPanel.remove(manageUser);
					manageUser = manageUser();
					adminPanel.add(manageUser);
					nameAccountT.setText("");mailT.setText("");passwordT.setText("");
					cPasswordT.setText("");lastnameT.setText("");firstnameT.setText("");
					adminPanel.repaint();
					adminPanel.revalidate();
				}
			}
		});
		
		result.add(inscription);
		
		return result;
	}
	
	private JPanel manageUser(){
		JPanel jpanel = new JPanel();
		
		userList = UserAction.getInstance().getAllUser();
		
		if (userList.size() > 0) {
			lmodel();
			users.setModel(lmodel);
			users.getSelectionModel().addListSelectionListener(this);
			users.setPreferredSize(new Dimension(150, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(users);
			
			jpanel.add(js);
			jpanel.add(usersUpdate);
		} else {
			jpanel.add(new JLabel("Pb de récupération des users"));
		}
		return jpanel;
	}

	private JPanel usersUpdate(){
		JPanel result = new JPanel();
		
		JPanel form = new JPanel();
		
		GridLayout gl = new GridLayout(6, 2);
		form.setLayout(gl);
		
		JLabel mail = new JLabel("mail : ");
		JLabel password = new JLabel("mot de passe : ");
		JLabel cPassword = new JLabel("confirmation mdp : ");
		JLabel lastname = new JLabel("nom");
		JLabel firstname = new JLabel("prenom");

		final JTextField mailT = new JTextField(userSelected.getMail());
		final JTextField passwordT = new JTextField(userSelected.getPassword());
		final JTextField cPasswordT = new JTextField(userSelected.getPassword());
		final JTextField lastnameT = new JTextField(userSelected.getNom());
		final JTextField firstnameT = new JTextField(userSelected.getPrenom());
		
		form.add(mail);form.add(mailT);
		form.add(password);form.add(passwordT);
		form.add(cPassword);form.add(cPasswordT);
		form.add(lastname);form.add(lastnameT);
		form.add(firstname);form.add(firstnameT);
		
		result.add(form);
		
		JPanel btn = new JPanel();
		btn.setLayout(new GridLayout(2, 1));
		
		JButton add = new JButton("Valider");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Modifier un user");
				if(mailT.getText()!=null && passwordT.getText()!=null && cPasswordT.getText()!=null
						 && passwordT.getText().equals(cPasswordT.getText()) && 
						 lastnameT.getText()!=null && firstnameT.getText()!=null){					
					userSelected.setPrenom(firstnameT.getText());
					userSelected.setNom(lastnameT.getText());
					userSelected.setMail(mailT.getText());
					userSelected.setPassword(passwordT.getText());
					UnitOfWork.getInstance().commit();
					
					mailT.setText("");
					passwordT.setText("");cPasswordT.setText("");
					lastnameT.setText("");firstnameT.setText("");
					
					userSelected = null;
					
					adminPanel.remove(manageUser);
					manageUser=manageUser();
					adminPanel.add(manageUser);
					adminPanel.repaint();
					adminPanel.revalidate();
				}
			}
		});
		
		btn.add(add);
		
		JButton delete = new JButton("Supprimer");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Supprimer un user");
				UserAction.getInstance().removeUser(userSelected);
				
				mailT.setText("");
				passwordT.setText("");cPasswordT.setText("");
				lastnameT.setText("");firstnameT.setText("");
				
				userSelected = null;
				
				adminPanel.remove(manageUser);
				manageUser=manageUser();
				adminPanel.add(manageUser);
				adminPanel.repaint();
				adminPanel.revalidate();
			}
		});
		btn.add(delete);
		result.add(btn);
		return result;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		int debutIndex = users.getSelectionModel().getMinSelectionIndex();
		int finIndex = users.getSelectionModel().getMaxSelectionIndex();
		
		if(e.getValueIsAdjusting()){
			if (!users.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndex; i <= finIndex; i++) {
					hob += users.getModel().getElementAt(i).getNomCompte();
				}
				userSelected = UserAction.getInstance().getUserByName(hob);
				if(usersUpdate!=null){
					manageUser.remove(usersUpdate);
				}
				usersUpdate = usersUpdate();
				manageUser.add(usersUpdate);
				manageUser.repaint();
				manageUser.revalidate();
				users.getSelectionModel().clearSelection();
			}
		}
	}
	
}
