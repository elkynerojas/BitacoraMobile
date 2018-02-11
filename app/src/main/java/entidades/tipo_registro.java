package entidades;

import java.io.Serializable;

/**
 * Created by Elkin Rojas on 01/02/2018.
 */

public class tipo_registro implements Serializable {
 private  Integer TIR_ID;
 private String TIR_DES;

    public tipo_registro(Integer TIR_ID, String TIR_DES) {
        this.TIR_ID = TIR_ID;
        this.TIR_DES = TIR_DES;
    }

    public tipo_registro() {}

    public Integer getTIR_ID() {
        return TIR_ID;
    }

    public void setTIR_ID(Integer TIR_ID) {
        this.TIR_ID = TIR_ID;
    }

    public String getTIR_DES() {
        return TIR_DES;
    }

    public void setTIR_DES(String TIR_DES) {
        this.TIR_DES = TIR_DES;
    }
}
