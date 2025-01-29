public class Lama extends Obstaculo {
    public Lama(Localizacao localizacao) {
        super(localizacao, "/Imagens/lama.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        formiga.setVelocidade(200);
    }
}