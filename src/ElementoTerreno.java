import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class ElementoTerreno {
    private Localizacao localizacao;
    private Image imagemObstaculo;
    
    ElementoTerreno(Localizacao localizacao, String caminhoImagem) {
        this.localizacao = localizacao;
        java.net.URL imgURL = getClass().getResource(caminhoImagem);
        if (imgURL != null) {
            this.imagemObstaculo = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Recurso não encontrado: " + caminhoImagem);
            // Você pode definir uma imagem padrão ou lançar uma exceção
            this.imagemObstaculo = null; // ou uma imagem padrão
        }
    } 

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Image getImagem() {
        return imagemObstaculo;
    }   
}
