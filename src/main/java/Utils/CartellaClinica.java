package Utils;

import java.util.Date;

public class CartellaClinica {
    private String CF;
    private String Nome;
    private String Cognome;
    private String Telefone;
    private int letto;
    private Date Data_modifica;
    private String CF_MedicoCurante;
    private String note;

    public CartellaClinica() {
    }
    public String getCF() {
        return CF;
    }
    public String getNome() {
        return Nome;
    }
    public String getCognome() {
        return Cognome;
    }

    public Date getData_modifica() {
        return Data_modifica;
    }

    public int getLetto() {
        return letto;
    }

    public String getCF_MedicoCurante() {
        return CF_MedicoCurante;
    }

    public String getNote() {
        return note;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public void setCF_MedicoCurante(String CF_MedicoCurante) {
        this.CF_MedicoCurante = CF_MedicoCurante;
    }

    public void setData_modifica(Date data_modifica) {
        Data_modifica = data_modifica;
    }

    public void setLetto(int letto) {
        this.letto = letto;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }
}
