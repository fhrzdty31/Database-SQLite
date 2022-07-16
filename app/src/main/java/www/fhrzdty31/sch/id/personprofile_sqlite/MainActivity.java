package www.fhrzdty31.sch.id.personprofile_sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.fhrzdty31.sch.id.personprofile_sqlite.adapter.Adapter;
import www.fhrzdty31.sch.id.personprofile_sqlite.helper.DBHelper;
import www.fhrzdty31.sch.id.personprofile_sqlite.model.ModelData;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<ModelData> lists = new ArrayList<>();
    Adapter adapter;
    DBHelper db = new DBHelper(this);
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Profile Data");

        db = new DBHelper(getApplicationContext());
        adapter = new Adapter(MainActivity.this, lists);

        Button btnAdd = findViewById(R.id.btn_add);
        listView = findViewById(R.id.tv_item);

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            startActivity(intent);
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            final String id = lists.get(i).getId();
            final String name = lists.get(i).getName();
            final String email = lists.get(i).getEmail();
            final String nomor = lists.get(i).getNomor();
            CharSequence[] dialogItem = {"Detail", "Edit", "Hapus"};
            CharSequence[] dialogDetail = {"\tNama", name, "\tNomor", nomor, "\tEmail", email};
            dialog = new AlertDialog.Builder(this);
                dialog.setItems(dialogItem, (dialogInterface, i1) -> {
                    switch (i1) {
                        case 0 :
                            dialog.setItems(dialogDetail, (dialogInterface1, i2) -> {
                                if (i2 == 2 || i2 == 3) {
                                    Uri tel = Uri.parse("tel:" + nomor);
                                    Intent intent = new Intent(Intent.ACTION_DIAL, tel);
                                    startActivity(intent);
                                }
                            }).show();
                            break;
                        case 1 :
                            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("nomor", nomor);
                            startActivity(intent);
                            break;
                        case 2 :
                            db.delete(Integer.parseInt(id));
                            lists.clear();
                            getData();
                            break;
                    }
            }).show();

        });
        getData();
    }
    public void getData() {
        ArrayList<HashMap<String, String>> rows = db.getAll();
        for (int i = 0; i < rows.size(); i++) {
            String id = rows.get(i).get("id");
            String name = rows.get(i).get("name");
            String email = rows.get(i).get("email");
            String nomor = rows.get(i).get("nomor");

            ModelData data = new ModelData();
            data.setId(id);
            data.setName(name);
            data.setEmail(email);
            data.setNomor(nomor);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        lists.clear();
        getData();
    }
}