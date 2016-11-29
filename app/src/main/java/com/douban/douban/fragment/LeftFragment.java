package  com.douban.douban.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.douban.douban.R;
import com.douban.douban.activity.MainActivity_;
import com.douban.douban.activity.SignInActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

/**
 * 作者：Andayoung on 2016/10/24 16:49
 * 邮箱：zhoup324@163.com
 */
@EFragment(R.layout.layout_menu)
public class LeftFragment extends Fragment{
    @StringRes(R.string.today)
    String today;
    @StringRes(R.string.lastList)
    String lastList;
    @StringRes(R.string.discussMeetting)
    String discussMeetting;
    @StringRes(R.string.myFavorities)
    String myFavorities;
    @StringRes(R.string.myComments)
    String myComments;
    @StringRes(R.string.settings)
    String settings;


    @ViewsById({R.id.tvToday,R.id.tvLastlist,R.id.tvDiscussMeeting
    ,R.id.tvMyFavorites,R.id.tvMyComments,R.id.tvMySettings})
    List<TextView> listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    
    @AfterViews
    public void inits() {

    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Click({R.id.tvToday,R.id.tvLastlist,R.id.tvDiscussMeeting
            ,R.id.tvMyFavorites,R.id.tvMyComments,R.id.tvMySettings})
    public void toToday(View view){
        Intent intent=new Intent(getActivity(), SignInActivity_.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
        switch (view.getId()){
            case R.id.tvToday:

                break;
            case R.id.tvLastlist:
                break;
            case R.id.tvDiscussMeeting:
                break;
            case R.id.tvMyFavorites:
                break;
            case R.id.tvMyComments:
                break;
            case R.id.tvMySettings:
                break;
        }

    }

    public void turnSwitch(String title,Fragment newContent){
        if (newContent != null) {
            switchFragment(newContent, title);
        }
    }

    
    /**
     * 切换fragment
     * @param fragment
     */
    private void switchFragment(Fragment fragment, String title) {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() instanceof MainActivity_) {
            MainActivity_ fca = (MainActivity_) getActivity();
            fca.switchConent(fragment, title);
        }
    }
    
}
