public class Lama extends Obstaculo {

    public Lama(Localizacao localizacao) {
        super(localizacao, "/imagens/lama.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        if (formiga.getLocalizacao().equals(getLocalizacao())) {
            System.out.println("A formiga entrou na lama e está mais lenta!");
            // Reduz a velocidade da formiga atrasando o próximo movimento
            //formiga.setVelocidade(formiga.getVelocidade() + 500);
        }
    }
}
