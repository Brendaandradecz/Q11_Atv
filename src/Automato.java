import java.util.*;

public class Automato {
    Set<Estado> estados;
    Set<Character> alfabeto;
    Map<Estado, Map<Character, Estado>> transicoes;
    Estado estadoInicial;
    Set<Estado> estadosFinais;

    public Automato(Set<Estado> estados, Set<Character> alfabeto, Map<Estado, Map<Character, Estado>> transicoes, Estado estadoInicial, Set<Estado> estadosFinais) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
    }

    public Automato converteParaDeterministico() {
        Set<Set<Estado>> novosEstados = new HashSet<>();
        Map<Set<Estado>, Map<Character, Set<Estado>>> novasTransicoes = new HashMap<>();
        Set<Set<Estado>> novosEstadosFinais = new HashSet<>();

        Set<Estado> estadoInicialAFD = new HashSet<>();
        estadoInicialAFD.add(estadoInicial);
        novosEstados.add(estadoInicialAFD);

        Queue<Set<Estado>> fila = new LinkedList<>();
        fila.add(estadoInicialAFD);

        while (!fila.isEmpty()) {
            Set<Estado> estadosAtuais = fila.poll();
            Map<Character, Set<Estado>> transicoesEstado = new HashMap<>();

            for (char simbolo : alfabeto) {
                Set<Estado> proximosEstados = new HashSet<>();

                for (Estado estado : estadosAtuais) {
                    if (transicoes.containsKey(estado) && transicoes.get(estado).containsKey(simbolo)) {
                        proximosEstados.add(transicoes.get(estado).get(simbolo));
                    }
                }

                transicoesEstado.put(simbolo, proximosEstados);

                if (!novosEstados.contains(proximosEstados) && !proximosEstados.isEmpty()) {
                    novosEstados.add(proximosEstados);
                    fila.add(proximosEstados);
                }
            }

            novasTransicoes.put(estadosAtuais, transicoesEstado);

            for (Estado estado : estadosAtuais) {
                if (estadosFinais.contains(estado)) {
                    novosEstadosFinais.add(estadosAtuais);
                    break;
                }
            }
        }

        Set<Estado> estadosAFD = new HashSet<>();
        Map<Set<Estado>, Estado> estadoMap = new HashMap<>();
        for (Set<Estado> conjunto : novosEstados) {
            String nome = conjunto.toString();
            boolean isFinal = novosEstadosFinais.contains(conjunto);
            Estado novoEstado = new Estado(nome, isFinal);
            estadosAFD.add(novoEstado);
            estadoMap.put(conjunto, novoEstado);
        }

        Map<Estado, Map<Character, Estado>> transicoesAFD = new HashMap<>();
        for (Map.Entry<Set<Estado>, Map<Character, Set<Estado>>> entry : novasTransicoes.entrySet()) {
            Estado estadoOrigem = estadoMap.get(entry.getKey());
            Map<Character, Estado> transicoesEstadoAFD = new HashMap<>();

            for (Map.Entry<Character, Set<Estado>> transicao : entry.getValue().entrySet()) {
                if (!transicao.getValue().isEmpty()) {
                    Estado estadoDestino = estadoMap.get(transicao.getValue());
                    transicoesEstadoAFD.put(transicao.getKey(), estadoDestino);
                }
            }
            transicoesAFD.put(estadoOrigem, transicoesEstadoAFD);
        }

        Estado estadoInicialDeterministico = estadoMap.get(estadoInicialAFD);
        Set<Estado> estadosFinaisDeterministicos = new HashSet<>();
        for (Set<Estado> conjunto : novosEstadosFinais) {
            estadosFinaisDeterministicos.add(estadoMap.get(conjunto));
        }

        return new Automato(estadosAFD, alfabeto, transicoesAFD, estadoInicialDeterministico, estadosFinaisDeterministicos);
    }
}
