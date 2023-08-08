package edu.sungshin.newkey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CatActivity2 extends AppCompatActivity {
    private HashMap<String, Integer> catDict;
    private ArrayList<Integer> catList;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat2);

        queue= Volley.newRequestQueue(getApplicationContext());
        catDict = new HashMap<>();
        catDict.put("wor1",231);catDict.put("wor2",232);catDict.put("wor3",233);catDict.put("wor4",234);catDict.put("wor5",322);
        catDict.put("it1",731);catDict.put("it2",226);catDict.put("it3",227);catDict.put("it4",230);catDict.put("it5",732);catDict.put("it6",283);catDict.put("it7",229);catDict.put("it8",228);
        catDict.put("opi1",111);catDict.put("opi2",112);catDict.put("opi3",113);
        catDict.put("spo1",121);catDict.put("spo2",122);catDict.put("spo3",123);catDict.put("spo4",124);catDict.put("spo5",125);catDict.put("spo6",126);catDict.put("spo7",127);catDict.put("spo8",128);
        catDict.put("ent1",131);catDict.put("ent2",132);

        CheckBox wor1 = (CheckBox) findViewById(R.id.wor1);
        CheckBox wor2 = (CheckBox) findViewById(R.id.wor2);
        CheckBox wor3 = (CheckBox) findViewById(R.id.wor3);
        CheckBox wor4 = (CheckBox) findViewById(R.id.wor4);
        CheckBox wor5 = (CheckBox) findViewById(R.id.wor5);

        CheckBox it1 = (CheckBox) findViewById(R.id.it1);
        CheckBox it2 = (CheckBox) findViewById(R.id.it2);
        CheckBox it3 = (CheckBox) findViewById(R.id.it3);
        CheckBox it4 = (CheckBox) findViewById(R.id.it4);
        CheckBox it5 = (CheckBox) findViewById(R.id.it5);
        CheckBox it6 = (CheckBox) findViewById(R.id.it6);
        CheckBox it7 = (CheckBox) findViewById(R.id.it7);
        CheckBox it8 = (CheckBox) findViewById(R.id.it8);

        CheckBox opi1 = (CheckBox) findViewById(R.id.opi1);
        CheckBox opi2 = (CheckBox) findViewById(R.id.opi2);
        CheckBox opi3 = (CheckBox) findViewById(R.id.opi3);

        CheckBox spo1 = (CheckBox) findViewById(R.id.spo1);
        CheckBox spo2 = (CheckBox) findViewById(R.id.spo2);
        CheckBox spo3 = (CheckBox) findViewById(R.id.spo3);
        CheckBox spo4 = (CheckBox) findViewById(R.id.spo4);
        CheckBox spo5 = (CheckBox) findViewById(R.id.spo5);
        CheckBox spo6 = (CheckBox) findViewById(R.id.spo6);
        CheckBox spo7 = (CheckBox) findViewById(R.id.spo7);
        CheckBox spo8 = (CheckBox) findViewById(R.id.spo8);

        CheckBox ent1 = (CheckBox) findViewById(R.id.ent1);
        CheckBox ent2 = (CheckBox) findViewById(R.id.ent2);

        Button nextButton=findViewById(R.id.nextButton);

        String url="http://44.212.55.152:5000/register";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        Intent intent = getIntent();
        if (intent.hasExtra("catList")) {
            catList = intent.getIntegerArrayListExtra("catList");
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> keys = catDict.keySet();

                for (String key : keys) {
                    // Find the CheckBox view dynamically based on the key
                    int resId = getResources().getIdentifier(key, "id", getPackageName());
                    CheckBox checkBox = findViewById(resId);

                    // Check if the CheckBox is checked
                    if (checkBox.isChecked()) {
                        Integer value = catDict.get(key);
                        catList.add(value);
                    }
                }

                final StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }){
                    //@Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        // String 형태로 데이터 추가
                        params.put("user_id", userId); // 로그인 아이디로 바꾸기
                        params.put("click_news", "[]");
                        params.put("select_cat", catList.toString()); // 사용자가 선택한 선호 카테고리로 바꾸기

                        return params;
                    }
                };

                request.setShouldCache(false);
                queue.add(request);

                Intent intent=new Intent(CatActivity2.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}