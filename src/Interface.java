import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Interface extends JFrame {
    private List<Evento> eventos = new ArrayList<>();
    private JTextArea mensagemArea;
    private JTextField codigoField, dataField, latitudeField, longitudeField;

    public Interface() {
        super("Cadastro de Eventos");

        codigoField = new JTextField(10);
        dataField = new JTextField(10);
        latitudeField = new JTextField(10);
        longitudeField = new JTextField(10);

        mensagemArea = new JTextArea(10, 30);
        mensagemArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(mensagemArea);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarEvento();
            }
        });

        JButton limparButton = new JButton("Limpar");
        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        JButton mostrarDadosButton = new JButton("Mostrar Dados");
        mostrarDadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDadosCadastrados();
            }
        });

        JButton finalizarButton = new JButton("Finalizar");
        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.add(new JLabel("Código:"));
        panel.add(codigoField);
        panel.add(new JLabel("Data:"));
        panel.add(dataField);
        panel.add(new JLabel("Latitude:"));
        panel.add(latitudeField);
        panel.add(new JLabel("Longitude:"));
        panel.add(longitudeField);
        panel.add(new JLabel());
        panel.add(cadastrarButton);
        panel.add(limparButton);
        panel.add(mostrarDadosButton);
        panel.add(finalizarButton);

        Container container = getContentPane();
        container.setLayout(new BorderLayout(5, 5));
        container.add(panel, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cadastrarEvento() {
        String codigo = codigoField.getText();
        String data = dataField.getText();
        double latitude = Double.parseDouble(latitudeField.getText());
        double longitude = Double.parseDouble(longitudeField.getText());

        try {
            if (codigo.isEmpty() || data.isEmpty()) {
                throw new IllegalArgumentException("Código e Data são obrigatórios.");
            }

            for (Evento evento : eventos) {
                if (codigo.equals(evento.getCodigo())) {
                    throw new IllegalArgumentException("Já existe um evento com o código indicado.");
                }
            }

            eventos.add(new Evento(codigo, data, latitude, longitude));
            Collections.sort(eventos, Comparator.comparing(Evento::getCodigo));
            mensagemArea.setText("Evento cadastrado com sucesso.");
        } catch (NumberFormatException ex) {
            mensagemArea.setText("Erro: Latitude e Longitude devem ser valores numéricos.");
        } catch (IllegalArgumentException ex) {
            mensagemArea.setText("Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        codigoField.setText("");
        dataField.setText("");
        latitudeField.setText("");
        longitudeField.setText("");
        mensagemArea.setText("");
    }

    private void mostrarDadosCadastrados() {
        if (eventos.isEmpty()) {
            mensagemArea.setText("Lista de eventos está vazia.");
        } else {
            StringBuilder eventosString = new StringBuilder();
            for (Evento evento : eventos) {
                eventosString.append(String.format(
                        "\nCodinome: %s\nQuantidade: %s\nLatitude: %s\nLongitude: %s\n",
                        evento.getCodigo(), evento.getData(), evento.getLatitude(), evento.getLongitude()));
            }
            mensagemArea.setText(eventosString.toString());
        }
    }

    private void finalizar() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interface();
            }
        });
    }
}
