public class Player {
    private String nombre;
    private String equipo;
    private int votes;

    public Player(String nombre, String equipo) {
        this.nombre = nombre;
        this.equipo = equipo;
        this.votes = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEquipo() {
        return equipo;
    }

    public int getVotos() {
        return votes;
    }

    public void setVotes(int newVotes) {
        this.votes = newVotes;
    }

    public void sumarVoto() {
        votes++;
    }
}
