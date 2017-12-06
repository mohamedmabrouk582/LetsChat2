package com.example.mohamed.letschat.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 04/12/2017.  time :19:44
 */

public class ToolBar {
    private String title;
    private String img;
    private String lastSeen;
    private boolean back;
    private Context context;

    public String getLastSeen() {
        return lastSeen;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public boolean isBack() {
        return back;
    }

    public Context getContext() {
        return context;
    }

    private ToolBar(ToolBarBuilder builder) {
        this.title = builder.title;
        this.back = builder.back;
        this.img=builder.img;
        this.lastSeen=builder.lastSeen;
    }

    /**
     *
     * Tool bar builder
     *
     */

    public  static class ToolBarBuilder{
      private String title;
      private String img;
      private boolean back;
      private Activity context;
      private CircleImageView circleImageView;
      private TextView titleView,lastseenTextView;
      private ImageView backView;
        private String lastSeen;


        public ToolBarBuilder(String title,Activity context) {
          this.title = title;
          this.context=context;
          init();
      }

      public ToolBarBuilder setImg(String img) {
          this.img = img;
          return this;
      }

      public ToolBarBuilder setLastSeen(String lastSeen){
            this.lastSeen=lastSeen;
            return this;
      }

      public ToolBarBuilder setBack(boolean back) {
          this.back = back;
          return this;
      }

      public ToolBarBuilder setIMGAction(View.OnClickListener listener){
           circleImageView.setOnClickListener(listener);
          return this;
      }

      public ToolBarBuilder setBackAction(View.OnClickListener listener){
            backView.setOnClickListener(listener);
          return this;
      }

      public ToolBarBuilder setTitleAction(View.OnClickListener listener){
           titleView.setOnClickListener(listener);
          return this;
      }

       private void init(){
          circleImageView=context.findViewById(R.id.toolbar_img);
          lastseenTextView=context.findViewById(R.id.last_seen);
          titleView=context.findViewById(R.id.toolbar_user_name);
          backView=context.findViewById(R.id.toolbar_back);
          setBackAction(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  context.finish();
              }
          });


       }

       private void setToolBar(ToolBar toolBar){
           lastseenTextView.setText(toolBar.getLastSeen());
         titleView.setText(toolBar.getTitle());
           if (!TextUtils.isEmpty(toolBar.getImg())){
               Glide.with(context).load(toolBar.getImg()).error(R.drawable.logo).into(circleImageView);
           }
       }

      public ToolBar build(){
          ToolBar toolBar=new ToolBar(this);
          setToolBar(toolBar);
          return toolBar;
      }


  }
}
