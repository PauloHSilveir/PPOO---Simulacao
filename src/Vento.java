import java.util.Random;

public class Vento extends Obstaculo {
    public Vento(Localizacao localizacao) {
        super(localizacao, "/imagens/vento.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
         // Empurra a formiga para uma direção aleatória
        Random rand = new Random();
        int deslocamentoX = rand.nextInt(3) - 1;
        int deslocamentoY = rand.nextInt(3) - 1;

        Localizacao atual = formiga.getLocalizacao();
        Localizacao nova = new Localizacao(
                Math.max(0, Math.min(34, atual.getX() + deslocamentoX)),
                Math.max(0, Math.min(34, atual.getY() + deslocamentoY))
        );

        formiga.setLocalizacaoAtual(nova);
    }
}