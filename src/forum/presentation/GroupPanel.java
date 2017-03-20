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

import forum.action.GroupAction;
import forum.action.UserAction;
import forum.bean.data.Groupe;
import forum.bean.data.User;

public class GroupPanel extends JPanel implements ListSelectionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6176875825653852085L;
	User u;
	Groupe groupSelected;
	User userSelected;
	Boolean boolUserSelected = true;
	
	JPanel mainPanel;
	JPanel manageGroup;
	JPanel Groups;
	JPanel usersForGroup;
	JPanel Users;
	JPanel otherUsers;
	
	ArrayList<Groupe> groupsList = new ArrayList<Groupe>();
	JList<Groupe> groups = new JList<Groupe>();
	DefaultListModel<Groupe> lmodel = new DefaultListModel<Groupe>();
	
	ArrayList<User> usersList = new ArrayList<User>();
	JList<User> users = new JList<User>();
	DefaultListModel<User> l2model = new DefaultListModel<User>();
	
	ArrayList<User> othersUsersList = new ArrayList<User>();
	JList<User> othersUsers = new JList<User>();
	DefaultListModel<User> l3model = new DefaultListModel<User>();
	
	GroupPanel(User u) {
		this.u=u;
		
		mainPanel = this;
		GridLayout gl = new GridLayout(3, 1);
		mainPanel.setLayout(gl);
		mainPanel.add(createGroup());
		mainPanel.add(new JSeparator());
		manageGroup = manageGroup();
		mainPanel.add(manageGroup);
	}
	
	private void lmodel(){
		lmodel.clear();
		if(u.getRole().equals("admin")){
			groupsList=GroupAction.getInstance().getListGroupe();
		}else{
			groupsList=UserAction.getInstance().getListGroupOfUser(u);
		}
		
		for(int i=0;i<groupsList.size();i++){
			lmodel.addElement(groupsList.get(i));		
		}
	}
	
	private void l2model(){
		l2model.clear();
		usersList=GroupAction.getInstance().getListUserOfGroupById(groupSelected.getIdGroupe());
		for (int i = 0; i < usersList.size(); i++) {
			l2model.addElement(usersList.get(i));
		}
	}
	
	private void l3model(){
		l3model.clear();
		othersUsersList = GroupAction.getInstance().getFreeUserForGroup(groupSelected);
		
		for (int i = 0; i < othersUsersList.size(); i++) {
			l3model.addElement(othersUsersList.get(i));
		}
	}

	private JPanel createGroup(){
		JPanel result = new JPanel();

		JPanel form = new JPanel();
		
		GridLayout gl = new GridLayout(6, 2);
		form.setLayout(gl);
		
		JLabel nameAccount = new JLabel("nom de groupe : ");

		final JTextField nameAccountT = new JTextField();
		
		form.add(nameAccount);form.add(nameAccountT);
		
		result.add(form);
		
		JButton inscription = new JButton("Creation");
		inscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameA = nameAccountT.getText();
				if(nameA!=null){
					nameAccountT.setText("");
					GroupAction.getInstance().createGroupe(nameA, u);
					mainPanel.remove(manageGroup);
					manageGroup = manageGroup();
					mainPanel.add(manageGroup);
					mainPanel.repaint();
					mainPanel.revalidate();
				}
			}
		});
		
		result.add(inscription);
		
		return result;
	}
	
	private JPanel manageGroup(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		Groups = groups();
		result.add(Groups);
		usersForGroup = usersForGroup();
		result.add(usersForGroup);
		return result;
	}
	
	private JPanel groups(){
		JPanel result = new JPanel();
		
		lmodel();
		
		GridLayout gl = new GridLayout(2, 1);
		result.setLayout(gl);
		
		if (groupsList.size() > 0) {			
			groups.setModel(lmodel);
			groups.getSelectionModel().addListSelectionListener(this);
			groups.setPreferredSize(new Dimension(150, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(groups);

			JButton delete = new JButton("Supprimer");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Supprimer un group");
					GroupAction.getInstance().deleteGroup(groupSelected);
					lmodel();
					mainPanel.remove(manageGroup);
					manageGroup = manageGroup();
					mainPanel.add(manageGroup);
					
					mainPanel.repaint();
					mainPanel.revalidate();
				}
			});
			
			result.add(js);
			result.add(delete);
		} else {
			result.add(new JLabel("Vous n'avez pas de groupe"));
		}
		
		return result;
	}
	
	private JPanel usersForGroup(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		Users = Users();
		result.add(Users);
		otherUsers = otherUsers();
		result.add(otherUsers);
		return result;
	}
	
	private JPanel Users(){
		JPanel result = new JPanel();
		
		GridLayout gl = new GridLayout(2, 1);
		result.setLayout(gl);
		
		if (usersList.size() > 0) {			
			users.setModel(l2model);
			users.getSelectionModel().addListSelectionListener(this);
			users.setPreferredSize(new Dimension(150, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(users);

			JButton add = new JButton("Supprimer");
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Supprimer un participant");
					System.out.println(userSelected + " : " + groupSelected);
					if(boolUserSelected==true){
						GroupAction.getInstance().removeUserOfGroup(userSelected, groupSelected);
					}
					l2model();
					l3model();
					usersForGroup.remove(otherUsers);
					usersForGroup.remove(Users);
					otherUsers=otherUsers();
					Users = Users();
					usersForGroup.add(Users);
					usersForGroup.add(otherUsers);
					usersForGroup.repaint();
					usersForGroup.revalidate();
				}
			});
			
			result.add(js);
			result.add(add);
		} else {
			result.add(new JLabel("Choisissez un groupe"));
		}
		
		return result;
	}
	
	private JPanel otherUsers(){
		JPanel result = new JPanel();
		
		GridLayout gl = new GridLayout(2, 1);
		result.setLayout(gl);
		
		if (othersUsersList.size() > 0) {			
			othersUsers.setModel(l3model);
			othersUsers.getSelectionModel().addListSelectionListener(this);
			othersUsers.setPreferredSize(new Dimension(150, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(othersUsers);

			JButton add = new JButton("Ajouter");
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Ajouter un participant");
					System.out.println(userSelected + " : " + groupSelected);
					if(boolUserSelected==false){
						GroupAction.getInstance().addUserInGroup(userSelected, groupSelected);
					}
					l2model();
					l3model();
					usersForGroup.remove(otherUsers);
					usersForGroup.remove(Users);
					otherUsers=otherUsers();
					Users = Users();
					usersForGroup.add(Users);
					usersForGroup.add(otherUsers);
					usersForGroup.repaint();
					usersForGroup.revalidate();
				}
			});
			
			result.add(js);
			result.add(add);
		}
		
		return result;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		int debutIndex = groups.getSelectionModel().getMinSelectionIndex();
		int finIndex = groups.getSelectionModel().getMaxSelectionIndex();
		
		int debutIndexMembers = users.getSelectionModel().getMinSelectionIndex();
		int finIndexMembers = users.getSelectionModel().getMaxSelectionIndex();
		
		int debutIndexAll = othersUsers.getSelectionModel().getMinSelectionIndex();
		int finIndexAll = othersUsers.getSelectionModel().getMaxSelectionIndex();
		
		if(e.getValueIsAdjusting()){
			if (!groups.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndex; i <= finIndex; i++) {
					hob += groups.getModel().getElementAt(i).getIdGroupe();
				}
				int idGroup = Integer.parseInt(hob);
				groupSelected = GroupAction.getInstance().getGroupeById(idGroup);
				l2model();
				l3model();
				usersForGroup.remove(otherUsers);
				usersForGroup.remove(Users);
				otherUsers=otherUsers();
				Users = Users();
				usersForGroup.add(Users);
				usersForGroup.add(otherUsers);
				usersForGroup.repaint();
				usersForGroup.revalidate();
				groups.getSelectionModel().clearSelection();
			}else if(!users.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndexMembers; i <= finIndexMembers; i++) {
					hob += users.getModel().getElementAt(i).getNomCompte();
				}
				userSelected = UserAction.getInstance().getUserByName(hob);
				boolUserSelected = true;
				users.getSelectionModel().clearSelection();
			}else if(!othersUsers.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndexAll; i <= finIndexAll; i++) {
					hob += othersUsers.getModel().getElementAt(i).getNomCompte();
				}
				userSelected = UserAction.getInstance().getUserByName(hob);
				boolUserSelected = false;
				othersUsers.getSelectionModel().clearSelection();
			}
		}
	}
}
