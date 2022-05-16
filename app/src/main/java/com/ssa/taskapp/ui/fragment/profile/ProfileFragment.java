package com.ssa.taskapp.ui.fragment.profile;


import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ssa.taskapp.databinding.FragmentProfileBinding;
import com.ssa.taskapp.utils.Prefs;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Uri uri;
    ActivityResultLauncher<Intent> activityResultLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        getImages();
        initBtn();
        initText();
        initImageListener();
    }


    private void initListener() {
        binding.addImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);

        });

    }

    public void getImages() {
        activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                try {
                    assert result.getData() != null;
                    final Uri imageUri = result.getData().getData();
                    Prefs.prefs.saveImage(imageUri);
                    final InputStream imageStream = requireActivity().
                            getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    binding.imgProfile.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initImageListener() {
        if (!Prefs.getPrefs().getImage().equals("")) {
            Uri uri = Uri.parse(Prefs.getPrefs().getImage());
            Glide.with(binding.imgProfile).load(uri).into(binding.imgProfile);
        }
    }

    private void initText() {
        binding.txtName.setText(Prefs.getPrefs().firstName());
        binding.txtLastName.setText(Prefs.getPrefs().lastName());
    }

    private void initBtn() {
        binding.btnTxtSave.setOnClickListener(v -> {
            saveData();
            initText();
            initImageListener();
        });
    }

    private void saveData() {
        String firstName = binding.edtName.getText().toString();
        String lastName = binding.edtLastName.getText().toString();
        if (!firstName.trim().isEmpty()) {

            Prefs.getPrefs().saveFirstName(firstName);
        }
        if (!lastName.equals("")) {
            Prefs.getPrefs().saveLastName(lastName);
        }
        if (uri != null) {
            Prefs.getPrefs().saveImage(uri);
        }
    }
}