import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulacao {
    private List<Formiga> formigas;
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    

    private double calcularDistancia(Localizacao loc1, Localizacao loc2) {
        int dx = loc1.getX() - loc2.getX();
        int dy = loc1.getY() - loc2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    //encontrar o formigueiro mais próximo
    private Formigueiro encontrarFormigueiroMaisProximo(Localizacao localizacaoFormiga) {
        List<Formigueiro> formigueiros = mapa.getFormigueiros();
        Formigueiro formigueiroMaisProximo = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Formigueiro formigueiro : formigueiros) {
            double distancia = calcularDistancia(localizacaoFormiga, formigueiro.getLocalizacao());
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                formigueiroMaisProximo = formigueiro;
            }
        }

        return formigueiroMaisProximo;
    }

    
    public Simulacao() {
        Random rand = new Random();
        mapa = new Mapa();
        formigas = new ArrayList<>();
        int largura = mapa.getLargura();
        int altura = mapa.getAltura();

        int quantidadeFormigas = 60;
        for (int i = 0; i < quantidadeFormigas; i++) {
            Localizacao localizacaoInicial = new Localizacao(rand.nextInt(largura), rand.nextInt(altura));
            Formiga formiga = new Formiga(localizacaoInicial);

            // Encontra o formigueiro mais próximo para esta formiga
            if (!mapa.getFormigueiros().isEmpty()) {
                Formigueiro formigueiroMaisProximo = encontrarFormigueiroMaisProximo(localizacaoInicial);
                formiga.setLocalizacaoDestino(formigueiroMaisProximo.getLocalizacao());
                formiga.setFormigueiroDestino(formigueiroMaisProximo);
            }

            formigas.add(formiga);
            mapa.adicionarItem(formiga);
        }

        mapa.adicionarObstaculosAleatorios();
        janelaSimulacao = new JanelaSimulacao(mapa);
        if (mapa == null) {
            throw new IllegalStateException("Erro: O mapa não foi inicializado corretamente!");
        }
    }
    
    public void executarSimulacao(int numPassos) {
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(200);

            //esperar(executarUmPasso());
        }
    }
    /*
    private void executarUmPasso() {
        for (Veiculo veiculo : veiculos) {
            mapa.removerItem(veiculo);  // Remove da posição antiga
            veiculo.executarAcao();
            mapa.adicionarItem(veiculo);  // Adiciona na nova posição
        
            if (veiculo.getFormigueiroDestino() != null && 
            formigaConcluiuTarefa(veiculo)) {
            veiculo.acessarFormigueiro(veiculo.getFormigueiroDestino());
        }
        }
        janelaSimulacao.executarAcao();
    }*/
    /* 
    private int executarUmPasso() {
        for (Formiga formiga : formigas) {
            // Obtém a próxima posição da formiga com base no destino
            Localizacao proximaPosicao = formiga.getLocalizacao().proximaLocalizacao(formiga.getLocalizacaoDestino());
            
            // Verifica se existe algum item na próxima posição
            ElementoTerreno itemNaFrente = mapa.getItemNaPosicao(proximaPosicao);
    
            if (itemNaFrente == null) {
                // Se a célula está livre, a formiga pode se mover
                mapa.removerItem(formiga);
                formiga.executarAcao();
                mapa.adicionarItem(formiga);
            } else if (itemNaFrente instanceof Obstaculo) {
                // Se for um obstáculo, aplica o efeito na formiga
                Obstaculo obstaculo = (Obstaculo) itemNaFrente;
                obstaculo.afetarFormiga(formiga);
            } else if (itemNaFrente instanceof Formiga) {
                // Se for outra formiga, mantém a posição atual (fila indiana)
                // A lógica de permanência já está implícita ao não executar ações
                continue;
            }
            return formiga.getVelocidade();
        }
        // Atualiza a interface gráfica após todas as ações
        janelaSimulacao.executarAcao();
        return 200;
    }*/
    
    private void executarUmPasso() {
        for (Formiga formiga : formigas) {
            
            mapa.removerItem(formiga);
            formiga.executarAcao();
            mapa.adicionarItem(formiga);
            
        }
        // Atualiza a interface gráfica após todas as ações
        janelaSimulacao.executarAcao();
    }
    
    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
