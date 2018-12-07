package com.iyoyogo.android.ui.home.recommend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoXiuDetailAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoXiuDetailPresenter;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class YoXiuDetailActivity extends BaseActivity<YoXiuDetailContract.Presenter> implements YoXiuDetailContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.share_img)
    ImageView shareImg;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.img_go)
    ImageView imgGo;
    @BindView(R.id.img_bottom)
    ImageView imgBottom;
    @BindView(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.collection)
    TextView collection;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.num_look)
    TextView numLook;
    @BindView(R.id.num_look_tv)
    TextView numLookTv;
    @BindView(R.id.comment_view)
    View commentView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.tv_more_comment)
    TextView tvMoreComment;
    @BindView(R.id.srcoll)
    ScrollView srcoll;
    @BindView(R.id.activity_yoxiu_detail)
    RelativeLayout activityYoxiuDetail;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_water_mark)
    ImageView imgWaterMark;
    @BindView(R.id.img_comment_null)
    ImageView imgCommentNull;
    @BindView(R.id.tv_comment_null)
    TextView tvCommentNull;
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    private LocalMedia mMedia;
    private String mimeType;
    private String user_id;
    private String user_token;
    private int id;
    private List<YoXiuDetailBean.DataBean> dataBeans;
    private YoXiuDetailAdapter yoXiuDetailAdapter;
    private String target_id;
    private int yo_user_id;
    private int is_my_attention;

    @Override
    protected void initView() {
        super.initView();

        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(YoXiuDetailActivity.this, MoreTopicActivity.class));
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
                        mPresenter.addComment(user_id, user_token, 0, id, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
                        editComment.clearFocus();
                        editComment.setFocusable(false);
//                        yoXiuDetailAdapter.notifyItemInserted(dataBeans.size());
                    } else {
                        Toast.makeText(YoXiuDetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_xiu_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        super.initData(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        user_id = SpUtils.getString(YoXiuDetailActivity.this, "user_id", null);
        user_token = SpUtils.getString(YoXiuDetailActivity.this, "user_token", null);
        Log.d("YoXiuDetailActivity", "id:" + id);
        Log.d("YoXiuDetailActivity", "user_id:" + user_id);
        Log.d("YoXiuDetailActivity", "user_token:" + user_token);
        mPresenter.getDetail(user_id, user_token, id);
        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
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
                    layoutParams.setMargins(0, 0, DensityUtil.dp2px(YoXiuDetailActivity.this, 40), 0);
                    editComment.setLayoutParams(layoutParams);

                } else {
                    //失去焦点
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(YoXiuDetailActivity.this, 230), ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, DensityUtil.dp2px(YoXiuDetailActivity.this, 20), 0, 0);
                    editComment.setLayoutParams(layoutParams);
                    tvCollection.setVisibility(View.VISIBLE);
                    tvLike.setVisibility(View.VISIBLE);
                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    sendEmoji.setVisibility(View.GONE);

                }
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
    protected YoXiuDetailContract.Presenter createPresenter() {
        return new YoXiuDetailPresenter(this);
    }

    public void share() {
        View view = getLayoutInflater().inflate(R.layout.popup_share, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dp2px(YoXiuDetailActivity.this,220), true);
        popup_share.setBackgroundDrawable(new ColorDrawable());
        popup_share.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_share.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout qq_layout = view.findViewById(R.id.qq_layout);
        LinearLayout comment_layout = view.findViewById(R.id.comment_layout);
        LinearLayout wechat_layout = view.findViewById(R.id.wechat_layout);
        LinearLayout sina_layout = view.findViewById(R.id.sina_layout);
        TextView tv_cancel = view.findViewById(R.id.cancel);
        ImageView img_close = view.findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        sina_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);

    }


    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @OnClick({R.id.back, R.id.share_img, R.id.img_brow, R.id.tv_like, R.id.tv_collection, R.id.img_logo, R.id.tv_desc, R.id.img_go, R.id.collection, R.id.num_look, R.id.num_look_tv, R.id.tv_comment, R.id.tv_more_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share_img:
                share();
                break;
            case R.id.img_brow:

                break;
            case R.id.tv_like:
                int count_praise = dataBeans.get(0).getCount_praise();
                dataBeans.get(0).setIs_my_like(dataBeans.get(0).getIs_my_like() == 1 ? 0 : 1);
                if (dataBeans.get(0).getIs_my_like() == 1) {
                    count_praise += 1;
                } else if (count_praise > 0) {
                    count_praise -= 1;
                }
                dataBeans.get(0).setCount_praise(count_praise);


                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);
                if (dataBeans.get(0).getIs_my_like() == 0) {

                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            like, null, null);
                    tvLike.setText(count_praise + "");
                } else {
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            liked, null, null);
                    tvLike.setText(count_praise + "");
                }

                tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                        dataBeans.get(0).getIs_my_like() == 0 ? like : liked, null, null);


                DataManager.getFromRemote().praise(user_id, user_token, dataBeans.get(0).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                                refresh();
                            }
                        });
                break;
            case R.id.tv_collection:
//TODO
//                collections();
//                collection();
                break;
            case R.id.img_logo:

                break;
            case R.id.tv_desc:

                break;
            case R.id.img_go:

                break;
            case R.id.collection:
                if (collection.getText().toString().trim().equals("已关注")) {

                    if (is_my_attention == 0) {
                        int target = Integer.parseInt(target_id);
                        mPresenter.deleteAttention(user_id, user_token, target);

                    } else {
                        mPresenter.deleteAttention(user_id, user_token, is_my_attention);
                    }
                    collection.setText("+ 关注");
                } else {
                    mPresenter.addAttention(user_id, user_token, yo_user_id);
                    collection.setText("已关注");
                }
//                attention();

                break;
            case R.id.num_look:

                break;
            case R.id.num_look_tv:

                break;
            case R.id.tv_comment:

                break;
            case R.id.tv_more_comment:

                break;
        }
    }

    private void createCollectionFolder() {
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.popup_collection, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        EditText edit_title_collection = view.findViewById(R.id.edit_title_collection);
        TextView tv_sure = view.findViewById(R.id.sure);
        ImageView clear = view.findViewById(R.id.clear);
        tv_sure.setClickable(false);
        if (edit_title_collection.getText().length() < 0) {
            clear.setVisibility(View.GONE);
            tv_sure.setBackgroundResource(R.color.cut_off_line);
        } else {
            tv_sure.setClickable(false);
            clear.setVisibility(View.VISIBLE);
            tv_sure.setBackgroundResource(R.color.color_orange);
        }
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.item_collection_list, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());

        LinearLayout create_folder = view.findViewById(R.id.create_folder);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCollectionFolder();
            }
        });
        RecyclerView recycler_collection = view.findViewById(R.id.recycler_collection);
        recycler_collection.setLayoutManager(new LinearLayoutManager(YoXiuDetailActivity.this));
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        backgroundAlpha(0.6f);
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getDetailSuccess(YoXiuDetailBean.DataBean data) {
        yo_user_id = data.getUser_id();
        dataBeans = new ArrayList<>();
        dataBeans.add(data);
        is_my_attention = data.getIs_my_attention();
        if (is_my_attention == 0) {
            collection.setText("+ 关注");

        } else {
            collection.setText("已关注");
        }
        String create_time = data.getCreate_time();
        String time = create_time.replaceAll("-", ".");
        tvTime.setText(time);
        int count_collect = data.getCount_collect();
        tvCollection.setText(count_collect + "");
        int count_praise = data.getCount_praise();
        tvLike.setText(count_praise + "");
        int count_comment = data.getCount_comment();
        tvCollection.setText(count_comment + "");
        int count_view = data.getCount_view();
        numLook.setText(count_view + "");
        String file_desc = data.getFile_desc();
        textDesc.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

        String path = data.getFile_path();
        RequestOptions requestOptions = new RequestOptions();
        if (editComment.getText().toString().length() > 0) {
            mPresenter.addComment(user_id, user_token, 0, this.id, editComment.getText().toString().trim());
            closeInputMethod();
            yoXiuDetailAdapter.notifyDataSetChanged();
            refresh();
        } else {
            Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
        }
        ViewGroup.LayoutParams layoutParams = imgLogo.getLayoutParams();
        int width = layoutParams.width;
        requestOptions.placeholder(R.mipmap.default_ic).override(DensityUtil.dp2px(YoXiuDetailActivity.this, ViewGroup.LayoutParams.MATCH_PARENT), DensityUtil.dp2px(YoXiuDetailActivity.this, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(this).load(path)
                .apply(requestOptions)
                .into(imgLogo);
        int file_type = data.getFile_type();
        if (file_type == 2) {
            imgVideo.setVisibility(View.VISIBLE);
        } else if (file_type == 1) {
            imgVideo.setVisibility(View.GONE);
        }
        String user_logo = data.getUser_logo();
        Glide.with(this).load(user_logo).into(imgUserIcon);
        String position_name = data.getPosition_name();
        tvDesc.setText(position_name);
        String user_nickname = data.getUser_nickname();
        userName.setText(user_nickname);
        praise();
        collections();
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


        tvCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* DataManager.getFromRemote().praise(user_id, user_token, dataBeans.get(0).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                int count_collect = dataBeans.get(0).getCount_collect();
                                dataBeans.get(0).setIs_my_collect(dataBeans.get(0).getIs_my_like() == 1 ? 0 : 1);
                                if (dataBeans.get(0).getIs_my_collect() == 1) {
                                    count_collect += 1;
                                } else if (count_collect > 0) {
                                    count_collect -= 1;
                                }
                                dataBeans.get(0).setCount_collect(count_collect);
                                refresh();
                            }
                        });*/
            }
        });
    }

    private void attention() {

        if (dataBeans.get(0).getIs_my_like() == 0) {

            collection.setVisibility(View.VISIBLE);

        } else {
            collection.setVisibility(View.GONE);
        }

        collection.setVisibility(dataBeans.get(0).getIs_my_like() == 0 ? View.VISIBLE : View.GONE);


        tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collection.setVisibility(View.GONE);
                refresh();
            }
        });
    }

    private void praise() {
        Drawable like = getResources().getDrawable(
                R.mipmap.xihuan_xiangqing);
        Drawable liked = getResources().getDrawable(
                R.mipmap.yixihuan_xiangqing);
        if (dataBeans.get(0).getIs_my_like() == 0) {

            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    like, null, null);

        } else {
            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    liked, null, null);
        }

        tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_like() == 0 ? like : liked, null, null);

    }

    public void refresh() {

        getWindow().getDecorView().invalidate();

    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {

        List<CommentBean.DataBean.ListBean> list = data.getList();
        List<CommentBean.DataBean.ListBean> commentList = new ArrayList<>();
        commentList.addAll(list);
        if (commentList.size() > 0) {
            tvMoreComment.setVisibility(View.VISIBLE);
            recyclerComment.setVisibility(View.VISIBLE);
            imgCommentNull.setVisibility(View.GONE);
            tvCommentNull.setVisibility(View.GONE);
        } else {
            tvMoreComment.setVisibility(View.GONE);
            recyclerComment.setVisibility(View.GONE);
            imgCommentNull.setVisibility(View.VISIBLE);
            tvCommentNull.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoXiuDetailActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerComment.setLayoutManager(linearLayoutManager);
        yoXiuDetailAdapter = new YoXiuDetailAdapter(YoXiuDetailActivity.this, commentList);
        recyclerComment.setAdapter(yoXiuDetailAdapter);

    }


    @Override
    public void addCommentSuccess(BaseBean baseBean) {
        String msg = baseBean.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        editComment.setText("");
        yoXiuDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void addAttentionSuccess(AttentionBean.DataBean data) {
        target_id = data.getId();
        Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteAttentionSuccess(BaseBean baseBean) {
        Toast.makeText(this, "取消关注", Toast.LENGTH_SHORT).show();
    }


    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }
}
