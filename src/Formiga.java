import java.util.Random;

/**
 * Classe que representa uma formiga
 * 
 */
public class Formiga extends ElementoTerreno {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Formigueiro formigueiroDestino;

    private int velocidade = 1;
    private int id;
    private static int nextId = 1;
    private int tempoNoFormigueiro;
    private String estado;
    private boolean visivel;

    public Formiga(Localizacao localizacao) {
        super(localizacao, "Imagens/formigaFolha.png");
        this.id = nextId++;
        localizacaoAtual = localizacao;
        localizacaoDestino = null;
        formigueiroDestino = null;
        Random randNum = new Random();
        this.tempoNoFormigueiro = 1000 + randNum.nextInt(1000);
        this.estado = "MOVENDO";
        this.visivel = true;
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
        if ("REMOVIDA".equals(estado) || !isVisivel()) {
            return;
        }

        if ("NA_FILA".equals(estado)) {
            return;
        }

        if("PARADA".equals(estado)) {
            return;
        }

        if ("MOVENDO".equals(estado) && localizacaoDestino != null) {
            Localizacao proximaLocalizacao = localizacaoAtual.proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
    
            if (localizacaoAtual.equals(localizacaoDestino) && formigueiroDestino != null) {
                
                formigueiroDestino.entrar(this);
            } else {
                
                try {
                    Thread.sleep(velocidade);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    /**
     * Move e jogue para trás
     */
    @Override
    public String toString() {
        return "Formiga-" + id + " em " + getLocalizacao();
    }
}