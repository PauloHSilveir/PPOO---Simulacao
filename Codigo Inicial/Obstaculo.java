public abstract class Obstaculo extends ElementoTerreno{
    
    public Obstaculo(Localizacao localizacao) {
        super(localizacao, "veiculo.png");
        
    }
    
    public abstract void afetarFormiga(Formiga formiga);

    
}
