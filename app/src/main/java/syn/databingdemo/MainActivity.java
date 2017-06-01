package syn.databingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import syn.databingdemo.databinding.ActivityMainBinding;
import syn.databingdemo.databinding.ListItemBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //private Context mContext;
    MyAdapter adapter = new MyAdapter();
    ArrayList<Express> expresses = new ArrayList<>();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what ==100){
                Log.e("结果",msg.obj.toString());
            }else if (msg.what ==101){
                Log.e("结果",msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //ActivityMainBinding是由databinding根据当前xml命名方式生成，activity_main名字改变，ActivityMainBinding也随之改变
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        getdata();
        try {
            validate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        binding.list.addFooterView(LayoutInflater.from(MainActivity.this).inflate(R.layout.foot_view,null));
        binding.list.setAdapter(adapter);
        //binding.list.setLayoutManager(new LinearLayoutManager(this));
        //binding.list.setAdapter(new MyAdapter());
        //binding.swipe.
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newDatas = new ArrayList<String>();
                        for (int i = 0; i <5; i++) {
                            int index = i + 1;
                            newDatas.add("new item" + index);
                        }
                        //adapter.addItem(newDatas);
                        binding.swipe.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "更新了五条数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        binding.list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    //当不滚动的时候
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //判断是否是最底部
                        if (view.getLastVisiblePosition() == (view.getCount()) - 1) {
                            getdata();
                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
            //正在滑动时执行
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //判断是否是最底部
                if (view.getLastVisiblePosition() == (view.getCount()) - 1) {

                }
            }
        });
    }

    public void getdata() {
        expresses.add(new Express("a54865235845","2016/8/1 12:03:21","在途中","http://www.baidu.com/img/bdlogo.gif"));
        expresses.add(new Express("a54865235846","2016/8/1 12:03:21","已交货","http://www.baidu.com/img/bdlogo.gif"));
        expresses.add(new Express("a54865235847","2016/8/1 12:03:21","在途中","http://www.baidu.com/img/bdlogo.gif"));
        expresses.add(new Express("a54865235848","2016/8/1 12:03:21","在途中","http://www.baidu.com/img/bdlogo.gif"));
        expresses.add(new Express("a54865235849","2016/8/1 12:03:21","异常","http://www.baidu.com/img/bdlogo.gif"));
    }

    private class MyAdapter extends BaseAdapter {

        public MyAdapter(){

        }
        @Override
        public int getCount() {
            return expresses.size();
        }

        @Override
        public Object getItem(int position) {
            return expresses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemBinding binding = null;
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                //获取item布局的binding
                binding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this),
                        R.layout.list_item, parent, false);
                //获取布局
                convertView = binding.getRoot();
                //缓存binding到holder
                holder.setItemBinding(binding);
                //设置Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                binding = (ListItemBinding) holder.getItemBinding();
            }
            //通过binding设置当前的item对象，然后它就会自动给textview赋值，这里是xml中data里的对象
            binding.setExpress(expresses.get(position));
            return convertView;
        }


    }

    //viewholder类里只有一个binding对象和它的get,set方法
    private class ViewHolder {
        private ViewDataBinding itemBinding;

        public void setItemBinding(ViewDataBinding itemBinding) {
            this.itemBinding = itemBinding;
        }

        public ViewDataBinding getItemBinding() {
            return itemBinding;
        }
    }




    public static final MediaType TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private void validate() throws IOException {
        //运单号是否存在

        Log.i("结果","000");
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(TYPE,"billCode=XS0226958");
        Request request = new Request.Builder()
                .url("http://10.9.62.119:8080/express/huge/checkBillCode")
                .post(requestBody)
                .build();
        //Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.obj = e;
                message.what = 100;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i("结果",response.body().string());
                Message message = Message.obtain();
                message.obj = response;
                message.what = 101;
                handler.sendMessage(message);
            }
        });

    }

    //post请求
    void doPost(){
        String strurl = "请求地址";
        try {
            URL url = new URL(strurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//设置输入流采用字节流
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");//设置参数
            connection.setRequestProperty("Charset","utf-8");

            connection.connect();

            DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
            dop.writeBytes("param"+ URLEncoder.encode("Q:下周去游泳","utf-8"));//发送参数
            dop.flush();
            dop.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String readLine = null;
            while ((readLine = reader.readLine())!=null){
                result +=readLine;
            }
            reader.close();
            connection.disconnect();
            Log.i(TAG,result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
