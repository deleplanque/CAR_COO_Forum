package forum.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import forum.action.GroupAction;
import forum.action.UserAction;
import forum.bean.data.Groupe;
import forum.bean.data.Message;
import forum.bean.data.MessageAccuse;
import forum.bean.data.MessageChiffre;
import forum.bean.data.MessageDelai;
import forum.bean.data.MessageGroup;
import forum.bean.data.MessagePrioritaire;
import forum.bean.data.User;

public class MessagePanel extends JPanel implements ItemListener, ListSelectionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4438928042277078795L;

	User u;
	
	JCheckBox accuseButton;
    JCheckBox prioButton;
    JCheckBox chiffreButton;
    JCheckBox delaiButton;
    
    StringBuffer choices;
    
    JPanel listGroup = new JPanel();
    JPanel messageFor1Group = new JPanel();
    JPanel allMessagesFor1Group = new JPanel();
    
    Groupe groupSelected;
    
    ArrayList<MessageGroup> messagesList = new ArrayList<MessageGroup>();
    ArrayList<Groupe> groupList = new ArrayList<Groupe>();
    
    JList<Groupe> groups = new JList<Groupe>();
	DefaultListModel<Groupe> lmodel = new DefaultListModel<Groupe>();
	
	JList<MessageGroup> messages = new JList<MessageGroup>();
	DefaultListModel<MessageGroup> l2model = new DefaultListModel<MessageGroup>();

	/**
	 * Create the main Panel
	 * */
	MessagePanel(User u){
		this.u=u;
		listGroup = listGroup();
		this.add(listGroup);
	}
	
	private void lmodel(){
		lmodel.clear();
		groupList = UserAction.getInstance().getListGroupOfUser(u);
		if(UserAction.getInstance().getListGroupOfUser(u)!=null){
			for (int i = 0; i < groupList.size(); i++) {
				lmodel.addElement(groupList.get(i));
			}
		}
	}
	
	private void l2model(){
		l2model.clear();
		messagesList = GroupAction.getInstance().getMessageOfGroupe(groupSelected.getIdGroupe());
		if(UserAction.getInstance().getListGroupOfUser(u)!=null){
			for (int i = 0; i < messagesList.size(); i++) {
				l2model.addElement(messagesList.get(i));
			}
		}
	}
	
	private JPanel listGroup(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		
		lmodel();
		
		if(groupList!=null){
			if (groupList.size() > 0) {			
				groups.setModel(lmodel);
				groups.getSelectionModel().addListSelectionListener(this);
				groups.setPreferredSize(new Dimension(300, 300));
				JScrollPane js = new JScrollPane();
				js.getViewport().setView(groups);
				
				result.add(js);
				result.add(messageFor1Group);
				//result.add(messageGroup());
			} else {
				result.add(new JLabel("Vous n'avez pas de groupe"));
			}
		} else {
			result.add(new JLabel("Vous n'avez pas de groupe"));
		}
		
		return result;
	}
	
	/**
	 * Handle all the parts of messages for 1 group
	 * */
	private JPanel messageGroup(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(2, 1);
		result.setLayout(gl);
		JPanel messageType = messageType();
		result.add(messageType);
		allMessagesFor1Group = messagesOfGroup();
		result.add(allMessagesFor1Group);
		return result;
	}
	
	/**
	 * Handle all the messages of a group
	 * Faire une JList
	 * */
	private JPanel messagesOfGroup(){
		JPanel result = new JPanel();
		
		if (messagesList.size() > 0) {			
			messages.setModel(l2model);
			messages.getSelectionModel().addListSelectionListener(this);
			messages.setPreferredSize(new Dimension(600, 300));
			JScrollPane js = new JScrollPane();
			js.getViewport().setView(messages);
			
			result.add(js);
		} else {
			result.add(new JLabel("Ce groupe n'a pas de message"));
		}
		
		return result;
	}
	
	/**
	 * Specify the different types of a message, and handle the sending too. 
	 * */
	private JPanel messageType(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(2, 1);
		result.setLayout(gl);
		result.add(messageSend());
		result.add(generateBox());
		return result;
	}
	
	/**
	 * Handle the Textfield and the Button to send the message. 
	 * */
	private JPanel messageSend(){
		JPanel result = new JPanel();
		GridLayout gl = new GridLayout(1, 2);
		result.setLayout(gl);
		final JTextField message = new JTextField();
		result.add(message);
		JButton send = new JButton("Envoyer");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message currentMessage = new MessageGroup(message.getText(),groupSelected);
				System.out.println(message.getText());
				if(choices.substring(0, 1).equals('a')){
					currentMessage = new MessageAccuse(currentMessage);
				}
				if(choices.substring(1, 2).equals('p')){
					currentMessage = new MessagePrioritaire(currentMessage);
				}
				if(choices.substring(2, 3).equals('c')){
					currentMessage = new MessageChiffre(currentMessage);
				}
				if(choices.substring(3, 4).equals('d')){
					currentMessage = new MessageDelai(currentMessage,10);
				}
				
				GroupAction.getInstance().sendMessageGroup((MessageGroup) currentMessage);
				
				message.setText("");
				l2model();
				messageFor1Group.remove(allMessagesFor1Group);
				allMessagesFor1Group = messagesOfGroup();
				messageFor1Group.add(allMessagesFor1Group);
				messageFor1Group.repaint();
				messageFor1Group.revalidate();
			}
		});
		result.add(send);
		
		return result;
	}

	public void itemStateChanged(ItemEvent e) {
		int index = 0;
        char c = '-';
        Object source = e.getItemSelectable();

        if (source == accuseButton) {
            index = 0;
            c = 'a';
        } else if (source == prioButton) {
            index = 1;
            c = 'p';
        } else if (source == chiffreButton) {
            index = 2;
            c = 'c';
        } else if (source == delaiButton) {
            index = 3;
            c = 'd';
        }

        //Now that we know which button was pushed, find out
        //whether it was selected or deselected.
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            c = '-';
        }

        //Apply the change to the string.
        choices.setCharAt(index, c);
	}
	
    public JPanel generateBox() {
    	JPanel result = new JPanel();
    	
        //Create the check boxes.
        accuseButton = new JCheckBox("Accuse");
        accuseButton.setMnemonic(KeyEvent.VK_C);
        accuseButton.setSelected(true);

        prioButton = new JCheckBox("Prioritaire");
        prioButton.setMnemonic(KeyEvent.VK_G);
        prioButton.setSelected(true);

        chiffreButton = new JCheckBox("Chiffre");
        chiffreButton.setMnemonic(KeyEvent.VK_H);
        chiffreButton.setSelected(true);

        delaiButton = new JCheckBox("Delai");
        delaiButton.setMnemonic(KeyEvent.VK_T);
        delaiButton.setSelected(true);

        //Register a listener for the check boxes.
        accuseButton.addItemListener(this);
        prioButton.addItemListener(this);
        chiffreButton.addItemListener(this);
        delaiButton.addItemListener(this);

        //Indicates what's on the geek.
        choices = new StringBuffer("----");

        //Put the check boxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 4));
        checkPanel.add(accuseButton);
        checkPanel.add(prioButton);
        checkPanel.add(chiffreButton);
        checkPanel.add(delaiButton);

        result.add(checkPanel, BorderLayout.LINE_START);
        result.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        return result;
    }
  
	public void valueChanged(ListSelectionEvent e) {
		int debutIndex = groups.getSelectionModel().getMinSelectionIndex();
		int finIndex = groups.getSelectionModel().getMaxSelectionIndex();
		
		if(e.getValueIsAdjusting()){
			if (!groups.getSelectionModel().isSelectionEmpty()) {
				String hob = "";
				for (int i = debutIndex; i <= finIndex; i++) {
					hob += groups.getModel().getElementAt(i).getIdGroupe();
				}
				int idGroup = Integer.parseInt(hob);
				groupSelected = GroupAction.getInstance().getGroupeById(idGroup);
				//System.out.println(userSelected);
				if(messageFor1Group!=null){
					listGroup.remove(messageFor1Group);
				}
				l2model();
				messageFor1Group = messageGroup();
				listGroup.add(messageFor1Group);
				listGroup.repaint();
				listGroup.revalidate();
				groups.getSelectionModel().clearSelection();
			}
		}
	}	
}
