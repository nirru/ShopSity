package com.oxilo.shopsity;

import android.support.v7.widget.RecyclerView;

import com.oxilo.shopsity.utility.AnimationUtils;

/**
 * Created by ericbasendra on 22/11/15.
 */
public class MyAnimator extends RecyclerView.ItemAnimator {
    @Override
    public boolean animateDisappearance(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        AnimationUtils.animate(viewHolder,true);
        return false;
    }

    @Override
    public boolean animatePersistence(RecyclerView.ViewHolder viewHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        AnimationUtils.animate(viewHolder,true);
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, ItemHolderInfo preLayoutInfo, ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void onAnimationFinished(RecyclerView.ViewHolder viewHolder) {
        super.onAnimationFinished(viewHolder);
    }
}
