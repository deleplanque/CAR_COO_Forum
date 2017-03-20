package forum.persistance.dao;

import java.util.HashSet;
import java.util.Set;

import forum.UnitOfWork.IDomainObject;
import forum.UnitOfWork.Observateur;
import forum.UnitOfWork.Visiteur;

public class UnitOfWork implements Observateur {
    Set<IDomainObject> dirty;
    static UnitOfWork inst = null;
    public UnitOfWork() {
        dirty = new HashSet<IDomainObject>();
    }
    public static UnitOfWork getInstance() {
        if (inst == null)
            inst = new UnitOfWork();
        return inst;
    }
    public void action(IDomainObject o) {
        // enregister l'objet dans la liste des objets modifies
        dirty.add(o);
    }
    public void commit() {
        Visiteur v = new Committer();
        for (IDomainObject o : dirty) {
            v.visiter(o);
        }
        dirty.clear();
    }
}