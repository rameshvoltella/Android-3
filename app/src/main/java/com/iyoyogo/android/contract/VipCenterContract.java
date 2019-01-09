package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;

/**
 * 会员中心的契约类
 */
public interface VipCenterContract {

    interface View extends IBaseView {
        void getVipCenterSuccess(VipCenterBean vipCenterBean);

        void getUserInfoSuccess(MineMessageBean.DataBean data);

    }

    interface Presenter extends IBasePresenter {
        void getUserInfo(String user_id, String user_token);
        void getVipCenter(String user_id, String user_token);
    }
}
