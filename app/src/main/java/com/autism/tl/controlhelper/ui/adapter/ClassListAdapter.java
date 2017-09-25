package com.autism.tl.controlhelper.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.User;
import com.autism.tl.controlhelper.model.ClassInfo;
import com.autism.tl.controlhelper.ui.activity.CheckStudentActivity;
import com.autism.tl.controlhelper.ui.activity.StudentListActivity;

import java.util.List;

/**
 * Created by 唐亮 on 2017/8/5.
 */
public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ViewHolder> {

    private List<ClassInfo> classList;
    private int type;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View classInfoView;
        TextView name;
        TextView teacher;

        public ViewHolder(View view) {
            super(view);
            classInfoView = view;
            name = (TextView) view.findViewById(R.id.class_name);
            teacher = (TextView) view.findViewById(R.id.class_teacher);
        }
    }

    public ClassListAdapter(List<ClassInfo> classList,int type) {
        this.classList = classList; this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.classInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                ClassInfo classInfo=classList.get(position);
                User.getInstance().setClassId((int) classInfo.getId());
                if(type==1||type==2){
                    Intent intent =new Intent(v.getContext(), StudentListActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("class_info",classInfo);
                    v.getContext().startActivity(intent);
                }else if(type==0){
                    Intent intent =new Intent(v.getContext(), CheckStudentActivity.class);
                    intent.putExtra("class_info",classInfo);
                    v.getContext().startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassInfo classInfo = classList.get(position);
        holder.name.setText(classInfo.getName());//////
        holder.teacher.setText(classInfo.getTeacher() + ':' + classInfo.getNumber());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
