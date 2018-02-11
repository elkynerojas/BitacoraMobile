package elkinrojas.com.bitacora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import entidades.dependencia;
import utilidades.stringsDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nuevoFuncionarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nuevoFuncionarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nuevoFuncionarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnFuncionarioRegistrar;
    EditText etFuncionarioId, etEtFuncionarioNombre, etEtFuncionarioApelllido;
    Spinner spinFuncionarioDependencia;

    ArrayList <String> listaDependenciasCadena;
    ArrayList<dependencia> listaDependenciaObjeto;

    conexionDataBase con;

    private OnFragmentInteractionListener mListener;

    public nuevoFuncionarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nuevoFuncionarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static nuevoFuncionarioFragment newInstance(String param1, String param2) {
        nuevoFuncionarioFragment fragment = new nuevoFuncionarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_nuevo_funcionario, container, false);

        btnFuncionarioRegistrar = (Button) vista.findViewById(R.id.btnFuncionarioRegistrar);
        etFuncionarioId = (EditText) vista.findViewById(R.id.etFuncionarioId);
        etEtFuncionarioNombre = (EditText) vista.findViewById(R.id.etFuncionarioNombre);
        etEtFuncionarioApelllido = (EditText) vista.findViewById(R.id.etFuncionarioApellido);
        spinFuncionarioDependencia = (Spinner) vista.findViewById(R.id.spinFuncionarioDependencia);
        con = new conexionDataBase(getContext());
        consultarListaCadena();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaDependenciasCadena);
        spinFuncionarioDependencia.setAdapter(adaptador);

        btnFuncionarioRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFuncionario();
            }
        });

        return vista;
    }

    private void registrarFuncionario() {
        String msj="";
        SQLiteDatabase db=con.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(stringsDataBase.FUNCIONARIO_FUN_ID,etFuncionarioId.getText().toString());
        values.put(stringsDataBase.FUNCIONARIO_FUN_NOM,etEtFuncionarioNombre.getText().toString().toUpperCase());
        values.put(stringsDataBase.FUNCIONARIO_FUN_APE,etEtFuncionarioApelllido.getText().toString().toUpperCase());
        int idDep= (int) spinFuncionarioDependencia.getSelectedItemId();

        if (idDep!=0){

            values.put(stringsDataBase.FUNCIONARIO_FUN_DEPID,idDep);
            values.put(stringsDataBase.FUNCIONARIO_FUN_EST,"true");

            try {
                Long idResultante=db.insert(stringsDataBase.TABLA_FUNCIONARIO,stringsDataBase.FUNCIONARIO_FUN_ID,values);

                if(idResultante>0){
                    msj = "Registro Exitoso";
                }else{
                    msj = "Registro Fallido";
                }
            }catch (Exception e){
                msj = e.getMessage();
            }
            db.close();
            Toast.makeText(getContext(),msj,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Debe seleccionar una Dependencia",Toast.LENGTH_LONG).show();
        }
    }

    private void consultarListaCadena() {
        SQLiteDatabase db=con.getReadableDatabase();

        dependencia dependencia=null;
        listaDependenciaObjeto =new ArrayList<dependencia>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM " + stringsDataBase.TABLA_DEPENDENCIA,null);

        while (cursor.moveToNext()){
            dependencia=new dependencia();
            dependencia.setDEP_ID(cursor.getString(0));
            dependencia.setDEP_DES(cursor.getString(1));

            Log.i("Dep_id",dependencia.getDEP_ID().toString());
            Log.i("Dep_des",dependencia.getDEP_DES());


            listaDependenciaObjeto.add(dependencia);

        }
        obtenerListaCadena();
    }

    private void obtenerListaCadena() {
        listaDependenciasCadena=new ArrayList<String>();
        listaDependenciasCadena.add("Seleccione");

        for(int i=0;i<listaDependenciaObjeto.size();i++){
            listaDependenciasCadena.add(listaDependenciaObjeto.get(i).getDEP_DES());
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
