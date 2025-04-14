public class ImageDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView detailsText;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.imageView);
        detailsText = findViewById(R.id.detailsText);
        imagePath = getIntent().getStringExtra("imagePath");

        File imageFile = new File(imagePath);
        imageView.setImageURI(Uri.fromFile(imageFile));
        detailsText.setText(getImageDetails(imageFile));

        findViewById(R.id.deleteButton).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete?")
                    .setMessage("Do you really want to delete this image?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (imageFile.delete()) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private String getImageDetails(File file) {
        return "Name: " + file.getName() +
                "\nPath: " + file.getAbsolutePath() +
                "\nSize: " + (file.length() / 1024) + " KB" +
                "\nDate: " + new Date(file.lastModified()).toString();
    }
}
