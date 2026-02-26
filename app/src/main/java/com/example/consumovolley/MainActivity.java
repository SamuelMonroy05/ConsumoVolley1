package com.example.consumovolley;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private RequestQueue queue;
    private ArrayList<String> listaTodos;
    private static String URL_API = "https://jsonplaceholder.typicode.com/todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniObject();
        consumoApi();
        //prueba de comit
    }

    private void consumoApi() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL_API,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaTodos.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                int id = item.getInt("id");
                                String title = item.getString("title");
                                boolean completed = item.getBoolean("completed");

                                String texto = "ID: " + id +
                                        "\nTitulo: " + title +
                                        "\nCompletado: " + completed;

                                listaTodos.add(texto);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    MainActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    listaTodos
                            );

                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,
                                    "Error JSON: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Error en la solicitud: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(request);
    }

    public void iniObject(){
    this.listView = findViewById(R.id.lvTools);
    this.queue = Volley.newRequestQueue(this);
    this.listaTodos = new ArrayList<>();
    }
}