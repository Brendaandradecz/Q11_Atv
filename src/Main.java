import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Estado> estados = new HashSet<>();
        Estado q0 = new Estado("q0", false);
        Estado q1 = new Estado("q1", true);
        estados.add(q0);
        estados.add(q1);

        Set<Character> alfabeto = new HashSet<>();
        alfabeto.add('a');
        alfabeto.add('b');

        Map<Estado, Map<Character, Estado>> transicoes = new HashMap<>();

        Map<Character, Estado> transicoesQ0 = new HashMap<>();
        transicoesQ0.put('a', q1);
        transicoesQ0.put('b', q0);
        transicoes.put(q0, transicoesQ0);

        Map<Character, Estado> transicoesQ1 = new HashMap<>();
        transicoesQ1.put('a', q0);
        transicoesQ1.put('b', q1);
        transicoes.put(q1, transicoesQ1);

        Set<Estado> estadosFinais = new HashSet<>();
        estadosFinais.add(q1);

        Automato afn = new Automato(estados, alfabeto, transicoes, q0, estadosFinais);

        Automato afd = afn.converteParaDeterministico();

        System.out.println("Estados do AFD: " + afd.estados);
        System.out.println("Transições do AFD: " + afd.transicoes);
        System.out.println("Estado inicial do AFD: " + afd.estadoInicial);
        System.out.println("Estados finais do AFD: " + afd.estadosFinais);
    }
}
