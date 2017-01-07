package com.sicnu.cheer.im.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sicnu.cheer.generalmodule.util.HttpAccessUtils;
import com.sicnu.cheer.generalmodule.util.MD5;
import com.sicnu.cheer.generalmodule.util.SharedPreferencesUtil;
import com.sicnu.cheer.generalmodule.util.UrlUtil;
import com.sicnu.cheer.im.R;
import com.sicnu.cheer.im.util.UrlConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by cheer on 2016/12/19.
 */

public class MessageFragment extends Fragment implements RongIM.UserInfoProvider {
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        RongIM.setUserInfoProvider(this, false);
        RongIM.getInstance().setMessageAttachedUserInfo(true);

        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversation_list);

        Uri uri = Uri.parse("rong://" + activity.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
        return view;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        getUser(userId);
        return null;
    }

    private void getUser(String userId) {
        String url = UrlUtil.getUrlWithAction(activity, UrlConfig.USER_INFO_ACTION);
        String token = SharedPreferencesUtil.getUserItem(activity, "token");
        long timestamp = System.currentTimeMillis();
        String keycode = MD5.MD5Encode(token + timestamp + "china_life_user_info");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        params.put("timestamp", timestamp);
        params.put("keycode", keycode);
        params.put("userId", userId);
        HttpAccessUtils.callHttpAccess(url, params, new HttpAccessUtils.HttpAccessCallBack() {
            @Override
            public void callback(String result) {
                if (result != null) {
                    try {
                        JSONObject resultJson = new JSONObject(result);
                        String userId = resultJson.optString("id", "");
                        String name = resultJson.optString("name", "");
                        String attachmentId = resultJson.optString("attachment_id", "");
                        String portraitUri = "android.resource://" + activity.getPackageName() + "/" + R.mipmap.default_user_img;
//                        if (!StringUtils.isEmpty(attachmentId)) {
//                            portraitUri = UrlUtil.getUrlWithAction(activity, getString(R.string.userHeadPhotoAction)) + "?userId=" + userId + "&path=" + attachmentId;
//                        }
                        UserInfo userInfo = new UserInfo(userId, name, Uri.parse(portraitUri));
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
