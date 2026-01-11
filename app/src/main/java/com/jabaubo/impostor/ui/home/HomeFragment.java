package com.jabaubo.impostor.ui.home;

import android.content.DialogInterface;
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
import com.jabaubo.impostor.ui.dialogs.nImpostorDialog;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Controlador controlador;

    private Button btSiguienteJugador;
    private Button btIniciarPartida;
    private Button btVerPalabra;
    private TextView tvPalabra;
    private TextView tvJugador;
    private TextView tvJugadorInicial;
    private EditText etJugadores;

    private String palabra;
    private int nJugadores;
    private int jugadorActual;
    private int jugadorInicial;
    private int[] listaImpostores = new int[1];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listaImpostores[0] = 267;
        controlador = ((MainActivity) getActivity()).getControlador();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btSiguienteJugador = root.findViewById(R.id.btSiguienteJugador) ;
        btIniciarPartida= root.findViewById(R.id.btIniciar) ;
        tvPalabra= root.findViewById(R.id.tvPalabra) ;
        tvJugador= root.findViewById(R.id.tvJugador) ;
        tvJugadorInicial = root.findViewById(R.id.tvJugadorInicial);
        etJugadores = root.findViewById(R.id.etJugadores);
        btVerPalabra = root.findViewById(R.id.btVerPalabra);
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
        btVerPalabra.setEnabled(false);
        btVerPalabra.setOnTouchListener(new View.OnTouchListener() {
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
                nImpostorDialog nImpostorDialog = new nImpostorDialog(nJugadores,this);
                nImpostorDialog.show(getParentFragmentManager(),null);
                System.out.println("Inciamos partida");
                palabra = controlador.elegirPalabra();
                jugadorInicial = (int) (Math.random()*nJugadores);
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
        boolean ultimoJugador = jugadorActual==(nJugadores-1);
        btSiguienteJugador.setEnabled(!ultimoJugador);
        if (ultimoJugador) {
            tvJugadorInicial.setText(String.format("Empieza el jugador %d",(jugadorInicial+1)));
        }
        btVerPalabra.setEnabled(true);
    }

    private void touchBtVerPalabra(MotionEvent motionEvent){
        System.out.println(motionEvent.getAction());
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN | motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            boolean isImpostor = false;
            for (int i = 0; i < listaImpostores.length; i++) {
                System.out.printf("%d %d %b\n",jugadorActual,listaImpostores[i],jugadorActual == listaImpostores[i]);
                if (jugadorActual == listaImpostores[i]){
                    System.out.println("Salto");
                    isImpostor=true;
                    break;
                }
            }
            if (isImpostor){
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

    public int[] getListaImpostores() {
        return listaImpostores;
    }

    public void setListaImpostores(int[] listaImpostores) {
        this.listaImpostores = listaImpostores;
    }
}