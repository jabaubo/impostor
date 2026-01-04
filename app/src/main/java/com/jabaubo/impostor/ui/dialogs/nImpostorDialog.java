package com.jabaubo.impostor.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jabaubo.impostor.R;
import com.jabaubo.impostor.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Collections;

public class nImpostorDialog extends DialogFragment {
    private int nJugadores;
    private HomeFragment homeFragment;
    private TextView tvNJugadores;
    private TextView tvNImpostores;
    private SeekBar sbImpostores;

    public nImpostorDialog(int nJugadores, HomeFragment homeFragment) {
        this.nJugadores = nJugadores;
        this.homeFragment = homeFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_impostor_number,null);
        tvNJugadores = view.findViewById(R.id.tvNJugadores);
        tvNImpostores = view.findViewById(R.id.tvNImpostores);
        sbImpostores = view.findViewById(R.id.sbNImpostores);
        tvNImpostores.setText(String.valueOf(sbImpostores.getProgress()+1));
        tvNJugadores.setText(String.valueOf(nJugadores));

        sbImpostores.setMax(nJugadores-2);
        sbImpostores.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvNImpostores.setText(String.valueOf(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ArrayList<Integer> listaCandidatos = new ArrayList<>();
                for (int j = 0; j < nJugadores; j++) {
                    listaCandidatos.add(j);
                }

                Collections.shuffle(listaCandidatos);

                int[] listaImpostores = new int[sbImpostores.getProgress()+1];
                for (int j = 0; j < listaImpostores.length; j++) {
                    listaImpostores[j]=listaCandidatos.get(j);
                }
                homeFragment.setListaImpostores(listaImpostores);
            }
        });
        alertDialogBuilder.setView(view);
        return alertDialogBuilder.create();
    }
}
