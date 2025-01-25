import java.util.Random;

/**
 * Representa uma localização no mapa
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Localizacao {
    private int x;
    private int y;
    private static Random rand = new Random();
    
    /**
     * Representa uma localização na cidade
     * @param x Coordenada x: deve ser maior ou igual a 0.
     * @param y Coordenada y: deve ser maior ou igual a 0.
     */
    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    /**
     * Gera a localizacao para se mover visando alcançar o destino
     * @param localizacaoDestino: localizacao que se deseja alcancar.
     * @return Localizacao para onde se deve ir
     */
    public Localizacao proximaLocalizacao(Localizacao localizacaoDestino){
        if(localizacaoDestino.equals(this)){//Verifica se já alcancou o destino
            return localizacaoDestino;
        }else{
            int destX = localizacaoDestino.getX();
            int destY = localizacaoDestino.getY();
            int deslocX = x < destX ? 1 : x > destX ? -1 : 0;//Deslocamento de 1 ou 0 ou -1 posição em x
            int deslocY = y < destY ? 1 : y > destY ? -1 : 0;//Deslocamento de 1 ou 0 ou -1 posição em y
            Localizacao novaLocalizacao;
            if(deslocX != 0 && deslocY != 0){//Se nenhuma coordenada coincide com a localizacao destino
                if(rand.nextInt(2) == 0){//Atualizar x
                    novaLocalizacao = new Localizacao(x + deslocX, y);
                }else{//Atualizar y
                    novaLocalizacao = new Localizacao(x, y + deslocY);
                }
            }else{
                if(deslocX != 0) novaLocalizacao = new Localizacao(x + deslocX, y);
                else novaLocalizacao = new Localizacao(x, y + deslocY);
            }
            return novaLocalizacao;
        }
    }
    
    /**
     * Verificacao de igualdade de conteudo de objetos do tipo Localizacao.
     * @return true: se a localizacao é igual.
     *         false: caso contrario.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }else if(!(obj instanceof Localizacao)){
            return false;
        }else{
            Localizacao outro = (Localizacao) obj;
            return x == outro.x && y == outro.y;
        }
    }
    
    /**
     * @return A representacao da localizacao.
     */
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    
}
