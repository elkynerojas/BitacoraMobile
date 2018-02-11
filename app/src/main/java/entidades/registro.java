package entidades;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Elkin Rojas on 01/02/2018.
 */

public class registro implements Serializable {

    private BigInteger REG_FUNID;
    private String REG_FEC;
    private String REG_HOR;
    private Integer REG_TIRID;

    public registro(BigInteger REG_FUNID, String REG_FEC, String REG_HOR, Integer REG_TIRID) {
        this.REG_FUNID = REG_FUNID;
        this.REG_FEC = REG_FEC;
        this.REG_HOR = REG_HOR;
        this.REG_TIRID = REG_TIRID;
    }
    public registro() {}

    public BigInteger getREG_FUNID() {
        return REG_FUNID;
    }

    public void setREG_FUNID(BigInteger REG_FUNID) {
        this.REG_FUNID = REG_FUNID;
    }

    public String getREG_FEC() {
        return REG_FEC;
    }

    public void setREG_FEC(String REG_FEC) {
        this.REG_FEC = REG_FEC;
    }

    public String getREG_HOR() {
        return REG_HOR;
    }

    public void setREG_HOR(String REG_HOR) {
        this.REG_HOR = REG_HOR;
    }

    public Integer getREG_TIRID() {
        return REG_TIRID;
    }

    public void setREG_TIRID(Integer REG_TIRID) {
        this.REG_TIRID = REG_TIRID;
    }
}
