import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    // contexto do processo
    private int accAtual = 0;
    private int pcAtual = 0;
    private int tempoEspera = -1;

    // instrucoes e variaveis do programa
    private List<Instrucao> instrucoes = new ArrayList<>();
    private Map<String, Integer> data = new HashMap<>(); // .data
    private Map<String, Integer> labels = new HashMap<>();

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
        this.deadline = instrucoes.size(); // isso faz sentido? ou tem que ser a quantidade de syscalls * um valor
                                           // prclock
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

    public Map<String, Integer> getData() {
        return data;
    }

    public Map<String, Integer> getLabels() {
        return labels;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    // apaga esse carregarArquivo e coloca a lógica de leitura do arquivo no escalonador, o processo só tem que ter os dados, não tem que saber de onde veio

    public void carregarArquivo(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        boolean emCodigo = false;
        boolean emDados = false;
        int indiceLinha = 0;

        while ((linha = br.readLine()) != null) {
            System.out.println("DEBUG raw: '" + linha + "' emCodigo=" + emCodigo);

            if (linha.isEmpty())
                continue;

            if (linha.equals(".code")) {
                emCodigo = true;
                emDados = false;
                continue;
            }
            if (linha.equals(".endcode")) {
                emCodigo = false;
                continue;
            }
            if (linha.equals(".data")) {
                emDados = true;
                emCodigo = false;
                continue;
            }
            if (linha.equals(".enddata")) {
                emDados = false;
                continue;
            }

            if (emCodigo) {
                
                // verifica se tem label na linha
                if (linha.contains(":")) {
                    String label = linha.split(":")[0].trim().toLowerCase();
                    labels.put(label, indiceLinha); // salva label com índice atual
                    linha = linha.split(":", 2)[1].trim(); // pega o resto da linha
                    if (linha.isEmpty()) continue; // label sozinho na linha, vai para próxima
                }

                String[] partes = linha.trim().split("\\s+", 2); // trim() aqui também
                String mnemonico = partes[0].toUpperCase();
                String operando  = partes.length > 1 ? partes[1].trim() : "";
                System.out.println("DEBUG cod: mnemonico='" + mnemonico + "' operando='" + operando + "'");
                instrucoes.add(new Instrucao(mnemonico, operando));
                indiceLinha++;


            } else if (emDados) {
                String[] partes = linha.trim().split("\\s+");
                data.put(partes[0].toLowerCase(), Integer.parseInt(partes[1]));
            }
        }

        br.close();
        addDeadlineFromPrograma();
    }
}