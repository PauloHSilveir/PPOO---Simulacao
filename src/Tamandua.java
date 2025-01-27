public class Tamandua extends Obstaculo {

    public Tamandua(Localizacao localizacao) {
        super(localizacao, "/imagens/tamandua.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        if (formiga.getLocalizacao().equals(super.getLocalizacao())) {
            System.out.println("A formiga encontrou um tamanduá e foi atrasada!");
            formiga.setVelocidade(formiga.getVelocidade() + 500); // Exemplo: reduz a velocidade pela metade
            // Faz a formiga permanecer na posição atual (parada) por um turno
            formiga.setLocalizacaoDestino(formiga.getLocalizacao());
        }
    }
}
