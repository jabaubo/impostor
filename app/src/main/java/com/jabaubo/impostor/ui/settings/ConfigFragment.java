package com.jabaubo.impostor.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jabaubo.impostor.Controlador;
import com.jabaubo.impostor.MainActivity;
import com.jabaubo.impostor.R;
import com.jabaubo.impostor.databinding.FragmentConfigBinding;

import java.util.ArrayList;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;
    private EditText etPalabras;
    private Button btDefault;
    private Button btGuardar;
    private TextView tvPalabrasGuardadas;
    private TextView tvPalabrasActuales;
    private Controlador controlador;
    private String textoIncial;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        controlador = ((MainActivity)getActivity()).getControlador();
        etPalabras = root.findViewById(R.id.etPalabras);
        btDefault = root.findViewById(R.id.btReset);
        btGuardar = root.findViewById(R.id.btGuardar);
        tvPalabrasGuardadas = root.findViewById(R.id.tvNPalabrasGuardadas);
        tvPalabrasActuales = root.findViewById(R.id.tvNPalabrasActuales);
        btDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palabrasDefault();
            }
        });
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPalabras();
            }
        });
        etPalabras.addTextChangedListener(new TextWatcher() {
            int textLines;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLines = etPalabras.getLineCount();
                System.out.println("1 "+etPalabras.getLineCount());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                System.out.println("2 "+etPalabras.getLineCount());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("creo q peta aqui");
                System.out.println(etPalabras.getLineCount());
                System.out.println(textLines);
                System.out.println("k1");
                if (etPalabras.getLineCount()!=textLines){
                    System.out.println("k2");
                    String strPalabras = String.valueOf(etPalabras.getText());
                    System.out.println("k3");
                    String[] listaPalabras = strPalabras.split("\n");
                    System.out.println("k4");
                    if (listaPalabras.length > 1){
                        tvPalabrasActuales.setText(String.valueOf(listaPalabras.length));
                    }
                }
            }
        });
        textoIncial = controlador.cargarPalabras();
        tvPalabrasGuardadas.setText(String.valueOf(controlador.wordCountDB()));
        tvPalabrasActuales.setText(String.valueOf(controlador.wordCountDB()));
        etPalabras.setText(textoIncial);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void actualizarPalabras() {
        System.out.println(textoIncial.length());
        System.out.println(etPalabras.getText().toString().length());
        if (textoIncial.equals(etPalabras.getText().toString())){
            AlertDialog adError = new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage("No se han detectado cambios en las palabras")
                    .setPositiveButton(R.string.ok, null)
                    .create();
            adError.show();
        }
        else {
            String strPalabras = String.valueOf(etPalabras.getText());
            String[] listaPalabras = strPalabras.split("\n");
            if (listaPalabras.length <= 1){
                AlertDialog adError = new AlertDialog.Builder(this.getContext())
                        .setTitle("Error")
                        .setMessage("No hay suficientes palabras")
                        .setPositiveButton(R.string.ok, null)
                        .create();
                adError.show();
            }
            else {
                AlertDialog adAvertencia = new AlertDialog.Builder(this.getContext())
                        .setTitle("¿Quieres actualizar las palabras?")
                        .setMessage("El resto de palabras serán borradas")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int j = 0; j < listaPalabras.length; j++) {
                                    System.out.println(listaPalabras[j] + " " + listaPalabras[j].length());
                                }
                                controlador.insertarPalabras(listaPalabras);
                                AlertDialog adCorrecto = new AlertDialog.Builder(getContext())
                                        .setTitle("Palabras actualizadas")
                                        .setMessage("Tu lista de palabras ha sido actualizada")
                                        .setPositiveButton(R.string.ok, null)
                                        .create();
                                adCorrecto.show();
                                tvPalabrasGuardadas.setText(String.valueOf(controlador.wordCountDB()));
                            }
                        })
                        .setNegativeButton(R.string.no,null)
                        .create();
                adAvertencia.show();
            }
        }

    }

    public void palabrasDefault() {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext())
                .setTitle("Restaurar palabras predeterminadas")
                .setMessage("Tu lista de palabras será reemplazada por la lista de palabras por defecto.\n¿Estás seguro?")
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controlador.palabrasPrueba();
                        etPalabras.setText(controlador.cargarPalabras());
                        tvPalabrasGuardadas.setText(String.valueOf(controlador.wordCountDB()));
                    }
                }).setNegativeButton(R.string.no, null).create();
        alertDialog.show();
    }


}