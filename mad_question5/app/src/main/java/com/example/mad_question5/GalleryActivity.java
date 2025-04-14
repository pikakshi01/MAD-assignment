package com.example.mad_question5;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<File> imageFiles = new ArrayList<>();
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        String folderUriStr = getIntent().getStringExtra("folderUri");
        Uri folderUri = Uri.parse(folderUriStr);
        loadImagesFromUri(folderUri);
    }

    private void loadImagesFromUri(Uri uri) {
        DocumentFile dir = DocumentFile.fromTreeUri(this, uri);
        if (dir != null && dir.isDirectory()) {
            for (DocumentFile file : dir.listFiles()) {
                if (file.getType() != null && file.getType().startsWith("image/")) {
                    imageFiles.add(new File(file.getUri().getPath()));
                }
            }
        }
        adapter = new GalleryAdapter(this, imageFiles);
        recyclerView.setAdapter(adapter);
    }
}
