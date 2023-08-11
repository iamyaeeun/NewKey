package edu.sungshin.newkey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankFragment extends Fragment {
    RankItem rankItem;

    String url = "http://44.212.55.152:5000/keyword";

    ArrayList<NewsData> newsList;
    RequestQueue queue;
    String keyword;

    public RankFragment() {
    }

    public RankFragment(RankItem rankItem) {
        this.rankItem= rankItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_rank, container, false);

        keyword=rankItem.getContent();

        newsList = new ArrayList<>();
        queue = Volley.newRequestQueue(rootView.getContext());

        final StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() { //서버로 키워드를 전달하면서 요청을 보내는 키워드
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");
                        String press = jsonObject.getString("media");
                        String date = jsonObject.getString("date");

                        // NewsData 클래스를 사용하여 데이터를 저장하고 리스트에 추가
                        NewsData newsData = new NewsData(id,title,content,press,date);
                        System.out.println(title);
                        newsList.add(newsData);

                        // 이후에 newsList를 사용하여 원하는 처리를 진행
                        //Adapter
                        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        RecyclerView recyclerView=rootView.findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(layoutManager);
                        NewsAdapter adapter=new NewsAdapter(rootView.getContext(),newsList);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
                params.put("keyword", keyword); // 로그인 아이디로 바꾸기
                return params;
            }
        };

        request.setShouldCache(false);
        queue.add(request);

        return rootView;
    }
}