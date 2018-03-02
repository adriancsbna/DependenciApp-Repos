package dam.android.dependeciapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.concurrent.ExecutionException;

import dam.android.dependeciapp.AsyncTasks.CargaRecordatorios;
import dam.android.dependeciapp.AsyncTasks.RecordatorioTerminado;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.RecordatorioAdapter;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecordatorioDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordatorioDetalleFragment extends Fragment {

    private TextView titulo;
    private TextView contenido;
    private TextView cuando;
    private TextView hora;
    private Recordatorio recordatorio;
    private RecordatorioAdapter adapter;
    private Fragment fragment;
    private int idUsuario;
    private RelativeLayout fabContiner;
    private FrameLayout mapFrame;


    public RecordatorioDetalleFragment() {
        // Required empty public constructor
    }

    public static RecordatorioDetalleFragment newInstance(Recordatorio recordatorio, RecordatorioAdapter adapter, int id) {
        RecordatorioDetalleFragment fragment = new RecordatorioDetalleFragment();
        fragment.setAdapter(adapter);
        fragment.setRecordatorio(recordatorio);
        fragment.setIdUsuario(id);
        return fragment;
    }

    public void setAdapter(RecordatorioAdapter adapter) {
        this.adapter = adapter;
    }

    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_recordatorio_detalle, container, false);
        fabContiner = getActivity().findViewById(R.id.fabtoolbar_container);
        mapFrame = getActivity().findViewById(R.id.frameMap);
        if (mapFrame != null)
            fabContiner.setVisibility(View.VISIBLE);

        titulo = v.findViewById(R.id.tvTitulo);
        contenido = v.findViewById(R.id.tvContenido);
        cuando = v.findViewById(R.id.tvFecha);
        hora = v.findViewById(R.id.tvHora);
        fragment = this;
        ImageView terminar = (ImageView) getActivity().findViewById(R.id.terminado);
        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conexion.isNetDisponible(getContext())) {
                    RecordatorioTerminado rt = new RecordatorioTerminado();
                    rt.execute(recordatorio.getId());
                    try {
                        if (rt.get()) {
                            if (mapFrame != null)
                                fabContiner.setVisibility(View.INVISIBLE);
                            adapter.getRecordatorioList().remove(recordatorio);
                            DependenciaDBManager.RecordatoriosDBManager db = new DependenciaDBManager.RecordatoriosDBManager(getContext());
                            db.delete(String.valueOf(recordatorio.id));
                            getFragmentManager().beginTransaction().remove(fragment).commit();
                            FABToolbarLayout fabToolbar = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
                            fabToolbar.hide();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), R.string.no_conexion_no_borra, Toast.LENGTH_SHORT).show();

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.no_conexion_no_borra, Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView cerrar = (ImageView) getActivity().findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABToolbarLayout fabToolbar = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
                getFragmentManager().beginTransaction().remove(fragment).commit();
                fabToolbar.hide();

            }
        });
        titulo.setText(recordatorio.getTitulo());
        contenido.setText(recordatorio.getContent());
        String cuando = Recordatorio.obtrenCuando(getContext(), recordatorio);
        this.cuando.setText(cuando);
        hora.setText(recordatorio.getHora());
        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
