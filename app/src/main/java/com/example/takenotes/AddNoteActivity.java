package com.example.takenotes;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button ok, cancel;
    NoteViewModel noteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.editTextAddTitle);
        description = findViewById(R.id.editTextTextMultiLineAddDescription);
        ok = findViewById(R.id.btnUpdateOk);
        cancel = findViewById(R.id.btnUpdateCancel);
        noteViewModel = new NoteViewModel(getApplication());

        cancel.setOnClickListener(v -> finish());

        ok.setOnClickListener(v -> {
            if(title.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
                Toast.makeText(this,"Fill up the note!", Toast.LENGTH_SHORT).show();
                return;
            }

//            saveNote();
            saveNoteByCourse();
            Toast.makeText(this,"Save success", Toast.LENGTH_SHORT).show();
            finish();
        });


    }

    private void saveNoteByCourse() {

        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();

        Intent i = new Intent();
        i.putExtra("noteTitle", noteTitle);
        i.putExtra("noteDescription", noteDescription);
        setResult(RESULT_OK, i);
        finish();

    }

    private void saveNote() {
        Note note = new Note(
                title.getText().toString(),
                description.getText().toString());

        noteViewModel.insert(note);
    }
}