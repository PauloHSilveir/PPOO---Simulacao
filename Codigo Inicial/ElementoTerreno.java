import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class ElementoTerreno {
    private Localizacao localizacaoAtual;
    private Image imagem;

    public ElementoTerreno(Localizacao localizacao, String imagem) {
        this.localizacaoAtual = localizacao;
        this.imagem = new ImageIcon(getClass().getResource(imagem)).getImage();{
        };
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Image getImagem() {
        return imagem;
    }

    public void setLocalizacaoAtual(Localizacao localizacao) {
        this.localizacaoAtual = localizacao;
    }
}
