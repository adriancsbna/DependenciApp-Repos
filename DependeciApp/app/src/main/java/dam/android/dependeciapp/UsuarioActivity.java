package dam.android.dependeciapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dam.android.dependeciapp.Pojo.Usuario;

/**
 * Created by Tiodor on 16/02/2018.
 */

public class UsuarioActivity extends AppCompatActivity {

    private TextView tvUsuarioIdResult;
    private TextView tvNombreResult;
    private TextView tvApellidosResult;
    private TextView tvDniResult;
    private TextView tvFechaNacimientoResult;
    private TextView tvGeneroResult;
    private Button btnCerrar;
    private Usuario user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Bundle b = getIntent().getExtras();
        user = (Usuario) b.getSerializable("Usuario");
        setUI();
    }

    private void setUI(){
        tvUsuarioIdResult = findViewById(R.id.tvUsuarioIdResult);
        tvNombreResult = findViewById(R.id.tvNombreResult);
        tvApellidosResult = findViewById(R.id.tvApellidosResult);
        tvDniResult = findViewById(R.id.tvDniResult);
        tvFechaNacimientoResult = findViewById(R.id.tvFechaNacimientoResult);
        tvGeneroResult = findViewById(R.id.tvGeneroResult);

        btnCerrar = findViewById(R.id.btnCerrar);

        cargarDatosUsuario();
    }

    private void cargarDatosUsuario(){
        tvUsuarioIdResult.setText(String.valueOf(user.getIdPersona()));
        tvNombreResult.setText(user.getNombre());
        tvApellidosResult.setText(user.getApellidos());
        tvDniResult.setText(user.getDNI());
        tvFechaNacimientoResult.setText(user.getfNacimiento().toString());
        tvGeneroResult.setText(user.getGenero());

    }

    public void onClick(View v){
        finish();
    }
}
