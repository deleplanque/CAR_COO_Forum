package forum.persistance.dao;

import forum.UnitOfWork.Visiteur;
import forum.bean.data.Groupe;
import forum.bean.data.User;

public class Committer extends Visiteur {


	@Override
	public void visiter(User u) {
		 UserDAO.getInstance().update(u);
	}

	@Override
	public void visiter(Groupe g) {
		GroupDAO.getInstance().update(g);
		
	}

}
	
