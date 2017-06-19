package br.com.soledade.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by soledade on 19/06/17.
 */

public class webClient {

    public String post(String json){
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setRequestProperty("Content-type", "application/sjon");
            conection.setRequestProperty("Accept", "application/sjon");

            //adiciona elementos post
            conection.setDoOutput(true);


            PrintStream output = new PrintStream(conection.getOutputStream());
            output.println(json);

            conection.connect();

            Scanner scanner= new Scanner(conection.getInputStream());
            return scanner.next();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
