package br.com.soledade.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.soledade.agenda.dao.AlunoDAO;
import br.com.soledade.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
 
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno)  listaAlunos.getItemAtPosition(position);
                Intent vaiProForm = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                vaiProForm.putExtra("aluno",aluno);
                startActivity(vaiProForm);
            }
        });

        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno)  listaAlunos.getItemAtPosition(position);
                //Toast.makeText(ListaAlunosActivity.this, "Click longo!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Button novoAluno= (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                // cria uma instacia de uma tela e chama em primeiro plano
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void carregaAlunos() {
        AlunoDAO dao=new AlunoDAO(this);
        List<Aluno> alunos= dao.buscaAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter =new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar=menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
                AlunoDAO dao=new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                carregaAlunos();
                return false;
            }
        });
    }
}