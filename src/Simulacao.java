import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Classe que representa a simulação do sistema.
 * A simulação é composta por formigas, obstáculos e formigueiros.
 */
public class Simulacao {
    private List<Formiga> formigas;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    private static EstatisticasSimulacao estatisticas;
    private Random rand = new Random();
    private int contadorPassos = 0;
    private static final int INTERVALO_SPAWN = 1; // Spawna a cada 1 passo
    private static final double CHANCE_SPAWN = 0.5; //Chance de Spawn
    private static final int MAX_FORMIGAS_POR_SPAWN = 3; // Máximo de formigas que podem aparecer a cada spawn
    
    
    public static EstatisticasSimulacao getEstatisticas() {
        return estatisticas;
    }
    
    /**
     * Cria uma nova simulação com formigas e obstáculos.
     */
    public Simulacao() {
        mapa = new Mapa();
        formigas = new ArrayList<>();
        estatisticas = new EstatisticasSimulacao(3);
        
        // Adicionar obstáculos iniciais (apenas uma vez no início)
        mapa.adicionarObstaculosAleatorios();
        
        // Criar e adicionar formigas iniciais
        spawnFormigas(10); // Começa com 10 formigas
        
        janelaSimulacao = new JanelaSimulacao(mapa);
    }

     // Novo método para spawnar formigas
     private void spawnFormigas(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            Localizacao localizacaoInicial;
            do {
                localizacaoInicial = new Localizacao(
                    rand.nextInt(mapa.getLargura()), 
                    rand.nextInt(mapa.getAltura())
                );
            } while (!isPosicaoValida(localizacaoInicial));
            
            Formiga formiga = new Formiga(localizacaoInicial);
            
            // Define o formigueiro mais próximo como destino
            if (!mapa.getFormigueiros().isEmpty()) {
                Formigueiro formigueiroDestino = encontrarFormigueiroMaisProximo(formiga);
                formiga.setLocalizacaoDestino(formigueiroDestino.getLocalizacao());
                formiga.setFormigueiroDestino(formigueiroDestino);
            }
            
            formigas.add(formiga);
            mapa.adicionarItem(formiga);
        }
    }

    /**
     * Verifica se a posição é válida para a formiga.
     * @param loc A localização a ser verificada.
     * @return true se a posição é válida, false caso contrário.
     */
    private boolean isPosicaoValida(Localizacao loc) {
        // Verifica se a posição está livre de obstáculos
        for (Obstaculo obstaculo : mapa.getObstaculos()) {
            Localizacao obsLoc = obstaculo.getLocalizacao();
            if (loc.getX() >= obsLoc.getX() && loc.getX() < obsLoc.getX() + 3 &&
                loc.getY() >= obsLoc.getY() && loc.getY() < obsLoc.getY() + 3) {
                return false;
            }
        }
        return true;
    }

    /**
     * Encontra o formigueiro mais próximo da formiga.
     * @param formiga A formiga que deseja encontrar o formigueiro mais próximo.
     * @return O formigueiro mais próximo.
     */
    private Formigueiro encontrarFormigueiroMaisProximo(Formiga formiga) {
        List<Formigueiro> formigueiros = mapa.getFormigueiros();
        if (formigueiros.isEmpty()) return null;
        
        Formigueiro maisProximo = formigueiros.get(0);
        double menorDistancia = calcularDistancia(formiga.getLocalizacao(), maisProximo.getLocalizacao());
        
        for (Formigueiro formigueiro : formigueiros) {
            double distancia = calcularDistancia(formiga.getLocalizacao(), formigueiro.getLocalizacao());
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                maisProximo = formigueiro;
            }
        }
        
        return maisProximo;
    }

    /**
     * Calcula a distância entre duas localizações.
     * @param loc1 A primeira localização.
     * @param loc2 A segunda localização.
     * @return A distância entre as localizações.
     */
    private double calcularDistancia(Localizacao loc1, Localizacao loc2) {
        int dx = loc1.getX() - loc2.getX();
        int dy = loc1.getY() - loc2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void executarUmPasso() {
        contadorPassos++;
        
        // Tenta adicionar novas formigas periodicamente
        if (contadorPassos % INTERVALO_SPAWN == 0) {
            if (rand.nextDouble() < CHANCE_SPAWN) {
                // Gera um número aleatório de formigas para spawnar (entre 1 e MAX_FORMIGAS_POR_SPAWN)
                int quantidadeNovasFormigas = 1 + rand.nextInt(MAX_FORMIGAS_POR_SPAWN);
                spawnFormigas(quantidadeNovasFormigas);
            }
        }

        Iterator<Formiga> iterator = formigas.iterator();
        while (iterator.hasNext()) {
            Formiga formiga = iterator.next();
            
            // Ignora formigas já removidas
            if ("REMOVIDA".equals(formiga.getEstado()) || !formiga.isVisivel()) {
                iterator.remove();
                continue;
            }

            mapa.removerItem(formiga);
            
            // Verifica colisão com obstáculos apenas uma vez por formiga
            if (!"AFETADA".equals(formiga.getEstado())) {
                verificarColisaoComObstaculos(formiga);
            }

            formiga.executarAcao();
            mapa.adicionarItem(formiga);
        }
        janelaSimulacao.executarAcao();
    }
    
    /**
     * Verifica se a formiga colidiu com algum obstáculo.
     * @param formiga A formiga a ser verificada.
     */
    private void verificarColisaoComObstaculos(Formiga formiga) {
        Localizacao locFormiga = formiga.getLocalizacao();
    
        for (Obstaculo obstaculo : mapa.getObstaculos()) {
            Localizacao locObstaculo = obstaculo.getLocalizacao();
    
            boolean dentroAreaX = locFormiga.getX() >= locObstaculo.getX() && 
                                  locFormiga.getX() < locObstaculo.getX() + 3;
            boolean dentroAreaY = locFormiga.getY() >= locObstaculo.getY() && 
                                  locFormiga.getY() < locObstaculo.getY() + 3;
    
            if (dentroAreaX && dentroAreaY) {
                obstaculo.afetarFormiga(formiga);
                break; // Sai do loop após afetar a formiga
            }
        }
    }
    

    /**
     * Executa a simulação por um número de passos.
     * @param numPassos O número de passos a ser executado.
     */
    public void executarSimulacao(int numPassos) {
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(200);
        }
        // Exibe estatísticas ao final da simulação
        System.out.println(estatisticas.toString());
    }
    
    /**
     * Espera por um número de milisegundos.
     * @param milisegundos O número de milisegundos a esperar.
     */
    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}