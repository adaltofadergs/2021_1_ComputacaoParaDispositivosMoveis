package br.pro.adalto.appswipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private TextView tvTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layoutFundo);
        tvTexto = findViewById(R.id.tvTexto);

        layout.setOnTouchListener( new OnSwipeTouchListener(this){

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                tvTexto.setText("Para baixo");
                layout.setBackgroundColor(Color.RED);
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                tvTexto.setText("Para cima");
                layout.setBackgroundColor(Color.BLUE);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                tvTexto.setText("Para esquerda");
                layout.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                tvTexto.setText("Para direita");
                layout.setBackgroundColor(Color.YELLOW);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvTexto.setText("Só tocou");
                layout.setBackgroundColor(Color.WHITE);
                return super.onTouch(v, event);
            }
        });

    }

}