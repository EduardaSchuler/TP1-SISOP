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

        // ordena alfabeticamente para definir arrival time
        Arrays.sort(arquivos, (a, b) -> a.getName().compareTo(b.getName()));

        List<Processo> processos = new ArrayList<>();

        for (int i = 0; i < arquivos.length; i++) {
            System.out.println("Carregando: " + arquivos[i].getName() + " (arrivalTime=" + i + ")");
            Processo p = new Processo(i); // arrival time = índice alfabético
            p.carregarArquivo(arquivos[i].getPath());
            processos.add(p);
            System.out.println("  Ci=Pi=" + p.getDeadline() + " | instrucoes=" + p.getInstrucoes().size());
        }

        // aqui vai entrar o escalonador
        // EscalonadorEDF esc = new EscalonadorEDF();
        // esc.executar(processos);
    }
}