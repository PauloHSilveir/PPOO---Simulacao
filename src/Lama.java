public class Lama extends Obstaculo {
    public Lama(Localizacao localizacao) {
        super(localizacao, "/imagens/lama.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        formiga.setVelocidade(200);
    }
}