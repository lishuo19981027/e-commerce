package com.imooc.ecommerce.filter;


import com.imooc.ecommerce.vo.LoginUserInfo;

    /*使用 ThreadLocal 去单独存储每一个线程携带的 LoginUserInfo 信息
    * 要及时的清理我们保存到ThreadLocal中的用户信息
    * 1.保证没有资源泄漏
    * 2.保证线程在重用时，不会出现数据混乱
    * */
public class AccessContext {

    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();

    public static LoginUserInfo getLoginUserInfo(){
        return loginUserInfo.get();
    }

    public static void setLoginUserInfo(LoginUserInfo loginUserInfo_){
        loginUserInfo.set(loginUserInfo_);
    }

    public static void clearLoginUserInfo(){
        loginUserInfo.remove();
    }

}
