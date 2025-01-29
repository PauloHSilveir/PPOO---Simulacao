import java.util.Random;

public class Vento extends Obstaculo {
    public Vento(Localizacao localizacao) {
        super(localizacao, "/Imagens/vento.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        // Empurra a formiga para uma direção aleatória
        Random rand = new Random();
        int deslocamentoX = rand.nextInt(3) - 5;
        int deslocamentoY = rand.nextInt(3) - 5;

        Localizacao atual = formiga.getLocalizacao();
        Localizacao nova = new Localizacao(
                Math.max(0, Math.min(34, atual.getX() + deslocamentoX)),
                Math.max(0, Math.min(34, atual.getY() + deslocamentoY))
        );

        formiga.setLocalizacaoAtual(nova);
        Simulacao.getEstatisticas().registrarAfetadaPorVento(formiga.getId());
    }

    @Override
    public int[] getTamanho() {
        return new int[]{1, 1};
    }
}