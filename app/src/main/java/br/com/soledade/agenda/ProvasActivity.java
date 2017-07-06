package br.com.soledade.agenda;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.com.soledade.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal,new ListaProvasFragment());
        if(estaNoModoPaisagem()) {
            tx.replace(R.id.frame_segundario, new DetalhesProvaFragment());
        }
        tx.commit();
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if(!estaNoModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();
            Bundle params =new Bundle();
            params.putSerializable("prova",prova);
            detalhesFragment.setArguments(params);

            tx.replace(R.id.frame_principal,detalhesFragment);
            tx.addToBackStack(null);
            tx.commit();
        }else{
            DetalhesProvaFragment detalheFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_segundario);
            detalheFragment.populaCampos(prova);

        }
    }
}
