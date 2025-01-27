import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa um mapa com todos os itens que participam da simulacao.
 */
public class Mapa {
    private ElementoTerreno[][] itensMapa;
    private Formiga[][] itens;
    private Obstaculo[][] itensObstaculos;
    private Formigueiro[][] itensFormigueiros;

    private int largura;
    private int altura;
    private List<Formigueiro> formigueiros; // Lista de formigueiros no mapa
    private List<Obstaculo> obstaculos;

    private static final int LARGURA_PADRAO = 35;
    private static final int ALTURA_PADRAO = 35;

    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        itens = new Formiga[altura][largura];
        itensFormigueiros = new Formigueiro[altura][largura]; // Inicializar matriz de formigueiros
        itensObstaculos = new Obstaculo[altura][largura];
        itensMapa = new ElementoTerreno[altura][largura];
        this.formigueiros = new ArrayList<>();
        this.obstaculos = new ArrayList<>();

        inicializarFormigueiros(); // Adicionar formigueiros automaticamente
    }

    /**
     * Cria mapa com tamanho padrão.
     */
    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public void adicionarItem(Formiga v) {
        itens[v.getLocalizacao().getX()][v.getLocalizacao().getY()] = v;
    }

    public void removerItem(Formiga v) {
        itens[v.getLocalizacao().getX()][v.getLocalizacao().getY()] = null;
    }

    public void adicionarObstaculoA(Obstaculo v) {
        itensObstaculos[v.getLocalizacao().getX()][v.getLocalizacao().getY()] = v;
    }

    public void atualizarMapa(Formiga v) {
        removerItem(v);
        adicionarItem(v);
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public Formiga getItem(int x, int y) {
        return itens[x][y];
    }
    
    public void adicionarObstaculosAleatorios() {
        Random rand = new Random();
        int quantidadeObstaculos = 10; // Quantidade de obstáculos no mapa
        for (int i = 0; i < quantidadeObstaculos; i++) {
            int x = rand.nextInt(largura);
            int y = rand.nextInt(altura);
            Localizacao localizacao = new Localizacao(x, y);

            // Escolhe aleatoriamente o tipo de obstáculo
            Obstaculo obstaculo;
            int tipo = rand.nextInt(3);
            if (tipo == 0) {
                obstaculo = new Tamandua(localizacao);
            } else if (tipo == 1) {
                obstaculo = new Vento(localizacao);
            } else {
                obstaculo = new Lama(localizacao);
            }

            adicionarObstaculo(obstaculo);
        }
    }

    public void adicionarObstaculo(Obstaculo obstaculo) {
        obstaculos.add(obstaculo);
    }

    public List<Obstaculo> getObstaculos() {
        return obstaculos;
    }

    public Obstaculo getObstaculoNaPosicao(Localizacao localizacao) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getLocalizacao().equals(localizacao)) {
                return obstaculo;
            }
        }
        return null;
    }


    private void inicializarFormigueiros() {
        // Definir as posições fixas
        int[][] posicoesFormigueiros = {
            {0, 1},  // Formigueiro 1
            //{0, 4},  // Formigueiro 2
            //{0, 7}   // Formigueiro 3
        };

        // Criar e adicionar formigueiros automaticamente
        for (int[] posicao : posicoesFormigueiros) {
            Localizacao localizacao = new Localizacao(posicao[0], posicao[1]);
            Formigueiro formigueiro = new Formigueiro(localizacao, "/Imagens/formigueiro.png", this);
            formigueiros.add(formigueiro);
            itensFormigueiros[localizacao.getY()][localizacao.getX()] = formigueiro; // Adicionar à matriz
        }
    }

    // Método para exibir o mapa
    /*
    public void mostrarMapa() {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (itensFormigueiros[i][j] instanceof Formigueiro) {
                    System.out.print("[F] "); // Representar um formigueiro
                } else {
                    System.out.print("[ ] "); // Espaço vazio
                }
            }
            System.out.println();
        }
    }*/
    public void adicionarFormigueiro(Formigueiro formigueiro) {
        formigueiros.add(formigueiro);
    }
    
    public List<Formigueiro> getFormigueiros() {
        return formigueiros;
    }

    public ElementoTerreno getItemNaPosicao(Localizacao localizacao) {
        return itensMapa[localizacao.getX()][localizacao.getY()];
    }

    /* 
    public void moverVeiculo(Veiculo veiculo, Localizacao novaLocalizacao) {
        removerItem(veiculo);  // Remove o veículo da posição antiga
        veiculo.setLocalizacaoAtual(novaLocalizacao);
        adicionarItem(veiculo);  // Adiciona o veículo na nova posição
    }*/
    
}
