public class Vento extends Obstaculo {

    public Vento(Localizacao localizacao) {
        super(localizacao, "/imagens/vento.png");
    }

    @Override
    public void afetarFormiga(Formiga formiga) {
        if (formiga.getLocalizacao().equals(super.getLocalizacao())) {
            System.out.println("A formiga foi empurrada pelo vento!");
            int x = formiga.getLocalizacao().getX();
            int y = formiga.getLocalizacao().getY() - 2; // Move 2 casas para trás
            formiga.setLocalizacaoAtual(new Localizacao(x, Math.max(0, y))); // Garante que não saia do mapa
        }
    }
}
