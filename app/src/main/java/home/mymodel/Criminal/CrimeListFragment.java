package home.mymodel.Criminal;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import Base.SingleBaseFragment;
import home.mymodel.Criminal.MyRecycleView.MyRecyclerVerAdapter;
import home.mymodel.R;
import newhome.baselibrary.Bus.BusAction;

/**
 * Created by Administrator on 2017/7/20 0020.
 */
public class CrimeListFragment extends SingleBaseFragment {
    private RecyclerView mCrimeRecyclerView;
    private MyRecyclerVerAdapter mMyRecyclerVerAdapter;
    @Override
    protected int createFragmentView() {
        return R.layout.fragment_crime_list;
    }
    @Override
    protected void findView() {
        mCrimeRecyclerView=(RecyclerView)mFragmentView.findViewById(R.id.crime_recycler_view);
    }
    @Override
    protected void loadeData() {
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    @Override
    protected void setUp() {
        updateUI();
    }
    private void updateUI(){
        CrimeLabData crimeLabData=CrimeLabData.get(getActivity());
        List<CrimeData> crimeDatas= crimeLabData.getCrimeDatas();
//        mMyRecyclerVerAdapter=new MyRecyclerVerAdapter<List<CrimeData>>(crimeDatas,getActivity(),BusAction.CRIME_RECYCLE_VIEW_TYPT);//使用一
//        mMyRecyclerVerAdapter=new MyRecyclerVerAdapter<List<CrimeData>>(crimeDatas,R.layout.simple_list_item_1,getActivity(),BusAction.CRIME_RECYCLE_VIEW_TYPT);//使用二
        mMyRecyclerVerAdapter=new MyRecyclerVerAdapter<List<CrimeData>>(crimeDatas,R.layout.list_item_crime,getActivity(),BusAction.CRIME_RECYCLE_VIEW_TYPT_TWO);//使用二
        mCrimeRecyclerView.setAdapter(mMyRecyclerVerAdapter);
    }
}
