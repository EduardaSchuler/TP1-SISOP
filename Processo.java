import java.util.List;
import java.util.Map;

public class Processo {
    public enum ProcessState {
        READY, RUNNING, BLOCKED, DONE
    }

    private String nome;
    private int arrivalTime;
    private int deadline;
    private int remainingTime;
    private int hardDeadline;
    private ProcessState state;

    private int accAtual = 0;
    private int pcAtual = 0;
    private int tempoEspera = -1;

    private List<Instrucao> instrucoes;
    private Map<String, Integer> data;
    private Map<String, Integer> labels;

    Processo(String nome, int arrivalTime, List<Instrucao> instrucoes, Map<String, Integer> data, Map<String, Integer> labels) {
        this.nome = nome;
        this.arrivalTime = arrivalTime;
        this.instrucoes = instrucoes;
        this.data = data;
        this.labels = labels;
        this.state = ProcessState.READY;
        this.deadline = instrucoes.size();
        for (Instrucao instrucao : instrucoes) {
            if (instrucao.getMnemonico().equalsIgnoreCase("SYSCALL")) {
                this.deadline = this.deadline + 2;
            }
        }
        this.remainingTime = this.deadline;
        this.hardDeadline = arrivalTime + deadline;
    }

    public void reiniciarPeriodo(int novoHardDeadline) {
        this.hardDeadline = novoHardDeadline;
        this.remainingTime = this.deadline;
        this.pcAtual = 0;
        this.accAtual = 0;
        this.state = ProcessState.READY;
    }

    public void updateProcessState(ProcessState newState) {
        this.state = newState;
    }

    public void decrementarRemainingTime() {
        this.remainingTime--;
    }

    public String getNome() {
        return nome;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getHardDeadline() {
        return hardDeadline;
    }

    public ProcessState getState() {
        return state;
    }

    public int getAccAtual() {
        return accAtual;
    }

    public void setAccAtual(int accAtual) {
        this.accAtual = accAtual;
    }

    public int getPcAtual() {
        return pcAtual;
    }

    public void setPcAtual(int pcAtual) {
        this.pcAtual = pcAtual;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int t) {
        this.tempoEspera = t;
    }

    public List<Instrucao> getInstrucoes() {
        return instrucoes;
    }

    public Map<String, Integer> getData() {
        return data;
    }

    public Map<String, Integer> getLabels() {
        return labels;
    }
}