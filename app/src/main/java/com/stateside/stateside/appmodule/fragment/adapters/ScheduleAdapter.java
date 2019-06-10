package com.stateside.stateside.appmodule.fragment.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stateside.stateside.R;
import com.stateside.stateside.appmodule.fragment.HomeFragment;
import com.stateside.stateside.appmodule.fragment.ScheduleFragment;
import com.stateside.stateside.information.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    Event[] events;
    Context context;
    HomeFragment scheduleFragment;

    public ScheduleAdapter(Event[] events, HomeFragment scheduleFragment) {
        this.events = events;
        this.scheduleFragment = scheduleFragment;
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_schedule;
    }

    @Override
    public void onBindViewHolder(final ScheduleAdapter.ViewHolder holder, int position) {
        final Event event = events[position];
        holder.getTextViewStartTime().setText(event.getStartTime());
        holder.getTextViewEndTime().setText(event.getEndTime());
        holder.getTextViewTitle().setText(event.getTitle());
        holder.getTextViewVenue().setText(event.getVenue());
        Calendar startTime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mma");
        Calendar endTime = Calendar.getInstance();
        try {
            startTime.setTime(dateFormat.parse("1/18/2018 " + event.getStartTime()));
            endTime.setTime(dateFormat.parse("1/18/2018 " + event.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar currentTime = new GregorianCalendar();
        if (startTime.before(currentTime) && endTime.after(currentTime)) {
            holder.getTextViewStartTime().setTextColor(Color.WHITE);
            holder.getTextViewEndTime().setTextColor(Color.WHITE);
            holder.getTextViewTitle().setTextColor(Color.WHITE);
            holder.getTextViewVenue().setTextColor(Color.WHITE);
            holder.background.setVisibility(View.GONE);
            holder.getRelativeLayout().setBackgroundResource(R.color.colorPrimary);
        } else if (endTime.before(currentTime)) {
            holder.getTextViewStartTime().setTextColor(Color.GRAY);
            holder.getTextViewEndTime().setTextColor(Color.GRAY);
            holder.getTextViewTitle().setTextColor(Color.GRAY);
            holder.getTextViewVenue().setTextColor(Color.GRAY);
            holder.background.setVisibility(View.VISIBLE);
            holder.getRelativeLayout().setBackgroundResource(R.drawable.back_line);
        } else {
            holder.getTextViewStartTime().setTextColor(Color.BLACK);
            holder.getTextViewEndTime().setTextColor(Color.BLACK);
            holder.getTextViewTitle().setTextColor(Color.BLACK);
            holder.getTextViewVenue().setTextColor(Color.BLACK);
            holder.background.setVisibility(View.GONE);
            holder.getRelativeLayout().setBackgroundResource(R.drawable.back_line);
        }

        if (event.getDescription() == null || "".equals(event.getDescription().trim())){
            holder.getImageViewArrow().setVisibility(View.GONE);
            holder.getRelativeLayout().setOnClickListener(null);
        } else {
            holder.getImageViewArrow().setVisibility(View.VISIBLE);
            holder.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scheduleFragment.display(event.getTitle(), event.getDescription());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return events.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStartTime;
        TextView textViewEndTime;
        TextView textViewTitle;
        TextView textViewVenue;
        ImageView imageViewArrow;
        View background;
        RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewStartTime = (TextView)itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = (TextView)itemView.findViewById(R.id.textViewEndTime);
            textViewTitle = (TextView)itemView.findViewById(R.id.textViewTitle);
            textViewVenue = (TextView)itemView.findViewById(R.id.textViewVenue);
            imageViewArrow = (ImageView)itemView.findViewById(R.id.imageViewArrow);
            background = itemView.findViewById(R.id.background);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }

        public TextView getTextViewStartTime() {
            return textViewStartTime;
        }

        public TextView getTextViewEndTime() {
            return textViewEndTime;
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle) {
            this.textViewTitle = textViewTitle;
        }

        public TextView getTextViewVenue() {
            return textViewVenue;
        }

        public void setTextViewVenue(TextView textViewVenue) {
            this.textViewVenue = textViewVenue;
        }

        public ImageView getImageViewArrow() {
            return imageViewArrow;
        }

        public void setImageViewArrow(ImageView imageViewArrow) {
            this.imageViewArrow = imageViewArrow;
        }

        public View getBackground() {
            return background;
        }

        public void setBackground(View background) {
            this.background = background;
        }

        public RelativeLayout getRelativeLayout() {
            return relativeLayout;
        }

        public void setRelativeLayout(RelativeLayout relativeLayout) {
            this.relativeLayout = relativeLayout;
        }
    }
}