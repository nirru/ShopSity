package com.oxilo.shopsity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.ui.CustomTextView;
import com.oxilo.shopsity.utility.ActivityUtils;
import com.oxilo.shopsity.utility.AnimationUtils;

import java.util.List;

/**
 * Created by ericbasendra on 22/11/15.
 */
public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder>{

    private Context mContext;
    private List<CampList> campLists;
    private static MyClickListener myClickListener;
    private int mLastPosition = 5;

    public CampaignListAdapter(List<CampList> campLists,Context mContext) {
        this.mContext = mContext;
        this.campLists = campLists;
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public void addItem(CampList dataObj, int index) {
        campLists.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        campLists.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CampList campList = campLists.get(position);
        holder.call_webView.setText("" + campList.getCall() + campList.getWeb());
        holder.totalAdsRemainingView.setText("" + campList.getShownImp());
        holder.totalAdsView.setText("" + campList.getTotalImp());
        holder.startTimeView.setText("" + ActivityUtils.GetDateTime(Long.valueOf(campList.getStartDate())));
        holder.campaignView.setText("" + campList.getCampId());
        holder.action_status.setText("" + campList.getCampStatus());
        setAnimation(holder,position);
    }

    @Override
    public int getItemCount() {
        if (campLists!=null)
        return campLists.size();
        else
            return 0;
    }






    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView action_status;
        public TextView totalAdsView;
        protected TextView totalAdsRemainingView;
        protected TextView startTimeView;
        protected TextView call_webView;
        protected TextView campaignView;
        protected TextView action_DashBoard;
        public ViewHolder(View itemView) {
            super(itemView);
            action_status = (CustomTextView)itemView.findViewById(R.id.action_status);
            call_webView = (CustomTextView)itemView.findViewById(R.id.action_call_web);
            totalAdsRemainingView = (CustomTextView)itemView.findViewById(R.id.action_total_ads_remaining);
            totalAdsView = (TextView)itemView.findViewById(R.id.action_total_ads);
            startTimeView = (TextView)itemView.findViewById(R.id.action_start_time);
            campaignView = (TextView)itemView.findViewById(R.id.action_campaign_id);
            action_DashBoard = (TextView)itemView.findViewById(R.id.action_dashboard);
            action_DashBoard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getLayoutPosition(), view);
        }
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(ViewHolder viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > mLastPosition)
        {
            AnimationUtils.animate(viewToAnimate, true);
            mLastPosition = position;
        }
    }

    /**
     * y Custom Item Listener
     */

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
