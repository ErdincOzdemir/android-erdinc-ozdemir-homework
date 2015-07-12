package it2015.sabanciuniv.edu.erdincozdemir.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.objects.Comment;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {


    public CommentsAdapter(Context context, List<Comment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentHolder holder = null;

        View row = convertView;
        if (row == null){

            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();

            row = inflater.inflate(R.layout.comment_line,parent,false);

            holder = new CommentHolder(row);
            row.setTag(holder);

        }else{
            holder = (CommentHolder)row.getTag();
        }

        holder.getTxtComment().setText(getItem(position).getText());
        holder.getTxtNameLastName().setText(getItem(position).getName() + " " + getItem(position).getLastName());
        return row;
    }

    class CommentHolder {
        private View base;

        private TextView txtNameLastName, txtComment;

        public CommentHolder(View base) {
            this.base = base;
        }

        public TextView getTxtComment() {
            if(this.txtComment == null) {
                this.txtComment = (TextView) this.base.findViewById(R.id.txtComment);
            }
            return txtComment;
        }

        public TextView getTxtNameLastName() {
            if(this.txtNameLastName == null) {
                this.txtNameLastName = (TextView) this.base.findViewById(R.id.txtCommentNameLastName);
            }
            return txtNameLastName;
        }
    }
}
