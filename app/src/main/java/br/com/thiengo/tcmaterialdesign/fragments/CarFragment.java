package br.com.thiengo.tcmaterialdesign.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.thiengo.tcmaterialdesign.MainActivity;
import br.com.thiengo.tcmaterialdesign.R;
import br.com.thiengo.tcmaterialdesign.adapters.CarAdapter;
import br.com.thiengo.tcmaterialdesign.domain.Car;
import br.com.thiengo.tcmaterialdesign.interfaces.RecyclerViewOnClickListenerHack;

public class CarFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<Car> mList;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                //GridLayoutManager llm = (GridLayoutManager) mRecyclerView.getLayoutManager();
                /*StaggeredGridLayoutManager llm = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
                int[] aux = llm.findLastCompletelyVisibleItemPositions(null);
                int max = -1;
                for(int i = 0; i < aux.length; i++){
                    max = aux[i] > max ? aux[i] : max;
                }*/

                CarAdapter adapter = (CarAdapter) mRecyclerView.getAdapter();

                if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                //if (mList.size() == max + 1) {
                    List<Car> listAux = ((MainActivity) getActivity()).getSetCarList(10);

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), mList.size());
                    }
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener( getActivity(), mRecyclerView, this ));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setReverseLayout(true);
        mRecyclerView.setLayoutManager(llm);

        /*GridLayoutManager llm = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);*/

        /*StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        llm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(llm);*/

        mList = ((MainActivity) getActivity()).getSetCarList(10);
        CarAdapter adapter = new CarAdapter(getActivity(), mList);
        //adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter( adapter );


        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onClickListener(): "+position, Toast.LENGTH_SHORT).show();

        /*CarAdapter adapter = (CarAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);*/
    }
    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onLongPressClickListener(): "+position, Toast.LENGTH_SHORT).show();

        /*CarAdapter adapter = (CarAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);*/
    }


    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh){
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildPosition(cv) );
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildPosition(cv) );
                    }

                    return(true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
    }
}
