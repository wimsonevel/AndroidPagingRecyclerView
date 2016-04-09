package wim.example.com.androidpaging.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wim.example.com.androidpaging.R;
import wim.example.com.androidpaging.adapter.MemberListAdapter;
import wim.example.com.androidpaging.model.Member;

/**
 * Created by wim on 4/5/16.
 */
public class MemberFragment extends Fragment {

    private int PAGE_SIZE = 6;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private RecyclerView listMember;
    private LinearLayoutManager linearLayoutManager;
    private MemberListAdapter memberListAdapter;

    protected Context context;

    int thumb[] = {R.drawable.acha, R.drawable.yupi, R.drawable.della,
            R.drawable.kinal, R.drawable.uty, R.drawable.shafa,
            R.drawable.hanna, R.drawable.lidya, R.drawable.nadila,
            R.drawable.nat, R.drawable.sisil, R.drawable.viny,
            R.drawable.chika_chan, R.drawable.ikha, R.drawable.ayen,
            R.drawable.saktia, R.drawable.dudut, R.drawable.yona};

    String name[] = {"Alicia Chanzia", "Cindy Yuvia", "Della Delila", "Devi Kinal Putri",
            "Dwi Putri Bonita", "Fakhriyani Shafariyanti", "Jennifer Hanna", "Lidya Maulida Djuhandar",
            "Nadila Cindi Wantari", "Natalia", "Priscillia Sari Dewi", "Ratu Vienny Fitrilya",
            "Rina Chikano", "Riskha Fairunissa", "Rona Anggreani", "Saktia Oktapyani",
            "Sinka Juliani", "Viviyona Apriani"};

    String motto[] = {"Hap!Tangkap Aku!", "Mau Kemana Kita?", "Future Processor", "K3bahagiaan Depi",
            "Different", "Shafa Disana?", "Target", "Banyak Mau", "Yossha Ikuzo!", "Spicy Hot",
            "Yuk Isi Batre Senter", "#KitaBisa", "Yuk.. Kita Dangdutan!", "Nothing Gonna Stop Us Now",
            "Stand Up!", "Saktia Dalam Jiwa", "Berarti Dalam Hidup", "Life In Technicolor"};

    public static MemberFragment newInstance(){
        return new MemberFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member, container, false);

        listMember = (RecyclerView) rootView.findViewById(R.id.listMember);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(context);
        memberListAdapter = new MemberListAdapter();

        listMember.setLayoutManager(linearLayoutManager);
        listMember.setAdapter(memberListAdapter);
        listMember.addOnScrollListener(recyclerViewOnScrollListener);

        initData();
    }

    private void initData(){
        List<Member> memberList = new ArrayList<>();
        Member member;

        for(int i=0; i < PAGE_SIZE; i++){
            member = new Member();

            member.setId(i+1);
            member.setName(name[i]);
            member.setTeam(motto[i]);
            member.setThumb(thumb[i]);

            memberList.add(member);
        }

        memberListAdapter.addAll(memberList);

    }

    private void loadData(){
        isLoading = false;

        List<Member> memberList = new ArrayList<>();
        Member member;

        int index = memberListAdapter.getItemCount();
        int end = index + PAGE_SIZE;

        if (end <= thumb.length) {
            for (int i = index; i < end; i++) {
                member = new Member();

                member.setId(i + 1);
                member.setName(name[i]);
                member.setTeam(motto[i]);
                member.setThumb(thumb[i]);

                memberList.add(member);
            }
            memberListAdapter.addAll(memberList);
            if(end >= thumb.length){
                memberListAdapter.setLoading(false);
            }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadData();
                        }
                    }, 2000);
                }
            }
        }
    };
}
