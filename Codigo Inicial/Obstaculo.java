public abstract class Obstaculo extends ElementoTerreno{
    
    public Obstaculo(Localizacao localizacao) {
        super(localizacao, "imagemObstaculo.png");
        
    }
    
    public abstract void afetarFormiga(Formiga formiga);

    
}
