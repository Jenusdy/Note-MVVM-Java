package io.github.jenusdy.note_mvvm_tutorial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import io.github.jenusdy.livedata_tutorial.R;
import io.github.jenusdy.note_mvvm_tutorial.entity.Note;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private ExtendedFloatingActionButton fab;
    public static final int ADD_NOTE_REQUEST = 123456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_note);
        fab = findViewById(R.id.add_note);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(i, ADD_NOTE_REQUEST);
            }
        });
    }

    private void createInitialNote(){
        noteViewModel.insert(new Note("Note Kedua", "Ini adalah note kedua", 1));
        noteViewModel.insert(new Note("Note ketiga", "Ini adalah note ketiga", 2));
        noteViewModel.insert(new Note("Note keempat", "Ini adalah note keempat", 3));
        noteViewModel.insert(new Note("Note kelima", "Ini adalah note kelima", 1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, -1);

            if (priority == -1){
                Toast.makeText(this, "Terdapat kesalahan coba ulangi lagi!", Toast.LENGTH_SHORT).show();
            }else{
                Note note = new Note(title, description, priority);
                noteViewModel.insert(note);
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}