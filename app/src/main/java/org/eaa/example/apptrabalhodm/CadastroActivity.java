package org.eaa.example.apptrabalhodm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eaa.example.apptrabalhodm.model.Usuario;
import org.eaa.example.apptrabalhodm.utilities.TextMask;
import org.eaa.example.apptrabalhodm.utilities.ValidaCampos;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {
    //REFERENCIA DA AUTENTICACAO
    private FirebaseAuth autenticacao;
    //REFERENCIA DO BANCO DE DADOS
    FirebaseDatabase banco = FirebaseDatabase.getInstance();
    DatabaseReference usuarios;

    TextInputLayout textInputLayoutNome;
    TextInputLayout textInputLayoutDataNasc;
    TextInputLayout textInputLayoutCelular;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutSenha;
    TextInputLayout textInputLayoutConfirmacaoSenha;
    Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        usuarios = banco.getReference("usuários");


        inicializaComponentes();
        validaCamposEMascaras();

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nomeValido = ValidaCampos.NOME_COMPLETO(textInputLayoutNome.getEditText().getText().toString());
                if (nomeValido) {
                    textInputLayoutNome.setError("");
                }else{
                    textInputLayoutNome.setError("Nome necessário");
                }

                Usuario novoUsuario = new Usuario();
                novoUsuario.setNome(textInputLayoutNome.getEditText().getText().toString());
                novoUsuario.setDataNasc(textInputLayoutDataNasc.getEditText().getText().toString());
                novoUsuario.setCelular(textInputLayoutCelular.getEditText().getText().toString());
                novoUsuario.setEmail(textInputLayoutEmail.getEditText().getText().toString());
                novoUsuario.setSenha(textInputLayoutSenha.getEditText().getText().toString());


                autenticacao = FirebaseAuth.getInstance();

                //Criar usuário
                autenticacao.createUserWithEmailAndPassword(novoUsuario.getEmail(), novoUsuario.getSenha()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            usuarios.push().setValue(novoUsuario);
                            Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CadastroActivity.this, "Falha ao cadastrar", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }

    public void inicializaComponentes(){
        textInputLayoutNome = findViewById(R.id.textFieldNome);
        textInputLayoutDataNasc = findViewById(R.id.textFieldDataNasc);
        textInputLayoutCelular = findViewById(R.id.textFieldCelular);
        textInputLayoutEmail = findViewById(R.id.textFieldEmail);
        textInputLayoutSenha = findViewById(R.id.TextInputLayoutSenhaCadastro);
        textInputLayoutConfirmacaoSenha = findViewById(R.id.TextInputLayoutConfirmacaoSenhaCadastro);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

    }

    public void validaCamposEMascaras(){
        textInputLayoutDataNasc.getEditText().addTextChangedListener(TextMask.mask(textInputLayoutDataNasc.getEditText(),TextMask.FORMAT_DATE));
        textInputLayoutCelular.getEditText().addTextChangedListener(TextMask.mask(textInputLayoutCelular.getEditText(),TextMask.FORMAT_FONE));
    }


}