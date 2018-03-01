package dam.android.dependeciapp.Controladores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import dam.android.dependeciapp.Fragments.RecordatorioDetalleFragment;
import dam.android.dependeciapp.Pojo.Recordatorio;
import dam.android.dependeciapp.R;

import java.util.List;


public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.ViewHolder> {

    private final List<Recordatorio> recordatorioList;
    private Context context;
    private RecordatorioAdapter adapter;
    private FABToolbarLayout fabToolbar;
    private int idUsuario;


    public RecordatorioAdapter(List<Recordatorio> items, Context cont, FABToolbarLayout fabToolbar,int id) {
        recordatorioList = items;
        context = cont;
        idUsuario=id;
        this.fabToolbar = fabToolbar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordatorio_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = recordatorioList.get(position);
        holder.tvTitulo.setText(recordatorioList.get(position).titulo);
        holder.tvHora.setText(recordatorioList.get(position).hora);
        String cuando = Recordatorio.obtrenCuando(context,recordatorioList.get(position));

        holder.tvCuando.setText(cuando);
        adapter = this;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Creamos una nueva instancia del Fragment de Detalle
                    RecordatorioDetalleFragment fragmentDetalle = RecordatorioDetalleFragment.newInstance(holder.mItem, adapter,idUsuario);

                    cierraRecordatorioDetalle();

                    //Introducimos el Fragment Detalle en el FrameLayout correspondiente
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragmentDetalle).addToBackStack(null).commit();
                    //Al hacerlo, hacemos visible el menu, para poder cerar el Fragment
                    // private FABToolbarLayout fabToolbar;
                    if (fabToolbar.isFab())
                        fabToolbar.show();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private void cierraRecordatorioDetalle() {
        List<Fragment> listFragment = ((FragmentActivity) context).getSupportFragmentManager().getFragments();
        //Seleccionamos el fragment que sea RecordatorioDetalleFragment y lo eliminamos
        for (Fragment fragment : listFragment) {
            if (fragment instanceof RecordatorioDetalleFragment) {
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    @Override
    public int getItemCount() {
        return recordatorioList.size();
    }

    public List<Recordatorio> getRecordatorioList() {
        return recordatorioList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitulo;
        public final TextView tvHora;
        public final TextView tvCuando;
        public Recordatorio mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            tvHora = (TextView) view.findViewById(R.id.tvHora);
            tvCuando = (TextView) view.findViewById(R.id.tvFecha);
        }

    }
}
