public class EstatisticasSimulacao {
    private int[] formigasPorFormigueiro;
    private int formigasAfetadasPorTamandua;
    private int formigasAfetadasPorLama;
    private int formigasAfetadasPorVento;
    private java.util.Set<Integer> formigasRegistradas; // Conjunto para controlar formigas já registradas
    private java.util.Set<Integer> formigasAfetadasRegistradas; // Conjunto para controlar formigas afetadas já registradas

    /**
     * Construtor da classe EstatisticasSimulacao
     * @param numFormigueiros
     */
    public EstatisticasSimulacao(int numFormigueiros) {
        this.formigasPorFormigueiro = new int[numFormigueiros];
        this.formigasAfetadasPorTamandua = 0;
        this.formigasAfetadasPorLama = 0;
        this.formigasRegistradas = new java.util.HashSet<>();
        this.formigasAfetadasRegistradas = new java.util.HashSet<>();
    }

    /**
     * Registra a entrada de uma formiga em um formigueiro
     * @param indiceFormigueiro
     * @param formigaId
     */
    public void registrarEntradaFormigueiro(int indiceFormigueiro, int formigaId) {
        if (!formigasRegistradas.contains(formigaId)) {
            formigasPorFormigueiro[indiceFormigueiro]++;
            formigasRegistradas.add(formigaId);
        }
    }

    /**
     * Registra uma formiga afetada por um obstáculo
     * @param formigaId
     */
    public void registrarAfetadaPorTamandua(int formigaId) {
        if (!formigasAfetadasRegistradas.contains(formigaId)) {
            formigasAfetadasPorTamandua++;
            formigasAfetadasRegistradas.add(formigaId);
        }
    }

    /**
     * Registra uma formiga afetada por lama
     * @param formigaId
     */
    public void registrarAfetadaPorLama(int formigaId) {
        if (!formigasAfetadasRegistradas.contains(formigaId)) {
            formigasAfetadasPorLama++;
            formigasAfetadasRegistradas.add(formigaId);
        }
    }

    /**
     * Registra uma formiga afetada por vento
     * @param formigaId
     */
    public void registrarAfetadaPorVento(int formigaId) {
        if (!formigasAfetadasRegistradas.contains(formigaId)) {
            formigasAfetadasPorVento++;
            formigasAfetadasRegistradas.add(formigaId);
        }
    }

    /**
     * Retorna a quantidade de formigas por formigueiro
     */
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
        sb.append("Lama: ").append(formigasAfetadasPorLama).append(" formigas\n");
        sb.append("Vento: ").append(formigasAfetadasPorVento).append(" formigas\n");

        return sb.toString();
    }
}