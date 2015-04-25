package com.fenchtose.daggertest.app.user;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenchtose.daggertest.app.R;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Jay Rambhia on 16/03/15.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    private UserList userList;
    private int layout;
    private LayoutInflater inflater;
    private Drawable emptyDrawable;

    public UserAdapter(Context context, int layout, UserList userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Picasso.with(context).setIndicatorsEnabled(true);
        emptyDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, int i) {
        User user = userList.getItems().get(i);
        viewHolder.usernameTextView.setText(user.getLogin());
        viewHolder.scoreTextView.setText(String.valueOf(user.getScore()));
        Picasso.with(context)
               .load(user.getAvatar_url())
               .error(R.mipmap.ic_launcher)
               .into(viewHolder.displayView);
//        viewHolder.displayView.setImageDrawable(emptyDrawable);
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.getItems().size();
    }

    public void setUserList(UserList list) {
        userList = list;
    }

    public void clearUserList() {
        if (userList != null) {
            userList.getItems().clear();
        }
    }

    public User removeItem(int pos) {
        if (pos < 0 || pos > getItemCount()) {
            return null;
        }

        return userList.getItems().remove(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView usernameTextView;
        private TextView scoreTextView;
        private ImageView displayView;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = (TextView)itemView.findViewById(R.id.username_textview);
            scoreTextView = (TextView)itemView.findViewById(R.id.score_textview);
            displayView = (ImageView)itemView.findViewById(R.id.display_pic);
        }
    }
}
