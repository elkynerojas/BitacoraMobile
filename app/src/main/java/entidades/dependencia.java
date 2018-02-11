package entidades;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Elkin Rojas on 01/02/2018.
 */

public class dependencia implements Serializable {
    private String DEP_ID;
    private String DEP_DES;

    public dependencia(String DEP_ID, String DEP_DES) {
        this.DEP_ID = DEP_ID;
        this.DEP_DES = DEP_DES;
    }
    public dependencia() {

    }
    public String getDEP_ID() {
        return DEP_ID;
    }

    public void setDEP_ID(String DEP_ID) {
        this.DEP_ID = DEP_ID;
    }

    public String getDEP_DES() {
        return DEP_DES;
    }

    public void setDEP_DES(String DEP_DES) {
        this.DEP_DES = DEP_DES;
    }
}
