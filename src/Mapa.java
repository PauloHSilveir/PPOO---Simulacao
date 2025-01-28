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
    
    /*
     * Verifica se uma área retangular no mapa está livre para ser ocupada por um item. Usa a 
     * localização e as dimensões da área para checar se qualquer item já ocupa algum ponto 
     * dessa área.
     */
    public boolean isAreaLivre(Localizacao localizacao, int larguraArea, int alturaArea) {
        int inicioX = Math.max(0, localizacao.getX());
        int inicioY = Math.max(0, localizacao.getY());
        int fimX = Math.min(largura, inicioX + larguraArea);
        int fimY = Math.min(altura, inicioY + alturaArea);

        for (int x = inicioX; x < fimX; x++) {
            for (int y = inicioY; y < fimY; y++) {
                if (itensFormigueiros[y][x] != null || itensObstaculos[y][x] != null || itens[y][x] != null) {
                    return false; // Posição já ocupada
                }
            }
        }
        return true;
    }
    
    /*
     * Inicializa os formigueiros no mapa, inserindo-os em posições 
     * predefinidas, caso haja espaço livre para alocá-los.
     */
    private void inicializarFormigueiros() {
        int[][] posicoesFormigueiros = {
            {2, 1},  // Formigueiro 1
            {12, 1}, // Formigueiro 2
            {32, 1}  // Formigueiro 3
        };

        for (int[] posicao : posicoesFormigueiros) {
            Localizacao localizacao = new Localizacao(posicao[0], posicao[1]);
            if (isAreaLivre(localizacao, 3, 3)) {
                Formigueiro formigueiro = new Formigueiro(localizacao, "/Imagens/formigueiro.png", this);
                formigueiros.add(formigueiro);
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        int novoX = localizacao.getX() + x;
                        int novoY = localizacao.getY() + y;
                        if (novoX < largura && novoY < altura) {
                            itensFormigueiros[novoY][novoX] = formigueiro;
                        }
                    }
                }
            } else {
                System.out.println("Não foi possível adicionar formigueiro em: " + localizacao);
            }
        }
    }

    /*
     * Essa função adiciona obstáculos aleatórios ao mapa. Para cada obstáculo, ele 
     * tenta encontrar um espaço livre de 3x3 para alocá-lo. Se o espaço estiver 
     * livre, o obstáculo é colocado no mapa.
     */
    public void adicionarObstaculosAleatorios() {
        Random rand = new Random();
        int quantidadeObstaculos = 10;

        for (int i = 0; i < quantidadeObstaculos; i++) {
            boolean colocado = false;
            while (!colocado) {
                int x = rand.nextInt(largura - 2);
                int y = rand.nextInt(altura - 2);
                Localizacao localizacao = new Localizacao(x, y);

                if (isAreaLivre(localizacao, 3, 3)) {
                    Obstaculo obstaculo;
                    int tipo = rand.nextInt(3);
                    if (tipo == 0) {
                        obstaculo = new Tamandua(localizacao);
                    } else if (tipo == 1) {
                        obstaculo = new Vento(localizacao);
                    } else {
                        obstaculo = new Lama(localizacao);
                    }

                    for (int deslocamentoX = 0; deslocamentoX < 3; deslocamentoX++) {
                        for (int deslocamentoY = 0; deslocamentoY < 3; deslocamentoY++) {
                            int novoX = x + deslocamentoX;
                            int novoY = y + deslocamentoY;
                            if (novoX < largura && novoY < altura) {
                                itensObstaculos[novoY][novoX] = obstaculo;
                            }
                        }
                    }
                    adicionarObstaculo(obstaculo);
                    colocado = true;
                }
            }
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
