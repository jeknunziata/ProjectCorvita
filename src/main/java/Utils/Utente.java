package Utils;

public class Utente {

    String nome;
    String cognome;
    String CF;
    int chiaveLicenza;
    String tipo;

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

    public String getTipo() {
        return tipo;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
