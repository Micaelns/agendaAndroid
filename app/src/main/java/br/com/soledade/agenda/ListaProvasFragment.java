package br.com.soledade.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.soledade.agenda.modelo.Prova;

/**
 * Created by soledade on 06/07/17.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lista_provas,container,false);

        List<String> topicosPort = Arrays.asList("Sujeito","Objeto direto","Objeto Indireto");
        Prova provaPortugues=new Prova("Português","25/06/2017",topicosPort);

        List<String> topicosMat = Arrays.asList("Equações 2º grau","trigonometria");
        Prova provaMatematica=new Prova("Matemática","26/06/2017",topicosMat);

        List<Prova> provas= Arrays.asList(provaPortugues,provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(),android.R.layout.simple_list_item_1,provas);

        ListView lista =(ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);
            }
        });

        return view;
    }
}
