package alumno.JFrame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import org.json.*;

public class AlumnoJFrame extends javax.swing.JFrame {

    // URL del servicio REST
    private static final String BASE_URL = "http://localhost:8080/alumnos";

    // Componentes de la interfaz que vamos a agregar - DEFINIDOS FUERA DE LA SECCIÓN PROTEGIDA
    private JTable alumnosTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nombreField, edadField;
    // Campos extendidos
    private JTextField carreraField, paisField, promedioField, semestreField, emailField;
    private JButton refreshButton, getByIdButton, addButton, updateButton, deleteButton, clearButton;
    private JPanel formPanel, buttonPanel, tablePanel;

    public AlumnoJFrame() {
        // No modificar esto - es generado por NetBeans
        initComponents();

        // Agregar nuestras personalizaciones DESPUÉS
        setupAdditionalComponents();
        setupLayout();
        setupListeners();

        // Configuración adicional de la ventana
        setTitle("Gestión de Alumnos");
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Cargar datos iniciales
        try {
            refreshAlumnos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar al servidor. Verifique que esté en ejecución.",
                    "Error de conexión",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // Este método configura nuestros componentes adicionales
    private void setupAdditionalComponents() {
        // Tabla de alumnos con campos extendidos
        String[] columns = {"ID", "Nombre", "Edad", "Carrera", "País", "Promedio", "Semestre"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        alumnosTable = new JTable(tableModel);
        alumnosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alumnosTable.getTableHeader().setReorderingAllowed(false);

        // Configurar ancho preferido de columnas
        alumnosTable.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        alumnosTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        alumnosTable.getColumnModel().getColumn(2).setPreferredWidth(50);  // Edad
        alumnosTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Carrera
        alumnosTable.getColumnModel().getColumn(4).setPreferredWidth(100); // País
        alumnosTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Promedio
        alumnosTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Semestre

        // Campos de texto para todos los atributos
        idField = new JTextField(10);
        idField.setEditable(false); // El ID no se edita manualmente
        nombreField = new JTextField(20);
        edadField = new JTextField(5);
        carreraField = new JTextField(20);
        paisField = new JTextField(15);
        promedioField = new JTextField(5);
        semestreField = new JTextField(5);
        emailField = new JTextField(25);

        // Botones
        refreshButton = new JButton("Refrescar Lista");
        getByIdButton = new JButton("Buscar por ID");
        addButton = new JButton("Añadir");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        clearButton = new JButton("Limpiar");
    }

    // Este método configura el layout
    private void setupLayout() {
        // Crear un nuevo contentPane en lugar de modificar el existente
        Container contentPane = new JPanel(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Panel del formulario
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Alumno"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 1: ID y Carrera
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(idField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Carrera:"), gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(carreraField, gbc);

        // Fila 2: Nombre y País
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nombreField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("País:"), gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(paisField, gbc);

        // Fila 3: Edad y Semestre
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Edad:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(edadField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Semestre:"), gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(semestreField, gbc);

        // Fila 4: Promedio y Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Promedio:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(promedioField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(emailField, gbc);

        // Panel de botones
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(refreshButton);
        buttonPanel.add(getByIdButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Panel de la tabla
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista de Alumnos"));
        tablePanel.add(new JScrollPane(alumnosTable), BorderLayout.CENTER);

        // Añadir todo al frame principal
        contentPane.add(formPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(tablePanel, BorderLayout.CENTER);
    }

    // Este método configura los listeners de eventos
    private void setupListeners() {
        // Listener para selección en la tabla
        alumnosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && alumnosTable.getSelectedRow() != -1) {
                int row = alumnosTable.getSelectedRow();
                idField.setText(alumnosTable.getValueAt(row, 0).toString());
                nombreField.setText(alumnosTable.getValueAt(row, 1).toString());
                edadField.setText(alumnosTable.getValueAt(row, 2).toString());

                // Valores para los campos extendidos (con manejo seguro de null)
                carreraField.setText(alumnosTable.getValueAt(row, 3) != null
                        ? alumnosTable.getValueAt(row, 3).toString() : "");
                paisField.setText(alumnosTable.getValueAt(row, 4) != null
                        ? alumnosTable.getValueAt(row, 4).toString() : "");
                promedioField.setText(alumnosTable.getValueAt(row, 5) != null
                        ? alumnosTable.getValueAt(row, 5).toString() : "");
                semestreField.setText(alumnosTable.getValueAt(row, 6) != null
                        ? alumnosTable.getValueAt(row, 6).toString() : "");

                // El email no se muestra en la tabla, lo obtenemos del backend
                try {
                    getAlumnoById(Integer.parseInt(idField.getText()));
                } catch (Exception ex) {
                    // Si falla, no actualizamos el campo email
                }
            }
        });

        // Listeners para los botones
        refreshButton.addActionListener(e -> {
            try {
                refreshAlumnos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al obtener la lista de alumnos: " + ex.getMessage(),
                        "Error de conexión",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        getByIdButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Introduce el ID del alumno:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    getAlumnoById(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton.addActionListener(e -> {
            if (validateForm(false)) {
                addAlumno();
            }
        });

        updateButton.addActionListener(e -> {
            if (validateForm(true)) {
                updateAlumno();
            }
        });

        deleteButton.addActionListener(e -> {
            if (!idField.getText().isEmpty()) {
                int option = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que quieres eliminar este alumno?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    deleteAlumno();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un alumno para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> clearForm());
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateForm(boolean isUpdate) {
        // Para actualizar necesitamos un ID
        if (isUpdate && idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un alumno para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar nombre
        if (nombreField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar edad
        try {
            int edad = Integer.parseInt(edadField.getText().trim());
            if (edad < 0 || edad > 120) {
                JOptionPane.showMessageDialog(this, "La edad debe estar entre 0 y 120", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar promedio si está presente
        if (!promedioField.getText().trim().isEmpty()) {
            try {
                double promedio = Double.parseDouble(promedioField.getText().trim());
                if (promedio < 0 || promedio > 10) {
                    JOptionPane.showMessageDialog(this, "El promedio debe estar entre 0 y 10", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El promedio debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Validar semestre si está presente
        if (!semestreField.getText().trim().isEmpty()) {
            try {
                int semestre = Integer.parseInt(semestreField.getText().trim());
                if (semestre < 1 || semestre > 12) {
                    JOptionPane.showMessageDialog(this, "El semestre debe estar entre 1 y 12", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El semestre debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private void refreshAlumnos() throws Exception {
        // Todo el código, sin el try-catch
        String response = sendRequest(BASE_URL, "GET", null);
        JSONArray jsonArray = new JSONArray(response);

        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Llenar con los nuevos datos
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // Manejo seguro de valores opcionales
            String carrera = jsonObject.has("carrera") ? jsonObject.getString("carrera") : "";
            String pais = jsonObject.has("paisOrigen") ? jsonObject.getString("paisOrigen") : "";
            Double promedio = jsonObject.has("promedio") ? jsonObject.getDouble("promedio") : null;
            Integer semestre = jsonObject.has("semestre") ? jsonObject.getInt("semestre") : null;

            Object[] row = {
                jsonObject.getInt("id"),
                jsonObject.getString("nombre"),
                jsonObject.getInt("edad"),
                carrera,
                pais,
                promedio,
                semestre
            };
            tableModel.addRow(row);
        }

        // Limpiar el formulario
        clearForm();
    }

    private void getAlumnoById(int id) {
        try {
            String response = sendRequest(BASE_URL + "/" + id, "GET", null);
            JSONObject jsonObject = new JSONObject(response);

            // Actualizar el formulario con todos los campos
            idField.setText(String.valueOf(jsonObject.getInt("id")));
            nombreField.setText(jsonObject.getString("nombre"));
            edadField.setText(String.valueOf(jsonObject.getInt("edad")));

            // Manejo seguro de valores opcionales
            carreraField.setText(jsonObject.has("carrera") ? jsonObject.getString("carrera") : "");
            paisField.setText(jsonObject.has("paisOrigen") ? jsonObject.getString("paisOrigen") : "");
            promedioField.setText(jsonObject.has("promedio") ? String.valueOf(jsonObject.getDouble("promedio")) : "");
            semestreField.setText(jsonObject.has("semestre") ? String.valueOf(jsonObject.getInt("semestre")) : "");
            emailField.setText(jsonObject.has("email") ? jsonObject.getString("email") : "");

            // Seleccionar la fila correspondiente en la tabla
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (Integer.parseInt(tableModel.getValueAt(i, 0).toString()) == id) {
                    alumnosTable.setRowSelectionInterval(i, i);
                    break;
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener el alumno: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAlumno() {
        try {
            // Crear el JSON con los datos del alumno
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombreField.getText().trim());
            jsonObject.put("edad", Integer.parseInt(edadField.getText().trim()));

            // Añadir campos opcionales si tienen valor
            if (!carreraField.getText().trim().isEmpty()) {
                jsonObject.put("carrera", carreraField.getText().trim());
            }
            if (!paisField.getText().trim().isEmpty()) {
                jsonObject.put("paisOrigen", paisField.getText().trim());
            }
            if (!promedioField.getText().trim().isEmpty()) {
                jsonObject.put("promedio", Double.parseDouble(promedioField.getText().trim()));
            }
            if (!semestreField.getText().trim().isEmpty()) {
                jsonObject.put("semestre", Integer.parseInt(semestreField.getText().trim()));
            }
            if (!emailField.getText().trim().isEmpty()) {
                jsonObject.put("email", emailField.getText().trim());
            }

            // Enviar la solicitud POST
            String response = sendRequest(BASE_URL, "POST", jsonObject.toString());

            // Refrescar la tabla
            refreshAlumnos();

            JOptionPane.showMessageDialog(this, "Alumno añadido correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al añadir el alumno: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAlumno() {
        try {
            int id = Integer.parseInt(idField.getText());

            // Crear el JSON con los datos del alumno
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", nombreField.getText().trim());
            jsonObject.put("edad", Integer.parseInt(edadField.getText().trim()));

            // Añadir campos opcionales si tienen valor
            if (!carreraField.getText().trim().isEmpty()) {
                jsonObject.put("carrera", carreraField.getText().trim());
            }
            if (!paisField.getText().trim().isEmpty()) {
                jsonObject.put("paisOrigen", paisField.getText().trim());
            }
            if (!promedioField.getText().trim().isEmpty()) {
                jsonObject.put("promedio", Double.parseDouble(promedioField.getText().trim()));
            }
            if (!semestreField.getText().trim().isEmpty()) {
                jsonObject.put("semestre", Integer.parseInt(semestreField.getText().trim()));
            }
            if (!emailField.getText().trim().isEmpty()) {
                jsonObject.put("email", emailField.getText().trim());
            }

            // Enviar la solicitud PUT
            String response = sendRequest(BASE_URL + "/" + id, "PUT", jsonObject.toString());

            // Refrescar la tabla
            refreshAlumnos();

            JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el alumno: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAlumno() {
        try {
            int id = Integer.parseInt(idField.getText());

            // Enviar la solicitud DELETE
            String response = sendRequest(BASE_URL + "/" + id, "DELETE", null);

            // Refrescar la tabla
            refreshAlumnos();

            JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar el alumno: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        nombreField.setText("");
        edadField.setText("");
        carreraField.setText("");
        paisField.setText("");
        promedioField.setText("");
        semestreField.setText("");
        emailField.setText("");
        alumnosTable.clearSelection();
    }

    private String sendRequest(String urlStr, String method, String jsonBody) throws Exception {
        System.out.println("Intentando conectar a: " + urlStr);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(10000); // 10 segundos de timeout
        conn.setReadTimeout(10000);    // 10 segundos para leer

        if (jsonBody != null) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }
        System.out.println("Código de respuesta: " + conn.getResponseCode());

        int responseCode = conn.getResponseCode();

        if (responseCode >= 200 && responseCode < 300) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                StringBuilder error = new StringBuilder();
                String errorLine;
                while ((errorLine = br.readLine()) != null) {
                    error.append(errorLine.trim());
                }
                throw new Exception("HTTP error code: " + responseCode + ", message: " + error.toString());
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
