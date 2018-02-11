package elkinrojas.com.bitacora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import entidades.funcionario;
import utilidades.stringsDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registroIndividualFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registroIndividualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registroIndividualFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "obj";


    // TODO: Rename and change types of parameters
    private Serializable mParam1;


    TextView tvRegistroId, tvRegistroNombre, tvRegistroDependencia, tvRegistroEstado;
    EditText etRegistroObservacion;
    Button btnRegistrarEvento;

    conexionDataBase con;
    private OnFragmentInteractionListener mListener;

    public registroIndividualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment registroIndividualFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static registroIndividualFragment newInstance(Serializable param1) {
        registroIndividualFragment fragment = new registroIndividualFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_registro_individual, container, false);

        tvRegistroId = (TextView) vista.findViewById(R.id.tvRegistroId);
        tvRegistroNombre = (TextView) vista.findViewById(R.id.tvRegistroNombre);
        tvRegistroDependencia = (TextView) vista.findViewById(R.id.tvRegistroDependencia);
        tvRegistroEstado = (TextView) vista.findViewById(R.id.tvRegistroEstado);
        etRegistroObservacion = (EditText) vista.findViewById(R.id.etRegistroObservacion);
        btnRegistrarEvento = (Button) vista.findViewById(R.id.btnRegistrarEvento);

        con = new conexionDataBase(getContext());

        final funcionario obj = (funcionario) mParam1;

        consultaDependencia(obj.getFUN_DEPID());
        consultaEstado(obj.getFUN_EST());

        tvRegistroId.setText(obj.getFUN_ID());
        tvRegistroNombre.setText(obj.getFUN_NOM()+" "+obj.getFUN_APE());

        btnRegistrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarEvento(obj);
            }
        });

        return vista;
    }

    private void registrarEvento(funcionario obj) {
        String msj = "", tipo = "";
        SQLiteDatabase db=con.getWritableDatabase();

        ContentValues values=new ContentValues();
        if(obj.getFUN_EST().equals("true")){
            tipo = "2";
        }else {
            tipo = "1";
        }


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date = new Date();
            String fecha = dateFormat.format(date);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date time = new Date();
            String hora = timeFormat.format(time);

            values.put(stringsDataBase.REGISTRO_REG_FUNID,obj.getFUN_ID());
            values.put(stringsDataBase.REGISTRO_REG_FEC,fecha);
            values.put(stringsDataBase.REGISTRO_REG_HOR,hora);
            values.put(stringsDataBase.REGISTRO_REG_TIRID,tipo);
            values.put(stringsDataBase.REGISTRO_REG_OBS,etRegistroObservacion.getText().toString());

            try {
                Long idResultante=db.insert(stringsDataBase.TABLA_REGISTRO,stringsDataBase.REGISTRO_REG_TIRID,values);

                if(idResultante>0){
                    cambiarEstado(obj);
                    msj = "Registro Exitoso";
                }else{
                    msj = "Registro Fallido";
                }
            }catch (Exception e){
                msj = e.getMessage();
            }
            db.close();
        Toast.makeText(getContext(),msj,Toast.LENGTH_SHORT).show();
        registroFragment fragment = new registroFragment();
        getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();

    }

    private void cambiarEstado(funcionario obj) {
        String nuevoEstado = "";
        if(obj.getFUN_EST().equals("true")){
            nuevoEstado = "false";
        }else{
            nuevoEstado = "true";
        }
        try {
            SQLiteDatabase db=con.getWritableDatabase();
            String[] parametros={obj.getFUN_ID().toString()};
            ContentValues values=new ContentValues();
            values.put(stringsDataBase.FUNCIONARIO_FUN_EST,nuevoEstado);

            db.update(stringsDataBase.TABLA_FUNCIONARIO,values,stringsDataBase.FUNCIONARIO_FUN_ID+"=?",parametros);
            db.close();
        }catch (Exception e){

        }

    }


    private void consultaEstado(String fun_est) {
        String estado = "", ttxBoton = "";
        if (fun_est.equals("true")){
            estado = "EL FUNCIONARIO ESTÁ PRESENTE";
            ttxBoton = "REGISTRAR SALIDA";
            tvRegistroEstado.setBackgroundColor(Color.parseColor("#33FF3C"));
            btnRegistrarEvento.setBackgroundColor(Color.parseColor("#FF3933"));
        }else {
            estado = "EL FUNCIONARIO ESTÁ AUSENTE";
            ttxBoton = "REGISTRAR ENTRADA";
            btnRegistrarEvento.setBackgroundColor(Color.parseColor("#33FF3C"));
            tvRegistroEstado.setBackgroundColor(Color.parseColor("#FF3933"));
        }

        tvRegistroEstado.setText(estado);
        btnRegistrarEvento.setText(ttxBoton);
    }
    private void consultaDependencia(String fun_depid) {
        SQLiteDatabase db = con.getReadableDatabase();
        String[] parametros={fun_depid};
        String[] campos={stringsDataBase.DEPENDENCIA_DEP_ID,stringsDataBase.DEPENDENCIA_DEP_DES};

        try {
            Cursor cursor =db.query(stringsDataBase.TABLA_DEPENDENCIA,campos,stringsDataBase.DEPENDENCIA_DEP_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            tvRegistroDependencia.setText(cursor.getString(1));
            cursor.close();
        }catch (Exception e){

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
