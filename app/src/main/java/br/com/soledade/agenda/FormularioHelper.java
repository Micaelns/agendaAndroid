package br.com.soledade.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
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
    private final ImageView campoFoto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        campoNome= (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco= (EditText) activity.findViewById(R.id.formulario_endereco);
        campoSite= (EditText) activity.findViewById(R.id.formulario_site);
        campoTelefone= (EditText) activity.findViewById(R.id.formulario_telefone);
        campoNota= (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto= (ImageView) activity.findViewById(R.id.formulario_foto);
        aluno=new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String)campoFoto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress((int)aluno.getNota());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno=aluno;
    }

    public void carregaImagem(String caminhoFoto) {
        if(caminhoFoto!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
