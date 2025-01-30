import java.util.Random;

public class Vento extends Obstaculo {
    /**
     * Construtor para objetos da classe Vento.
     * @param localizacao A localização do vento.
     */
    public Vento(Localizacao localizacao) {
        super(localizacao, "/Imagens/vento.png");
    }

    /**
     * Afeta a formiga que entrou no vento.
     * @param formiga A formiga que entrou no vento.
     */
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

    /**
     * Retorna o tamanho do vento.
     * @return O tamanho do vento.
     */
    @Override
    public int[] getTamanho() {
        return new int[]{1, 1};
    }
}