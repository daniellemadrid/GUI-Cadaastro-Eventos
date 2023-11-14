import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
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
        try {
            String codigo = validarCampoNumerico(codigoField.getText(), "Código");
            double latitude = Double.parseDouble(validarCampoNumerico(latitudeField.getText(), "Latitude"));
            double longitude = Double.parseDouble(validarCampoNumerico(longitudeField.getText(), "Longitude"));
            String data = validarFormatoData(dataField.getText());

            String codigoSemZeros = codigo.replaceFirst("^0+(?!$)", "");

            if (eventos.stream().anyMatch(evento -> codigoSemZeros.equals(evento.getCodigo()))) {
                throw new IllegalArgumentException("Já existe um evento com o código indicado.");
            }
            if (codigo.isEmpty() || data.isEmpty()) {
                throw new IllegalArgumentException("Código e Data são obrigatórios.");
            }

            eventos.add(new Evento(codigo, data, latitude, longitude));
            mensagemArea.setText("Evento cadastrado com sucesso.");
        } catch (Exception e) {
            mensagemArea.setText("Erro: " + e.getMessage());
        }
    }

    private String validarCampoNumerico(String valor, String nomeCampo) {
        if (valor.isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " é obrigatório.");
        }

        if (!valor.matches("\\d+")) {
            throw new NumberFormatException(nomeCampo + " deve conter apenas números.");
        }

        return valor;
    }

    private String validarFormatoData(String valor) {
        if (!valor.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("A data deve estar no formato dd/MM/yyyy.");
        }

        try {
            java.util.Date dataUtil = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(valor);
            return new java.text.SimpleDateFormat("dd/MM/yyyy").format(dataUtil);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de data inválido. Utilize o formato dd/MM/yyyy.");
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
            eventos.sort(Comparator.comparingInt(evento -> Integer.parseInt(evento.getCodigo())));

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

    private boolean isCodigoValido(String codigo) {
        return codigo.matches("^\\d+$");
    }

}