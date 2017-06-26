package com.pixelplex.qtum.ui.fragment.CurrencyFragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixelplex.qtum.R;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragment;
import com.pixelplex.qtum.ui.fragment.SendBaseFragment.SendBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrencyFragment extends BaseFragment implements CurrencyFragmentView{

    private CurrencyFragmentPresenterImpl mCurrencyFragmentPresenter;
    private TokenAdapter mTokenAdapter;
    private String mSearchString;
    private List<String> mCurrentList;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_search_currency)
    EditText mEditTextSearchCurrency;
    @BindView(R.id.tv_currency_title)
    TextView mTextViewCurrencyTitle;
    @BindView(R.id.ll_currency)
    FrameLayout mFrameLayoutBase;

    @OnClick({R.id.ibt_back})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ibt_back:
                getActivity().onBackPressed();
                break;
        }
    }

    public static CurrencyFragment newInstance() {

        Bundle args = new Bundle();

        CurrencyFragment fragment = new CurrencyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createPresenter() {
        mCurrencyFragmentPresenter = new CurrencyFragmentPresenterImpl(this);
    }

    @Override
    protected CurrencyFragmentPresenterImpl getPresenter() {
        return mCurrencyFragmentPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_currency;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        mTextViewCurrencyTitle.setText(R.string.currency);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mEditTextSearchCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    mTokenAdapter.setFilter(mCurrentList);
                } else {
                    mSearchString = editable.toString().toLowerCase();
                    List<String> newList = new ArrayList<>();
                    for(String currency: mCurrentList){
                        if(currency.toLowerCase().contains(mSearchString))
                            newList.add(currency);
                    }
                    mTokenAdapter.setFilter(newList);
                }
            }
        });

        mEditTextSearchCurrency.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH) {
                    mFrameLayoutBase.requestFocus();
                    hideKeyBoard();
                    return false;
                }
                return false;
            }
        });

        mFrameLayoutBase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    hideKeyBoard();
            }
        });
    }

    @Override
    public void setTokenList(List<String> tokenList) {
        mTokenAdapter = new TokenAdapter(tokenList);
        mCurrentList = tokenList;
        mRecyclerView.setAdapter(mTokenAdapter);
    }

    class CurrencyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_single_string)
        TextView mTextViewCurrency;
        @BindView(R.id.iv_check_indicator)
        ImageView mImageViewCheckIndicator;
        @BindView(R.id.ll_single_item)
        LinearLayout mLinearLayoutCurrency;

        String mCurrency;

        CurrencyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SendBaseFragment) getTargetFragment()).onCurrencyChoose(mCurrency);
                    getActivity().onBackPressed();
                }
            });

        }

        void bindCurrency(String currency){
            mCurrency = currency;
            mTextViewCurrency.setText(currency);
        }
    }

    private class TokenAdapter extends RecyclerView.Adapter<CurrencyHolder>{

        List<String> mTokenList;

        TokenAdapter(List<String> tokenList){
            mTokenList = tokenList;
        }

        @Override
        public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_single_checkable, parent, false);
            return new CurrencyHolder(view);
        }

        @Override
        public void onBindViewHolder(CurrencyHolder holder, int position) {
            holder.bindCurrency(mTokenList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTokenList.size();
        }

        void setFilter(List<String> newList){
            mTokenList = new ArrayList<>();
            mTokenList.addAll(newList);
            notifyDataSetChanged();
        }

        List<String> getTokenList() {
            return mTokenList;
        }
    }
}