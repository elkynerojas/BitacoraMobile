package entidades;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Elkin Rojas on 01/02/2018.
 */

public class funcionario implements Serializable {

    private String FUN_ID;
    private String FUN_NOM;
    private String FUN_APE;
    private String FUN_DEPID;
    private String FUN_EST;

    public funcionario(String FUN_ID, String FUN_NOM, String FUN_APE, String FUN_DEPID, String FUN_EST) {
        this.FUN_ID = FUN_ID;
        this.FUN_NOM = FUN_NOM;
        this.FUN_APE = FUN_APE;
        this.FUN_DEPID = FUN_DEPID;
        this.FUN_EST = FUN_EST;
    }
    public funcionario() {}

    public String getFUN_ID() {
        return FUN_ID;
    }

    public void setFUN_ID(String FUN_ID) {
        this.FUN_ID = FUN_ID;
    }

    public String getFUN_NOM() {
        return FUN_NOM;
    }

    public void setFUN_NOM(String FUN_NOM) {
        this.FUN_NOM = FUN_NOM;
    }

    public String getFUN_APE() {
        return FUN_APE;
    }

    public void setFUN_APE(String FUN_APE) {
        this.FUN_APE = FUN_APE;
    }

    public String getFUN_DEPID() {
        return FUN_DEPID;
    }

    public void setFUN_DEPID(String FUN_DEPID) {
        this.FUN_DEPID = FUN_DEPID;
    }

    public String getFUN_EST() {
        return FUN_EST;
    }

    public void setFUN_EST(String FUN_EST) {
        this.FUN_EST = FUN_EST;
    }
}
