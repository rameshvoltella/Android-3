package com.iyoyogo.android.ui.home.yoji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.YoJiDetailCommentAdapter;
import com.iyoyogo.android.adapter.CollectionFolderAdapter;
import com.iyoyogo.android.adapter.YoJiDetailAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.contract.YoJiDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoJiDetailPresenter;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.MyNestedScrollView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class YoJiDetailActivity extends BaseActivity<YoJiDetailContract.Presenter> implements YoJiDetailContract.View {

    private int open = 2;
    private boolean isOpen;
    public static int expendedtag = 2;
    List<String> mList = new ArrayList<>();
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.img_message)
    RelativeLayout imgMessage;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time_create)
    TextView tvTimeCreate;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_count_see)
    TextView tvCountSee;
    @BindView(R.id.tv_see)
    TextView tvSee;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.tv_yoji_count)
    TextView tvYojiCount;
    @BindView(R.id.tv_yoxiu_count)
    TextView tvYoxiuCount;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_address_start)
    TextView tvAddressStart;
    @BindView(R.id.tv_address_end)
    TextView tvAddressEnd;
    @BindView(R.id.tv_address_spot)
    TextView tvAddressSpot;
    @BindView(R.id.tv_spot_time)
    TextView tvSpotTime;
    @BindView(R.id.tv_money_pay)
    TextView tvMoneyPay;
    @BindView(R.id.realtive)
    RelativeLayout realtive;
    @BindView(R.id.tv_address_start_fold)
    TextView tvAddressStartFold;
    @BindView(R.id.tv_address_end_fold)
    TextView tvAddressEndFold;
    @BindView(R.id.tv_address_spot_fold)
    TextView tvAddressSpotFold;
    @BindView(R.id.tv_spot_time_fold)
    TextView tvSpotTimeFold;
    @BindView(R.id.tv_money_pay_fold)
    TextView tvMoneyPayFold;
    @BindView(R.id.message_trip)
    RelativeLayout messageTrip;
    @BindView(R.id.user_layouts)
    RelativeLayout userLayouts;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.describe_relative)
    RelativeLayout describeRelative;
    @BindView(R.id.recycler_yoji)
    RecyclerView recyclerYoji;
    @BindView(R.id.tv_load_more)
    TextView tvLoadMore;
    @BindView(R.id.comment_view)
    ImageView commentView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.tv_more_comment)
    TextView tvMoreComment;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.nested)
    MyNestedScrollView nested;


    private String user_token;
    private String user_id;
    private int is_my_attention;
    private int is_my_praise;
    private int yo_id;
    private YoJiDetailCommentAdapter yoJiDetailCommentAdapter;
    private RecyclerView recycler_collection;
    private int yo_user_id;
    private int add_collection_id;
    private int count_collect;
    private PopupWindow popup;
    private int is_my_collect;
    private List<YoJiDetailBean.DataBean> dataBeans;
    private ImageView img_tip;
    private TextView tv_message_three;
    private TextView tv_message_two;
    private TextView tv_message;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        yo_id = intent.getIntExtra("yo_id", 0);
        setSupportActionBar(toolbar);
        appbar.setExpanded(true);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //通过日志得出活动启动是两次，由于之前有setExpanded所以三次
                Log.d("启动活动调用监听次数", "几次");
                if (getSupportActionBar().getHeight() - appBarLayout.getHeight() == verticalOffset) {
                    //折叠监听
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(android.R.color.white));
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                    MIUISetStatusBarLightMode(getWindow(), true);
                    imgBack.setImageResource(R.mipmap.fanhui_black);
                    imgShare.setImageResource(R.mipmap.fenxiang_hei);
                    imgMessage.setVisibility(View.VISIBLE);
                    messageTrip.setVisibility(View.VISIBLE);
                    realtive.setVisibility(View.GONE);
//                    StatusBarUtil.setStatusBarColor(YoJiDetailActivity.this,Color.parseColor("#ffffff"));
                    Toast.makeText(YoJiDetailActivity.this, "折叠了", Toast.LENGTH_SHORT).show();
                }
                if (expendedtag == 2 && verticalOffset == 0) {
                    statusbar();
                    //展开监听
                    MIUISetStatusBarLightMode(getWindow(), true);
                    imgBack.setImageResource(R.mipmap.back_icon);
                    imgShare.setImageResource(R.mipmap.fenxiang_bai);
                    imgMessage.setVisibility(View.GONE);
                    messageTrip.setVisibility(View.GONE);
                    realtive.setVisibility(View.VISIBLE);
//                    StatusBarUtil.setStatusBarColor(YoJiDetailActivity.this,Color.parseColor("#00000000"));
                    Toast.makeText(YoJiDetailActivity.this, "展开了", Toast.LENGTH_SHORT).show();
                }
                if (expendedtag != 2 && verticalOffset == 0) {
                    expendedtag++;
                }
            }
        });
        editComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获得焦点
                    tvCollection.setVisibility(View.GONE);
                    tvLike.setVisibility(View.GONE);
                    editComment.setHint("码字不容易，留个评论鼓励下嘛~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    sendEmoji.setVisibility(View.VISIBLE);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
////                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//                    layoutParams.alignWithParent=true;

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, 0, DensityUtil.dp2px(YoJiDetailActivity.this, 40), 0);
                    editComment.setLayoutParams(layoutParams);

                } else {
                    //失去焦点
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(YoJiDetailActivity.this, 230), ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, DensityUtil.dp2px(YoJiDetailActivity.this, 20), 0, 0);
                    editComment.setLayoutParams(layoutParams);
                    tvCollection.setVisibility(View.VISIBLE);
                    tvLike.setVisibility(View.VISIBLE);
                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    sendEmoji.setVisibility(View.GONE);

                }
            }
        });
        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(YoJiDetailActivity.this, MoreTopicActivity.class));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (editComment.getText().toString().length() > 0) {
                        mPresenter.addComment(user_id, user_token, 0, yo_id, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, yo_id, 0);
                        editComment.clearFocus();
                        editComment.setFocusable(false);
//                        yoXiuDetailAdapter.notifyItemInserted(dataBeans.size());
                    } else {
                    }
                    return true;
                }
                return false;

            }
        });
    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(editComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected YoJiDetailContract.Presenter createPresenter() {
        return new YoJiDetailPresenter(this);
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        mPresenter.getYoJiDetail(user_id, user_token, yo_id);
        mPresenter.getCommentList(user_id, user_token, 1, yo_id, 0);

    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.tv_attention, R.id.tv_load_more, R.id.tv_comment, R.id.tv_like, R.id.tv_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:

                break;
            case R.id.tv_attention:

                break;
            case R.id.tv_load_more:

                break;
            case R.id.tv_comment:

                break;
            case R.id.tv_like:
                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);
                tvLike.setCompoundDrawablesWithIntrinsicBounds(null, dataBeans.get(0).getIs_my_praise() > 0 ? liked : like, null, null);
                int count_praise = dataBeans.get(0).getCount_praise();

                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + dataBeans.get(0).getIs_my_praise());
                if (dataBeans.get(0).getIs_my_praise() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            like, null, null);
                    count_praise -= 1;
                    //设置点赞的数量
                    tvLike.setText(count_praise + "");
                    dataBeans.get(0).setIs_my_praise(0);
                    dataBeans.get(0).setCount_praise(count_praise);
                    like();
                    popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
                } else {
                    //由不喜欢变为喜欢，暗变亮
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            liked, null, null);
                    count_praise += 1;
                    //设置点赞的数量
                    tvLike.setText(count_praise + "");
                    dataBeans.get(0).setIs_my_praise(1);
                    dataBeans.get(0).setCount_praise(count_praise);
                }
                DataManager.getFromRemote().praise(user_id, user_token, dataBeans.get(0).getYo_id(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
                break;
            case R.id.tv_collection:
                collection();
                break;
        }
    }

    public void initPopup() {
        View view = LayoutInflater.from(YoJiDetailActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(YoJiDetailActivity.this, 300), DensityUtil.dp2px(YoJiDetailActivity.this, 145), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        tv_message = view.findViewById(R.id.tv_message);
        tv_message_two = view.findViewById(R.id.tv_message_two);

        tv_message_three = view.findViewById(R.id.tv_message_three);
        img_tip = view.findViewById(R.id.tip_img);

        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
    }

    public void like() {

        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢喜欢~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
    }

    private void collections() {
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        if (dataBeans.get(0).getIs_my_collect() == 0) {

            tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    collection, null, null);

        } else {
            tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    collectioned, null, null);
        }

        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

    }


    private void praise() {
        Drawable like = getResources().getDrawable(
                R.mipmap.xihuan_xiangqing);
        Drawable liked = getResources().getDrawable(
                R.mipmap.yixihuan_xiangqing);
        if (dataBeans.get(0).getIs_my_praise() == 0) {

            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    like, null, null);

        } else {
            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    liked, null, null);
        }
        tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_praise() == 0 ? like : liked, null, null);

    }

    @Override
    public void getYoJiDetailSuccess(YoJiDetailBean.DataBean data) {
        initPopup();
        dataBeans = new ArrayList<>();
        dataBeans.add(data);
        yo_user_id = data.getUser_id();
        count_collect = data.getCount_collect();
        is_my_collect = data.getIs_my_collect();
        is_my_attention = data.getIs_my_attention();
        is_my_praise = data.getIs_my_praise();
        collections();
        praise();
        is_my_attention = data.getIs_my_attention();
        if (is_my_attention == 0) {
            tvAttention.setText("+ 关注");
        } else {
            tvAttention.setText("已关注");
        }
        String logo = data.getLogo();
        Glide.with(this).load(data.getUser_logo()).into(userIcon);
        Glide.with(this).load(data.getUser_logo()).into(imgHead);
        Glide.with(this).load(logo).into(bg);
        tvUserName.setText(data.getUser_nickname());
        tvUserNickname.setText(data.getUser_nickname());
        tvTitle.setText(data.getTitle());
        tvYojiCount.setText(data.getCount_yoj() + "");
        tvYoxiuCount.setText(data.getCount_yox() + "");
        tvTimeCreate.setText(data.getCreate_time());
        tvCountSee.setText(data.getCount_view() + "人");
        tvMoneyPay.setText(data.getCost() + "人/天");
        tvMoneyPayFold.setText(data.getCost() + "人/天");
        tvSpotTime.setText(data.getCount_dates() + "天");
        tvSpotTimeFold.setText(data.getCount_dates() + "天");
        tvAddressStart.setText(data.getP_start());
        tvAddressStartFold.setText(data.getP_start());
        tvAddressSpot.setText(data.getList().size() + "个地点");
        tvAddressSpotFold.setText(data.getList().size() + "个地点");
        tvAddressEnd.setText(data.getP_end());
        tvAddressEndFold.setText(data.getP_end());

        List<YoJiDetailBean.DataBean.ListBean> list = data.getList();
        YoJiDetailAdapter yoJiDetailAdapter = new YoJiDetailAdapter(YoJiDetailActivity.this, list);

        recyclerYoji.setAdapter(yoJiDetailAdapter);
        recyclerYoji.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));

        yoJiDetailAdapter.setOnItemClickListener(new YoJiDetailAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        yoJiDetailAdapter.setOnItemDataListener(new YoJiDetailAdapter.OnPlayListener() {
            @Override
            public void getData(YoJiDetailAdapter.Holder holder, int position) {
            }
        });
    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    private void createCollectionFolder() {
        backgroundAlpha(0.6f);
        View view = LayoutInflater.from(YoJiDetailActivity.this).inflate(R.layout.popup_collection, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        EditText edit_title_collection = view.findViewById(R.id.edit_title_collection);
        TextView tv_sure = view.findViewById(R.id.sure);
        ImageView clear = view.findViewById(R.id.clear);
        ImageView close_img = view.findViewById(R.id.close_img);
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_title_collection.setText("");
                clear.setVisibility(View.GONE);
            }
        });
        ImageView img_btn = view.findViewById(R.id.img_btn);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    img_btn.setImageResource(R.mipmap.on);
                    open = 1;
                    isOpen = true;
                } else {
                    img_btn.setImageResource(R.mipmap.off);
                    open = 2;
                    isOpen = false;
                }
            }
        });

        edit_title_collection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    clear.setVisibility(View.GONE);
                    tv_sure.setClickable(false);
                    tv_sure.setBackgroundResource(R.color.cut_off_line);
                } else {
                    tv_sure.setClickable(true);
                    tv_sure.setClickable(true);
                    clear.setVisibility(View.VISIBLE);
                    tv_sure.setBackgroundResource(R.color.color_orange);
                }
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();

                mPresenter.createCollectionFolder(user_id, user_token, edit_title_collection.getText().toString().trim(), open, "");
            }
        });
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(YoJiDetailActivity.this).inflate(R.layout.item_collection_list, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());

        LinearLayout create_folder = view.findViewById(R.id.create_folder);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                backgroundAlpha(0.6f);
                createCollectionFolder();
            }
        });
        recycler_collection = view.findViewById(R.id.recycler_collection);
        mPresenter.getCollectionFolder(user_id, user_token);


        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {
        yoJiDetailCommentAdapter = new YoJiDetailCommentAdapter(YoJiDetailActivity.this, data.getList());
        recyclerComment.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));
        recyclerComment.setAdapter(yoJiDetailCommentAdapter);
    }

    @Override
    public void addCommentSuccess(BaseBean baseBean) {
        editComment.setText("");
        yoJiDetailCommentAdapter.notifyDataSetChanged();

    }

    @Override
    public void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean) {

        List<CollectionFolderBean.DataBean.ListBean> mList = new ArrayList<>();
        List<CollectionFolderBean.DataBean.ListBean> list = collectionFolderBean.getList();
        CollectionFolderBean.DataBean.ListBean listBean = new CollectionFolderBean.DataBean.ListBean();
        listBean.setId(0);
        listBean.setName("默认收藏");
        listBean.setOpen(1);
//        mList.add(listBean);
        mList.addAll(list);
        CollectionFolderAdapter collectionFolderAdapter = new CollectionFolderAdapter(mList);
        recycler_collection.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));
        recycler_collection.setAdapter(collectionFolderAdapter);
        collectionFolderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               /* int is_my_collect = collection_list.get(0).getIs_my_collect();

                if (is_my_collect == 0) {
                    mPresenter.addCollection(user_id, user_token, id, yo_id);
                } else {
                    if (collection_id == null) {
                        mPresenter.deleteCollection(user_id, user_token, is_my_collect);
                    } else {

                        int i = Integer.parseInt(collection_id);
                        mPresenter.deleteCollection(user_id, user_token, i);
                    }
                }*/
                int folder_id = mList.get(position).getId();
                mPresenter.getYoJiDetail(user_id, user_token, yo_id);
                if (is_my_attention == 0) {
                    mPresenter.addCollection(user_id, user_token, folder_id, yo_user_id);
//                    Log.d("YoXiuDetailActivity", target_id);

                } else {

                    if (add_collection_id == 0) {
                        mPresenter.deleteCollection(user_id, user_token, is_my_collect);
                    } else {
                        mPresenter.deleteCollection(user_id, user_token, add_collection_id);
                    }
                }
                popup.dismiss();

            }
        });
    }

    @Override
    public void createFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "创建文件夹", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCollectionSuccess(AddCollectionBean.DataBean data) {
        String collection_id = data.getId();
        add_collection_id = Integer.parseInt(collection_id);
        count_collect += 1;
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        dataBeans.get(0).setIs_my_collect(Integer.parseInt(collection_id));
        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

        tvCollection.setText(count_collect + "");
    }

    @Override
    public void deleteCollectionSuccess(BaseBean baseBean) {

    }


}