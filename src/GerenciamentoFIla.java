public interface GerenciamentoFIla<T> {
    void enter(T item);
    void process(T item);
    void exit(T item);
}