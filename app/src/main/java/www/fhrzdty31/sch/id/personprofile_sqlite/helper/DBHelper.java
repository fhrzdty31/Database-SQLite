package www.fhrzdty31.sch.id.personprofile_sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "person_profile";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE user" +
                "(id INTEGER PRIMARY KEY autoincrement, name TEXT NOT NULL, email TEXT NOT NULL, nomor TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM user";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("email", cursor.getString(2));
                map.put("nomor", cursor.getString(3));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public void  insert(String name, String email, String nomor) {
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "INSERT INTO user (name, email, nomor) VALUES ('"+name+"', '"+email+"', '"+nomor+"')";
        database.execSQL(QUERY);
    }
    public void  update(int id, String name, String email, String nomor) {
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY= "UPDATE user SET name = '"+name+"', email = '"+email+"', nomor = '"+nomor+"' WHERE id = "+id;
        database.execSQL(QUERY);
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM user WHERE id = "+id;
        database.execSQL(QUERY);
    }
}
