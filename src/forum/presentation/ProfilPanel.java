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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import forum.action.HobbyAction;
import forum.action.UserAction;
import forum.bean.data.Hobby;
import forum.bean.data.User;
import forum.persistance.dao.UnitOfWork;

public class ProfilPanel extends JPanel implements ListSelectionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2386033818253472772L;
	private User u;
	ArrayList<Hobby> hobbiesAction = new ArrayList<Hobby>();
	
	private Hobby hobbySelected;
	private boolean boolHobbySelected = false;
	
	JPanel Hobbies = new JPanel();
	JPanel UserHobbies = new JPanel();
	JPanel AllHobbies = new JPanel();
	
	ArrayList<Hobby> hobbiesList = new ArrayList<Hobby>();
	
	JList<Hobby> hobbies = new JList<Hobby>();
	DefaultListModel<Hobby> lmodel = new DefaultListModel<Hobby>();
	
	JList<Hobby> allHobbies = new JList<Hobby>();
	DefaultListModel<Hobby> l2model = new DefaultListModel<Hobby>();
	
	ProfilPanel(User u){
		this.u=u;
		
		lmodel();
		l2model();		
		
		GridLayout gl = new GridLayout(2, 1);
		this.setLayout(gl);
		this.add(infos());
		Hobbies = hobbies();
		this.add(Hobbies);
	}
	
	private void lmodel(){
		lmodel.clear();
		
		hobbiesList = UserAction.getInstance().getListHobbiesOfUser(u);
		
		for (int i = 0; i < hobbiesList.size(); i++) {
			lmodel.addElement(hobbiesList.get(i));
		}
		
		System.out.println(hobbiesList.size());
	}
	
	private void l2model() {
		l2model.clear();
		
		hobbiesAction = HobbyAction.getInstance().getOtherHobbies();
		
		for (int i = 0; i < hobbiesAction.size(); i++) {
			l2model.addElement(hobbiesAction.get(i));
		}
	}
	
	public JPanel infos(){
		JPanel result = new JPanel();
		GridLayout gl1 = new GridLayout(2, 1);
		result.setLayout(gl1);
		JPanel jp = new JPanel();
		GridLayout gl = new GridLayout(5, 2);
		jp.setLayout(gl);
		jp.add(new JLabel("Prenom : "));
		final JTextField prenom = new JTextField(u.getPrenom());
		jp.add(new JTextField(u.getPrenom()));
		jp.add(new JLabel("Nom : "));
		final JTextField nom = new JTextField(u.getNom());
		jp.add(new JTextField(u.getNom()));
		jp.add(new JLabel("Mail : "));
		final JTextField mail = new JTextField(u.getMail());
		jp.add(new JTextField(u.getMail()));
		jp.add(new JLabel("Mot de passe : "));
		final JPasswordField mdp = new JPasswordField(u.getPassword());
		jp.add(mdp);
		final JPasswordField cmdp = new JPasswordField(u.getPassword());
		jp.add(new JLabel("Confirmer le mdp : "));
		jp.add(cmdp);
		result.add(jp);
		JButton save = new JButton("Sauvegarder");
		System.out.println("u.getPassword : "+u.getPassword());
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mdp.getText().equals(cmdp.getText())){
					System.out.println("Sauvegarder un user");
					u.setPrenom(prenom.getText());
					u.setNom(nom.getText());
					u.setMail(mail.getText());
					u.setPassword(mdp.getText());
					UnitOfWork.getInstance().commit();
				}
			}
		});
		result.add(save);
		return result;
	}
	
	public JPanel hobbies(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		UserHobbies = userHobbies();
		result.add(UserHobbies);
		AllHobbies = allHobbies();
		result.add(AllHobbies);
		
		return result;
	}
	
	public JPanel userHobbies() {
		JPanel jpanel = new JPanel();
		if (u.getHobbies().size() > 0) {			
			hobbies.setModel(lmodel);
			hobbies.getSelectionModel().addListSelectionListener(this);
			hobbies.setPreferredSize(new Dimension(150, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(hobbies);

			JButton delete = new JButton("Supprimer");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(hobbySelected!=null && boolHobbySelected==true){
						System.out.println("Supprimer un hobby au user : " + hobbySelected.getNom());
						UserAction.getInstance().removeHobby(hobbySelected);
						lmodel();
						l2model();
						Hobbies.remove(UserHobbies);
						Hobbies.remove(AllHobbies);
						UserHobbies = userHobbies();
						AllHobbies = allHobbies();
						Hobbies.add(UserHobbies);
						Hobbies.add(AllHobbies);
						Hobbies.repaint();
						Hobbies.revalidate();
						hobbySelected=null;
					}
				}
			});
			
			jpanel.add(js);
			jpanel.add(delete);
		} else {
			jpanel.add(new JLabel("Vous n'avez pas 'hobby'"));
		}
		return jpanel;
	}
	
	public JPanel allHobbies() {
		JPanel jpanel = new JPanel();
		if (hobbiesAction.size() > 0) {
			JPanel jcenter = new JPanel();

			allHobbies.setModel(l2model);
			allHobbies.getSelectionModel().addListSelectionListener(this);
			allHobbies.setPreferredSize(new Dimension(150, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(allHobbies);
			jcenter.add(js);

			jpanel.add(jcenter);
			JButton add = new JButton("Ajouter");
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(hobbySelected!=null && boolHobbySelected==false){
						System.out.println("Ajouter un hobby au user : " + hobbySelected.getNom());
						UserAction.getInstance().addHobby(hobbySelected);
						lmodel();
						l2model();
						Hobbies.remove(UserHobbies);
						Hobbies.remove(AllHobbies);
						UserHobbies = userHobbies();
						AllHobbies = allHobbies();
						Hobbies.add(UserHobbies);
						Hobbies.add(AllHobbies);
						Hobbies.repaint();
						Hobbies.revalidate();
					}
				}
			});
			jpanel.add(add);
		} else {
			jpanel.add(new JLabel("Il n'y a plus de hobby disponible"));
		}
		return jpanel;
	}

	public void valueChanged(ListSelectionEvent e) {
		int debutIndex = hobbies.getSelectionModel().getMinSelectionIndex();
		int finIndex = hobbies.getSelectionModel().getMaxSelectionIndex();
		
		int debutIndexAll = allHobbies.getSelectionModel().getMinSelectionIndex();
		int finIndexAll = allHobbies.getSelectionModel().getMaxSelectionIndex();
		
		if(e.getValueIsAdjusting()){
			if (!hobbies.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndex; i <= finIndex; i++) {
					hob += hobbies.getModel().getElementAt(i).getNom();

				}
				hobbySelected = HobbyAction.getInstance().getHobbyByName(hob);
				boolHobbySelected = true;
				hobbies.getSelectionModel().clearSelection();
			}else if(!allHobbies.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndexAll; i <= finIndexAll; i++) {
					hob += allHobbies.getModel().getElementAt(i).getNom();
				}
				hobbySelected = HobbyAction.getInstance().getHobbyByName(hob);
				boolHobbySelected = false;
				allHobbies.getSelectionModel().clearSelection();
			}
		}
	}
	
}
