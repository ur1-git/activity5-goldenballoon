import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class BalonOroMini extends JFrame {

    // OBJETIVO: mostrar candidatos al Balón de Oro y permitir votar por ellos.
    // Conserva la ventana y el título de la base mientras completas los pasos.

    // PASO 1: revisa Player.java.
    // Cada Player guarda nombre, equipo y votos. Sus getters permiten leerlos
    // y sumarVoto() incrementa sus votos. La lista siguiente guardará los jugadores.
    private ArrayList<Player> players = new ArrayList<>();

    // PASO 2: atributos de la tabla (ya declarados).
    // model guardará las filas y table las mostrará en la ventana.
    private DefaultTableModel model;
    private JTable table;

    public BalonOroMini() {
        setTitle("Balón de Oro - Clasificación de votos");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título de la base: ocupa la zona NORTH.
        JLabel titulo = new JLabel("BALÓN DE ORO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(170, 125, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // PASO 3: crea el modelo de la tabla.
        String[] columns = {"Position", "Player", "Votes"};
        int votesColumn = 3;

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int file, int column) {
                return (column == votesColumn);
            }
            // después de editar una celda deberíamos actualizar la
        };
        model.addTableModelListener(e -> {
            if ((e.getType() == TableModelEvent.UPDATE) && (e.getColumn() == 3)) {
                int row = e.getFirstRow();
                int votes = Integer.parseInt(model.getValueAt(row, 3).toString());
                players.get(row).setVotes(votes);
            }
        });

        // PASO 4: muestra la tabla en el centro, debajo del título.
        // creates a table using the information of model
        table = new JTable(model);

        // i think this just adds a scroll bar when needed
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // PASO 5: prepara la zona de botones.
        // a) Crea un JPanel llamado panelBotones con un new FlowLayout().
        //    FlowLayout coloca los botones uno junto a otro.
        JPanel buttonsPanel = new JPanel();

        JButton
        // b) Crea dos JButton llamados botonAnadir y botonVotar con los textos
        //    "Añadir jugador" y "Votar", respectivamente.
        // c) Añade ambos botones al panel usando panelBotones.add(...).
        // d) Añade el panel con add(..., BorderLayout.SOUTH).
        // e) Ejecuta BalonOroMini: comprueba que los botones aparecen abajo.

        // PASO 6: conecta el botón de añadir.
        // a) Usa botonAnadir.addActionListener(e -> { ... });
        //    Dentro de las llaves escribe las acciones de los apartados siguientes.
        // b) Pide nombre y equipo con JOptionPane.showInputDialog(this, "...").
        // c) Si alguna respuesta es null (Cancelar) o está vacía, sal con return.
        //    Comprueba primero null, antes de llamar a isBlank().
        // d) Crea un Player con esos datos y añádelo a players usando add().
        // e) Añade una fila con model.addRow(new Object[] { ... }).
        //    Dentro del array coloca nombre, equipo y votos usando los getters.
        // f) Ejecuta BalonOroMini y añade dos jugadores para comprobar el resultado.

        // PASO 7: conecta botonVotar con otro addActionListener.
        // a) Guarda table.getSelectedRow() en una variable int llamada fila.
        // b) Si fila vale -1, no hay selección: muestra un aviso con
        //    JOptionPane.showMessageDialog(this, "Selecciona un jugador");
        //    Después sal del listener con return.
        // c) Obtén el jugador seleccionado con players.get(fila).
        //    La primera fila y el primer elemento de la lista tienen índice 0.
        // d) Llama al método sumarVoto() de ese jugador.
        // e) Actualiza sus votos con model.setValueAt(jugador.getVotos(), fila, 2).
        //    Usa jugador como nombre de la variable del apartado c).
        //    La columna Votos tiene índice 2 porque las columnas empiezan en 0.

        // PASO 8: comprobación final.
        // Añade dos jugadores, selecciona uno y pulsa Votar varias veces.
        // Comprueba que solo aumentan sus votos. Prueba también a cancelar
        // al añadir y a votar sin seleccionar ninguna fila.
        // Al cerrar el programa se pierden los datos: en este ejercicio es normal.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BalonOroMini ventana = new BalonOroMini();
            ventana.setVisible(true);
        });
    }

    // dos botones a la izquierda y uno a la derecha, hay que usar un layout con
    // dos layout dentro, uno a cada lado y en sentido contrario, uno va de
    // izquierda a derecha y otro de derecha a izquierda
}
