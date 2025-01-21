package Utils;

import java.sql.Timestamp;

public class Storico {
    private String nomeMedico;
    private String cognomeMedico;
    private int idCartellaClinica;
    private Timestamp dataModifica; // Cambiato a Timestamp
    private String note;

    public Storico(String nomeMedico, String cognomeMedico, int idCartellaClinica, Timestamp dataModifica, String note) {
        this.nomeMedico = nomeMedico;
        this.cognomeMedico = cognomeMedico;
        this.idCartellaClinica = idCartellaClinica;
        this.dataModifica = dataModifica;
        this.note = note;
    }

    // Getter e setter
    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCognomeMedico() {
        return cognomeMedico;
    }

    public void setCognomeMedico(String cognomeMedico) {
        this.cognomeMedico = cognomeMedico;
    }

    public int getIdCartellaClinica() {
        return idCartellaClinica;
    }

    public void setIdCartellaClinica(int idCartellaClinica) {
        this.idCartellaClinica = idCartellaClinica;
    }

    public Timestamp getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(Timestamp dataModifica) {
        this.dataModifica = dataModifica;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
