package www.fhrzdty31.sch.id.personprofile_sqlite;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import www.fhrzdty31.sch.id.personprofile_sqlite.helper.DBHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtNomor;
    private final DBHelper db = new DBHelper(this);
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        CircleImageView image = findViewById(R.id.avatar);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtNomor = findViewById(R.id.edt_nomor);
        Button btnSave = findViewById(R.id.btn_save);
        id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String nomor = getIntent().getStringExtra("nomor");

        if (id == null || id.equals("")) {
            setTitle("Tambah User");
        } else {
            setTitle("Edit User");
            Glide.with(getApplicationContext())
                    .load("https://picsum.photos/id/7"+id+"/200")
                    .into(image);
            edtName.setText(name);
            edtEmail.setText(email);
            edtNomor.setText(nomor);
        }
        btnSave.setOnClickListener(view -> {
            try {
                if (id == null || id.equals("")) {
                    save();
                } else {
                    edit();
                }
            } catch (Exception e) {
                Log.e("Saving", e.getMessage());
            }
        });

    }

    private void save() {
        if (String.valueOf(edtName.getText()).equals("") || String.valueOf(edtEmail.getText()).equals("") || String.valueOf(edtNomor.getText()).equals("")) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            db.insert(
                    edtName.getText().toString(),
                    edtEmail.getText().toString(),
                    edtNomor.getText().toString()
            );
            finish();
        }
    }

    private void edit() {
        if (String.valueOf(edtName.getText()).equals("") || String.valueOf(edtEmail.getText()).equals("") || String.valueOf(edtNomor.getText()).equals("")) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            db.update(
                    Integer.parseInt(id),
                    edtName.getText().toString(),
                    edtEmail.getText().toString(),
                    edtNomor.getText().toString()
            );
            finish();
        }
    }
}