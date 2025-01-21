package Utils;

import java.util.Date;

public class CartellaClinica {
    private int id;
    private String CF;
    private String Nome;
    private String Cognome;
    private String Telefono;
    private int letto;
    private Date Data_modifica;
    private String CF_MedicoCurante;
    private String note;
    private int ID;

    public CartellaClinica(int id, String CF,String Nome,String Cognome, String Telefono,int letto,Date Data_modifica,String CF_MedicoCurante,String note) {
        this.id = id;
        this.CF = CF;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Telefono = Telefono;
        this.letto = letto;
        this.Data_modifica = Data_modifica;
        this.CF_MedicoCurante = CF_MedicoCurante;
        this.note = note;
    }

    public CartellaClinica() {}

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
        return Telefono;
    }

    public int getID() {
        return ID;
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

    public void setTelefone(String telefono) {
        this.Telefono = telefono;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
