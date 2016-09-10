package com.example.jbbmobile.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jbbmobile.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUser;
    private EditText edtPassword;
    private EditText edtEqualsPassword;
    private EditText edtEmail;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initViews();
    }

    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        edtUser = (EditText) findViewById(R.id.nicknameEditText);
        edtUser.addTextChangedListener(textWatcher);
        // ********************
        edtPassword = (EditText) findViewById(R.id.passwordEditText);
        edtPassword.addTextChangedListener(textWatcher);
        // ********************
        edtEqualsPassword = (EditText) findViewById(R.id.passwordConfirmEditText);
        edtEqualsPassword.addTextChangedListener(textWatcher);
        // ********************
        edtEmail = (EditText) findViewById(R.id.emailEditText);
        edtEmail.addTextChangedListener(textWatcher);
        // ********************
        Button btnEnter = (Button) findViewById(R.id.registerButton);
        btnEnter.setOnClickListener((View.OnClickListener) this);
    }

    private void clearErrorFields(EditText edtUser) {
    }

    private void callClearErrors(Editable string) {
        if (!string.toString().isEmpty()) {
            clearErrorFields(edtUser);
        }
    }
    public void onClick(View v) {
        if (v.getId() == R.id.registerButton) {
            if (validateFields()) {
                Toast.makeText(this, resources.getString(R.string.en_register_valid), Toast.LENGTH_LONG).show();

                Intent registerIntent = new Intent(RegisterScreenActivity.this, MainScreenActivity.class);
                RegisterScreenActivity.this.startActivity(registerIntent);
                finish();
            }
        }
    }
    /**
     * Efetua a validação dos campos.Nesse caso, valida se os campos não estão vazios e se tem
     * tamanho permitido.
     * Nesse método você poderia colocar outros tipos de validações de acordo com a sua necessidade.
     *
     * @return boolean que indica se os campos foram validados com sucesso ou não
     */
    private boolean validateFields() {
        String user = edtUser.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String equalsPassword = edtEqualsPassword.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        return (!isEmptyFields(user,password,email,equalsPassword) && hasSizeValid(user, password) && hasEqualsPasswords(password,equalsPassword) && hasValidEmail(email) );
    }

    private boolean isEmptyFields(String user, String password,String email,String confirmPassword) {
        if (TextUtils.isEmpty(user)) {
            edtUser.requestFocus(); //seta o foco para o campo user
            edtUser.setError(resources.getString(R.string.en_nickname_validation));
            return true;
        } else if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus(); //seta o foco para o campo password
            edtPassword.setError(resources.getString(R.string.en_password_validation));
            return true;
        }else if (TextUtils.isEmpty(confirmPassword)) {
            edtEqualsPassword.requestFocus(); //seta o foco para o campo password
            edtEqualsPassword.setError(resources.getString(R.string.en_passwordConfirm_validation));
            return true;
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.requestFocus(); //seta o foco para o campo password
            edtEmail.setError(resources.getString(R.string.en_invalidEmail));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String password) {

        if (!(user.length() > 6)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.en_nickname_validation_size));
            return false;
        } else if (!(password.length() > 6)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.en_password_validation_size));
            return false;
        }
        return true;
    }

    private boolean hasEqualsPasswords(String password,String equalsPassword){

            if(password.equals(equalsPassword)){
                return true;
            }else{
                edtEqualsPassword.requestFocus();
                edtEqualsPassword.setError(resources.getString(R.string.en_passwordConfirm_validation));
                return false;
            }

    }

    private boolean hasValidEmail(String email){

        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                return true;
            }else{
                edtEmail.requestFocus();
                edtEmail.setError(resources.getString(R.string.en_invalidEmail));
                return false;
            }
        }else {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.en_invalidEmail));
            return false;
        }

    }
    /**
     * Limpa os ícones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }


}
