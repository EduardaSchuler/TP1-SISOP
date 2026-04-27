import java.util.List;

public class EscalonadorEDF{
    private List<Processo> processos;
    private int periodo;

    private List<Processo> filaProntos;
    private List<Processo> filaEspera;

    EscalonadorEDF(List<Processo> processos) {
        this.processos = processos;
        this.periodo = 0;
    }

    public void executar() {
        while (!processos.isEmpty()) {
            for (Processo p : processos) {
                if (p.getArrivalTime() == periodo) {
                    filaProntos.add(p);
                }
            }
            periodo++;
        }
    }

    public void trocaContexto(Processo p1, Processo p2) {
        p1.updateProcessState(Processo.ProcessState.BLOCKED);
        p2.updateProcessState(Processo.ProcessState.RUNNING);

        System.out.println("Troca de contexto: " + p1 + " -> " + p2);
    }
}