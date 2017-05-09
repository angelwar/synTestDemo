package syn.databingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import syn.databingdemo.databinding.ActivityMainBinding;
import syn.databingdemo.databinding.ListItemBinding;

public class MainActivity extends AppCompatActivity {
    //private Context mContext;
    ArrayList<Express> expresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //ActivityMainBinding是由databinding根据当前xml命名方式生成，activity_main名字改变，ActivityMainBinding也随之改变
        ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        getdata();
        binding.list.setAdapter(new MyAdapter());
    }

    public void getdata() {
        expresses.add(new Express("a54865235845","2016/8/1 12:03:21","在途中"));
        expresses.add(new Express("a54865235846","2016/8/1 12:03:21","已交货"));
        expresses.add(new Express("a54865235847","2016/8/1 12:03:21","在途中"));
        expresses.add(new Express("a54865235848","2016/8/1 12:03:21","在途中"));
        expresses.add(new Express("a54865235849","2016/8/1 12:03:21","异常"));
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
}
