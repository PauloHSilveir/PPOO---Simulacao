
public class Lama extends Obstaculo {

    public Lama(Localizacao localizacao) {
        super(localizacao, "/imagens/lama.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        // Reduz a velocidade da formiga
        formiga.setVelocidade(200); // Aumenta o delay do movimento
    }
}
