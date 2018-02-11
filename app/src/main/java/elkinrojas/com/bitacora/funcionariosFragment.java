package elkinrojas.com.bitacora;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import entidades.funcionario;
import utilidades.stringsDataBase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link funcionariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link funcionariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class funcionariosFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Spinner spinner;
    Button btnNuevofuncionario, btnEditarFuncionario, btnEliminarFuncionario, btnDetalleFuncionario;
    String eliminar = "";

    ArrayList<String> listaFuncionarioCadena;
    ArrayList<funcionario> listaFuncionarioObjeto;
    conexionDataBase con;
    boolean bandSelect = false;
    Bundle bundle = new Bundle();


    private OnFragmentInteractionListener mListener;


    public funcionariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment funcionariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static funcionariosFragment newInstance(String param1, String param2) {
        funcionariosFragment fragment = new funcionariosFragment();
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
        //DECLARACIONES
        vista = inflater.inflate(R.layout.fragment_funcionarios, container, false);

        btnNuevofuncionario = (Button) vista.findViewById(R.id.btnNuevoFuncionario);
        btnEditarFuncionario = (Button) vista.findViewById(R.id.btnEditarFuncionario);
        btnEliminarFuncionario = (Button) vista.findViewById(R.id.btnEliminarFuncionario);
        btnDetalleFuncionario = (Button) vista.findViewById(R.id.btnDetalleFuncionario);
        spinner = (Spinner) vista.findViewById(R.id.spinnerFuncionarios);

        //LLENADO DEL SPINNER
        con = new conexionDataBase(getContext());
        llenarSpinner();

        //CLICK BOTON NUEVO
        btnNuevofuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoFuncionarioFragment fragment = new nuevoFuncionarioFragment();
                getFragmentManager().beginTransaction().replace(R.id.contenedor, fragment).addToBackStack(null).commit();
            }
        });

        //CLICK BOTON EDITAR

        btnEditarFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(bandSelect){
                        EditarFuncionarioFragment fragment = new EditarFuncionarioFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();
                    }else {
                        Toast.makeText(getContext(),"Seleccione un Funcionario",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e ){

                }

            }
        });

        //CLICK BOTON DETALLE
        btnDetalleFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(bandSelect){
                        funcionarioDetalleFragment fragment = new funcionarioDetalleFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();
                    }else {
                        Toast.makeText(getContext(),"Seleccione un Funcionario",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e ){

                }
            }
        });

        //CLICK BOTON ELIMINAR
        btnEliminarFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar();
            }
        });

        //ENVENTO SELECCION DE SPINNER
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    funcionario obj = listaFuncionarioObjeto.get(position-1);
                    bundle.putSerializable("obj",obj);
                    eliminar = obj.getFUN_ID();
                    bandSelect = true;
                }else {
                    bandSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return vista;
    }

    //LLENA EL SPINNER DE FUNCIONARIOS
    private  void llenarSpinner(){
        consultarListaCadena();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaFuncionarioCadena);
        spinner.setAdapter(adaptador);

    }

    //CREA EL ARRAY DE OBJETOS DE TIPO FUNCIONARIO PARA LLENAR EL SPINNER
    private void consultarListaCadena() {
        SQLiteDatabase db=con.getReadableDatabase();
        funcionario funcionario=null;
        listaFuncionarioObjeto =new ArrayList<funcionario>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM " + stringsDataBase.TABLA_FUNCIONARIO,null);

        while (cursor.moveToNext()){
            funcionario =  new funcionario();
            funcionario.setFUN_ID(cursor.getString(0));
            funcionario.setFUN_NOM(cursor.getString(1));
            funcionario.setFUN_APE(cursor.getString(2));
            funcionario.setFUN_DEPID(cursor.getString(3));
            funcionario.setFUN_EST(cursor.getString(4));

            listaFuncionarioObjeto.add(funcionario);

        }
        obtenerListaCadena();
    }

    //CREA UN ARRAY DE CADENAS PARA LLENAR EL ADAPTER QUE SE LE ASIGNA AL SPINNER DE FUNCIONARIOS
    private void obtenerListaCadena() {
        listaFuncionarioCadena=new ArrayList<String>();
        listaFuncionarioCadena.add("Seleccione");

        for(int i=0;i<listaFuncionarioObjeto.size();i++){
            listaFuncionarioCadena.add(listaFuncionarioObjeto.get(i).getFUN_ID()+" - "+listaFuncionarioObjeto.get(i).getFUN_NOM()+" "+listaFuncionarioObjeto.get(i).getFUN_APE());
        }
    }

    //ELIMINA AL FUNCIONARIO SELECCIONADO Y VUELVE A LLENAR EL SPINNER
    private void eliminarFuncionario(String eliminar){
        SQLiteDatabase db=con.getWritableDatabase();
        String[] parametros={eliminar};

        db.delete(stringsDataBase.TABLA_FUNCIONARIO,stringsDataBase.FUNCIONARIO_FUN_ID+"=?",parametros);
        Toast.makeText(getContext(),"Se eliminó el funcionario",Toast.LENGTH_LONG).show();
        db.close();
        llenarSpinner();

    }

    //MUESTRA UN DIALOGO QUE SOLICITA CONFIRMACIÓN ANTES DE ELIMINAR UN FUNCIONARIO
    //SI ES POSITIVO LLAMA AL METODO DE ELIMINAR FUNCIONARIO CON EL PARAMETRO DEL ID DEL FUNCIONARIO SELECCIONADO EN EL SPINNER
    public void confirmar(){
        try{
            if (bandSelect){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("¿Está seguro de Eliminar el Funcionario?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarFuncionario(eliminar);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else {
                Toast.makeText(getContext(),"Seleccione un Funcionario",Toast.LENGTH_LONG).show();
            }
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
