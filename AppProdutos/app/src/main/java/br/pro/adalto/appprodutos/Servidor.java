package br.pro.adalto.appprodutos;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Servidor {
    public static final String SERVIDOR = "https://adalto.pro.br/aulas/api/";

    public static boolean temInternet(Context context){
        ConnectivityManager conn =
                (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE);
        return conn.getActiveNetworkInfo().isConnected();
    }


    public static String executar(String endereco,  String... strings){
        HttpURLConnection conn;

        String parametros = "";
        for(String s: strings){
            parametros += s;
        }

        try {
            URL url = new URL(SERVIDOR + endereco);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("charset", "utf-8");

            DataOutputStream saida = new DataOutputStream(conn.getOutputStream());
            saida.writeBytes( parametros );
            saida.flush();
            saida.close();

            InputStream entrada = conn.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(entrada, "UTF-8"));

            String linha;
            StringBuffer resposta = new StringBuffer();

            while ( (linha=bufferedReader.readLine()) != null){
                resposta.append(linha);
                resposta.append("...");
            }

            bufferedReader.close();
            return resposta.toString();

        }catch (Exception e){

        }finally {

        }
        return null;
    }


}
