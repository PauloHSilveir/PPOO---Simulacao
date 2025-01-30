import java.util.Random;
public class Formiga extends ElementoTerreno {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Formigueiro formigueiroDestino;

    private int velocidade = 50;
    private int tempoNoFormigueiro;
    
    private Formiga formigaAFrente;
    private final int id; // ID único da formiga
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

    /**
     * Retorna o ID da formiga
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a localização atual da formiga
     * @return
     */
    @Override
    public Localizacao getLocalizacao() {
        return super.getLocalizacao();
    }

    /**
     * Retorna a localização atual da formiga
     * @return
     */
    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    /**
     * Retorna a localização atual da formiga
     * @return
     */
    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        if (localizacaoAtual != null) {
            super.setLocalizacao(localizacaoAtual);
            this.localizacaoAtual = localizacaoAtual;
            //System.out.println("[Formiga-" + getId() + "] Nova posição: " + localizacaoAtual);
        }
    }

    /**
     * Retorna a localização atual da formiga
     * @return
     */
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    /**
     * Retorna a localização atual da formiga
     * @return
     */
    public void setFormigueiroDestino(Formigueiro formigueiroDestino) {
        this.formigueiroDestino = formigueiroDestino;
    }

    /**
     * Retorna a localização atual da formiga
     * @return
     */
    public void setTempoNoFormigueiro(int tempo) {
        this.tempoNoFormigueiro = tempo;
    }

    /**
     * Retorna o Tempo que a formiga deve ficar no formigueiro
     * @return
     */
    public int getTempoNoFormigueiro() {
        return tempoNoFormigueiro;
    }

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public void setFormigaAFrente(Formiga formiga) {
        this.formigaAFrente = formiga;
    }

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
        // Se estiver usando algum sistema de renderização, atualize a visibilidade
        if (!visivel) {
            System.out.println("[Formiga-" + getId() + "] Tornada invisível e removida da simulação");
        }
    }

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public boolean isVisivel() {
        return visivel;
    }

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public Formiga getFormigaAFrente() {
        return formigaAFrente;
    }

    /**
     * Retorna a formiga que está na frente
     * @param novaVelocidade
     */
    public void setVelocidade(int novaVelocidade) {
        this.velocidade = novaVelocidade;
    }
    

    /**
     * Retorna a formiga que está na frente
     * @return
     */
    public int getVelocidade() {
        return this.velocidade;
    }
    

    /**
     * Retorna a formiga que está na frente
     * @return
     */
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

    /**
     * Move e jogue para frente
     */
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

    /**
     * Move e jogue para trás
     */
    @Override
    public String toString() {
        return "Formiga-" + id + " em " + getLocalizacao() +
                (formigaAFrente != null ? " (seguindo Formiga-" + formigaAFrente.getId() + ")" : "");
    }
}