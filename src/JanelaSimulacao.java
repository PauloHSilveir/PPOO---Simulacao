import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JanelaSimulacao extends JFrame {
    private Mapa mapa;
    private VisaoMapa visaoMapa;
    
    public JanelaSimulacao(Mapa mapa) {
        this.mapa = mapa;
        visaoMapa = new VisaoMapa(mapa.getLargura(), mapa.getAltura());
        getContentPane().add(visaoMapa);
        setTitle("Ant Colony Simulator");
        setSize(1240, 975);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void executarAcao() {
        visaoMapa.preparePaint();
        
        // Desenhar os formigueiros no mapa
        for (Formigueiro formigueiro : mapa.getFormigueiros()) {
            Localizacao localizacao = formigueiro.getLocalizacao();
            visaoMapa.desenharImagem(localizacao.getX(), localizacao.getY() - 1, formigueiro.getImagem(), true);
        }

        // Desenhar os obstáculos
        for (Obstaculo obstaculo : mapa.getObstaculos()) {
            Localizacao localizacao = obstaculo.getLocalizacao();
            visaoMapa.desenharImagem(localizacao.getX(), localizacao.getY(), obstaculo.getImagem(), true);
        }

        // Desenhar as formigas no mapa
        for (int i = 0; i < mapa.getAltura(); i++) {
            for (int j = 0; j < mapa.getLargura(); j++) {
                ElementoTerreno elemento = mapa.getItem(i, j);
                if (elemento instanceof Formiga) {
                    Formiga formiga = (Formiga) elemento;
                    // Só desenha se a formiga estiver visível e não estiver removida
                    if (formiga.isVisivel() && !"REMOVIDA".equals(formiga.getEstado())) {
                        Localizacao localizacao = formiga.getLocalizacao();
                        visaoMapa.desenharImagem(localizacao.getX(), localizacao.getY(), formiga.getImagem(), false);
                    }
                }
            }
        }
    
        visaoMapa.repaint();
    }
    
    /**
     * Fornece uma visualizacao grafica do mapa. Esta eh 
     * uma classe interna que define os componentes da GUI.
     * Ela contém alguns detalhes mais avancados sobre GUI 
     * que voce pode ignorar para realizacao do seu trabalho.
     */    
    private class VisaoMapa extends JPanel {

        private final int VIEW_SCALING_FACTOR = 6;
        private final int ELEMENT_EXCEPT_ANT_SCALING_FACTOR = 3;

        private int larguraMapa, alturaMapa;
        private int xScale, yScale;
        private Dimension tamanho;
        private Graphics g;
        private Image imagemMapa;
        private Image backgroundImage;

        /**
         * Cria um novo componente VisaoMapa.
         */
        public VisaoMapa(int largura, int altura) {
            larguraMapa = largura;
            alturaMapa = altura;
            tamanho = new Dimension(0, 0);
            
            try {
                // Carrega a imagem de fundo usando getResource
                URL imageUrl = getClass().getClassLoader().getResource("Imagens/fundo.png");
                if (imageUrl != null) {
                    backgroundImage = ImageIO.read(imageUrl);
                    if (backgroundImage == null) {
                        System.out.println("Não foi possível carregar a imagem de fundo");
                    }
                } else {
                    System.out.println("Arquivo de imagem não encontrado");
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar imagem de fundo: " + e.getMessage());
                backgroundImage = null;
            }
        }
        
        /**
         * Informa para o gerenciador GUI o tamanho.
         */
        public Dimension getPreferredSize() {
            return new Dimension(larguraMapa * VIEW_SCALING_FACTOR,
                                 alturaMapa * VIEW_SCALING_FACTOR);
        }
        

        /**
         * Prepara para um novo ciclo de exibicao. Uma vez que o componente
         * pode ser redimensionado, calcula o "fator de escala" novamente.
         */
        public void preparePaint() {
            if (!tamanho.equals(getSize())) { // se o tamanho mudou...
                tamanho = getSize();
                imagemMapa = visaoMapa.createImage(tamanho.width, tamanho.height);
                g = imagemMapa.getGraphics();

                xScale = tamanho.width / larguraMapa;
                if (xScale < 1) {
                    xScale = VIEW_SCALING_FACTOR;
                }
                yScale = tamanho.height / alturaMapa;
                if (yScale < 1) {
                    yScale = VIEW_SCALING_FACTOR;
                }
            }
            
            // Desenha o background
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, tamanho.width, tamanho.height, this);
            } else {
                g.setColor(Color.white);
                g.fillRect(0, 0, tamanho.width, tamanho.height);
            }
            
            g.setColor(new Color(20,20,20,64));
            for (int i = 0, x = 0; x < tamanho.width; i++, x = i * xScale) {
                g.drawLine(x, 0, x, tamanho.height - 1);
            }
            for (int i = 0, y = 0; y < tamanho.height; i++, y = i * yScale) {
                g.drawLine(0, y, tamanho.width - 1, y);
            }
        }
        
        /**
         * Desenha a imagem para um determinado item.
         */
        public void desenharImagem(int x, int y, Image image, boolean usarEscalaAlternativa) {
            int escala;
            if(usarEscalaAlternativa)
                escala = ELEMENT_EXCEPT_ANT_SCALING_FACTOR;
            else
                escala = 1;

            int larguraImagem = (xScale - 1) * escala;
            int alturaImagem = (yScale - 1) * escala;

            g.drawImage(image, x * xScale + 1, y * yScale + 1,
                        larguraImagem, alturaImagem, this);
        }

        /**
         * O componente VisaoMapa precisa ser reexibido.
         */
        public void paintComponent(Graphics g) {
            if (imagemMapa != null) {
                g.drawImage(imagemMapa, 0, 0, null);
            }
        }
    }
}
