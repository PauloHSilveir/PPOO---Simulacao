import java.util.Queue;
import java.util.LinkedList;

public class Formigueiro extends ElementoTerreno {
    private boolean ocupado;//atendendo uma formiga
    private Queue<Formiga> filaDeEspera;
    private Mapa mapa;

    public Formigueiro(Localizacao localizacao, Mapa mapa) {
        super(localizacao, "imagemFormigueiro.png");
        filaDeEspera = new LinkedList<Formiga>();
        this.mapa = mapa;
    }

    
}
