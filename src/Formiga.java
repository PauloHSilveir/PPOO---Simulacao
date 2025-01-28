import java.util.Random;
public class Formiga extends ElementoTerreno {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Formigueiro formigueiroDestino;
    private int velocidade;
    private int tempoNoFormigueiro;
    private Formiga formigaAFrente;
    private int id; // Identificador único para cada formiga
    private static int nextId = 1; // Contador para gerar IDs únicos
    private String estado; // "PARADA", "MOVENDO", "REMOVIDA"
    private boolean visivel;

    public Formiga(Localizacao localizacao) {
        super(localizacao, "Imagens/formiga.png");
        this.id = nextId++;
        localizacaoAtual = localizacao;
        localizacaoDestino = null;
        formigueiroDestino = null;
        this.velocidade = 50000;
        Random randNum = new Random();
        this.tempoNoFormigueiro = 1000 + randNum.nextInt(4000);
        this.formigaAFrente = null;
        this.estado = "MOVENDO"; // Por padrão, todas as formigas estão movendo
        this.visivel = true;
        System.out.println("[Formiga-" + id + "] Criada na posição " + localizacao);
    }

    public int getId() {
        return id;
    }

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
            System.out.println("[Formiga-" + getId() + "] Nova posição: " + localizacaoAtual);
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

    /*
     * public void executarAcao() {
     * if (formigaAFrente != null) {
     * Localizacao locFormigaFrente = formigaAFrente.getLocalizacao();
     * setLocalizacaoDestino(new Localizacao(locFormigaFrente.getX(),
     * locFormigaFrente.getY() + 1));
     * System.out.println("[Formiga-" + id + "] Seguindo Formiga-" +
     * formigaAFrente.getId() +
     * " para posição " + localizacaoDestino);
     * }
     * 
     * if (localizacaoDestino != null) {
     * Localizacao proximaLocalizacao =
     * localizacaoAtual.proximaLocalizacao(localizacaoDestino);
     * System.out.println("[Formiga-" + id + "] Movendo de " + localizacaoAtual +
     * " para " + proximaLocalizacao);
     * setLocalizacaoAtual(proximaLocalizacao);
     * 
     * if (localizacaoAtual.equals(localizacaoDestino) && formigaAFrente == null) {
     * if (formigueiroDestino != null) {
     * System.out.println("[Formiga-" + id + "] Tentando entrar no formigueiro");
     * formigueiroDestino.entrar(this);
     * }
     * }
     * }
     * }
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

        // Normal movement when not in queue
        if ("MOVENDO".equals(estado) && localizacaoDestino != null) {
            Localizacao proximaLocalizacao = localizacaoAtual.proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);

            // If reached formigueiro
            if (localizacaoAtual.equals(localizacaoDestino) && formigueiroDestino != null) {
                System.out.println("[Formiga-" + getId() + "] Chegou ao formigueiro");
                formigueiroDestino.entrar(this);
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