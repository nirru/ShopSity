package com.oxilo.shopsity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxilo.shopsity.POJO.InVoiceObject;
import com.oxilo.shopsity.R;

import java.util.List;

/**
 * Created by ericbasendra on 22/11/15.
 */
public class InVoiceAdapter extends RecyclerView.Adapter<InVoiceAdapter.ViewHolder>{

    private List<InVoiceObject> inVoiceObjectList;

    public InVoiceAdapter(List<InVoiceObject> inVoiceObjectList) {
        this.inVoiceObjectList = inVoiceObjectList;
    }

    public void addItem(InVoiceObject dataObj, int index) {
        inVoiceObjectList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        inVoiceObjectList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.recyle_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InVoiceObject inVoiceObject = inVoiceObjectList.get(position);
        String ss = "" + inVoiceObject.getCreateDate();
        String[] splited = ss.split("\\s+");
        holder.inVoiceView.setText("\n"+inVoiceObject.getInvoiceId());
        holder.inrView.setText("\n"+inVoiceObject.getOrderAmount());
        holder.createDateView.setText(splited[0]+"\n"+splited[1]);
        holder.planView.setText("\n" + "500 Ads View");
        holder.campaignView.setText("\n"+inVoiceObject.getOrderId());
    }

    @Override
    public int getItemCount() {
        if (inVoiceObjectList!=null)
        return inVoiceObjectList.size();
        else
            return 0;
    }




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView inVoiceView;
        protected TextView inrView;
        protected TextView createDateView;
        protected TextView planView;
        protected TextView campaignView;
        public ViewHolder(View itemView) {
            super(itemView);
            inVoiceView = (TextView)itemView.findViewById(R.id.action_invoice_id);
            inrView = (TextView)itemView.findViewById(R.id.action_inr_view);
            createDateView = (TextView)itemView.findViewById(R.id.action_date_view);
            planView = (TextView)itemView.findViewById(R.id.plan_action_view);
            campaignView = (TextView)itemView.findViewById(R.id.action_campaign_id);
        }
    }
}
