import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String PASTA = "programasExemplo";

    public static void main(String[] args) throws IOException {

        File pasta = new File(PASTA);
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado em '" + PASTA + "'.");
            return;
        }

        Carregador carregador = new Carregador();
        List<Processo> processos = new ArrayList<>();

        for (int i = 0; i < arquivos.length; i++) {
            Processo p = carregador.carregarArquivo(arquivos[i].getPath());
            processos.add(p);
            System.out.println("Carregado: " + arquivos[i].getName()
                    + " | arrivalTime=" + p.getArrivalTime()
                    + " | Ci=" + p.getDeadline());
        }

        EscalonadorEDF esc = new EscalonadorEDF(processos);
        esc.executar();
    }
}