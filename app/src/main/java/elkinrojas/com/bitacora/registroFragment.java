package elkinrojas.com.bitacora;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import entidades.funcionario;
import utilidades.stringsDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> listaFuncionarioCadena;
    ArrayList<funcionario> listaFuncionarioObjeto;
    conexionDataBase con;
    boolean bandSelect = false;
    Bundle bundle = new Bundle();

    ListView lvFuncionarios;
    private OnFragmentInteractionListener mListener;

    public registroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static registroFragment newInstance(String param1, String param2) {
        registroFragment fragment = new registroFragment();
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

        View vista = inflater.inflate(R.layout.fragment_registro, container, false);
        lvFuncionarios = (ListView) vista.findViewById(R.id.lvFuncionarios);
        con = new conexionDataBase(getContext());
        consultarListaCadena();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (getContext(),android.R.layout.simple_list_item_1,listaFuncionarioCadena);

        lvFuncionarios.setAdapter(adaptador);

        lvFuncionarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                funcionario obj = listaFuncionarioObjeto.get(position);
                bundle.putSerializable("obj",obj);
                registroIndividualFragment fragment = new registroIndividualFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();
            }
        });
        // Inflate the layout for this fragment
        return vista;
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

        for(int i=0;i<listaFuncionarioObjeto.size();i++){
            listaFuncionarioCadena.add(listaFuncionarioObjeto.get(i).getFUN_NOM()+" "+listaFuncionarioObjeto.get(i).getFUN_APE());
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
