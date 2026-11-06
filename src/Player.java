public class Player {
    private String nombre;
    private String equipo;
    private String pais;
    private int votes;

    // saves the details we give it, a new player starts with 0 votes
    public Player(String nombre, String equipo, String pais) {
        this.nombre = nombre;
        this.equipo = equipo;
        this.pais = pais;
        this.votes = 0;
    }

    // these getters let us read the player data from the other class
    public String getNombre() {
        return nombre;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getPais() {
        return pais;
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
