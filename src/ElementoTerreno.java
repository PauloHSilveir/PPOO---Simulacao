import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class ElementoTerreno {
    private Localizacao localizacao;
    private Image imagemObstaculo;
    
    ElementoTerreno(Localizacao localizacao, String caminhoImagem) {
        this.localizacao = localizacao;
        this.imagemObstaculo = new ImageIcon(getClass().getResource(caminhoImagem)).getImage();
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
