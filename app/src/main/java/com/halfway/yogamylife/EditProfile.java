package com.halfway.yogamylife;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 911;
    private String imagePath;
    ImageView ivEditProfil;
    EditText etProfilFirstName;
    EditText etProfilLastName;
    EditText etWriteText;
    Button btnSave;
    public static RequestInterface requestInterface;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public static final String STORAGE_NAME = "StorageName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profil);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, ShopLenta.class);
                startActivity(intent);
            }
        });

        etProfilFirstName = (EditText) findViewById(R.id.et_profil_first_name);
        etProfilLastName = (EditText) findViewById(R.id.et_profil_last_name);
        etWriteText = (EditText) findViewById(R.id.et_write_text);
        ivEditProfil = (ImageView) findViewById(R.id.iv_edit_profil);
        requestInterface = Controller.getApi();
        settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        requestInterface.getUser(settings.getString("cookie", "")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String description = user.getDescription();
                String image = user.getImage();

                if(response.code() == 200){
                    if(!firstName.isEmpty() && !firstName.equals("")){
                        etProfilFirstName.setText(firstName);
                    }
                    if(!lastName.isEmpty() && !lastName.equals("")){
                        etProfilLastName.setText(lastName);
                    }
                    if(!description.isEmpty() && !description.equals("")){
                        etWriteText.setText(description);
                    }
                    if(!image.isEmpty() && !image.equals("")) {
                        Picasso.with(EditProfile.this).load(user.getImage()).into(ivEditProfil);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                editor = settings.edit();
                User user = new User();
                if(!etProfilFirstName.getText().toString().isEmpty() && !etProfilFirstName.getText().toString().equals("")) {
                    user.setFirstName(etProfilFirstName.getText().toString());
                    editor.putString("first_name", etProfilFirstName.getText().toString());
                }
                if(!etProfilLastName.getText().toString().isEmpty() && !etProfilLastName.getText().toString().equals("")) {
                    user.setLastName(etProfilLastName.getText().toString());
                    editor.putString("last_name", etProfilLastName.getText().toString());
                }
                if(!etWriteText.getText().toString().isEmpty() && !etWriteText.getText().toString().equals("")) {
                    user.setDescription(etWriteText.getText().toString());
                    editor.putString("description", etWriteText.getText().toString());
                }
                if(!settings.getString("image", "").isEmpty() && !settings.getString("image", "").equals("")){
                    user.setImage(settings.getString("image", ""));
                }
                editor.apply();
                user.setEmail(settings.getString("email", ""));
                if(!settings.getString("address", "").isEmpty() && !settings.getString("address", "").equals("")) {
                    user.setFirstAddress(settings.getString("address", ""));
                }
                if(!settings.getString("city", "").isEmpty() && !settings.getString("city", "").equals("")) {
                    user.setCity(settings.getString("city", ""));
                }
                if(!settings.getString("country", "").isEmpty() && !settings.getString("country", "").equals("")) {
                    user.setCountry(settings.getString("country", ""));
                }
                if(!settings.getString("state", "").isEmpty() && !settings.getString("state", "").equals("")) {
                    user.setState(settings.getString("state", ""));
                }
                if(!settings.getString("zip_code", "").isEmpty() && !settings.getString("zip_code", "").equals("")) {
                    user.setZipCode(settings.getString("zip_code", ""));
                }
                if(!settings.getString("phone", "").isEmpty() && !settings.getString("phone", "").equals("")) {
                    user.setPhoneNumber(settings.getString("phone", ""));
                }
                requestInterface.editUser(settings.getString("cookie", ""), user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("myLogs", "Code in edit: " + response.code());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                startActivity(new Intent(EditProfile.this, ShopLenta.class));
            }
        });

        requestStoragePermission();
        ivEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
    }

private void showFileChooser() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_PICK);
    startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            String imgUri = selectedImageUri.toString();
            Log.d("myLogs", "selectedImageUri.toString() " + selectedImageUri.toString());
            imagePath = getPath(this, selectedImageUri);
            settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
            editor = settings.edit();
            if(!imgUri.isEmpty()) {
                editor.putString("image", imgUri);
                editor.apply();
            }

            if(imagePath != null) {
                Picasso.with(this)
                        .load(new File(imagePath))
                        .resize(200, 200)
                        .centerCrop()
                        .into(ivEditProfil);
            }
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
// MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
// File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Предоставлены права на чтение.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Права на чтение не были предоставлены.", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}
