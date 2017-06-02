package com.bwie.xushuai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.bwie.xushuai.R;
import com.bwie.xushuai.adapter.XlistViewAdapter;
import com.bwie.xushuai.bean.WeekBean;
import com.bwie.xushuai.xlistView.XListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class WeekFragment extends Fragment implements XListView.IXListViewListener {

    private XListView xListView;

    public static List<WeekBean.DataBean.ComicsBean> list = new ArrayList<>();
    private XlistViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.week_fragment, container, false);

        /**
         * 找控件
         */
        xListView = (XListView) view.findViewById(R.id.xlistview);

        xListView.setPullLoadEnable(true);

        xListView.setPullRefreshEnable(true);

        xListView.setXListViewListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getExecute(true);
    }

    //下拉刷新
    @Override
    public void onRefresh() {

        list.clear();//清空list集合

        getExecute(true);//再请求一次

        xListView.stopRefresh();//停止刷新

        xListView.setRefreshTime("刚刚");//设置刷新时间

    }

    //上啦加载
    @Override
    public void onLoadMore() {

        getExecute(false);

    }

    //是否刷新和设置适配器
    public void setAdapter(boolean flag) {

        if (flag) {

            //设置适配器
            adapter = new XlistViewAdapter(getActivity(), list);

            xListView.setAdapter(adapter);

        } else {

            adapter.notifyDataSetChanged();//刷新

        }

    }

    //3.使用Xutils3.0请求数据
    public void getExecute(final boolean flag) {

        RequestParams params = new RequestParams("http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9");

        //请求数据
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {//成功的回调

                //实体类
                WeekBean weekBean = JSON.parseObject(result, WeekBean.class);

                list.addAll(weekBean.getData().getComics());//加入list集合中

                setAdapter(flag);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {//失败的回调

                System.out.println("请求失败");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}