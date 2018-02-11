package utilidades;

/**
 * Created by Elkin Rojas on 01/02/2018.
 */

public class stringsDataBase {
   //DATA BASE
    public static final String DB_NAME = "funcionarios";
    public static final int DB_VERSION = 1;


    // NOMBRES DE TABLAS

    public static final String TABLA_DEPENDENCIA = "DEPENDENCIA";
    public static final String TABLA_FUNCIONARIO = "FUNCIONARIO";
    public static final String TABLA_TIPO_REGISTRO = "TIPO_REGISTRO";
    public static final String TABLA_REGISTRO = "REGISTRO";
    //METODO ON CREATE

    //CREACION DE TABLA DEPENDECIA
    public static final String CREAR_TABLA_DEPENDENCIA = "CREATE TABLE DEPENDENCIA ( " +
            "DEP_ID NUMERIC NOT NULL PRIMARY KEY, " +
            "DEP_DES VARCHAR NOT NULL)";

    //CREACION DE TABLA TIPO_REGISTRO
    public static final String CREAR_TABLA_TIPO_REGISTRO = "CREATE TABLE TIPO_REGISTRO ( " +
            "TIR_ID NUMERIC NOT NULL PRIMARY KEY, " +
            "TIR_DES VARCHAR NOT NULL)";

    //CREACION DE TABLA FUNCIONARIO
    public static final String CREAR_TABLA_FUNCIONARIO = "CREATE TABLE FUNCIONARIO ( " +
            "FUN_ID NUMERIC NOT NULL PRIMARY KEY, " +
            "FUN_NOM VARCHAR NOT NULL, " +
            "FUN_APE VARCHAR NOT NULL, " +
            "FUN_DEPID NUMERIC NOT NULL, " +
            "FUN_EST BOOLEAN NOT NULL, " +
            "FOREIGN KEY (FUN_DEPID) REFERENCES DEPENDENCIA(DEP_ID) )";

    //CREACION DE TABLA REGISTRO
    public static final String CREAR_TABLA_REGISTRO = "CREATE TABLE REGISTRO ( " +
            "REG_FUNID NUMERIC NOT NULL, " +
            "REG_FEC DATE NOT NULL, " +
            "REG_HOR TIME NOT NULL, " +
            "REG_TIRID NUMERIC NOT NULL, " +
            "REG_OBS VARCHAR, " +
            "PRIMARY KEY (REG_FUNID, REG_FEC, REG_HOR), " +
            "FOREIGN KEY (REG_FUNID) REFERENCES FUNCIONARIO(FUN_ID), " +
            "FOREIGN KEY (REG_TIRID) REFERENCES TIPO_REGISTRO (TIR_ID) )";

    //METODO ONUPGRADE

    //UPGRADE TABLA REGISTRO
    public static final String UPGRADE_TABLA_REGISTRO = "DROP TABLE IF EXISTS REGISTRO";

    //UPGRADE TABLA FUNCIONARIO
    public static final String UPGRADE_TABLA_FUNCIONARIO = "DROP TABLE IF EXISTS FUNCIONARIO";

    //UPGRADE TABLA TIPO_REGISTRO
    public static final String UPGRADE_TABLA_TIPO_REGISTRO = "DROP TABLE IF EXISTS TIPO_REGISTRO";

    //UPGRADE TABLA DEPENDENCIA
    public static final String UPGRADE_TABLA_DEPENDENCIA = "DROP TABLE IF EXISTS DEPENDENCIA";


    //CAMPOS DE LAS TABLAS

    //CAMPOS DE LA TABLA DEPENDENCIA
    public static final String DEPENDENCIA_DEP_ID = "DEP_ID";
    public static final String DEPENDENCIA_DEP_DES = "DEP_DES";

    //CAMPOS DE LA TABLA TIPO_REGISTRO
    public static final String TIPO_REGISTRO_TIR_ID = "TIR_ID";
    public static final String TIPO_REGISTRO_TIR_DES = "TIR_DES";

    //CAMPOS DE LA TABLA FUNCIONARIO
    public static final String FUNCIONARIO_FUN_ID = "FUN_ID";
    public static final String FUNCIONARIO_FUN_NOM = "FUN_NOM";
    public static final String FUNCIONARIO_FUN_APE = "FUN_APE";
    public static final String FUNCIONARIO_FUN_DEPID = "FUN_DEPID";
    public static final String FUNCIONARIO_FUN_EST = "FUN_EST";

    //CAMPOS DE LA TABLA REGISTRO
    public static final String REGISTRO_REG_FUNID = "REG_FUNID";
    public static final String REGISTRO_REG_FEC = "REG_FEC";
    public static final String REGISTRO_REG_HOR = "REG_HOR";
    public static final String REGISTRO_REG_TIRID = "REG_TIRID";
    public static final String REGISTRO_REG_OBS = "REG_OBS";

    //ISERCIONES INICIALES

    public static final String INSERCION_TABLA_DEPENDENCIA = "INSERT INTO DEPENDENCIA VALUES " +
            "('1','Prevención'), " +
            "('2','Psicología'), " +
            "('3','Trabajo Social')," +
            "('4','Defensoria')," +
            "('5','Coordinación')," +
            "('6','Financiera'), " +
            "('7','Nutrición')," +
            "('8','SNBF')," +
            "('9','Mantenimiento')," +
            "('10','Sistemas')," +
            "('11','Otro'), " +
            "('12','No Aplica')";

    public static final String INSERCION_TABLA_TIPO_REGISTRO = "INSERT INTO TIPO_REGISTRO VALUES " +
            "('1','Entrada'),('2','Salida')";
}
