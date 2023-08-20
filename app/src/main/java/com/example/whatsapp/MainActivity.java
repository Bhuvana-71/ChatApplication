package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.Adapters.FragmentAdapter;
import com.example.whatsapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    public static final String[] profileUrls = {"https://blog.rackspace.com/wp-content/uploads/2018/09/pumping-iron-arnold-schwarzenegger-1-1108x0-c-default-696x522.jpg",
            "https://www.rollingstone.com/wp-content/uploads/2018/06/rs-213329-R1247_FEA_Rogen_A.jpg?crop=900:600&width=440",
            "https://assets.entrepreneur.com/content/3x2/2000/20170118220227-GettyImages-471763092.jpeg",
            "https://static.ffx.io/images/$zoom_0.238%2C$multiply_1%2C$ratio_1.776846%2C$width_1059%2C$x_0%2C$y_55/t_crop_custom/w_800/q_86%2Cf_auto/cf3f16e35b79207935da03f7b4a7e7d6e484ff71"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();

        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewPager);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



            switch(item.getItemId())
            {

                case R.id.settings:
                Intent i=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(i);
                finish();
                    break;

                case R.id.logout:
                       auth.signOut();
                         Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                         startActivity(intent);
                         finish();
                    break;
                case R.id.groupChat:
                    Intent intent1=new Intent(MainActivity.this,GroupChatActivity.class);
                    startActivity(intent1);
                    finish();
                    break;


                
            }




        return super.onOptionsItemSelected(item);
    }
}