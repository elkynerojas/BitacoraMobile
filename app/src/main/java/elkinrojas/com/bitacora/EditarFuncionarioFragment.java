package elkinrojas.com.bitacora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import entidades.dependencia;
import entidades.funcionario;
import utilidades.stringsDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditarFuncionarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditarFuncionarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarFuncionarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "obj";


    // TODO: Rename and change types of parameters
    private Serializable mParam1;

    Spinner spinner;
    ArrayList <String> listaDependenciasCadena;
    ArrayList<dependencia> listaDependenciaObjeto;
    EditText etFunEditarId, etFunEditarNombre, etFunEditarApellido;
    Button btnFunEditarGuardar, btnFunEditarCancelar;
    conexionDataBase con;
    boolean band = false;

    private OnFragmentInteractionListener mListener;

    public EditarFuncionarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment EditarFuncionarioFragment.
     */
    // TODO: Rename and change types and number of parameters

    //CAPTURA DEL OBJETO FUNCIONARIO ENVIADO DESDE EL FUNCIONARIOFRAGMENT
    public static EditarFuncionarioFragment newInstance(Serializable param1) {
        EditarFuncionarioFragment fragment = new EditarFuncionarioFragment();
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

    //LOGICA GENERAL DEL FRAGMENT
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_editar_funcionario, container, false);

        //ENLACE DE LOS ELEMENTOS GRAFICOS
        spinner = (Spinner) vista.findViewById(R.id.spinEditarFuncionario);
        etFunEditarId = (EditText) vista.findViewById(R.id.etFunEditarId);
        etFunEditarNombre = (EditText) vista.findViewById(R.id.etFunEditarNombre);
        etFunEditarApellido = (EditText) vista.findViewById(R.id.etFunEditarApellido);

        btnFunEditarGuardar = (Button) vista.findViewById(R.id.btnFunEditarGuardar);
        btnFunEditarCancelar = (Button) vista.findViewById(R.id.btnFunEditarCancelar);

        funcionario obj = (funcionario) mParam1;
        //CLICK DEL BOTON GUARDAR: SE LLAMA AL METODO QUE REALIZA LA ACTUALIZACION
        btnFunEditarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    funcionario obj = (funcionario) mParam1;
                    actualizarFuncionario(obj);

            }
        });

        //CLICK DEL BOTÓN CANCELAR: REGRESA AL FUNCIONARIOSFRAGMENT
        btnFunEditarCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionariosFragment fragment = new funcionariosFragment();
                getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();
            }
        });

        con = new conexionDataBase(getContext());

        //CREACION DEL SPINNER DE DEPENDENCIA
        consultarListaCadena();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaDependenciasCadena);
        spinner.setAdapter(adaptador);
        spinner.setSelection((Integer.valueOf(obj.getFUN_DEPID()))-1,true);

        // OBTENCION DE LOS DATOS DEL FUNCIONARIO QUE SE SELECCIONO Y SE MUESTRAN EN LOS CAMPOS

        mostrarDatos(obj);

        //EVENTO DE SELECCION DE UN ELEMENTO DEL SPINNER
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    band = true;
                }else {
                    band = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return vista;
    }

    //FUNCION QUE MUESTRA LOS DATOS DEL FUNCIONARIO EN LOS CAMPOS
    private void mostrarDatos(funcionario obj) {
            etFunEditarId.setText(obj.getFUN_ID());
            etFunEditarNombre.setText(obj.getFUN_NOM());
            etFunEditarApellido.setText(obj.getFUN_APE());
    }

    //FUNCION QUE CREA EL ARRAYLIST DE OBJETOS PARA LLENAR EL SPINNER DE DEPENDENCIA
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

            listaDependenciaObjeto.add(dependencia);
        }
        obtenerListaCadena();
    }

    //FUNCION QUE OBTIENE LOS DATOS DEL ARRAYLIST DE OBJETOS Y CREA EL ARRAY DE CADENA PARA LLENAR EL SPINNER
    private void obtenerListaCadena() {
        listaDependenciasCadena=new ArrayList<String>();
        for(int i=0;i<listaDependenciaObjeto.size();i++){
            listaDependenciasCadena.add(listaDependenciaObjeto.get(i).getDEP_DES());
        }
    }
    //FUNCION EN DONDE SE ACTUALIZAN LOS DATOS DEL FUNCIONARIO
    private void actualizarFuncionario(funcionario obj){
        String msj = "";
        SQLiteDatabase db = con.getWritableDatabase();
        String[] parametros={obj.getFUN_ID()};
        ContentValues values=new ContentValues();
        values.put(stringsDataBase.FUNCIONARIO_FUN_ID,etFunEditarId.getText().toString());
        values.put(stringsDataBase.FUNCIONARIO_FUN_NOM,etFunEditarNombre.getText().toString().toUpperCase());
        values.put(stringsDataBase.FUNCIONARIO_FUN_APE,etFunEditarApellido.getText().toString().toUpperCase());
        int idDep= (int) spinner.getSelectedItemId();
        if (idDep!=0){
            values.put(stringsDataBase.FUNCIONARIO_FUN_DEPID,idDep);
            try {
                db.update(stringsDataBase.TABLA_FUNCIONARIO,values,stringsDataBase.FUNCIONARIO_FUN_ID+"=?",parametros);
                msj = "Funcionario Actualizado";
                funcionariosFragment fragment = new funcionariosFragment();
                getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();
            }catch (Exception e){
                msj = e.getMessage();
            }
            db.close();
            Toast.makeText(getContext(),msj,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Debe seleccionar una Dependencia",Toast.LENGTH_LONG).show();
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
