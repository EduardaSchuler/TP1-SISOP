import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processo {
    public enum ProcessState {
        READY, RUNNING, WAIT, DONE
    }

    private int arrivalTime;
    private int deadline;
    private int remainingTime;
    private int hardDeadline;
    private ProcessState state;

    private List<Instrucao> instrucoes = new ArrayList<>();
    private Map<String, Integer> data = new HashMap<>(); // .data

    private int ultimoSyscall = -1;

    Processo(int arrivalTime) {
        this.arrivalTime = arrivalTime;
        // this.deadline = deadline;
        this.hardDeadline = arrivalTime + deadline;
        this.remainingTime = deadline;
        this.state = ProcessState.READY;
    }

    // o responsável pela lógica de mudança de estado nao é o processo e sim o
    // escalonador que diz para qual estado o porcesso deve ir
    public void updateProcessState(ProcessState newState) {
        this.state = newState;
    }

    public void addDeadlineFromPrograma() {
        // logica de calculo de deadline a partir da quantidade de syscalls do programa
        this.deadline = instrucoes.size(); // isso faz sentido? ou tem que ser a quantidade de syscalls * um valor pro
                                           // clock
        this.remainingTime = deadline;
        this.hardDeadline = arrivalTime + deadline;
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

    public void decrementarRemaining() {
        this.remainingTime--;
    }

    public int getHardDeadline() {
        return hardDeadline;
    }

    public ProcessState getState() {
        return state;
    }

    public List<Instrucao> getInstrucoes() {
        return instrucoes;
    }

    public Map<String, Integer> getDados() {
        return data;
    }
}