package it2015.sabanciuniv.edu.erdincozdemir.fragments;

import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.objects.Comment;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.PostCommentTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class PostCommentFragment extends DialogFragment implements View.OnClickListener, PostCommentTask.PostCommentTaskListener {

    private EditText txtCommentName, txtCommentLastName, txtCommentText;
    private Button btnPostComment, btnCancel;
    private News news;
    private PostCommentTask.PostCommentTaskListener listener;

    public static PostCommentFragment newInstance(News news) {

        Bundle args = new Bundle();
        args.putSerializable("news", news);

        PostCommentFragment fragment = new PostCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_comment, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.news = (News) bundle.getSerializable("news");
        }

        txtCommentName = (EditText) view.findViewById(R.id.txtCommentName);
        txtCommentLastName = (EditText) view.findViewById(R.id.txtCommentLastName);
        txtCommentText = (EditText) view.findViewById(R.id.txtCommentText);

        btnPostComment = (Button) view.findViewById(R.id.btnPostComment);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnPostComment.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnPostComment) {
            if(!validate())
                return;
            Comment comment = new Comment(this.news.getId(), txtCommentName.getText().toString(), txtCommentLastName.getText().toString(), txtCommentText.getText().toString());
            PostCommentTask postCommentTask = new PostCommentTask(getActivity(), comment);
            postCommentTask.setListener(this);
            postCommentTask.execute(Config.postCommentUrl);
        } else if(v.getId() == R.id.btnCancel) {
            dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        window.setLayout((int) (width * 0.9f), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    public void setListener(PostCommentTask.PostCommentTaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void commentPostedSuccessfully() {
        this.listener.commentPostedSuccessfully();
        dismiss();
    }

    @Override
    public void commentPostFailed() {
        this.listener.commentPostFailed();
        dismiss();
    }

    private boolean validate() {
        if(txtCommentName.getText().toString().equals("")) {
            txtCommentName.setError(getString(R.string.required_value_error));
            return false;
        }
        if(txtCommentLastName.getText().toString().equals("")) {
            txtCommentLastName.setError(getString(R.string.required_value_error));
            return false;
        }
        if(txtCommentText.getText().toString().equals("")) {
            txtCommentText.setError(getString(R.string.required_value_error));
            return false;
        }
        return true;
    }
}
