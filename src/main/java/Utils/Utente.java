package Utils;

public class Utente {

    String nome;
    String cognome;
    String CF;
    int chiaveLicenza;
    String professione;

    public Utente(int chiaveLicenza, String CF,String nome, String cognome, String professione) {
        this.chiaveLicenza = chiaveLicenza;
        this.CF = CF;
        this.nome = nome;
        this.cognome = cognome;
        this.professione = professione;
    }
    public Utente() {
    }

    public int getChiaveLicenza() {
        return chiaveLicenza;
    }

    public String getCF() {
        return CF;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getProfessione() {
        return professione;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public void setChiaveLicenza(int chiaveLicenza) {
        this.chiaveLicenza = chiaveLicenza;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }
}
