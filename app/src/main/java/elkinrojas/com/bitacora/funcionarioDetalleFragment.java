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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import entidades.funcionario;
import utilidades.stringsDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link funcionarioDetalleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link funcionarioDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class funcionarioDetalleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "obj";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;

    TextView tvFunDetalleId, tvFunDetalleNombre, tvFunDetalleApellido, tvFunDetalleDependencia, tvFunDetalleEstado;
    conexionDataBase con;
    private OnFragmentInteractionListener mListener;

    public funcionarioDetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment funcionarioDetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static funcionarioDetalleFragment newInstance(funcionario param1) {
        funcionarioDetalleFragment fragment = new funcionarioDetalleFragment();
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

    //LOGICA DEL FRAGMENT
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_funcionario_detalle, container, false);

        tvFunDetalleId = (TextView) vista.findViewById(R.id.tvFunDetalleId);
        tvFunDetalleNombre = (TextView) vista.findViewById(R.id.tvFunDetalleNombre);
        tvFunDetalleApellido = (TextView) vista.findViewById(R.id.tvFunDetalleApellido);
        tvFunDetalleDependencia = (TextView) vista.findViewById(R.id.tvFunDetalleDependencia);
        tvFunDetalleEstado = (TextView) vista.findViewById(R.id.tvFunDetalleEstado);
        con = new conexionDataBase(getContext());

         funcionario obj = (funcionario) mParam1;
         mostrarDatos(obj);

        return vista;
    }
    // FUNCIÓN QUE ASIGNA LOS CAMPOS DE ACUERDO AL OJETO RECIBIDO
    private void mostrarDatos(funcionario obj) {

        consultarDependecia(obj.getFUN_DEPID());
        consultarEstado(obj.getFUN_EST());
        tvFunDetalleId.setText(obj.getFUN_ID());
        tvFunDetalleNombre.setText(obj.getFUN_NOM());
        tvFunDetalleApellido.setText(obj.getFUN_APE());

    }

    private void consultarEstado(String fun_est) {
        String estado = "";
        if (fun_est.equals("true")){
            estado = "PRESENTE";
        }else {
            estado = "AUSENTE";
        }

        tvFunDetalleEstado.setText(estado);
    }

    private void consultarDependecia(String fun_depid) {

            SQLiteDatabase db = con.getReadableDatabase();
            String[] parametros={fun_depid};
            String[] campos={stringsDataBase.DEPENDENCIA_DEP_ID,stringsDataBase.DEPENDENCIA_DEP_DES};

            try {
                Cursor cursor =db.query(stringsDataBase.TABLA_DEPENDENCIA,campos,stringsDataBase.DEPENDENCIA_DEP_ID+"=?",parametros,null,null,null);
                cursor.moveToFirst();
                tvFunDetalleDependencia.setText(cursor.getString(1));
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
