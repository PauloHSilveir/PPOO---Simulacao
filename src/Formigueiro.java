import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Formigueiro extends ElementoTerreno {
    private volatile boolean ocupado;
    private final Mapa mapa;
    private final Queue<Formiga> fila;
    private final Localizacao localizacao;
    private final Lock lock;

    /**
     * Cria um novo formigueiro.
     * @param localizacao A localização do formigueiro.
     * @param imagem A imagem do formigueiro.
     * @param mapa O mapa onde o formigueiro está localizado.
     */
    public Formigueiro(Localizacao localizacao, String imagem, Mapa mapa) {
        super(localizacao, imagem);
        this.localizacao = localizacao;
        this.ocupado = false;
        this.fila = new LinkedList<>();
        this.mapa = mapa;
        this.lock = new ReentrantLock();
    }

    /**
     * Retorna a localização do formigueiro.
     * @return
     */
    @Override
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * Verifica se o formigueiro está ocupado.
     * @return
     */
    public boolean isOcupado() {
        return ocupado;
    }

    /**
     * Entra uma formiga no formigueiro.
     * @param formiga
     */
    public void entrar(Formiga formiga) {
        lock.lock();
        try {
            System.out.println("\n=== Estado do Formigueiro ===");
            System.out.println("Ocupado: " + ocupado);
            System.out.println("Tamanho da fila: " + fila.size());
    
            if (!isOcupado() && fila.isEmpty()) {
                ocupado = true;
                atenderFormiga(formiga);
            } 
            else {
                posicionarNaFila(formiga);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Atende uma formiga que está no formigueiro.
     * @param formiga
     */
    private void atenderFormiga(Formiga formiga) {
        new Thread(() -> {
            try {
                Thread.sleep(formiga.getTempoNoFormigueiro());
                lock.lock();
                try {
                    sair(formiga);
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

  
    private void posicionarNaFila(Formiga formiga) {
        fila.add(formiga);
        formiga.setEstado("NA_FILA");
        
        Localizacao posicaoFila = calcularPosicaoNaFila();
        formiga.setLocalizacaoAtual(posicaoFila);
    }
    
    /**
     * Calcula a posição da formiga na fila.
     * @return
     */
    private Localizacao calcularPosicaoNaFila() {
        // Posicionar a formiga na fila
        if(!fila.isEmpty()) {
            int xFila = getLocalizacao().getX();
            int yFila = getLocalizacao().getY() + (fila.size()-1);
            return new Localizacao(xFila, yFila);
        } else {
            return null;
        }
    }
    
    /**
     * Atualiza as posições das formigas na fila.
     */
    private void atualizarPosicoesFila() {
        for (Formiga formiga : fila) {
            Localizacao novaPosicao = calcularPosicaoNaFila();
            formiga.setLocalizacaoAtual(novaPosicao);
        }
    }
    
    /**
     * Remove a formiga do formigueiro.
     * @param formiga
     */
    private void sair(Formiga formiga) {
        
        formiga.setEstado("REMOVIDA");
        formiga.setVisivel(false);
        mapa.removerItem(formiga);
    
        lock.lock();
        try {
            if (!fila.isEmpty()) {

                Formiga proximaFormiga = fila.poll();
                proximaFormiga.setEstado("EM_ATENDIMENTO");
                atenderFormiga(proximaFormiga);
    
                atualizarPosicoesFila();
            } 
            else {
                System.out.println("Formigueiro disponivel");
                ocupado = false;
            }
        } finally {
            lock.unlock();
        }
    }
}