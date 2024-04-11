package com.example.soeapplication.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.soeapplication.HelperClass.ProductClass;
import com.example.soeapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    private EditText edittext_product_name, editText_product_info, editText_product_cost;
    private ImageButton imageButton;
    private Button addProductButton;
    private Bitmap imageBitmap;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference productReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference image_storageReference;
    private String imageUri;
    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getData() != null) {
                                //Lấy từ thư viện
                                try {
                                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                                    imageBitmap = BitmapFactory.decodeStream(inputStream);
                                    imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                                    imageButton.setImageBitmap(imageBitmap);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //Chụp từ máy
                                Bundle bundle = data.getExtras();
                                if (bundle != null) {
                                    imageBitmap = (Bitmap) bundle.get("data");
                                    imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                                    imageButton.setImageBitmap(imageBitmap);
                                }
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        AnhXa();
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    ThemAnh();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void AnhXa() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        edittext_product_name = findViewById(R.id.editText_productName);
        editText_product_info = findViewById(R.id.editText_productInfo);
        editText_product_cost = findViewById(R.id.editText_productCost);
        imageButton = findViewById(R.id.imageselectedButton);
        addProductButton = findViewById(R.id.addProductButton);
    }

    private void ThemAnh() {
        ProgressDialog progressDialog = new ProgressDialog(AddProduct.this);
        progressDialog.setMessage("Đang tải ảnh lên...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String imageName = "image_" + UUID.randomUUID().toString() + ".jpg";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        firebaseStorage = FirebaseStorage.getInstance();
        image_storageReference = firebaseStorage.getReference().child("image/" + imageName);

        UploadTask uploadTask = image_storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUri = uri.toString();
                        Log.e("this", "imageuri: " + imageUri);
                        progressDialog.dismiss();
                        ThemSP();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddProduct.this, "Lỗi khi tải lên hình ảnh sản phẩm, hãy thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ThemSP() {
        ProgressDialog progressDialog = new ProgressDialog(AddProduct.this);
        progressDialog.setMessage("Đang thêm sản phẩm...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.e("this", "Ham nay da duoc goi");
        String product_useruid = mUser.getUid();
        String product_name = edittext_product_name.getText().toString();
        String product_info = editText_product_info.getText().toString();
        String product_cost = editText_product_cost.getText().toString();
        String product_image = imageUri;

        firebaseDatabase = FirebaseDatabase.getInstance();
        productReference = firebaseDatabase.getReference("product");

        String product_id = productReference.push().getKey();

        ProductClass productClass = new ProductClass(product_useruid, product_name, product_info, product_cost, product_image, product_id);
        productReference.child(product_id).setValue(productClass);
        progressDialog.dismiss();
        Toast.makeText(AddProduct.this, "Sản phẩm đã được tải lên thành công", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProduct.this);
        builder.setTitle("Hãy chọn cách bạn muốn lấy ảnh");
        builder.setPositiveButton("Chụp ảnh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mActivityResultLauncher.launch(cameraIntent);
            }
        });
        builder.setNegativeButton("Lấy từ thư viện", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                mActivityResultLauncher.launch(galleryIntent);
            }
        });
        builder.show();
    }

    private boolean checkValid() {
        boolean flag = true;
        EditText[] e = {edittext_product_name, editText_product_info, editText_product_cost};
        for (EditText i : e) {
            if (!isDigit(i) && i == editText_product_cost) {
                flag = false;
                i.setError("Ký tự nhập vào phải là chữ số!");
            }
            if (isEmpty(i)) {
                flag = false;
                i.setError("Không được để trống!");
            }
        }
        if (!isImage(imageButton)) {
            flag = false;
            Toast.makeText(this, "Hãy lựa chọn ảnh", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    private boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString());
    }
    private boolean isDigit(EditText editText) {
        return TextUtils.isDigitsOnly(editText.getText().toString());
    }

    private boolean isImage(ImageButton imageButton) {
        Drawable drawable = imageButton.getDrawable();
        if (drawable != null && drawable.getConstantState() != null) {
            Drawable defaultDrawable = getResources().getDrawable(R.drawable.baseline_add_a_photo_24, this.getTheme());
            return !drawable.getConstantState().equals(defaultDrawable.getConstantState());
        } else {
            return false;
        }
    }
}