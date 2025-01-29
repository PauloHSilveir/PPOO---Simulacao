public class EstatisticasSimulacao {
    private int[] formigasPorFormigueiro;
    private int formigasAfetadasPorTamandua;
    
    public EstatisticasSimulacao(int numFormigueiros) {
        this.formigasPorFormigueiro = new int[numFormigueiros];
        this.formigasAfetadasPorTamandua = 0;
    }
    
    public void registrarEntradaFormigueiro(int indiceFormigueiro) {
        formigasPorFormigueiro[indiceFormigueiro]++;
    }
    
    public void registrarAfetadaPorTamandua() {
        formigasAfetadasPorTamandua++;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Estatísticas da Simulação ===\n");
        
        sb.append("\nFormigas por Formigueiro:\n");
        for (int i = 0; i < formigasPorFormigueiro.length; i++) {
            sb.append("Formigueiro ").append(i + 1).append(": ")
              .append(formigasPorFormigueiro[i]).append(" formigas\n");
        }
        
        sb.append("\nFormigas afetadas por obstáculos:\n");
        sb.append("Tamanduá: ").append(formigasAfetadasPorTamandua).append(" formigas\n");
        
        return sb.toString();
    }
}