package br.com.gamesseller.yugiooh;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout content_main;
    ImageView fundo;
    Button imgbtn_jogar;
    private static Context context;

    View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        mDecorView = getWindow().getDecorView();
        imgbtn_jogar = (Button) findViewById(R.id.imgbtn_jogar);

    }

    public void btnClick(View view) {
        switch (view.getId()){
            case R.id.imgbtn_jogar:
                startActivity(new Intent(this, JogarActivity.class));
                break;
            case R.id.imgbtn_instrucoes:
                startActivity(new Intent(this, InstrucoesActivity.class));
                break;
            case R.id.imgbtn_creditos:
                startActivity(new Intent(this, CreditosActivity.class));
                break;
        }

    }


}
