import java.util.ArrayList;
import java.util.Comparator;

public class CadastroEvento {

    private ArrayList<Evento> eventos;

    public CadastroEvento() {
        eventos = new ArrayList<>();
    }

    public boolean cadastrarEvento(Evento evento) {
        if (evento == null) {
            System.out.println("Erro: O evento é nulo.");
            return false;
        }

        for (Evento e : eventos) {
            if (evento.getCodigo().equals(e.getCodigo())) {
                System.out.println("Erro: Já existe um evento com o código indicado.");
                return false;
            }
        }

        eventos.add(evento);
        eventos.sort(Comparator.comparing(Evento::getCodigo));
        return true;
    }

    @Override
    public String toString() {
        if (eventos.isEmpty()) {
            return "Lista de eventos está vazia.";
        }
        eventos.sort(Comparator.comparing(Evento::getCodigo));
        String eventoString = "";
        for (Evento e : eventos) {
            eventoString += String.format(
                    "\nCodinome: %s\nQuantidade: %s\nLatitude: %s\nLongitude: %s\n",
                    e.getCodigo(), e.getData(), e.getLatitude(), e.getLongitude());
        }

        return eventoString;
    }

}
