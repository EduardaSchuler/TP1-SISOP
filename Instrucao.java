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
            case "ADD": // nao pega os dados se for referencia para outra variavel
                if (modoImediato)
                    return acc + Integer.parseInt(getOperando());
                else
                    return acc + processo.getDados().get(operando.toLowerCase());
            case "SUB":
                if (modoImediato)
                    return acc - Integer.parseInt(getOperando());
                else
                    return acc - processo.getDados().get(operando.toLowerCase());
            case "MUL":
                if (modoImediato)
                    return acc * Integer.parseInt(getOperando());
                else
                    return acc * processo.getDados().get(operando.toLowerCase());
            case "DIV":
                if (modoImediato)
                    return acc / Integer.parseInt(getOperando());
                else
                    return acc / processo.getDados().get(operando.toLowerCase());
            // memória
            case "LOAD":
                if (modoImediato)
                    return Integer.parseInt(operando);
                else
                    return processo.getDados().get(operando.toLowerCase());
            case "STORE":
                processo.getDados().put(operando.toLowerCase(), acc);
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
