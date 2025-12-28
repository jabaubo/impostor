package com.jabaubo.impostor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jabaubo.impostor.Controlador;
import com.jabaubo.impostor.MainActivity;
import com.jabaubo.impostor.R;
import com.jabaubo.impostor.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Controlador controlador;
    private Button btSiguienteJugador;
    private Button btIniciarPartida;
    private TextView tvPalabra;
    private TextView tvJugador;
    private Switch swVisibilidad;
    private EditText etJugadores;
    private String palabra;
    private int nJugadores;
    private int jugadorActual;
    private int impostor;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        controlador = ((MainActivity) getActivity()).getControlador();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btSiguienteJugador = root.findViewById(R.id.btSiguienteJugador) ;
        btIniciarPartida= root.findViewById(R.id.btIniciar) ;
        tvPalabra= root.findViewById(R.id.tvPalabra) ;
        tvJugador= root.findViewById(R.id.tvJugador) ;
        swVisibilidad= root.findViewById(R.id.swVisibilidad) ;
        etJugadores = root.findViewById(R.id.etJugadores);

        btIniciarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inciarPartida();
            }
        });
        btSiguienteJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugadorActual++;
                swVisibilidad.setChecked(false);
                tvPalabra.setText("¿Palabra?");
                cargarJugador();
            }
        });
        swVisibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSwitch();
            }
        });
        btSiguienteJugador.setEnabled(false);
        swVisibilidad.setEnabled(false);
        return root;

    }
    public void inciarPartida(){
        palabra = controlador.elegirPalabra();
        nJugadores = Integer.valueOf(etJugadores.getText().toString());
        impostor = (int) (Math.random()*nJugadores);
        System.out.println("El impostor es "+ impostor);
        jugadorActual = 0;
        swVisibilidad.setEnabled(true);
        swVisibilidad.setChecked(false);
        clickSwitch();
        cargarJugador();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void cargarJugador(){
        tvJugador.setText("Jugador "+(jugadorActual+1));
        btSiguienteJugador.setEnabled(jugadorActual!=(nJugadores-1));
    }
    private void clickSwitch(){
        if (swVisibilidad.isChecked()){
            if (jugadorActual == impostor){
                tvPalabra.setText("Impostor");
            }
            else {
                tvPalabra.setText(palabra);
            }
        }
        else {
            tvPalabra.setText("¿Palabra?");
        }
    }

}