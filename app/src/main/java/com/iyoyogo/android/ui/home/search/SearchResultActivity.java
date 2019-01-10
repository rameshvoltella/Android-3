package com.iyoyogo.android.ui.home.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.SearchUserAdapter;
import com.iyoyogo.android.adapter.SearchYoXiuListAdapter;
import com.iyoyogo.android.adapter.search.ListViewkeywordAdapter;
import com.iyoyogo.android.adapter.search.SearchYoJiListHorizontalAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.contract.KeywordContract;
import com.iyoyogo.android.presenter.KeywordPresenter;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.search.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Name: SearchResultActivity
 *
 * @author: dengyalan
 * Date: 2018-05-05 09:33
 */
public class SearchResultActivity extends BaseActivity<KeywordContract.Presenter> implements View.OnClickListener, KeywordContract.View, SearchView.OnQueryTextListener {

    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.tv_setname)
    TextView tvSetname;
    @BindView(R.id.lv_user)
    RecyclerView lvUser;
    @BindView(R.id.lv_content)
    RecyclerView lvContent;
    @BindView(R.id.name)
    ImageView name;
    @BindView(R.id.content)
    ImageView content;
    @BindView(R.id.hit)
    LinearLayout hit;
    @BindView(R.id.list_view_lv)
    ListView listViewLv;
    @BindView(R.id.sl)
    ScrollView sl;
    @BindView(R.id.search_guanjiaci)
    EditText searchGuanjiaci;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.im_dizhi)
    ImageView imDizhi;
    @BindView(R.id.text_name1)
    TextView textName1;
    @BindView(R.id.im_dizhi1)
    ImageView imDizhi1;
    /*    @BindView(R.id.search_btn_back)
        Button searchBtnBack;*/
    /*    @BindView(R.id.cancel)
        TextView cancel;*/
    @BindView(R.id.text_name2)
    TextView textName2;
    @BindView(R.id.im_dizhi2)
    ImageView imDizhi2;
    @BindView(R.id.text_name3)
    TextView textName3;
    @BindView(R.id.im_dizhi3)
    ImageView imDizhi3;
    @BindView(R.id.text_name4)
    TextView textName4;
    @BindView(R.id.im_dizhi4)
    ImageView imDizhi4;
    @BindView(R.id.text_name5)
    TextView textName5;
    @BindView(R.id.im_dizhi5)
    ImageView imDizhi5;
    @BindView(R.id.name1)
    RelativeLayout name1;
    @BindView(R.id.name2)
    RelativeLayout name2;
    @BindView(R.id.name3)
    RelativeLayout name3;
    @BindView(R.id.name4)
    RelativeLayout name4;
    @BindView(R.id.name5)
    RelativeLayout name5;
    @BindView(R.id.name6)
    RelativeLayout name6;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.view6)
    View view6;
    /*  @BindView(R.id.text_name2)
      TextView textName2;*/
    @BindView(R.id.text_name7)
    TextView textName7;
    @BindView(R.id.im_dizhi7)
    ImageView imDizhi7;
    @BindView(R.id.name7)
    RelativeLayout name7;
    @BindView(R.id.view7)
    View view7;
    @BindView(R.id.text_name8)
    TextView textName8;
    @BindView(R.id.im_dizhi8)
    ImageView imDizhi8;
    @BindView(R.id.name8)
    RelativeLayout name8;
    @BindView(R.id.view8)
    View view8;
    @BindView(R.id.text_name9)
    TextView textName9;
    @BindView(R.id.im_dizhi9)
    ImageView imDizhi9;
    @BindView(R.id.name9)
    RelativeLayout name9;
    @BindView(R.id.view9)
    View view9;
    @BindView(R.id.text_name10)
    TextView textName10;
    @BindView(R.id.im_dizhi10)
    ImageView imDizhi10;
    @BindView(R.id.name10)
    RelativeLayout name10;
    @BindView(R.id.view10)
    View view10;
    @BindView(R.id.tv_gson)
    TextView tvGson;
    @BindView(R.id.tv_gson1)
    TextView tvGson1;
    private PopupWindow popupWindow;
    private List<KeywordBean.DataBean.UserListBean> mUser = new ArrayList<>();
    private List<KeywordBean.DataBean.YojListBean> myoj = new ArrayList<>();
    private List<KeywordBean.DataBean.YoxListBean> myox = new ArrayList<>();
    private List<KeywordBean.DataBean> mdata = new ArrayList<>();
    private String user_id;
    private String user_token;
    private String keyword;
    private SearchUserAdapter adapter = new SearchUserAdapter(SearchResultActivity.this, mUser, mdata);
    private SearchYoJiListHorizontalAdapter adapter3 = new SearchYoJiListHorizontalAdapter(SearchResultActivity.this, myoj);
    private SearchYoXiuListAdapter adapter1 = new SearchYoXiuListAdapter(SearchResultActivity.this, myox);
    private String s;
    private List<KeywordBean.DataBean.UserListBean> user_list;
    private List<KeywordUserBean.DataBean.ListBean> listBeans = new ArrayList<>();
    private ListViewkeywordAdapter searchadapter;
    private int user_id1;
    private List<KeywordUserBean.DataBean.ListBean> list;
    private TextView tv_guanzhu1;
    boolean fig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        String key = getIntent().getStringExtra("key");
        //searchGuanjiaci.setText(key);
//        ImageView magImage = (ImageView) searchGuanjiaci.findViewById(magId);
//        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        lv.setLayoutManager(new LinearLayoutManager(this));
        lvUser.setLayoutManager(new LinearLayoutManager(this));
        lvContent.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent() != null) {
            Intent in = getIntent();
            keyword = in.getStringExtra("key");
            //放到网络请求上面
            searchGuanjiaci.setText(keyword);
            // table.setQueryHint(keyword);
        }

        mPresenter.getKeyWord(user_id, user_token, keyword, "all");
        //}

        tvSetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出pop
                init();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (cancel.getText().toString().equals("搜索")){
                    mPresenter.getSearch(user_id, user_token, s.toString());
                }else{*/
                finish();
                //   }

            }
        });


        adapter.setSetOnClickListener(new SearchUserAdapter.SetOnClickListener() {
            @Override
            public void setOnClickListener(TextView tv_guanzhu, int po) {
                tv_guanzhu1 = tv_guanzhu;
                s = tv_guanzhu.getText().toString();
                int user_id = mUser.get(po).getUser_id();
                Log.e("setOnClickListener", "setOnClickListener: " + user_id);
                if (s.equals("已关注")) {
                    mPresenter.getGuanZhu(SearchResultActivity.this.user_id, user_token, user_id + "");
                }
                if (s.equals("+关注")) {
                    mPresenter.getGuanZhu(SearchResultActivity.this.user_id, user_token, user_id + "");
                }
                if (s.equals("互相关注")) {
                    mPresenter.getGuanZhu(SearchResultActivity.this.user_id, user_token, user_id + "");
                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    //点击用户弹出PopupWindow
    private void init() {
        View view = LayoutInflater.from(SearchResultActivity.this).inflate(R.layout.popup_user_item, null);
        popupWindow = new PopupWindow();
        popupWindow.setContentView(view);//设置要显示的View控件
        popupWindow.setWidth(200);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout select_all = view.findViewById(R.id.select_all);
        LinearLayout youji = view.findViewById(R.id.youji);
        LinearLayout yoxiu = view.findViewById(R.id.yoxiu);
        LinearLayout user = view.findViewById(R.id.user);
        select_all.setOnClickListener(this);
        youji.setOnClickListener(this);
        user.setOnClickListener(this);
        yoxiu.setOnClickListener(this);

        popupWindow.setOutsideTouchable(true);//设置点击空白消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置背景;点击返回按钮,关闭PopupWindow
        popupWindow.showAsDropDown(tvSetname, 30, 50);
    }

    @Override
    protected KeywordContract.Presenter createPresenter() {
        return new KeywordPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        int i = 0;
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(SearchResultActivity.this, "user_id", null);
        user_token = SpUtils.getString(SearchResultActivity.this, "user_token", null);
        searchGuanjiaci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = String.valueOf(s);
      /*          if (s1 != null){
                    cancel.setText("搜索");
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (fig) {
                        mPresenter.getSearch(user_id, user_token, s.toString());
                        cancel.setVisibility(View.VISIBLE);
                        // cancel.setText("搜索");
                    } else {
                        fig = true;
                    }

                }
            }
        });
        //软键盘的搜索点击时间
        searchGuanjiaci.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软件盘
                    hideKeyboard(searchGuanjiaci);
                    Toast.makeText(SearchResultActivity.this, "全部", Toast.LENGTH_SHORT).show();
                    mPresenter.getKeyWord(user_id, user_token, searchGuanjiaci.getText().toString(), "all");
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                tvSetname.setText("全部");
                popupWindow.dismiss();
                //切换进行网络请求   调用BaseActivit
                mPresenter.getKeyWord(user_id, user_token, keyword, "all");

                break;
            case R.id.youji:
                tvSetname.setText("yo记");
                popupWindow.dismiss();
                mPresenter.getKeyWord(user_id, user_token, keyword, "yoj");
                break;
            case R.id.yoxiu:
                tvSetname.setText("yo秀");
                popupWindow.dismiss();
                mPresenter.getKeyWord(user_id, user_token, keyword, "yox");
                break;
            case R.id.user:
                tvSetname.setText("用户");
                popupWindow.dismiss();
                mPresenter.getKeyWord(user_id, user_token, keyword, "user");
                break;

        }
    }

    //网络请求成功的回调用户关注，
    @Override
    public void guanZhu(GuanZhuBean keywordBean) {
        int status = keywordBean.getData().getStatus();
        if (status == 1) {
            // mPresenter.getKeyWord(user_id, user_token, keyword, "user");
            tv_guanzhu1.setText("已关注");

        }
        if (status == 0) {
            //  mPresenter.getKeyWord(user_id, user_token, keyword, "user");
            tv_guanzhu1.setText("+关注");
        }
    }

    //关键字搜索  网络返回的接口
    @SuppressLint("ResourceAsColor")
    @Override
    public void search(KeywordUserBean keywordBean) {
        //清空集合
        listBeans.clear();
        if (keywordBean.getData().getList() != null) {
            Log.e("关键字搜索", "search: " + keywordBean.getData().getList().size());
            list = keywordBean.getData().getList();
            listBeans.addAll(list);
            listViewLv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lvUser.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);
            tvSetname.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);

            tvGson.setVisibility(View.GONE);
            tvGson1.setVisibility(View.GONE);
          /*  ListViewkeywordAdapter adapter = new ListViewkeywordAdapter(SearchResultActivity.this, listBeans, searchGuanjiaci.getText().toString());
            listViewLv.setAdapter(adapter);*/
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getType() == 1) {
                    if (list.get(i).getUser_nickname() != null) {
                        name1.setVisibility(View.VISIBLE);
                        imDizhi.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.VISIBLE);
                        textName.setVisibility(View.VISIBLE);
                        imDizhi.setImageResource(R.drawable.yonghu);
                        String content = listBeans.get(i).getUser_nickname();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName.setText(content.substring(0, index));
                                textName.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName.append(str);
                            }
                        }

                        int finalI = i;
                        textName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchResultActivity.this, Personal_homepage_Activity.class);
                                intent.putExtra("yo_user_id", list.get(finalI).getUser_id() + "");
                                //Log.e("adadada", "onClick: " + list.get(finalI).getUser_id()+"");
                                startActivity(intent);
                            }
                        });
                    }
                }
                if (list.get(i).getType() == 2) {
                    Log.e("search", "search: " + list.get(i).getTitle());
                    name2.setVisibility(View.VISIBLE);
                    imDizhi1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    textName1.setVisibility(View.VISIBLE);
                    imDizhi1.setImageResource(R.drawable.yoji_i);
                    int finalI1 = i;
                    String content = listBeans.get(i).getTitle();
                    if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                        int index = content.indexOf(searchGuanjiaci.getText().toString());
                        if (index >= 0) {
                            textName1.setText(content.substring(0, index));
                            textName1.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                            String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                            textName1.append(str);
                        }
                    }

                    textName1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SearchResultActivity.this, YoJiDetailActivity.class);
                            intent.putExtra("yo_id", list.get(finalI1).getYo_id());
                            startActivity(intent);
                        }
                    });
                }
                if (list.get(i).getType() == 3) {
                    if (list.get(i).getFile_desc() != null) {
                        name3.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.VISIBLE);
                        imDizhi2.setVisibility(View.VISIBLE);
                        textName2.setVisibility(View.VISIBLE);
                        imDizhi2.setImageResource(R.drawable.yoxiu_i);
                        String content = listBeans.get(i).getFile_desc();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName2.setText(content.substring(0, index));
                                textName2.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName2.append(str);
                            }
                        }
                        int finalI2 = i;
                        textName2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchResultActivity.this, YoXiuDetailActivity.class);
                                intent.putExtra("id", list.get(finalI2).getYo_id());
                                startActivity(intent);
                            }
                        });
                    }
                }

                if (list.get(i).getType() == 4) {
                    if (list.get(i).getLabel() != null) {
                        textName3.setVisibility(View.VISIBLE);
                        name4.setVisibility(View.VISIBLE);
                        view4.setVisibility(View.VISIBLE);
                        imDizhi3.setVisibility(View.VISIBLE);
                        imDizhi3.setImageResource(R.drawable.biaoqian);
                        String content = listBeans.get(i).getLabel();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName3.setText(content.substring(0, index));
                                textName3.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName3.append(str);
                            }
                        }
                        textName3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.getKeyWord(user_id,user_token,content,"all");
                            }
                        });
                    }
                }
                if (list.get(i).getType() == 5) {
                    if (list.get(i).getPosition_name() != null) {
                        name5.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.VISIBLE);
                        imDizhi4.setVisibility(View.VISIBLE);
                        textName4.setVisibility(View.VISIBLE);
                        imDizhi4.setImageResource(R.drawable.didian);
                        String content = listBeans.get(i).getPosition_name();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName4.setText(content.substring(0, index));
                                textName4.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName4.append(str);
                            }
                        }
                        textName4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.getKeyWord(user_id,user_token,content,"all");
                            }
                        });
                    }
                }

                if (list.get(i).getType() == 6) {
                    if (list.get(i).getChannel() != null) {
                        view6.setVisibility(View.VISIBLE);
                        name6.setVisibility(View.VISIBLE);
                        imDizhi5.setVisibility(View.VISIBLE);
                        imDizhi5.setImageResource(R.drawable.pindao);
                        textName5.setVisibility(View.VISIBLE);

                        String content = listBeans.get(i).getChannel();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName5.setText(content.substring(0, index));
                                textName5.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName5.append(str);
                            }
                        }

                        textName5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.getKeyWord(user_id,user_token,content,"all");
                            }
                        });
                    }
                }
            }
    /*            if (list.get(i).getType() == 1) {
                    if (list.get(i).getUser_nickname() != null) {
                        view7.setVisibility(View.VISIBLE);
                        name7.setVisibility(View.VISIBLE);
                        imDizhi7.setVisibility(View.VISIBLE);
                        imDizhi7.setImageResource(R.drawable.yonghu);
                        textName7.setVisibility(View.VISIBLE);
                        String content = listBeans.get(i).getUser_nickname();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName7.setText(content.substring(0, index));
                                textName7.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName7.append(str);
                            }
                        }

                        textName7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                }
                if (list.get(i).getType() == 2) {
                    if (list.get(i).getTitle() != null) {
                        view8.setVisibility(View.VISIBLE);
                        name8.setVisibility(View.VISIBLE);
                        imDizhi8.setVisibility(View.VISIBLE);
                        imDizhi8.setImageResource(R.drawable.yoji_i);
                        textName8.setVisibility(View.VISIBLE);
                        String content = listBeans.get(i).getTitle();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName8.setText(content.substring(0, index));
                                textName8.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName8.append(str);
                            }
                        }

                        textName8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                }
                if (list.get(i).getType() == 3) {
                    if (list.get(i).getFile_desc() != null) {
                        view9.setVisibility(View.VISIBLE);
                        name9.setVisibility(View.VISIBLE);
                        imDizhi9.setVisibility(View.VISIBLE);
                        imDizhi9.setImageResource(R.drawable.yoxiu_i);
                        textName9.setVisibility(View.VISIBLE);
                        String content = listBeans.get(i).getFile_desc();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName9.setText(content.substring(0, index));
                                textName9.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName9.append(str);
                            }
                        }

                        textName9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                }
                if (list.get(i).getType() == 4) {
                    if (list.get(i).getLabel() != null) {
                        view10.setVisibility(View.VISIBLE);
                        name10.setVisibility(View.VISIBLE);
                        imDizhi10.setVisibility(View.VISIBLE);
                        imDizhi10.setImageResource(R.drawable.biaoqian);
                        textName10.setVisibility(View.VISIBLE);
                        String content = listBeans.get(i).getLabel();
                        if (!TextUtils.isEmpty(searchGuanjiaci.getText().toString()) && !TextUtils.isEmpty(content)) {
                            int index = content.indexOf(searchGuanjiaci.getText().toString());
                            if (index >= 0) {
                                textName10.setText(content.substring(0, index));
                                textName10.append(Html.fromHtml("<font color='#FA800A'>" + searchGuanjiaci.getText().toString() + "</font>"));
                                String str = content.substring(searchGuanjiaci.getText().toString().length() + index, content.length());
                                textName10.append(str);
                            }
                        }

                        textName10.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                }*/


        }
        if (list.size() == 0) {
            //   lv.setVisibility(View.VISIBLE);
            tvSetname.setVisibility(View.VISIBLE);
            hit.setVisibility(View.VISIBLE);
            Toast.makeText(SearchResultActivity.this, "没有匹配到您要查询的关键字", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            // Clear the text filter.
            listViewLv.clearTextFilter();
        } else {
            // Sets the initial value for the text filter.
            listViewLv.setFilterText(newText.toString());
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void keyWordMessage(KeywordBean keywordBean) {
        if (keywordBean.getData().getType().equals("user")) {
            mUser.clear();
            user_list = keywordBean.getData().getUser_list();
            lv.setAdapter(adapter);
            mUser.addAll(user_list);
            name.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            lvContent.setVisibility(View.GONE);
            lvUser.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            if (user_list.size() <= 0) {
                hit.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                lvUser.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
            }

//            SharedPrefrenceUtils.putSerializableList(SearchResultActivity.this,"user111",mUser);
        }
        if (keywordBean.getData().getType().equals("yox")) {
            myox.clear();
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            SearchYoXiuListAdapter adapter = new SearchYoXiuListAdapter(SearchResultActivity.this, myox);
            lvContent.setAdapter(adapter);
            for (int i = 0; i < myox.size(); i++) {
                myox.remove(i);
            }
            myox.addAll(yox_list);
            adapter.notifyDataSetChanged();
            lvUser.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            lvContent.setVisibility(View.VISIBLE);
        }
        if (keywordBean.getData().getType().equals("yoj")) {
            myoj.clear();
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            SearchYoJiListHorizontalAdapter adapter = new SearchYoJiListHorizontalAdapter(SearchResultActivity.this, myoj);
            lvUser.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            lvUser.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);
            adapter.setSetoncli(new SearchYoJiListHorizontalAdapter.setoncli() {
                @Override
                public void setoncli(int p) {
                    Intent intent = new Intent(SearchResultActivity.this, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", myoj.get(p).getYo_id());
                    startActivity(intent);
                }

                @Override
                public void set(int position) {
                }
            });
        }
        if (keywordBean.getData().getType().equals("all")) {

            mUser.clear();
            myoj.clear();
            myox.clear();

            //显示全部展示全部数据 用户
            List<KeywordBean.DataBean.UserListBean> user_list1 = keywordBean.getData().getUser_list();
            mUser.addAll(user_list1);
            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            SharedPrefrenceUtils.putSerializableList(SearchResultActivity.this, "all", mUser);
            //优秀
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            lvUser.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            SharedPrefrenceUtils.putSerializableList(SearchResultActivity.this, "yoji", myox);
            //有机
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            lvContent.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            name.setVisibility(View.VISIBLE);
            lv.setVisibility(View.VISIBLE);
            lvUser.setVisibility(View.VISIBLE);
            lvContent.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            hit.setVisibility(View.GONE);
            if (mUser.size() == 0){
                tvGson.setVisibility(View.VISIBLE);
                if (myoj.size() == 0 && myox.size() == 0){
                    tvGson.setVisibility(View.VISIBLE);
                    tvGson1.setVisibility(View.VISIBLE);
                    name1.setVisibility(View.GONE);
                    imDizhi.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                    textName.setVisibility(View.GONE);

                    name2.setVisibility(View.GONE);
                    imDizhi1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    textName1.setVisibility(View.GONE);

                    name3.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    imDizhi2.setVisibility(View.GONE);
                    textName2.setVisibility(View.GONE);

                    textName3.setVisibility(View.GONE);
                    name4.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);
                    imDizhi3.setVisibility(View.GONE);

                    name5.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    imDizhi4.setVisibility(View.GONE);
                    textName4.setVisibility(View.GONE);

                    view6.setVisibility(View.GONE);
                    name6.setVisibility(View.GONE);
                    imDizhi5.setVisibility(View.GONE);
                    textName5.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param :上下文环境，一般为Activity实例
     * @param view                 :一般为EditText
     */
    public static void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
