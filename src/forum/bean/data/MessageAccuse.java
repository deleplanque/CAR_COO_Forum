package forum.bean.data;

import java.rmi.RemoteException;
import java.util.Date;

public class MessageAccuse extends DecorateurMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1615907822067848886L;

	public MessageAccuse(Message mess) throws RemoteException
    {
		message = mess;
		message.setWithAccuse(true);
    }

	@Override
	public int getIdMessage() {
		return message.getIdMessage();
	}

	@Override
	public Date getDateCreation() {
		return message.getDateCreation();
	}

	@Override
	public String getContenue() {
		return message.getContenue();
	}

	@Override
	public int getDelais() {
		return message.getDelais();
	}

	@Override
	public boolean isPrioritary() {
		return message.isPrioritary();
	}

	@Override
	public boolean isCrypt() {
		return message.isCrypt();
	}

	@Override
	public boolean isWithAccuse() {
		return message.isWithAccuse();
	}

	@Override
	public void setDelais(int delais) {
		message.setDelais(delais);		
	}

	@Override
	public void setPrioritary(boolean isPrioritary) {
		message.setPrioritary(isPrioritary);		
	}

	@Override
	public void setCrypt(boolean isCrypt) {
		message.setCrypt(isCrypt);
	}

	@Override
	public void setWithAccuse(boolean withAccuse) {
		message.setWithAccuse(withAccuse);		
	}
	


}
