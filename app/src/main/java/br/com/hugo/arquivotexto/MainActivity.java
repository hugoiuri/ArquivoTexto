package br.com.hugo.arquivotexto;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edtTexto;
    private static final String nomeArquivoBlocoDeNotas = "BlocoDeNotas.txt";
    private static final String PREFERENCES = "PREFERENCES";

    private void GetColor() {
        SharedPreferences shared = getSharedPreferences(PREFERENCES, 0);
        edtTexto.setTextColor(shared.getInt("color", Color.BLACK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTexto = findViewById(R.id.edtTextoId);

        GetColor();
        LerArquivo();
    }

    public void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void SetColor(int color) {
        edtTexto.setTextColor(color);
        SharedPreferences shared = getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = shared.edit();

        editor.putInt("color", color);
        editor.commit();
    }

    public void SalvarArquivo() {
        String texto = edtTexto.getText().toString();

        if (texto == null || texto.equals((""))) {
            print("Informe o texto!");
        } else {
            try {
                FileOutputStream fileOutputStream = openFileOutput(nomeArquivoBlocoDeNotas, Context.MODE_PRIVATE);
                OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
                output.write(texto);
                output.close();

                Log.i("MainActivity", "Texto salvo!");
            } catch(IOException e) {
                Log.e("MainActivity", e.toString());
            }
        }
    }

    public void LerArquivo() {
        try {
            FileInputStream fileInputStream = openFileInput(nomeArquivoBlocoDeNotas);
            InputStreamReader input = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(input);

            String line;
            String text = "";

            while ((line = bufferedReader.readLine()) != null) {
                text += line + "\n";
            }

            edtTexto.setText(text);
        } catch(IOException e) {
            Log.e("MainActivity", e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnSalvarId:
                SalvarArquivo();
                print("Texto salvo com sucesso!");
                break;
            case R.id.mnCorPretoId:
                SetColor(Color.BLACK);
                break;
            case R.id.mnCorAzulId:
                SetColor(Color.BLUE);
                break;
            case R.id.mnCorVerdeId:
                SetColor(Color.GREEN);
                break;
            case R.id.mnCorVermelhoId:
                SetColor(Color.RED);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
