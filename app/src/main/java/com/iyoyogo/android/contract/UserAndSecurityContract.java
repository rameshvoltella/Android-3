package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
/**
 * 用户安全的契约类
 */
public interface UserAndSecurityContract{
    interface View extends IBaseView{
        void getBindInfoSuccess(GetBindInfoBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void getBindInfo(String user_id,String user_token);
    }
}
