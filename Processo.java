public class Processo{
    public enum  ProcessState{
        READY, RUNNING, WAIT, DONE
    }
    
    private int arrivalTime;
    private int deadline;
    private int remainingTime;
    private int hardDeadline;
    private ProcessState state;

    Processo(int arrivalTime, int deadline){
        this.arrivalTime = arrivalTime;
        this.deadline = deadline;
        this.hardDeadline = arrivalTime + deadline;
        this.remainingTime = deadline;
        this.state = ProcessState.READY;
    }

    // o responsável pela lógica de mudança de estado nao é o processo e sim o escalonador que diz para qual estado o porcesso deve ir 
    public void updateProcessState(ProcessState newState){
        this.state = newState;
    }
}