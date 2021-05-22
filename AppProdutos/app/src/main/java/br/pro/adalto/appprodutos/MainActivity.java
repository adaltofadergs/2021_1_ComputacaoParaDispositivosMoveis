package br.pro.adalto.appprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvProdudos;
    private ProgressBar progressBar;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProdudos = findViewById(R.id.lvProdutos);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility( View.INVISIBLE );
        progressBar.setActivated( false );

    }

    @Override
    protected void onResume() {
        super.onResume();

        BuscaProduto conexao =new BuscaProduto();
        conexao.execute();
    }

    private class BuscaProduto extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setActivated( true );
            progressBar.setVisibility( View.VISIBLE );
        }

        @Override
        protected String doInBackground(String... strings) {
            return Servidor.executar("produtos.php",  strings );
        }

        @Override
        protected void onPostExecute(String resposta) {
            super.onPostExecute(resposta);
            if( resposta  != null ){
                listaProdutos = jsonToProdutos( resposta );
                ArrayAdapter adapter = new ArrayAdapter(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        listaProdutos);
                lvProdudos.setAdapter( adapter );
            }
            progressBar.setActivated( false );
            progressBar.setVisibility( View.INVISIBLE );
        }
    }


    private List<Produto> jsonToProdutos( String json){
        List<Produto> lista = new ArrayList<>();

        try {
            JSONObject jsonOBJ = new JSONObject( json );
            JSONArray array = jsonOBJ.getJSONArray("produtos");
            if( array.length() > 0 ){
                for(int i =0 ; i < array.length() ; i++ ){
                    JSONObject jsonProduto = array.getJSONObject( i );
                    Produto prod = new Produto();
                    prod.setId( jsonProduto.getInt( "id") );
                    prod.setNome(jsonProduto.getString( "nome"));
                    prod.setPreco( jsonProduto.getDouble("preco") );
                    prod.setQuantidade( jsonProduto.getDouble("quantidade") );
                    lista.add( prod );
                }
            }
        }catch (JSONException e){

        }
        return lista;
    }

}