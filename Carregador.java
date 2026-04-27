import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Carregador {

    public Processo carregarArquivo(String caminho) throws IOException {
        List<Instrucao> instrucoes = new ArrayList<>();
        Map<String, Integer> data = new HashMap<>();
        Map<String, Integer> labels = new HashMap<>();

        int arrivalTime = 1 + new Random().nextInt(10);

        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        int i = 0;

        while ((linha = br.readLine()) != null) {
            linha = linha.trim();

            if (linha.equals(".code")) {
                linha = br.readLine().trim();
                while (!linha.equals(".endcode")) {
                    if (linha.contains(":")) {
                        labels.put(linha.split(":")[0].trim().toLowerCase(), i);
                        linha = linha.split(":", 2)[1].trim();
                        if (linha.isEmpty())
                            continue;
                    }
                    if (!linha.isEmpty()) {
                        if (!linha.isEmpty()) {
                            String[] partes = linha.split(" ", 2);
                            if (partes.length > 1) {
                                instrucoes.add(new Instrucao(partes[0], partes[1].trim()));
                            } else {
                                instrucoes.add(new Instrucao(partes[0], ""));
                            }
                            i++;
                        }
                    }
                    linha = br.readLine().trim();
                }
            }

            if (linha.equals(".data")) {
                linha = br.readLine().trim();
                while (!linha.equals(".enddata")) {
                    if (!linha.isEmpty()) {
                        String[] partes = linha.split(" ");
                        String nome = partes[0];
                        String valor = partes[1];
                        if (data.containsKey(valor)) {
                            data.put(nome, data.get(valor));
                        } else {
                            data.put(nome, Integer.parseInt(valor));
                        }
                    }
                    linha = br.readLine().trim();
                }
            }
        }
        br.close();

        return new Processo(arrivalTime, instrucoes, data, labels);
    }
}