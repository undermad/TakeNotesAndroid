package com.example.takenotes;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> activityResultLauncherForAddNote;
    private ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerActivityForAddNote();
        registerActivityForUpdateNote();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        // update RecyclerView
        noteViewModel.getAllNotes().observe(this, adapter::setNotes);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(adapter.getNoteFromPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
            intent.putExtra("title", note.getTittle());
            intent.putExtra("description", note.getDescription());
            intent.putExtra("id", note.getId());
            //activity launcher
            activityResultLauncherForUpdateNote.launch(intent);

        });


    }

    public void registerActivityForUpdateNote() {
        activityResultLauncherForUpdateNote = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == RESULT_OK && data != null) {
                        String title = data.getStringExtra("titleLast");
                        String description = data.getStringExtra("descriptionLast");
                        int id = data.getIntExtra("noteId", -1);
                        Note note = new Note(title, description);
                        note.setId(id);
                        noteViewModel.update(note);
                        Toast.makeText(this,"Update success", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    public void registerActivityForAddNote() {
        activityResultLauncherForAddNote = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();

                    if (resultCode == RESULT_OK && data != null) {
                        String title = data.getStringExtra("noteTitle");
                        String description = data.getStringExtra("noteDescription");

                        Note note = new Note(title, description);
                        noteViewModel.insert(note);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.top_menu) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//            startActivityForResult(intent, 1);
            activityResultLauncherForAddNote.launch(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && requestCode == RESULT_OK && data != null) {
//
//            String title = data.getStringExtra("noteTitle");
//            String description = data.getStringExtra("noteDescription");
//
//            Note note = new Note(title, description);
//            noteViewModel.insert(note);
//        }
//
//    }


}