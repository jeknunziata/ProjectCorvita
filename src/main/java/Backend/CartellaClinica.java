package Backend;

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
}
