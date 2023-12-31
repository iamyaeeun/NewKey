package edu.sungshin.newkey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    ArrayList<NewsData> items=new ArrayList<>();
    RequestQueue queue;
    Context context;

    public NewsAdapter(Context context,ArrayList<NewsData> NewsList){
        this.context=context;
        items=NewsList;//생성자:전달한 comList 받아 items리스트에 대입
    }

    public void addItem(NewsData item){
        items.add(item);
    }
    public void setItems(ArrayList<NewsData> items){ this.items=items; }

    public NewsData getItem(int position){ return items.get(position); }

    public void setItem(int position, NewsData item){ items.set(position,item); }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.news_item,parent,false);
        queue=Volley.newRequestQueue(context);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        NewsData item=items.get(position);
        holder.setItem(item);

        //특정 뉴스 클릭
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new NewsFragment(item));
                fragmentTransaction.addToBackStack(null); // 뒤로 가기 버튼으로 이전 Fragment로 돌아갈 수 있도록 추가
                fragmentTransaction.commit();

                //퍼블릭 IPv4 주소
                String flask_url = "http://18.233.147.47:5000/click";
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();

                final StringRequest request=new StringRequest(Request.Method.POST, flask_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //클릭 시 기사 자세히 보여주기
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
                        params.put("user_id", userId);
                        params.put("click_news", item.getId());

                        return params;
                    }
                };

                request.setShouldCache(false);
                queue.add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,press,date;

        public ViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            press=itemView.findViewById(R.id.press);
            date=itemView.findViewById(R.id.date);
        }

        public void setItem(NewsData item){
            title.setText(item.getTitle());
            press.setText(item.getPress());
            date.setText(item.getDate());
        }
    }
}