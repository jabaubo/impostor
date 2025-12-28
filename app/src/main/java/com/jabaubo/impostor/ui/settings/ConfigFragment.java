package com.jabaubo.impostor.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.jabaubo.impostor.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;
    private EditText etPalabras;
    private Button btDefault;
    private Button btGuardar;
    private Controlador controlador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfigBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        controlador = ((MainActivity)getActivity()).getControlador();
        etPalabras = root.findViewById(R.id.etPalabras);
        btDefault = root.findViewById(R.id.btReset);
        btGuardar = root.findViewById(R.id.btGuardar);
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
        controlador.wordCount();
        etPalabras.setText(controlador.cargarPalabras());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void actualizarPalabras() {
        String strPalabras = String.valueOf(etPalabras.getText());
        String[] listaPalabras = strPalabras.split("\n");
        for (int i = 0; i < listaPalabras.length; i++) {
            System.out.println(listaPalabras[i] + " " + listaPalabras[i].length());
        }
        controlador.insertarPalabras(listaPalabras);
        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext())
                .setTitle("Palabras actualizadas")
                .setMessage("Tu lista de palabras ha sido actualizada")
                .setPositiveButton(R.string.ok, null)
                .create();
        alertDialog.show();
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
                    }
                }).setNegativeButton(R.string.no, null).create();
        alertDialog.show();
    }


}