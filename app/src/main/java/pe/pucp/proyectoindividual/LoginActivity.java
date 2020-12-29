package pe.pucp.proyectoindividual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btnIniciar;
    Button btnIniciarGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnIniciar= findViewById(R.id.buttonIniciar);
        btnIniciarGoogle = findViewById(R.id.buttonIniciarGoogle);
        configurarLogin();

    }


    public void configurarLogin(){

        btnIniciar.setOnClickListener(new View.OnClickListener() {

            List<AuthUI.IdpConfig> proveedores = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            @Override
            public void onClick(View view) {
                AuthMethodPickerLayout customLayout =
                        new AuthMethodPickerLayout.Builder(R.layout.activity_login)
                                .setEmailButtonId(R.id.buttonIniciar)
                                .build();

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAuthMethodPickerLayout(customLayout)
                                .setAvailableProviders(proveedores)
                                .build(),
                        9001);
            }
        });

        btnIniciarGoogle.setOnClickListener(new View.OnClickListener() {

            List<AuthUI.IdpConfig> proveedores = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            @Override
            public void onClick(View view) {
                AuthMethodPickerLayout customLayout =
                        new AuthMethodPickerLayout.Builder(R.layout.activity_login)
                        .setGoogleButtonId(R.id.buttonIniciarGoogle)
                        .build();

                startActivityForResult(
                        AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAuthMethodPickerLayout(customLayout)
                        .setAvailableProviders(proveedores)
                        .build(),
                        9001);
            }
        });

    }


    private void aPrincipal(FirebaseUser miUser){

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("email", miUser.getEmail());
        i.putExtra("UID", miUser.getUid());
        i.putExtra("nombreUsuario",miUser.getDisplayName());
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null){
                    aPrincipal(currentUser);
                    finish();
            }
        }
    }
}