package br.com.gamesseller.yugiooh;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Arena extends AppCompatActivity {


    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;

    private static Context context;



    public static ImageView imgStatusPartida;
    public static TextView textoStatusPartida;
    TextView txtBtn1Jogador, txtBtn2Jogador, txtBtn3Jogador;
    public static Button botao_sair, btnPedra, btnPapel, btnTesoura;
    Integer idElemento;
    Integer idBtnClicado;
    Integer imgElemento;
    public static Integer btnClicadoVerificar1 = 0;
    public static Integer btnClicadoVerificar2 = 0;
    Integer contador;
    String nomeElemento;
    public static ConnectionThread connect;
    String modo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);

        context = this;

        Intent intent = getIntent();

        imgStatusPartida = (ImageView) findViewById(R.id.imgStatusPartida);
        textoStatusPartida = (TextView) findViewById(R.id.textoStatusPartida);
        txtBtn1Jogador = (TextView) findViewById(R.id.txtBtn1Jogador);
        txtBtn2Jogador = (TextView) findViewById(R.id.txtBtn2Jogador);
        txtBtn3Jogador = (TextView) findViewById(R.id.txtBtn3Jogador);
        btnPedra = (Button) findViewById(R.id.btnPedra);
        btnPapel = (Button) findViewById(R.id.btnPapel);
        btnTesoura = (Button) findViewById(R.id.btnTesoura);
        botao_sair = (Button) findViewById(R.id.botao_sair);

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c;

        for(contador=0;contador<=3;contador++) {

            c = db.rawQuery("SELECT * FROM tbl_elementos WHERE idElemento = "+contador, null);

            if (c.getCount() > 0) {
                c.moveToFirst();

                idElemento = c.getInt(0);
                nomeElemento = c.getString(1);
                imgElemento = c.getInt(2);

                c.moveToNext();

                if(contador == 1){

                    txtBtn1Jogador.setText(nomeElemento);
                    btnPedra.setId(idElemento);
                    btnPedra.setBackgroundResource(R.drawable.pedrilson);

                }

                if(contador == 2){

                    txtBtn2Jogador.setText(nomeElemento);
                    btnPapel.setId(idElemento);
                    btnPapel.setBackgroundResource(R.drawable.papeldesu);

                }

                if(contador == 3){

                    txtBtn3Jogador.setText(nomeElemento);
                    btnTesoura.setId(idElemento);
                    btnTesoura.setBackgroundResource(R.drawable.pessoura);

                }

            }
        }

        if(intent != null){
            modo = intent.getStringExtra("modo");
        }

        Log.d("modo",modo);

        if (modo.equals("servidor")){
            waitConnection();
        }else if(modo.equals("client")){
            searchPairedDevices();
        }


        botao_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(modo.equals("client")){
                        String informacoes = "0$4";

                        byte[] data =  informacoes.getBytes();
                        connect.write(data);

                    }else {
                        if (finishMao() == false){
                            startActivity(new Intent(context, JogarActivity.class));
                        }
                    }
                }catch(Exception e){
                    e.getMessage();
                }

            }
        });

    }

    public void searchPairedDevices() {
        Intent searchPairedDevicesIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
    }

    public void waitConnection() {

        connect = new ConnectionThread();
        connect.start();
    }

    public void sendAction(View view) {

    }


    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N")) {
                Toast.makeText(context,"A conexão foi finalizada !",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context, JogarActivity.class));
            }else if (dataString.equals("---S")){
                Toast.makeText(context,"Conectado com sucesso !",Toast.LENGTH_LONG).show();

            }else {

                String informacoessaida = new String(data);
                String[] output = informacoessaida.split("\\$");
                if (output[0].equals("0")){

                    finishMao();

                }

                String informacoes = new String(data);
                btnClicadoVerificar2 = Integer.parseInt(informacoes);
                if(btnClicadoVerificar1 != 0){

                    verificarGanhador();

                    btnPapel.setEnabled(true);
                    btnTesoura.setEnabled(true);
                    btnPedra.setEnabled(true);


                }

            }
        }
    };

    private static void esperarParaLimpar(){

        textoStatusPartida.setText("");
        imgStatusPartida.setBackgroundResource(0);

    }


    private static void verificarGanhador() {

        /*
        *   1 = pedra
        *   2 = papel
        *   3 = tesoura
        * */

        if (btnClicadoVerificar1 == btnClicadoVerificar2){
            textoStatusPartida.setText("Empate!");
            imgStatusPartida.setBackgroundResource(R.drawable.empate);
        }else if(btnClicadoVerificar1 == 1 && btnClicadoVerificar2 == 2){
            textoStatusPartida.setText("Pedra(Adversário) x Papel(Você) : Adversário Ganhou!");
            imgStatusPartida.setBackgroundResource(R.drawable.perdedor);
        }else if(btnClicadoVerificar1 == 1 && btnClicadoVerificar2 == 3){
            textoStatusPartida.setText("Pedra(Você) x Tesoura(Adversário) : Você Ganhou!");
            imgStatusPartida.setBackgroundResource(R.drawable.vencedor);
        }else if(btnClicadoVerificar1 == 2 && btnClicadoVerificar2 == 1){
            textoStatusPartida.setText("Papel(Você) x Pedra(Adversário) : Você Ganhou!");
            imgStatusPartida.setBackgroundResource(R.drawable.vencedor);
        }else if(btnClicadoVerificar1 == 3 && btnClicadoVerificar2 == 1){
            textoStatusPartida.setText("Tesoura(Adversário) x Pedra(Você) : Adversário Ganhou!");
            imgStatusPartida.setBackgroundResource(R.drawable.perdedor);
        }else if(btnClicadoVerificar1 == 2  && btnClicadoVerificar2 == 3){
            textoStatusPartida.setText("Papel(Adversário) x Tesoura(Você) : Adversário Ganhou!");
            imgStatusPartida.setBackgroundResource(R.drawable.perdedor);
        }else if(btnClicadoVerificar1 == 3  && btnClicadoVerificar2 == 2){
            textoStatusPartida.setText("Papel(Você) x Tesoura(Adversário) : Você Ganhou!");
            imgStatusPartida.setBackgroundResource(R.drawable.vencedor);
        }


        btnClicadoVerificar1 = 0;
        btnClicadoVerificar2 = 0;


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {
            if(resultCode == RESULT_OK) {

                connect = new ConnectionThread(data.getStringExtra("btDevAddress"));
                try {
                    connect.start();

                }catch(Exception e){

                }
            }
            else {
                Toast.makeText(this,"Não foi possível realizar a conexão !",Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, JogarActivity.class));
            }
        }
    }


    @Override
    public void onBackPressed() {

    }

    public static boolean finishMao(){

        if (connect != null) {

            connect.cancel();
            return true;

        }else{
            return false;
        }

    }


    public void enviarIdPedra(View view) {

        Toast.makeText(context,"Você Selecionou Pedra", Toast.LENGTH_SHORT).show();

        idBtnClicado = btnPedra.getId();
        btnPapel.setEnabled(false);
        btnTesoura.setEnabled(false);
        btnClicadoVerificar1 = idBtnClicado;

        String informacoes = idBtnClicado.toString();

        byte[] data =  informacoes.getBytes();
        connect.write(data);

        if(btnClicadoVerificar2 != 0){

            verificarGanhador();

            btnPapel.setEnabled(true);
            btnTesoura.setEnabled(true);

        }

    }

    public void enviarIdPapel(View view) {

        Toast.makeText(context,"Você Selecionou Papel", Toast.LENGTH_SHORT).show();

        idBtnClicado = btnPapel.getId();
        btnPedra.setEnabled(false);
        btnTesoura.setEnabled(false);
        btnClicadoVerificar1 = idBtnClicado;

        String informacoes = idBtnClicado.toString();

        byte[] data =  informacoes.getBytes();
        connect.write(data);

        if(btnClicadoVerificar2 != 0){

            verificarGanhador();
            btnPedra.setEnabled(true);
            btnTesoura.setEnabled(true);


        }

    }

    public void enviarIdTesoura(View view) {

        Toast.makeText(context,"Você Selecionou Tesoura", Toast.LENGTH_SHORT).show();

        idBtnClicado = btnTesoura.getId();
        btnPedra.setEnabled(false);
        btnPapel.setEnabled(false);
        btnClicadoVerificar1 = idBtnClicado;

        String informacoes = idBtnClicado.toString();

        byte[] data =  informacoes.getBytes();
        connect.write(data);

        if(btnClicadoVerificar2 != 0){

            verificarGanhador();

            btnPedra.setEnabled(true);
            btnPapel.setEnabled(true);

        }

    }
}
