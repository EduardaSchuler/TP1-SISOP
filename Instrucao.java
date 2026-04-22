import java.util.Random;
import java.util.Scanner;

public class Instrucao {
    private String mnemonico;
    private String operando;
    private boolean modoImediato;

    public Instrucao(String mnemonico, String operando) {
        this.mnemonico = mnemonico;

        if (operando.startsWith("#")) {
            this.modoImediato = true;
            this.operando = operando.substring(1);
        } else {
            this.modoImediato = false;
            this.operando = operando;
        }
    }

    public int funcao(int acc, Processo processo) {
        switch (getMnemonico()) {
            // aritmético
            case "ADD":
                if (modoImediato)
                    return acc + Integer.parseInt(getOperando());
                else
                    return acc + processo.getDados().get(getOperando());
            case "SUB":
                if (modoImediato)
                    return acc - Integer.parseInt(getOperando());
                else
                    return acc - processo.getDados().get(getOperando());
            case "MUL":
                if (modoImediato)
                    return acc * Integer.parseInt(getOperando());
                else
                    return acc * processo.getDados().get(getOperando());
            case "DIV":
                if (modoImediato)
                    return acc / Integer.parseInt(getOperando());
                else
                    return acc / processo.getDados().get(getOperando());
                // memória
            case "LOAD":
                if (modoImediato)
                    return Integer.parseInt(getOperando());
                else
                    return processo.getDados().get(getOperando());
            case "STORE":
                processo.getDados().put(getOperando(), acc);
                return acc;
            case "SYSCALL":
                int indice = Integer.parseInt(operando);
                if (indice == 0) {
                    processo.updateProcessState(Processo.ProcessState.DONE);
                } if (indice == 1) {
                    // imprime acc e bloqueia
                    int duracaoWait = new Random().nextInt(3) + 1; // +1 pra nao gerar um valor de 0
                    processo.updateProcessState(Processo.ProcessState.WAIT);
                } if (indice == 2) {
                    // lê valor e bloqueia
                    processo.updateProcessState(Processo.ProcessState.WAIT);
                } 
                return acc;
            default:
                return acc;
        }
    }

    public String getMnemonico() {
        return mnemonico;
    }

    public String getOperando() {
        return operando;
    }

}
