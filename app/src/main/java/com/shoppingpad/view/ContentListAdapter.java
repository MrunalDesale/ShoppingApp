package com.shoppingpad.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppingpad.BR;
import com.shoppingpad.R;
import com.shoppingpad.databinding.CustomRowBinding;

import com.shoppingpad.viewmodel.ContentViewModelHandler;
import com.shoppingpad.viewmodel.ContentViewModelData;

/**
 * Created by bridgelabz3 on 13/3/16.
 * Purpose:
 *
 * Set adapter of RecycleView
 */
public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter
                                                            .ViewDataHolder> {

    ContentViewModelHandler mContentViewModelHandler;

    public ContentListAdapter(ContentViewModelHandler contentViewModelHandler){
        this.mContentViewModelHandler = contentViewModelHandler;
    }

    @Override
    public ViewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.
                                                        custom_row,parent,false);
        return new ViewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewDataHolder holder, int position) {

        ContentViewModelData current= mContentViewModelHandler.getSingleData(position);

        holder.getBinding().setVariable(BR.contentviewmodeldata,current);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mContentViewModelHandler.getListSize();
    }

    //Class to bind view
    public class ViewDataHolder extends RecyclerView.ViewHolder {

        CustomRowBinding mBinding;

        public ViewDataHolder(View itemView) {

            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
        public CustomRowBinding getBinding(){
            return mBinding;
        }
    }
}