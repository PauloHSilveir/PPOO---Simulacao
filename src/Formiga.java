import java.util.Random;
public class Formiga extends ElementoTerreno {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Formigueiro formigueiroDestino;

    private int velocidade = 50;
    private int tempoNoFormigueiro;
    
    private Formiga formigaAFrente;
    private final int id; // Identificador único para cada formiga
    private static int nextId = 1; // Contador para gerar IDs únicos
    private String estado; // "PARADA", "MOVENDO", "REMOVIDA", "AFETADA", "NA_FILA"
    private boolean visivel;

    public Formiga(Localizacao localizacao) {
        super(localizacao, "Imagens/formigaFolha.png");
        this.id = nextId++;
        localizacaoAtual = localizacao;
        localizacaoDestino = null;
        formigueiroDestino = null;
        Random randNum = new Random();
        this.tempoNoFormigueiro = 1000 + randNum.nextInt(1000);
        this.formigaAFrente = null;
        this.estado = "MOVENDO";
        this.visivel = true;
        System.out.println("[Formiga-" + id + "] Criada na posição " + localizacao);
    }

    public int getId() {
        return id;
    }

    @Override
    public Localizacao getLocalizacao() {
        return super.getLocalizacao();
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        if (localizacaoAtual != null) {
            super.setLocalizacao(localizacaoAtual);
            this.localizacaoAtual = localizacaoAtual;
            //System.out.println("[Formiga-" + getId() + "] Nova posição: " + localizacaoAtual);
        }
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    public void setFormigueiroDestino(Formigueiro formigueiroDestino) {
        this.formigueiroDestino = formigueiroDestino;
    }

    public void setTempoNoFormigueiro(int tempo) {
        this.tempoNoFormigueiro = tempo;
    }

    public int getTempoNoFormigueiro() {
        return tempoNoFormigueiro;
    }

    public void setFormigaAFrente(Formiga formiga) {
        this.formigaAFrente = formiga;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
        // Se estiver usando algum sistema de renderização, atualize a visibilidade
        if (!visivel) {
            System.out.println("[Formiga-" + getId() + "] Tornada invisível e removida da simulação");
        }
    }

    public boolean isVisivel() {
        return visivel;
    }

    public Formiga getFormigaAFrente() {
        return formigaAFrente;
    }

    public void setVelocidade(int novaVelocidade) {
        this.velocidade = novaVelocidade;
    }
    
    public int getVelocidade() {
        return this.velocidade;
    }
    
    public void executarAcao() {
        // If removed or invisible, do nothing
        if ("REMOVIDA".equals(estado) || !isVisivel()) {
            return;
        }

        // If in queue, maintain queue position
        if ("NA_FILA".equals(estado)) {
            // Don't move if in queue - position is managed by Formigueiro
            return;
        }

        if("PARADA".equals(estado)) {
            // Do nothing if stopped
            return;
        }

        // Normal movement when not in queue
        if ("MOVENDO".equals(estado) && localizacaoDestino != null) {
            Localizacao proximaLocalizacao = localizacaoAtual.proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
    
            // If reached formigueiro
            if (localizacaoAtual.equals(localizacaoDestino) && formigueiroDestino != null) {
                System.out.println("[Formiga-" + getId() + "] Chegou ao formigueiro");
                formigueiroDestino.entrar(this);
            } else {
                // Espera um tempo baseado na velocidade antes de se mover novamente
                try {
                    Thread.sleep(velocidade);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void moverParaFrente() {
        if ("NA_FILA".equals(estado)) {
            if (formigaAFrente != null) {
                Localizacao locFormigaFrente = formigaAFrente.getLocalizacao();
                setLocalizacaoAtual(new Localizacao(locFormigaFrente.getX(), locFormigaFrente.getY() + 1));
            }
        } else if (localizacaoAtual != null && localizacaoDestino != null) {
            Localizacao proximaLocalizacao = localizacaoAtual.proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
        }
    }

    @Override
    public String toString() {
        return "Formiga-" + id + " em " + getLocalizacao() +
                (formigaAFrente != null ? " (seguindo Formiga-" + formigaAFrente.getId() + ")" : "");
    }
}