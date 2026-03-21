package catansim;

public interface Originator {
    Memento createMemento();
    void restore(Memento memento);
}