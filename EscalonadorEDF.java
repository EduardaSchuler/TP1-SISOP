import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EscalonadorEDF {
    private List<Processo> processos;
    private List<Processo> filaProntos;
    private List<Processo> filaEspera;

    private int tempoAtual;

    public EscalonadorEDF(List<Processo> processos) {
        this.processos = new ArrayList<>(processos);
        this.filaProntos = new ArrayList<>();
        this.filaEspera = new ArrayList<>();
        this.tempoAtual = 0;
    }

    public void executar() {
        while (!todosFinalizados()) {
            adicionarProcessosQueChegaram();
            desbloquearProcessos();

            Processo processoAtual = escolherProcessoEDF();

            if (processoAtual == null) {
                System.out.println("Tempo " + tempoAtual + ": CPU ociosa");
                tempoAtual++;
                continue;
            }

            executarUmaInstrucao(processoAtual);

            tempoAtual++;
        }

        System.out.println("\nExecução finalizada no tempo " + tempoAtual);
    }

    private void adicionarProcessosQueChegaram() {
        for (Processo p : processos) {
            if (p.getArrivalTime() == tempoAtual && p.getState() == Processo.ProcessState.READY) {
                if (!filaProntos.contains(p)) {
                    filaProntos.add(p);

                    System.out.println("Tempo " + tempoAtual + ": processo " + p.getNome() + " chegou | deadline absoluto = " + p.getHardDeadline());
                }
            }
        }
    }

    private void desbloquearProcessos() {
        List<Processo> desbloqueados = new ArrayList<>();

        for (Processo p : filaEspera) {
            if (p.getTempoEspera() <= tempoAtual) {
                p.updateProcessState(Processo.ProcessState.READY);
                desbloqueados.add(p);
                filaProntos.add(p);

                System.out.println("Tempo " + tempoAtual + ": processo " + p.getNome() + " voltou para READY");
            }
        }

        filaEspera.removeAll(desbloqueados);
    }

    private Processo escolherProcessoEDF() {
        if (filaProntos.isEmpty()) {
            return null;
        }

        filaProntos.sort(Comparator.comparingInt(Processo::getHardDeadline));

        Processo escolhido = filaProntos.remove(0);
        escolhido.updateProcessState(Processo.ProcessState.RUNNING);

        return escolhido;
    }

    private void executarUmaInstrucao(Processo p) {
        if (p.getPcAtual() >= p.getInstrucoes().size()) {
            p.updateProcessState(Processo.ProcessState.DONE);

            System.out.println("Tempo " + tempoAtual + ": processo " + p.getNome() + " finalizado");
            return;
        }

        Instrucao instrucao = p.getInstrucoes().get(p.getPcAtual());

        System.out.println("Tempo " + tempoAtual + ": executando " + p.getNome() + " | instrução = "
                + instrucao.getMnemonico() + " | deadline absoluto = " + p.getHardDeadline());

        int pcAntes = p.getPcAtual();

        int novoAcc = instrucao.funcao(p.getAccAtual(), p, tempoAtual);

        p.setAccAtual(novoAcc);

        if (p.getState() == Processo.ProcessState.DONE) {
            System.out.println("Tempo " + tempoAtual + ": processo " + p.getNome() + " finalizado por SYSCALL 0");
            return;
        }

        if (p.getState() == Processo.ProcessState.BLOCKED) {
            p.setPcAtual(pcAntes + 1);
            p.decrementarRemainingTime();
            filaEspera.add(p);

            System.out.println("Tempo " + tempoAtual + ": processo " + p.getNome() + " bloqueado por SYSCALL");
            return;
        }

        if (p.getPcAtual() == pcAntes) {
            p.setPcAtual(pcAntes + 1);
        }

        p.decrementarRemainingTime();

        if (p.getPcAtual() >= p.getInstrucoes().size()) {
            p.updateProcessState(Processo.ProcessState.DONE);

            System.out.println("Tempo " + tempoAtual + ": processo " + p.getNome() + " finalizado");
            return;
        }

        p.updateProcessState(Processo.ProcessState.READY);
        filaProntos.add(p);
    }

    private boolean todosFinalizados() {
        for (Processo p : processos) {
            if (p.getState() != Processo.ProcessState.DONE) {
                return false;
            }
        }

        return true;
    }
}