# synTestDemo
# DataBinding在listView中的使用 
### 1. gradle配置
在build.gradle下加入代码

dataBinding {<br/>
        enabled = true<br/>
}
##### 当然需要编译一下才可以使用
### 2. xml文件修改
```
<?xml version="1.0" encoding="utf-8"?>
<layout>
...
</layout>
```
在需要databing的xml中加入<data>,name是你需要databing的实体类对象（自己命名），type是该实体类所在的位置
···
<data>
        <variable
            name="express"
            type="syn.databingdemo.Express">
        </variable>
</data>
···
在每个需要绑定的控件里加入如下代码,express是上面data中的实体类对象，number是实体类中的成员变量

android:text="@{express.number}"
### 3. Activity中操作
在activity中加入如下代码，相当于setContentView(R.layout.activity_main)这个操作;
//ActivityMainBinding是由databinding根据当前xml命名方式生成，activity_main名字改变，ActivityMainBinding也随之改变
```
ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
```
在自定义的Adapter中获取item布局的binding <Br/>
```
                binding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this),
                        R.layout.list_item, parent, false);
```
设置ListView的适配器，list是xml中的id 

binding.list.setAdapter(new MyAdapter());
# 完成，详情可查看我的代码
