package br.com.soledade.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.soledade.agenda.modelo.Aluno;

/**
 * Created by soledade on 08/06/17.
 */

public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context) {
        super(context,"Agenda", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE Alunos(" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL," +
                "caminhoFoto TEXT DEFAULT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);//vai para a versão 2
            case 2:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT DEFAULT NULL";
                db.execSQL(sql);//vai para a versão 3
        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getPegaDadosDoAluno(aluno);
        db.insert("Alunos",null, dados);
    }

    @NonNull
    private ContentValues getPegaDadosDoAluno(Aluno aluno) {
        ContentValues dados =new ContentValues();
        dados.put("nome",aluno.getNome());
        dados.put("endereco",aluno.getEndereco());
        dados.put("telefone",aluno.getTelefone());
        dados.put("site",aluno.getSite());
        dados.put("nota",aluno.getNota());
        dados.put("caminhoFoto",aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql= "Select *from Alunos;";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        List<Aluno> alunos=new ArrayList<Aluno>();
        while(c.moveToNext()){
            Aluno aluno =new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        c.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String[] param = {String.valueOf(aluno.getId())};

        db.delete("Alunos", "id=?", param);

    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db =getWritableDatabase();

        ContentValues dados = getPegaDadosDoAluno(aluno);

        String[] params ={String.valueOf(aluno.getId())};
        db.update("Alunos",dados,"id=?",params);
    }

    public boolean isAluno(String telefone){
        SQLiteDatabase db= getReadableDatabase();
        Cursor c=db.rawQuery("select *from Alunos where telefone =?",new String[]{telefone});
        int result=c.getCount();
        c.close();
        if (result > 0 ) {
            return true;
        }
        else
            return false;
    }
}
