package forum.bean.Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import forum.UnitOfWork.Observateur;
import forum.UnitOfWork.Visiteur;

public interface IUser extends Remote{
	public void accepter(Visiteur v) throws RemoteException;
	public void add(Observateur o) throws RemoteException;
	public void notifier() throws RemoteException;
}
