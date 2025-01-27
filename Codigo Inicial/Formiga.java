import java.awt.Image;


/**
 * Representa os veiculos da simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Formiga extends ElementoTerreno {
    private Localizacao localizacaoDestino;
    private Formigueiro formigueiroDestino;
    

    public Formiga(Localizacao localizacaoAtual, String imagemPath) {
        super(localizacaoAtual, imagemPath);
        localizacaoDestino = null;
        
    }

    public Localizacao getLocalizacaoAtual() {
        return super.getLocalizacaoAtual();
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }
    
    public Image getImagem(){
        return super.getImagem();
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        super.setLocalizacaoAtual(localizacaoAtual);;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }
    
    public void executarAcao(){
        Localizacao destino = getLocalizacaoDestino();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
        }
    } 
}
