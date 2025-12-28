package com.jabaubo.impostor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.jabaubo.impostor.Controlador;
import com.jabaubo.impostor.MainActivity;
import com.jabaubo.impostor.R;
import com.jabaubo.impostor.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Controlador controlador;
    private Button btSiguienteJugador;
    private Button btIniciarPartida;
    private Button btPruebas;
    private TextView tvPalabra;
    private TextView tvJugador;
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
        etJugadores = root.findViewById(R.id.etJugadores);
        btPruebas = root.findViewById(R.id.btVerPalabra);
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
                tvPalabra.setText("¿Palabra?");
                cargarJugador();
            }
        });

        btSiguienteJugador.setEnabled(false);
        btPruebas.setEnabled(false);
        btPruebas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    touchBtVerPalabra(motionEvent);
                return false;
            }
        });
        return root;

    }
    public void inciarPartida(){
        try {
            nJugadores = Integer.valueOf(etJugadores.getText().toString());
            if (nJugadores<= 2){
                AlertDialog adError = new AlertDialog.Builder(this.getContext())
                        .setTitle("Error")
                        .setMessage("No hay suficientes jugadores")
                        .setPositiveButton(R.string.ok, null)
                        .create();
                adError.show();
            }
            else{
                palabra = controlador.elegirPalabra();
                impostor = (int) (Math.random()*nJugadores);
                jugadorActual = 0;
                cargarJugador();
            }
        }catch (NumberFormatException e){
            AlertDialog adError = new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage("Cantidad de jugadores no válida")
                    .setPositiveButton(R.string.ok, null)
                    .create();
            adError.show();
        }


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void cargarJugador(){
        tvJugador.setText("Jugador "+(jugadorActual+1));
        btSiguienteJugador.setEnabled(jugadorActual!=(nJugadores-1));
        btPruebas.setEnabled(true);
    }

    private void touchBtVerPalabra(MotionEvent motionEvent){
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if (jugadorActual == impostor){
                tvPalabra.setText("Impostor");
            }
            else {
                tvPalabra.setText(palabra);
            }
        }
        else{
            tvPalabra.setText("¿Palabra?");
        }
    }
}