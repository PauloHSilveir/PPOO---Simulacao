public class Lama extends Obstaculo {
    /**
     * Construtor para objetos da classe Lama.
     * @param localizacao A localização da lama.
     */
    public Lama(Localizacao localizacao) {
        super(localizacao, "/Imagens/lama.png");
    }

    /**
     * Afeta a formiga que entrou na lama.
     * @param formiga A formiga que entrou na lama.
     */
    @Override
    public void afetarFormiga(Formiga formiga) {
        if(formiga.getEstado().equals("MOVENDO"))
            formiga.setEstado("PARADA");
        else
            formiga.setEstado("MOVENDO");
        Simulacao.getEstatisticas().registrarAfetadaPorLama(formiga.getId());
    }

    /**
     * Retorna o tamanho da lama.
     * @return O tamanho da lama.
     */
    @Override
    public int[] getTamanho() {
        return new int[]{3, 3};
    }
}