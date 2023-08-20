package com.example.whatsapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Adapters.StatusAdapter;
import com.example.whatsapp.MainActivity;
import com.example.whatsapp.Models.Status;
import com.example.whatsapp.Models.Users;
import com.example.whatsapp.R;
import com.example.whatsapp.SettingsActivity;
import com.example.whatsapp.databinding.FragmentStatusBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class StatusFragment extends Fragment {

	public StatusFragment() {

	}

	ArrayList<Status> list;
	FragmentStatusBinding binding;
	FirebaseStorage storage;
	FirebaseDatabase database;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		binding = FragmentStatusBinding.inflate(inflater, container, false);

		StatusAdapter adapter = new StatusAdapter(getContext(), list);

		database = FirebaseDatabase.getInstance();
		storage=FirebaseStorage.getInstance();

		binding.rvStatus.setAdapter(adapter);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		binding.rvStatus.setLayoutManager(layoutManager);






		binding.plusButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, 827);


			}
		});




		return binding.getRoot();


	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


		super.onActivityResult(requestCode, resultCode, data);

		if (data.getData() != null) {
			Uri sFile = data.getData();
			binding.profile.setImageURI(sFile);

			final StorageReference reference = storage.getReference().child("status")
					.child(FirebaseAuth.getInstance().getUid());

			reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


					reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
						@Override
						public void onSuccess(Uri uri) {




							database.getReference().child("Status").child(FirebaseAuth.getInstance().getUid()).child("imageUrl").setValue(uri.toString());
							database.getReference().child("Status").child(FirebaseAuth.getInstance().getUid()).child("time").setValue(new Date().getTime());



						}
					});
				}
			});


		}


	}
}
