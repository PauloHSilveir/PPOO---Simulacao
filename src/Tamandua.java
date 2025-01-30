public class Tamandua extends Obstaculo {
    /**
     * Construtor para objetos da classe Tamandua.
     * @param localizacao A localização do tamanduá.
     */
    public Tamandua(Localizacao localizacao) {
        super(localizacao, "/Imagens/tamandua.png");
    }

    /**
     * Afeta a formiga que entrou no tamanduá.
     * @param formiga A formiga que entrou no tamanduá.
     */
    @Override
    public void afetarFormiga(Formiga formiga) {
        formiga.setVisivel(false);
        formiga.setEstado("REMOVIDA");
        Simulacao.getEstatisticas().registrarAfetadaPorTamandua(formiga.getId());
    }

    /**
     * Retorna o tamanho do tamanduá.
     * @return O tamanho do tamanduá.
     */
    @Override
    public int[] getTamanho() {
        return new int[]{2, 2};
    }
}