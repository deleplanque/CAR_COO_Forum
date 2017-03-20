package forum.UnitOfWork;

import forum.bean.data.Groupe;
import forum.bean.data.User;

public abstract class Visiteur {
	public void visiter(IDomainObject o) {
		o.accepter(this);
	}

	abstract public void visiter(User u);

	abstract public void visiter(Groupe g);
}
