package Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Administrator on 2017/7/20 0020.
 */

public abstract class SingleBaseFragment extends Fragment {
    public View mFragmentView;
    protected abstract int createFragmentView();

    protected abstract void findView();//UI 文件初始化

    protected abstract void loadeData();//数据加载

    protected abstract void setUp();//逻辑处理

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(createFragmentView(), container, false);
        findView();
        loadeData();
        setUp();
        return mFragmentView;
    }
}
