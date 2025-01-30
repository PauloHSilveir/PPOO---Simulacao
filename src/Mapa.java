import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa um mapa que contém e gerencia todos os elementos da simulação.
 * O mapa é responsável por controlar a disposição dos formigueiros, formigas e obstáculos,
 * garantindo as regras de distanciamento e posicionamento.
 * 
 */
public class Mapa {
    /** Distância mínima necessária entre obstáculos */
    private static final int DISTANCIA_MINIMA = 5;

    /** Distância mínima necessária entre formigueiros */
    private static final int DISTANCIA_FORMIGUEIRO = 8;

    /** Matriz que armazena elementos do terreno */
    private ElementoTerreno[][] itensMapa;
    /** Matriz que controla posição das formigas */
    private Formiga[][] itens;

    /** Matriz que controla posição dos obstáculos */
    private Obstaculo[][] itensObstaculos;
    
    /** Matriz que controla posição dos formigueiros */
    private Formigueiro[][] itensFormigueiros;

    private int largura;
    
    private int altura;

    private List<Formigueiro> formigueiros;
    private List<Obstaculo> obstaculos;

    /** Largura padrão do mapa */
    private static final int LARGURA_PADRAO = 35;

    /** Altura padrão do mapa */
    private static final int ALTURA_PADRAO = 35;

    /**
     * Constrói um novo mapa com dimensões especificadas.
     * 
     * @param largura A largura do mapa
     * @param altura A altura do mapa
     */
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
     * Verifica se uma área específica está livre, respeitando distâncias mínimas.
     * 
     * @param localizacao Posição inicial a ser verificada
     * @param larguraArea Largura da área a verificar
     * @param alturaArea Altura da área a verificar
     * @return true se a área estiver livre, false caso contrário
     */
    private boolean isAreaLivreComDistancia(Localizacao localizacao, int larguraArea, int alturaArea) {
        // Verifica limites do mapa
        if (localizacao.getX() + larguraArea > largura || 
            localizacao.getY() + alturaArea > altura ||
            localizacao.getX() < 0 || localizacao.getY() < 0) {
            return false;
        }

        // Verifica área imediata
        for (int x = localizacao.getX(); x < localizacao.getX() + larguraArea; x++) {
            for (int y = localizacao.getY(); y < localizacao.getY() + alturaArea; y++) {
                if (itensFormigueiros[y][x] != null || 
                    itensObstaculos[y][x] != null || 
                    itens[y][x] != null) {
                    return false;
                }
            }
        }

        // Verifica distância dos formigueiros
        for (Formigueiro formigueiro : formigueiros) {
            int distX = Math.abs(localizacao.getX() - formigueiro.getLocalizacao().getX());
            int distY = Math.abs(localizacao.getY() - formigueiro.getLocalizacao().getY());
            if (distX < DISTANCIA_FORMIGUEIRO && distY < DISTANCIA_FORMIGUEIRO) {
                return false;
            }
        }

        // Verifica distância de outros obstáculos
        for (Obstaculo outro : obstaculos) {
            int distX = Math.abs(localizacao.getX() - outro.getLocalizacao().getX());
            int distY = Math.abs(localizacao.getY() - outro.getLocalizacao().getY());
            if (distX < DISTANCIA_MINIMA && distY < DISTANCIA_MINIMA) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Adiciona obstáculos aleatoriamente no mapa.
     * Gera diferentes tipos de obstáculos (Tamandua, Vento, Lama) em posições aleatórias,
     * respeitando as regras de distanciamento.
     */
    public void adicionarObstaculosAleatorios() {
        Random rand = new Random();
        int quantidadeObstaculos = 20;
        int tentativasMaximas = 100;

        for (int i = 0; i < quantidadeObstaculos; i++) {
            boolean colocado = false;
            int tentativas = 0;

            while (!colocado && tentativas < tentativasMaximas) {
                int x = rand.nextInt(largura - 5); // Margem para evitar bordas
                int y = rand.nextInt(altura - 5);  // Margem para evitar bordas
                Localizacao localizacao = new Localizacao(x, y);

                Obstaculo obstaculo;
                int tipo = rand.nextInt(3);
                obstaculo = switch (tipo) {
                    case 0 -> new Tamandua(localizacao);
                    case 1 -> new Vento(localizacao);
                    default -> new Lama(localizacao);
                };

                int[] tamanho = obstaculo.getTamanho();
                if (isAreaLivreComDistancia(localizacao, tamanho[0], tamanho[1])) {
                    // Marca área como ocupada
                    for (int dx = 0; dx < tamanho[0]; dx++) {
                        for (int dy = 0; dy < tamanho[1]; dy++) {
                            itensObstaculos[y + dy][x + dx] = obstaculo;
                        }
                    }
                    obstaculos.add(obstaculo);
                    colocado = true;
                }
                tentativas++;
            }
        }
    }

    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    /**
     * Adiciona uma formiga ao mapa na posição especificada pela sua localização
     * 
     * @param v A formiga a ser adicionada ao mapa
     */
    public void adicionarItem(Formiga v) {
        itens[v.getLocalizacao().getX()][v.getLocalizacao().getY()] = v;
    }

    /**
     * Remove uma formiga do mapa na posição especificada pela sua localização
     * 
     * @param v A formiga a ser removida do mapa
     */
    public void removerItem(Formiga v) {
        itens[v.getLocalizacao().getX()][v.getLocalizacao().getY()] = null;
    }

    /**
     * Adiciona um obstáculo ao mapa na posição especificada pela sua localização
     * 
     * @param v O obstáculo a ser adicionado ao mapa
     */
    public void adicionarObstaculoA(Obstaculo v) {
        itensObstaculos[v.getLocalizacao().getX()][v.getLocalizacao().getY()] = v;
    }

    /**
     * Atualiza a posição de uma formiga no mapa, removendo-a da posição antiga
     * e adicionando-a na nova posição
     * 
     * @param v A formiga cuja posição será atualizada
     */
    public void atualizarMapa(Formiga v) {
        removerItem(v);
        adicionarItem(v);
    }

    
    /**
     * Retorna a largura do mapa
     * 
     * @return int representando a largura do mapa
     */
    public int getLargura() {
        return largura;
    }

    /**
     * Retorna a altura do mapa
     * 
     * @return int representando a altura do mapa
     */
    public int getAltura() {
        return altura;
    }

    public Formiga getItem(int x, int y) {
        return itens[x][y];
    }
    
    /**
     * Verifica se uma área específica está livre, respeitando distâncias mínimas.
     * 
     * @param localizacao Posição inicial a ser verificada
     * @param larguraArea Largura da área a verificar
     * @param alturaArea Altura da área a verificar
     * @return true se a área estiver livre, false caso contrário
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
            {16, 1}, // Formigueiro 2
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


    /**
     * Adiciona um item ao mapa na posição especificada pela sua localização
     * 
     * @param item O item a ser adicionado ao mapa
     */
    public void adicionarObstaculo(Obstaculo obstaculo) {
        obstaculos.add(obstaculo);
    }

    public List<Obstaculo> getObstaculos() {
        return obstaculos;
    }

    /**
     * Retorna o obstáculo na posição especificada
     * 
     * @param localizacao A localização do obstáculo
     * @return Obstaculo na posição especificada
     */
    public Obstaculo getObstaculoNaPosicao(Localizacao localizacao) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getLocalizacao().equals(localizacao)) {
                return obstaculo;
            }
        }
        return null;
    }

    /**
     * Adiciona um item ao mapa na posição especificada pela sua localização
     * 
     * @param item O item a ser adicionado ao mapa
     */
    public void adicionarFormigueiro(Formigueiro formigueiro) {
        formigueiros.add(formigueiro);
    }
    
    /**
     * Retorna a lista de formigueiros no mapa
     * 
     * @return List<Formigueiro> representando a lista de formigueiros no mapa
     */
    public List<Formigueiro> getFormigueiros() {
        return formigueiros;
    }

    /**
     * Retorna o formigueiro na posição especificada
     * 
     * @param localizacao A localização do formigueiro
     * @return Formigueiro na posição especificada
     */
    public ElementoTerreno getItemNaPosicao(Localizacao localizacao) {
        return itensMapa[localizacao.getX()][localizacao.getY()];
    }
    
}
