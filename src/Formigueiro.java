import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Formigueiro extends ElementoTerreno {
    private volatile boolean ocupado;
    private final Mapa mapa;
    private final Queue<Formiga> filaDeEspera;
    private final Localizacao localizacao;
    private final Lock lock;
    private Formiga ultimaFormigaNaFila;
    private final int id;
    private static int nextId = 1;
    private static final int ESPACAMENTO_FILA = 1; // Spacing between ants in queue
    private final int indiceFormigueiro;

    public Formigueiro(Localizacao localizacao, String imagem, Mapa mapa) {
        super(localizacao, imagem);
        this.id = nextId++;
        this.indiceFormigueiro = id - 1; // índice 0-based para estatísticas
        this.localizacao = localizacao;
        this.ocupado = false;
        this.filaDeEspera = new LinkedList<>();
        this.mapa = mapa;
        this.lock = new ReentrantLock();
        this.ultimaFormigaNaFila = null;
    }

    public int getTamanhoFila() {
        return filaDeEspera.size() + (ocupado ? 1 : 0);
    }
    
    private void sair(Formiga formiga) {
        if (formiga.getEstado().equals("REMOVIDA")) {
            return; // Ignora se já foi removida
        }

        System.out.println("[Formigueiro-" + id + "] Formiga-" + formiga.getId() + " saindo");

        formiga.setEstado("REMOVIDA");
        formiga.setVisivel(false);
        mapa.removerItem(formiga);
        formiga.setFormigaAFrente(null);

        // Registra a entrada da formiga nas estatísticas com o ID da formiga
        Simulacao.getEstatisticas().registrarEntradaFormigueiro(indiceFormigueiro, formiga.getId());

        lock.lock();
        try {
            if (!filaDeEspera.isEmpty()) {
                Formiga proximaFormiga = filaDeEspera.poll();
                proximaFormiga.setFormigaAFrente(null);
                proximaFormiga.setEstado("EM_ATENDIMENTO");
                atenderFormiga(proximaFormiga);
                atualizarPosicoesFila();
            } else {
                ocupado = false;
                ultimaFormigaNaFila = null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void entrar(Formiga formiga) {
        lock.lock();
        try {
            System.out.println("\n=== Estado do Formigueiro-" + id + " ===");
            System.out.println("Ocupado: " + ocupado);
            System.out.println("Tamanho da fila: " + filaDeEspera.size());
            if (!filaDeEspera.isEmpty()) {
                System.out.println("Formigas na fila: " + formatarFilaDeEspera());
            }
    
            // Se o formigueiro não estiver ocupado E não houver fila, entra direto
            if (!ocupado && filaDeEspera.isEmpty()) {
                System.out.println("[Formigueiro-" + id + "] Formiga-" + formiga.getId() +
                                   " entrando diretamente no formigueiro");
                ocupado = true;
                atenderFormiga(formiga);
            } 
            // Se estiver ocupado OU já houver fila, entra na fila
            else {
                System.out.println("[Formigueiro-" + id + "] Formiga-" + formiga.getId() +
                                   " entrando na fila de espera");
                posicionarNaFila(formiga);
                formiga.setEstado("PARADA"); // Define estado da formiga
            }
        } finally {
            lock.unlock();
        }
    }
     
    
    private void atenderFormiga(Formiga formiga) {
        System.out.println("[Formigueiro-" + id + "] Iniciando atendimento da Formiga-" + 
                          formiga.getId() + " por " + formiga.getTempoNoFormigueiro() + "ms");
        
        new Thread(() -> {
            try {
                // Simular o tempo de atendimento
                Thread.sleep(formiga.getTempoNoFormigueiro());
                
                lock.lock();
                try {
                    System.out.println("[Formigueiro-" + id + "] Finalizando atendimento da Formiga-" + 
                                     formiga.getId());
                    
                    // Remover e finalizar a formiga
                    sair(formiga);
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Formigueiro-" + id + "] Erro ao atender Formiga-" + 
                                 formiga.getId() + ": " + e.getMessage());
            }
        }).start();
    }
    
    
    /*private void sair(Formiga formiga) {
        System.out.println("[Formigueiro-" + id + "] Formiga-" + formiga.getId() + " saindo");
    
        // Remover do mapa
        mapa.removerItem(formiga);
        formiga.setFormigaAFrente(null);
        formiga.setEstado("REMOVIDA"); // Finaliza a formiga da simulação
    
        lock.lock();
        try {
            if (!filaDeEspera.isEmpty()) {
                Formiga proximaFormiga = filaDeEspera.poll();
                System.out.println("[Formigueiro-" + id + "] Próxima formiga da fila: Formiga-" + proximaFormiga.getId());
    
                proximaFormiga.setFormigaAFrente(null);
                proximaFormiga.setEstado("PARADA"); // Mantém parada até entrar
                atenderFormiga(proximaFormiga);
    
                atualizarReferenciasDaFila();
            } else {
                System.out.println("[Formigueiro-" + id + "] Fila vazia, formigueiro disponível");
                ocupado = false;
                ultimaFormigaNaFila = null;
            }
        } finally {
            lock.unlock();
        }
    } */  
    private void posicionarNaFila(Formiga formiga) {
        filaDeEspera.add(formiga);
        formiga.setEstado("NA_FILA");
        
        // Calculate the correct position in the queue
        int posicaoNaFila = filaDeEspera.size() - 1; // 0-based index
        Localizacao posicaoFila = calcularPosicaoNaFila(posicaoNaFila);
        
        System.out.println("[Formigueiro-" + id + "] Posicionando Formiga-" + formiga.getId() + 
                          " na posição " + posicaoFila + " da fila");
        
        // Update ant's position and destination
        formiga.setLocalizacaoAtual(posicaoFila);
        formiga.setLocalizacaoDestino(posicaoFila);
        
        // Set reference to ant in front
        if (posicaoNaFila > 0) {
            Formiga formigaFrente = null;
            for (Formiga f : filaDeEspera) {
                if (f != formiga) {
                    formigaFrente = f;
                    break;
                }
            }
            formiga.setFormigaAFrente(formigaFrente);
        }
        
        ultimaFormigaNaFila = formiga;
        System.out.println("[Formigueiro-" + id + "] Estado atual da fila: " + formatarFilaDeEspera());
    }
    
    private Localizacao calcularPosicaoNaFila(int posicaoNaFila) {
        // Calculate position based on formigueiro's location
        // Queue forms vertically downward from the formigueiro
        int xFila = getLocalizacao().getX();
        int yFila = getLocalizacao().getY() + ((posicaoNaFila) * ESPACAMENTO_FILA);
        
        return new Localizacao(xFila, yFila);
    }
    
    private void atualizarPosicoesFila() {
        int posicao = 0;
        for (Formiga formiga : filaDeEspera) {
            Localizacao novaPosicao = calcularPosicaoNaFila(posicao);
            formiga.setLocalizacaoAtual(novaPosicao);
            formiga.setLocalizacaoDestino(novaPosicao);
            posicao++;
        }
    }

    private String formatarFilaDeEspera() {
        StringBuilder sb = new StringBuilder();
        for (Formiga f : filaDeEspera) {
            if (sb.length() > 0) sb.append(" -> ");
            sb.append("Formiga-").append(f.getId());
        }
        return sb.toString();
    }

    private void atualizarReferenciasDaFila() {
        if (filaDeEspera.isEmpty()) {
            ultimaFormigaNaFila = null;
            return;
        }

        Formiga formigaAnterior = null;
        for (Formiga formiga : filaDeEspera) {
            formiga.setFormigaAFrente(formigaAnterior);
            formigaAnterior = formiga;
        }
        ultimaFormigaNaFila = formigaAnterior;
    }

    @Override
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public boolean isOcupped() {
        return ocupado;
    }

    private Localizacao getPosicaoAtrasDoFormigueiro() {
        return new Localizacao(localizacao.getX(), localizacao.getY() + 1);
    }

    private Localizacao getPosicaoAtrasDeOutraFormiga(Formiga formiga) {
        Localizacao localizacaoAtual = formiga.getLocalizacao();
        return new Localizacao(localizacaoAtual.getX(), localizacaoAtual.getY() + 1);
    }
}