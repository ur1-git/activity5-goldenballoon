import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class BalonOroMini extends JFrame {

    // i declare the arraylist where players are, one for row
    private ArrayList<Player> players = new ArrayList<>();
    // model is the object that stores the row and columns data
    private DefaultTableModel model;
    // jtable is the visual table the user sees when launching the app, without jtable, you wouldnt be able to see model anyway
    private JTable table;

    // the constructor of this class, as it extends a jframe, it sets its value as if it were a jframe
    public BalonOroMini() {
        setTitle("Balón de Oro - Clasificación de votos");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // this is a label that shows text, we modify its values so it is golden colour and has big letters
        JLabel titulo = new JLabel("BALÓN DE ORO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(170, 125, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10)); // adds space around the title: top, left, bottom, right
        add(titulo, BorderLayout.NORTH); // this adds "titulo" to the jframe and places it in the top of the window

        String[] columns = {"Posición", "Jugador", "Equipo", "País", "Votos"}; // we make a string array that holds the name of each column
        int votesColumn = 4; // the votes column is 4, i added this variable so you can change it here instead of hardcoding it through the code

        // we now instantiate the table data model
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { // we can only edit votes
                return column == votesColumn;
            }
        };
        // this runs when the table data changes, we only need changes in votes
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == votesColumn) {
                int row = e.getFirstRow();
                try {
                    int votes = Integer.parseInt(model.getValueAt(row, votesColumn).toString()); // turns the cell text into an int
                    if (votes < 0) {
                        throw new NumberFormatException();
                    }
                    players.get(row).setVotes(votes); // updates the player too, not just the table
                } catch (NumberFormatException ex) {
                    // if the number is invalid, we show a message and put the old votes back
                    JOptionPane.showMessageDialog(this, "Introduce un número entero de votos igual o mayor que 0");
                    model.setValueAt(players.get(row).getVotos(), row, votesColumn);
                }
            }
        });

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table); // lets us scroll when there are too many rows
        add(scrollPane, BorderLayout.CENTER);

        // two smaller panels inside this one, so the buttons can go on each side
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addButton = new JButton("Añadir jugador");
        JButton sortButton = new JButton("Ordenar votos");
        JButton deleteButton = new JButton("Eliminar jugador");

        leftPanel.add(addButton);
        leftPanel.add(sortButton);
        rightPanel.add(deleteButton);
        buttonsPanel.add(leftPanel, BorderLayout.WEST);
        buttonsPanel.add(rightPanel, BorderLayout.EAST);
        add(buttonsPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            // if we cancel or leave a field empty, we stop adding the player
            String nombre = JOptionPane.showInputDialog(this, "Nombre del jugador:");
            if (nombre == null || nombre.trim().isEmpty()) {
                return;
            }
            String equipo = JOptionPane.showInputDialog(this, "Equipo del jugador:");
            if (equipo == null || equipo.trim().isEmpty()) {
                return;
            }
            String pais = JOptionPane.showInputDialog(this, "País del jugador:");
            if (pais == null || pais.trim().isEmpty()) {
                return;
            }

            // trim removes spaces at the start and end, then we add the player to the list and table
            Player jugador = new Player(nombre.trim(), equipo.trim(), pais.trim());
            players.add(jugador);
            model.addRow(new Object[] {players.size(), jugador.getNombre(), jugador.getEquipo(),
                    jugador.getPais(), jugador.getVotos()});
        });

        sortButton.addActionListener(e -> {
            // finishes editing the cell before moving the rows around
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            players.sort((p1, p2) -> Integer.compare(p2.getVotos(), p1.getVotos())); // compares two players, highest votes first
            model.setRowCount(0); // clears the table rows, the players are still in the list
            // fills the table again using the new order
            for (int i = 0; i < players.size(); i++) {
                Player jugador = players.get(i);
                model.addRow(new Object[] {i + 1, jugador.getNombre(), jugador.getEquipo(),
                        jugador.getPais(), jugador.getVotos()});
            }
        });

        deleteButton.addActionListener(e -> {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            int row = table.getSelectedRow();
            if (row == -1) { // -1 means no row is selected
                JOptionPane.showMessageDialog(this, "Selecciona un jugador");
                return;
            }
            // removes the selected player from both places
            players.remove(row);
            model.removeRow(row);
            // fixes the position numbers so there are no gaps after deleting
            for (int i = 0; i < players.size(); i++) {
                model.setValueAt(i + 1, i, 0);
            }
        });
    }

    public static void main(String[] args) {
        // opens the window on the thread swing uses for the interface
        SwingUtilities.invokeLater(() -> {
            BalonOroMini ventana = new BalonOroMini();
            ventana.setVisible(true);
        });
    }
}
