package forum.UnitOfWork;

interface Observable {
	void add(Observateur o);
    void notifier();
}
