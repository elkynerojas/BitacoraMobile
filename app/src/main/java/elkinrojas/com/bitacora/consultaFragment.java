package elkinrojas.com.bitacora;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import entidades.funcionario;
import entidades.registro;
import utilidades.stringsDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link consultaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link consultaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class consultaFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int dia, mes, anio;

    ArrayList<String> listaFuncionarioCadena;
    ArrayList<funcionario> listaFuncionarioObjeto;
    conexionDataBase con;
    boolean bandSelect = false;
    Bundle bundle = new Bundle();

    Spinner spinner;
    EditText etFechaInicial, etFechaFinal;
    Button btnConsultar;

    private OnFragmentInteractionListener mListener;

    public consultaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment consultaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static consultaFragment newInstance(String param1, String param2) {
        consultaFragment fragment = new consultaFragment();
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
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_consulta, container, false);

        spinner = (Spinner) vista.findViewById(R.id.spinConsultaRegistro);
        etFechaInicial = (EditText) vista.findViewById(R.id.etFechaInicial);
        etFechaFinal = (EditText) vista.findViewById(R.id.etFechafinal);
        btnConsultar = (Button) vista.findViewById(R.id.btnConsultar);


        con = new conexionDataBase(getContext());
        llenarSpinner();

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        return vista;
    }

    private void llenarSpinner() {
        consultarListaCadena();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (getContext(),android.R.layout.simple_spinner_item,listaFuncionarioCadena);
        spinner.setAdapter(adaptador);
    }
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

    private void obtenerListaCadena() {
        listaFuncionarioCadena=new ArrayList<String>();
        listaFuncionarioCadena.add("Seleccione");

        for(int i=0;i<listaFuncionarioObjeto.size();i++){
            listaFuncionarioCadena.add(listaFuncionarioObjeto.get(i).getFUN_ID()+" - "+listaFuncionarioObjeto.get(i).getFUN_NOM()+" "+listaFuncionarioObjeto.get(i).getFUN_APE());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.etFechaInicial:

                break;
            case R.id.etFechafinal:


                break;
        }
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
