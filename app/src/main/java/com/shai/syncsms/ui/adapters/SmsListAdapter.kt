package com.shai.syncsms.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shai.syncsms.R
import com.shai.syncsms.data.entity.Sms


class SmsListAdapter : RecyclerView.Adapter<SmsListAdapter.ViewHolder>() {
    val smsList = ArrayList<Sms>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val countryView: View = inflater.inflate(R.layout.layout_sms_list_item, parent, false)
        return ViewHolder(countryView)
    }

    override fun onBindViewHolder(holder: SmsListAdapter.ViewHolder, position: Int) {
        val sms: Sms = smsList[position]
        holder.tvAmount.text = sms.amount
        holder.tvDate.text = sms.getFormattedDate()
        if (sms.synced) {
            holder.ivSynced.setImageResource(R.drawable.ic_baseline_check_circle_24)
            holder.ivSynced.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN)
        } else {
            holder.ivSynced.setImageResource(R.drawable.ic_baseline_highlight_off_24)
            holder.ivSynced.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
        }
    }

    override fun getItemCount(): Int {
        return smsList.size
    }

    fun addUpdateItems(updatedSms: List<Sms>) {
        for (sms in updatedSms) {
            var position = 0
            var itemFound = false
            for (previousSms in smsList) {
                if (previousSms.uid == sms.uid) {
                    itemFound = true
                    previousSms.synced = sms.synced
                    notifyItemChanged(position)
                    break
                }
                position++
            }
            if (!itemFound) {
                smsList.add(0, sms)
                notifyItemInserted(0)
            }
        }
    }

    inner class ViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var ivSynced: ImageView = itemView.findViewById(R.id.ivSynced)
    }
}
