public abstract class Obstaculo extends ElementoTerreno {
    
    public Obstaculo(Localizacao localizacao, String caminhoImagem) {
        super(localizacao, caminhoImagem);

    }
    
    public Localizacao getLocalizacao() {
        return super.getLocalizacao();
       }
    /*
    public Image getImagem() {
        return imagemObstaculo;
    }*/

    // Método abstrato para definir o comportamento do obstáculo
    public abstract void afetarFormiga(Formiga formiga);
}