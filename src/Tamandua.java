
public class Tamandua extends Obstaculo {
    public Tamandua(Localizacao localizacao) {
        super(localizacao, "/Imagens/tamandua.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        formiga.setVisivel(false);
        formiga.setEstado("REMOVIDA");
        Simulacao.getEstatisticas().registrarAfetadaPorTamandua();
    }

    @Override
    public int[] getTamanho() {
        return new int[]{2, 2};
    }
}