package com.oxilo.shopsity.expandcollapse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.oxilo.shopsity.POJO.CampList;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.ui.CustomTextView;
import com.oxilo.shopsity.utility.ActivityUtils;

import java.util.List;

/**
 * An example custom implementation of the ExpandableRecyclerAdapter.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class VerticalExpandableAdapter extends ExpandableRecyclerAdapter<VerticalParentViewHolder, VerticalExpandableAdapter.VerticalChildViewHolder> {

    private LayoutInflater mInflater;
    private static DashBoard myClickListener;
    private Context mContext;
    /**
     * Public primary constructor.
     *
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public VerticalExpandableAdapter(Context context, List<? extends ParentListItem> parentItemList,final DashBoard dashBoard) {
        super(parentItemList);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.myClickListener = dashBoard;
    }

//    public void setOnItemClickListener(DashBoard myClickListener) {
//        this.myClickListener = myClickListener;
//    }

    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public VerticalParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item_parent_vertical, parent, false);
        return new VerticalParentViewHolder(view);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public VerticalChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.cardview, parent, false);
        return new VerticalChildViewHolder(view);
    }

    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(VerticalParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        VerticalParent verticalParent = (VerticalParent) parentListItem;
        parentViewHolder.bind(verticalParent.getParentNumber(), verticalParent.getParentText());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(VerticalChildViewHolder childViewHolder, int position, Object childListItem) {
        VerticalChild verticalChild = (VerticalChild) childListItem;
        childViewHolder.bind(verticalChild.getCampList());
    }


    /**
     * Custom child ViewHolder. Any views should be found and set to public variables here to be
     * referenced in your custom ExpandableAdapter later.
     *
     * Must extend ChildViewHolder.
     *
     */
    public class VerticalChildViewHolder extends ChildViewHolder implements View.OnClickListener{

        public TextView action_status;
        public TextView totalAdsView;
        protected TextView totalAdsRemainingView;
        protected TextView startTimeView;
        protected TextView call_webView;
        protected TextView campaignView;
        protected TextView action_DashBoard;
        /**
         * Public constructor for the custom child ViewHolder
         *
         * @param itemView the child ViewHolder's view
         */
        public VerticalChildViewHolder(View itemView) {
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

        public void bind(CampList campList) {
            call_webView.setText("" + campList.getCall() + campList.getWeb());
            totalAdsRemainingView.setText("" + campList.getShownImp());
            totalAdsView.setText("" + campList.getTotalImp());
            startTimeView.setText("" + ActivityUtils.GetDateTime(Long.valueOf(campList.getStartDate())));
            campaignView.setText("" + campList.getCampId());
            action_status.setText("" + getStatus(campList.getCampStatus()));
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getLayoutPosition(), view);
        }

    }

    public interface DashBoard {
        public void onItemClick(int position, View v);
    }

    public String getStatus(String status){
        if (status.toString().trim().equals(mContext.getResources().getString(R.string.status_paused))){
            return "paused";
        }
        else if (status.toString().trim().equals(mContext.getResources().getString(R.string.status_awaiting))){
            return "awaiting";
        }
        else if(status.toString().trim().equals(mContext.getResources().getString(R.string.status_completed))){
            return "completed";
        }
        else if(status.toString().trim().equals(mContext.getResources().getString(R.string.status_running))){
            return "running";
        }
        else if(status.toString().trim().equals(mContext.getResources().getString(R.string.status_unpaid))){
            return "unpaid";
        }
        else if(status.toString().trim().equals(mContext.getResources().getString(R.string.status_compliance))){
            return "compliance";
        }
        else{
            return "compliance";
        }
    }
}