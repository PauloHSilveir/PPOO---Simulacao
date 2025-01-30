public abstract class Obstaculo extends ElementoTerreno {
    
    /**
     *  Construtor para objetos da classe Obstaculo.
     */
    public Obstaculo(Localizacao localizacao, String caminhoImagem) {
        super(localizacao, caminhoImagem);

    }
    
    /**
     * @return A localizacao do obstaculo.
     */
    public Localizacao getLocalizacao() {
        return super.getLocalizacao();
       }

    /**
     * Afeta a formiga que entrou no obstaculo.
     * @param formiga A formiga que entrou no obstaculo.
     */
    public abstract void afetarFormiga(Formiga formiga);

    /**
     * Retorna o tamanho do obstaculo.
     * @return O tamanho do obstaculo.
     */
    public abstract int[] getTamanho();
}