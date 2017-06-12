package br.com.soledade.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.soledade.agenda.modelo.Aluno;

/**
 * Created by soledade on 07/06/17.
 */

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoSite;
    private final EditText campoTelefone;
    private final RatingBar campoNota;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        campoNome= (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco= (EditText) activity.findViewById(R.id.formulario_endereco);
        campoSite= (EditText) activity.findViewById(R.id.formulario_site);
        campoTelefone= (EditText) activity.findViewById(R.id.formulario_telefone);
        campoNota= (RatingBar) activity.findViewById(R.id.formulario_nota);
        aluno=new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress((int)aluno.getNota());
        this.aluno=aluno;
    }
}
