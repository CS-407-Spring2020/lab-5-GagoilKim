package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //1.
        EditText  editText = (EditText) findViewById(R.id.EditTextView);

        //2.
        Intent intent = getIntent();
        //3.
        noteid = intent.getIntExtra("noteid",-1);

        //4.
        if(noteid != -1){
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMethod(v);
            }
        });
    }
    public void saveMethod(View view){
        EditText editText = (EditText) findViewById(R.id.EditTextView);
        String content = editText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
       //3.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        //4.
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username =  sharedPreferences.getString("username", "");
        //5.
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        if(noteid == -1){
            title = "NOTE_" + (Main2Activity.notes.size() +1);
            dbHelper.saveNotes(username, title,  content, date);
            System.out.println("ERROROR" + content);
        }else{
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username );
        }

        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        intent.putExtra("message", username);
        startActivity(intent);

    }


}
