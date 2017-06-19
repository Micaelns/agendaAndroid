package br.com.soledade.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.soledade.agenda.converter.AlunoConvert;
import br.com.soledade.agenda.dao.AlunoDAO;
import br.com.soledade.agenda.modelo.Aluno;

/**
 * Created by soledade on 19/06/17.
 */
//public class EnviaAlunosTask  extends AsyncTask{
public class EnviaAlunosTask  extends AsyncTask<Object,Object,String>{
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog= ProgressDialog.show(context,"Aguarde","Enviando alunos...", true,true);
    }

    @Override
    protected String doInBackground(Object[] params) {

        AlunoDAO dao=new AlunoDAO(context);
        List<Aluno> alunos =dao.buscaAlunos();
        dao.close();

        AlunoConvert conversor= new AlunoConvert();
        String json= conversor.convertParaJSON(alunos);

        webClient client = new webClient();
        String resp= client.post(json);
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context,resposta,Toast.LENGTH_LONG).show();
    }
}
