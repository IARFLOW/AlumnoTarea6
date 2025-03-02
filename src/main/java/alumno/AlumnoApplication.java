package alumno;

import alumno.JFrame.AlumnoJFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AlumnoApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Intentar usar el Look and Feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Crear y mostrar la ventana de la aplicación
            AlumnoJFrame gui = new AlumnoJFrame();
            gui.setVisible(true);
            
            System.out.println("Aplicación de gestión de alumnos iniciada");
        });
    }
}