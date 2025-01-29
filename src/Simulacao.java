import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulacao {
    private List<Formiga> formigas;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    private static EstatisticasSimulacao estatisticas;
    
    public static EstatisticasSimulacao getEstatisticas() {
        return estatisticas;
    }

    public Simulacao() {
        Random rand = new Random();
        mapa = new Mapa();
        formigas = new ArrayList<>();
        estatisticas = new EstatisticasSimulacao(3); // 3 formigueiros
        
        // Primeiro, adicionar obstáculos antes das formigas
        mapa.adicionarObstaculosAleatorios();
        
        // Depois, criar e adicionar formigas
        int quantidadeFormigas = 10;
        for (int i = 0; i < quantidadeFormigas; i++) {
            // Gerar posição inicial válida (evitando obstáculos)
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
        
        janelaSimulacao = new JanelaSimulacao(mapa);
    }

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

    private double calcularDistancia(Localizacao loc1, Localizacao loc2) {
        int dx = loc1.getX() - loc2.getX();
        int dy = loc1.getY() - loc2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void executarUmPasso() {
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
    

    public void executarSimulacao(int numPassos) {
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(200);
        }
        // Exibe estatísticas ao final da simulação
        System.out.println(estatisticas.toString());
    }
    
    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
