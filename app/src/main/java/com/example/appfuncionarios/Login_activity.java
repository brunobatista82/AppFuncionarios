package com.example.appfuncionarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_activity extends AppCompatActivity {
    EditText usuario, senha;
    Button entrar, sair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        usuario = findViewById(R.id.Usuario);
        senha = findViewById(R.id.Senha);

        entrar = findViewById(R.id.Entrar);

        sair = findViewById(R.id.Sair);



        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txUsuario = usuario.getText().toString();
                String txSenha = senha.getText().toString();

                if (txUsuario.equals("projeto") && txSenha.equals("final")) {

                    Intent abrir = new Intent(Login_activity.this, MainActivity.class);
                    startActivity(abrir);

                    Toast.makeText(getApplicationContext(),
                            "Bem vindo ao AppFuncionario",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Usuário ou senha inválidos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}