public class Lama extends Obstaculo {
    public Lama(Localizacao localizacao) {
        super(localizacao, "/Imagens/lama.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        if(formiga.getEstado().equals("MOVENDO"))
            formiga.setEstado("PARADA");
        else
            formiga.setEstado("MOVENDO");
        Simulacao.getEstatisticas().registrarAfetadaPorLama(formiga.getId());
    }

    @Override
    public int[] getTamanho() {
        return new int[]{3, 3};
    }
}