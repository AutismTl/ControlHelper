package com.autism.tl.controlhelper.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.MyApplication;
import com.autism.tl.controlhelper.model.PostInfo;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.autism.tl.controlhelper.utils.RxBus;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 唐亮 on 2017/8/14.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<StudentInfo> studentList;
    private List<String> imageList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View studentInfoView;
        TextView name;
        TextView XH;
        CircleImageView image;

        public ViewHolder(View view) {
            super(view);
            studentInfoView = view;
            name = (TextView) view.findViewById(R.id.student_name);
            XH = (TextView) view.findViewById(R.id.student_id);
            image=(CircleImageView)view.findViewById(R.id.student_image);
        }
    }

    public StudentListAdapter(List<StudentInfo> studentList, List<String> imageList) {
        this.studentList = studentList;
        this.imageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_studentlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.studentInfoView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                StudentInfo studentInfo = studentList.get(position);
                if(-1 ==((ColorDrawable)v.getBackground()).getColor()){
                    v.setBackgroundResource(R.color.red);
                    RxBus.getInstance().post(new PostInfo(String.valueOf(studentInfo.getId()),true));
                }else{
                    v.setBackgroundResource(R.color.white);
                    RxBus.getInstance().post(new PostInfo(String.valueOf(studentInfo.getId()),false));
                }

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentInfo studentInfo = studentList.get(position);
        Glide
                .with(MyApplication.getContext())
                .load("http://"+imageList.get(position))
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.image);
        holder.name.setText(studentInfo.getName());
        holder.XH.setText(String.valueOf(studentInfo.getId()));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
