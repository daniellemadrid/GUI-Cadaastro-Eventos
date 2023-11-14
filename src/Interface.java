import javax.swing.*;
import java.awt.*;
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
        cadastrarButton.addActionListener(e -> cadastrarEvento());

        JButton limparButton = new JButton("Limpar");
        limparButton.addActionListener(e -> limparCampos());

        JButton mostrarDadosButton = new JButton("Mostrar Dados");
        mostrarDadosButton.addActionListener(e -> mostrarDadosCadastrados());

        JButton finalizarButton = new JButton("Finalizar");
        finalizarButton.addActionListener(e -> finalizar());

        codigoField.addActionListener(e -> dataField.requestFocusInWindow());
        dataField.addActionListener(e -> latitudeField.requestFocusInWindow());
        latitudeField.addActionListener(e -> longitudeField.requestFocusInWindow());
        longitudeField.addActionListener(e -> cadastrarEvento());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Código:"));
        inputPanel.add(codigoField);
        inputPanel.add(new JLabel("Data:"));
        inputPanel.add(dataField);
        inputPanel.add(new JLabel("Latitude:"));
        inputPanel.add(latitudeField);
        inputPanel.add(new JLabel("Longitude:"));
        inputPanel.add(longitudeField);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(limparButton);
        buttonPanel.add(mostrarDadosButton);
        buttonPanel.add(finalizarButton);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.CENTER);

        Container container = getContentPane();
        container.setLayout(new BorderLayout(5, 5));
        container.add(panel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cadastrarEvento() {
        String codigo = codigoField.getText();
        String data = dataField.getText();
        double latitude, longitude;

        try {
            latitude = Double.parseDouble(latitudeField.getText());
            longitude = Double.parseDouble(longitudeField.getText());

            if (codigo.isEmpty() || data.isEmpty()) {
                throw new IllegalArgumentException("Código e Data são obrigatórios.");
            }

            if (eventos.stream().anyMatch(evento -> codigo.equals(evento.getCodigo()))) {
                throw new IllegalArgumentException("Já existe um evento com o código indicado.");
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
                        "\nCódigo: %s\nData: %s\nLatitude: %s\nLongitude: %s\n",
                        evento.getCodigo(), evento.getData(), evento.getLatitude(), evento.getLongitude()));
            }
            mensagemArea.setText(eventosString.toString());
        }
    }

    private void finalizar() {
        System.exit(0);
    }
}
