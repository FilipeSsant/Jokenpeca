package br.com.gamesseller.yugiooh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class JogarActivity extends AppCompatActivity {

    Button btnListaDeDispositivosPareados, btnServer;
    ArrayList<String> listaDispositivosPareados = new ArrayList<>();
    ArrayAdapter btDispostivosAdapter;
    ListView dispositivosPareados;
    Context context;
    Integer btn_clicked;
    View mDecorView;

    private static final int BT_DISABLED = 0;
    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        btnListaDeDispositivosPareados = (Button)findViewById(R.id.listaDispositivosPareados);
        btnServer = (Button) findViewById(R.id.criarSala);

        btDispostivosAdapter = new ArrayAdapter(this, R.layout.device_list, listaDispositivosPareados);


        btnListaDeDispositivosPareados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarEstadoDoBluetoothCliente();
            }
        });

        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checarEstadoDoBluetoothServer();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void checarEstadoDoBluetoothCliente(){

        //Verificar se o bluetooth é suportado no dispositivo
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Se o mBluetoothAdapter estiver null o dispositivo não tem o necessário para o bluetooth
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"Dispostivo não suporta o Bluetooth",Toast.LENGTH_LONG);
        }
        //Se o bluetooth está ativado
        if (mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(this, Arena.class);
            intent.putExtra("modo","client");
            startActivity(intent);

        }else{
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            btn_clicked = 1;
        }

    }

    public void checarEstadoDoBluetoothServer(){

        //Verificar se o bluetooth é suportado no dispositivo
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Se o mBluetoothAdapter estiver null o dispositivo não tem o necessário para o bluetooth
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"Dispostivo não suporta o Bluetooth",Toast.LENGTH_LONG);
        }
        //Se o bluetooth está ativado
        if (mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(this, Arena.class);
            intent.putExtra("modo","servidor");
            startActivity(intent);

        }else{
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            btn_clicked = 2;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ENABLE_BT){
            if (requestCode != BT_DISABLED){
                if (btn_clicked == 1){
                    checarEstadoDoBluetoothCliente();
                    btn_clicked = 0;
                }else if (btn_clicked == 2){
                    checarEstadoDoBluetoothServer();
                    btn_clicked = 0;
                }
            }
        }

    }

    @Override
    public void onBackPressed() {

    }
}