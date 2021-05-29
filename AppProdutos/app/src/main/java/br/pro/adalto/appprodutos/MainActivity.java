package br.pro.adalto.appprodutos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("Novo Produto");
        menu.add( getResources().getString(R.string.txtFechar) );
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.toString().equals( getResources().getString(R.string.txtFechar) ) ){
            finish();
        }
        if( item.toString().equals( "Novo Produto"  ) ){
            addProduto();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addProduto(){

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Adicionar novo Produto...");
        alerta.setIcon( android.R.drawable.ic_menu_add  );
        EditText etNome = new EditText(this);
        etNome.setHint("Digite aqui o nome do produto...");
        alerta.setView( etNome );
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = etNome.getText().toString();
                if( nome.isEmpty() ){
                    Toast.makeText(MainActivity.this,
                            "O nome deve ser preenchido...", Toast.LENGTH_LONG).show();
                }else {
                    AddProduto addProduto = new AddProduto();
                    addProduto.execute("nome="+ nome  + "&preco=0.0&quantidade=0.0" );
                }
            }
        });
        alerta.show();
    }

    private class AddProduto extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setActivated( true );
            progressBar.setVisibility( View.VISIBLE );
        }

        @Override
        protected String doInBackground(String... strings) {
            return Servidor.executar("addProdutos.php",  strings );
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("servidor" , s );
            if( s != null ){
                try {
                    JSONObject json = new JSONObject( s );
                    int id =  json.getInt("id");
                    if( id > 0 ){
                        atualizar();
                        Toast.makeText(MainActivity.this,
                                "Produto inserido, id: "+ id , Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this,
                                "Erro ao inserir o Produto!" , Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    Toast.makeText(MainActivity.this,
                            "Erro no servidor 1!" , Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(MainActivity.this,
                        "Erro no servidor 2!" , Toast.LENGTH_LONG).show();
            }
            progressBar.setActivated(false);
            progressBar.setVisibility( View.INVISIBLE );
        }
    }





    @Override
    protected void onResume() {
        super.onResume();

        atualizar();
    }

    private void atualizar(){
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