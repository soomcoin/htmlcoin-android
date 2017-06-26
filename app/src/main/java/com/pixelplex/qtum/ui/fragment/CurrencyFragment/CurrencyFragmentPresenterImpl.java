package com.pixelplex.qtum.ui.fragment.CurrencyFragment;


import com.pixelplex.qtum.model.contract.Token;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragmentPresenterImpl;

import java.util.ArrayList;
import java.util.List;


class CurrencyFragmentPresenterImpl extends BaseFragmentPresenterImpl implements CurrencyFragmentPresenter{

    private CurrencyFragmentView mCurrencyFragmentView;
    private CurrencyFragmentInteractorImpl mCurrencyFragmentInteractor;

    CurrencyFragmentPresenterImpl(CurrencyFragmentView currencyFragmentView){
        mCurrencyFragmentView = currencyFragmentView;
        mCurrencyFragmentInteractor = new CurrencyFragmentInteractorImpl(getView().getContext());
    }

    @Override
    public CurrencyFragmentView getView() {
        return mCurrencyFragmentView;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        List<String> contractTokenList = new ArrayList<>();
        contractTokenList.add("Qtum (default currency)");
        for(Token token : getInteractor().getTokenList()){
            if(token.isSubscribe()){
                contractTokenList.add(token.getContractName());
            }
        }
        getView().setTokenList(contractTokenList);
    }

    public CurrencyFragmentInteractorImpl getInteractor() {
        return mCurrencyFragmentInteractor;
    }
}