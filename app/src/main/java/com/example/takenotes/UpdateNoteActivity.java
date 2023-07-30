package com.example.takenotes;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button ok, cancel;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit note");
        setContentView(R.layout.activity_update_note);

        title = findViewById(R.id.editTextUpdateTitle);
        description = findViewById(R.id.editTextTextMultiLineUpdateDescription);
        ok = findViewById(R.id.btnUpdateOk);
        cancel = findViewById(R.id.btnUpdateCancel);

        getData();

        cancel.setOnClickListener(v -> finish());

        ok.setOnClickListener(v -> {
            if(title.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
                Toast.makeText(this,"Fill up the note!", Toast.LENGTH_SHORT).show();
                return;
            }

            updateNote();
        });



    }

    private void updateNote() {
        String titleLast = title.getText().toString();
        String descriptionLast = description.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("titleLast", titleLast);
        intent.putExtra("descriptionLast", descriptionLast);

        if (noteId != -1){
            intent.putExtra("noteId", noteId);
            setResult(RESULT_OK, intent);
            finish();
        }





    }

    public void getData() {
        Intent i = getIntent();
        noteId = i.getIntExtra("id", -1);
        title.setText(i.getStringExtra("title"));
        description.setText(i.getStringExtra("description"));
    }
}