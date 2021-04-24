package br.pro.adalto.appfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome;
    private Spinner spAno;
    private Button btnSalvar;
    private String acao;
    private Filme filme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = findViewById( R.id.etNome );
        spAno = findViewById( R.id.spAno );
        btnSalvar = findViewById( R.id.btnSalvar );

        acao = getIntent().getStringExtra("acao");
        if( acao.equals("editar")){
            carregarFormulario();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

    }

    private void carregarFormulario(){
        int idFilme = getIntent().getIntExtra("idFilme", 0);
        if( idFilme != 0) {
            filme = FilmeDAO.getFilmeById(this, idFilme);

            etNome.setText( filme.nome );

            String[] arrayAno = getResources().getStringArray(R.array.arrayAno);
            for(int i =0; i < arrayAno.length ; i++){
                if( Integer.valueOf( arrayAno[i] ) == filme.getAno()){
                    spAno.setSelection( i );
                }
            }
        }
    }

    private void salvar(){
        if( spAno.getSelectedItemPosition() == 0 || etNome.getText().toString().isEmpty() ) {

            Toast.makeText(this, "Todos campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();

        }else{

            if (acao.equals("novo")) {
                filme = new Filme();
            }

            filme.nome = etNome.getText().toString();
            filme.setAno( Integer.valueOf( spAno.getSelectedItem().toString()  ) );

            if( acao.equals("editar")){
                FilmeDAO.editar(filme, this);
                finish();
            }else {
                FilmeDAO.inserir(filme, this);
                etNome.setText("");
                spAno.setSelection(0);
            }
        }
    }



}