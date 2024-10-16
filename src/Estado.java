import java.util.*;
public class Estado {
    String nome;
    boolean isFinal;

    public Estado(String nome, boolean isFinal) {
        this.nome = nome;
        this.isFinal = isFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estado estado = (Estado) o;
        return Objects.equals(nome, estado.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}