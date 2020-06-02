package com.example.appfuncionarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editFuncionarioId, editCargo, editSalario, editAtividades;
    Button buttonAddUpdate;
    ListView listView;
    ProgressBar progressBar;

    List<funcionarios> funcionariosList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFuncionarioId = findViewById(R.id.editFuncionarioId);
        editCargo = findViewById(R.id.editCargo);
        editSalario = findViewById(R.id.editSalario);
        editAtividades = findViewById(R.id.editAtividades);
        progressBar = findViewById(R.id.progressBar);

        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        listView = findViewById(R.id.listViewFuncionarios);

        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpdating) {
                    updateFuncionario();
                } else {
                    createFuncionario();
                }

            }
        });

        readFuncionario();
    }

    private void createFuncionario() {
        String cargo = editCargo.getText().toString().trim();
        Double salario = Double.valueOf(editSalario.getText().toString().trim());
        String atividades = editAtividades.getText().toString().trim();

        if (TextUtils.isEmpty(cargo)) {
            editCargo.setError("Por favor, insira o cargo");
            editCargo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(salario.toString())) {
            editCargo.setError("Por favor, insira o salario");
            editCargo.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("cargo", cargo);
        params.put("atividades", atividades);
        params.put("salario", String.valueOf(salario));

        PerformNetworkRequest request = new PerformNetworkRequest(API.URL_CREATE_FUNCIONARIO, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readFuncionario() {
        PerformNetworkRequest request = new PerformNetworkRequest(API.URL_READ_FUNCIONARIO, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateFuncionario() {

        String id = editFuncionarioId.getText().toString();
        String cargo = editCargo.getText().toString();
        String salario = editSalario.getText().toString();
        String atividades = editAtividades.getText().toString();

        if (TextUtils.isEmpty(cargo)) {
            editCargo.setError("Por favor, insira o cargo");
            editCargo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(salario)) {
            editCargo.setError("Por favor, insira o salario");
            editCargo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(atividades)) {
            editCargo.setError("Por favor, insira as atividades");
            editCargo.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("cargo", cargo);
        params.put("salario",salario);

        PerformNetworkRequest request = new PerformNetworkRequest(API.URL_UPDATE_FUNCIONARIO + id, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editCargo.setText("");
        editSalario.setText("");

        isUpdating = false;
    }

    private void deleteFuncionario(int id){
        PerformNetworkRequest request = new PerformNetworkRequest(API.URL_DELETE_FUNCIONARIO + id, null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refreshFuncionarioList(JSONArray funcionarios) throws JSONException {
        funcionariosList.clear();

        for (int i = 0; i < funcionarios.length(); i++) {
            JSONObject obj = funcionarios.getJSONObject(i);

            funcionariosList.add(new funcionarios(
                    obj.getInt("id"),
                    obj.getString("cargo"),
                    obj.getString("atividades"),
                    obj.getDouble("salario")
            ));
        }

        FuncionarioAdapter adapter = new FuncionarioAdapter(funcionariosList);
        listView.setAdapter(adapter);
    }
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshFuncionarioList((object.getJSONArray("funcionarios")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class FuncionarioAdapter extends ArrayAdapter<funcionarios> {
        List<funcionarios> funcionariosList;

        public FuncionarioAdapter(List<funcionarios> funcionariosList) {
            super(MainActivity.this, R.layout.activity_main,funcionariosList);
            this.funcionariosList = funcionariosList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_main, null, true);

            TextView textViewCargo = listViewItem.findViewById(R.id.textViewCargo);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final funcionarios funcionarios = funcionariosList.get(position);

            textViewCargo.setText(funcionarios.getCargo());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editFuncionarioId.setText(String.valueOf(funcionarios.getId()));
                    editCargo.setText(funcionarios.getCargo());
                    editSalario.setText(String.valueOf(funcionarios.getSalario()));
                    editAtividades.setText(funcionarios.getAtividades());
                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete " + funcionarios.getCargo())
                            .setMessage("Você deseja excluir este cargo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteFuncionario(funcionarios.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
}
