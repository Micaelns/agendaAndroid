package br.com.soledade.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.soledade.agenda.dao.AlunoDAO;
import br.com.soledade.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
        final Intent intent =getIntent();
        Aluno aluno =(Aluno) intent.getSerializableExtra("aluno");
        if(aluno!=null){
            helper.preencheFormulario(aluno);
        }
        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null)+ "/"+System.currentTimeMillis()+".jpg";//pasta raiz da aplicação
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));//cria arquivo no caminho que queremos
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });

        // Button botaoSalvar = (Button) findViewById(R.id.formulario_salvar);
      //  botaoSalvar.setOnClickListener(new View.OnClickListener() {
        //    @Override
         //   public void onClick(View v) {
         //       Toast.makeText(FormularioActivity.this,"Botão Clicado",Toast.LENGTH_SHORT).show();
         //       finish();
        //    }
       // });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==CODIGO_CAMERA) {
                helper.carregaImagem(caminhoFoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int teste=0;
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno=helper.pegaAluno();
                AlunoDAO dao =new AlunoDAO(this);
                if(aluno.getId() != 0){
                    dao.altera(aluno);
                }else {
                    dao.insere(aluno);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this,"Aluno "+aluno.getNome()+" Salvo!",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                Toast.makeText(FormularioActivity.this,"Nenhuma Ação Implementada",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
