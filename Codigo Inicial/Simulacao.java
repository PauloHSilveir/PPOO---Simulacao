import java.util.Random;
/**
 * Responsavel pela simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    private Formiga formiga;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    
    public Simulacao() {
        Random rand = new Random(12345);
        mapa = new Mapa();
        int largura = mapa.getLargura();
        int altura = mapa.getAltura();
        formiga = new Formiga(new Localizacao(rand.nextInt(largura),rand.nextInt(altura)),"Imagens/formiga.png");//Cria um veiculo em uma posicao aleatoria
        formiga.setLocalizacaoDestino(new Localizacao(rand.nextInt(largura),rand.nextInt(altura)));//Define a posicao destino aleatoriamente
        mapa.adicionarItem(formiga);//Inicializando o mapa com o veículo
        janelaSimulacao = new JanelaSimulacao(mapa);
    }
    
    public void executarSimulacao(int numPassos){
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100);
        }        
    }

    private void executarUmPasso() {
        mapa.removerItem(formiga);
        formiga.executarAcao();
        mapa.adicionarItem(formiga);
        janelaSimulacao.executarAcao();
    }
    
    private void esperar(int milisegundos){
        try{
            Thread.sleep(milisegundos);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
}
