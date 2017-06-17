package br.com.soledade.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.soledade.agenda.R;
import br.com.soledade.agenda.dao.AlunoDAO;

/**
 * Created by soledade on 17/06/17.
 */

public class SMSReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //quando uma mensagem ultrapassa x caracteres, quebra a mensagem em partes, por isso array;
        Object[] pdus= (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu= (byte[]) pdus[0];
        String formato= (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);

        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.isAluno(telefone)){
            Toast.makeText(context,"Chegou um SMS de aluno!",Toast.LENGTH_SHORT).show();
            MediaPlayer mp= MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
        dao.close();
    }
}
