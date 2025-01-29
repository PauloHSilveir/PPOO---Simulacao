
public class Tamandua extends Obstaculo {

    public Tamandua(Localizacao localizacao) {
        super(localizacao, "/imagens/tamandua.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        // O tamanduá remove a formiga da simulação
        formiga.setVisivel(false);
        formiga.setEstado("REMOVIDA");
    }
}
