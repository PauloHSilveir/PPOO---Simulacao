public class Lama extends Obstaculo {
    public Lama(Localizacao localizacao) {
        super(localizacao, "/Imagens/lama.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        int velocidadeAtual = formiga.getVelocidade();
        int novaVelocidade = velocidadeAtual + 10; // Reduz a velocidade em 5, mas não abaixo de 0
        formiga.setVelocidade(novaVelocidade);
        formiga.setEstado("AFETADA");
        //Simulacao.getEstatisticas().registrarAfetadaPorLama();
    }

    @Override
    public int[] getTamanho() {
        return new int[]{3, 3};
    }
}