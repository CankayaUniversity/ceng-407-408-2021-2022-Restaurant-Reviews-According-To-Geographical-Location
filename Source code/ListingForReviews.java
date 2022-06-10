package com.Coskun.eatie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListingForReviews extends RecyclerView.Adapter<ListingForReviews.ListerforReviewsHolder> {
    private ArrayList<Reviews> ReviewList;
    private Context context;
    private String RestourantName;
    public OnClickFunc1Listener listener;

    public ListingForReviews(ArrayList<Reviews> Rest, String a, Context context) {
        this.ReviewList = Rest;
        this.context = context;
        this.RestourantName = a;
    }
    @NonNull
    @Override
    public ListerforReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.review_item,parent,false);
        return new ListerforReviewsHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ListerforReviewsHolder holder, int position) {
        Reviews reviews=ReviewList.get(position);
        holder.setData(reviews);
    }

    public int getItemCount() {
        return ReviewList.size();
    }


    class ListerforReviewsHolder extends RecyclerView.ViewHolder
    {

        TextView ReviewerName,ReviewRate,ReviewSum,diffrence;
        FloatingActionButton red,green;
        public ListerforReviewsHolder(View CurrentItem1)
        {
            super(CurrentItem1);
            ReviewerName=(TextView) CurrentItem1.findViewById(R.id.ReviewerName);
            ReviewRate=(TextView) CurrentItem1.findViewById(R.id.Reviewrate);
            ReviewSum=(TextView) CurrentItem1.findViewById(R.id.ResRev);
            red=(FloatingActionButton) CurrentItem1.findViewById(R.id.Redbtn);
            green=(FloatingActionButton) CurrentItem1.findViewById(R.id.greenBtn);
            diffrence=(TextView) CurrentItem1.findViewById(R.id.Diffrence);
           newSet(RestourantName,ReviewSum.getText().toString(),CurrentItem1);
            CurrentItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && (red.isPressed()||green.isPressed()))
                        listener.OnItemClick1();
                }
            });
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                      if(red.isClickable())
                      { int flag= Integer.parseInt(diffrence.getText().toString());
                          flag--;
                          diffrence.setText(String.valueOf(flag));
                          upd(RestourantName,ReviewSum.getText().toString(),Integer.parseInt(diffrence.getText().toString()));
                          newSet(RestourantName,ReviewSum.getText().toString(),CurrentItem1);
                          red.setClickable(false);
                          green.setClickable(true);
                      }
                    }
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        int flag= Integer.parseInt(diffrence.getText().toString());
                        flag++;
                        diffrence.setText(String.valueOf(flag));
                        upd(RestourantName,ReviewSum.getText().toString(),Integer.parseInt(diffrence.getText().toString()));
                        green.setClickable(false);
                        red.setClickable(true);
                    }
                }
            });
        }

        public void setData(Reviews reviews)
        {
            this.ReviewerName.setText(reviews.getReviewMaker());
            this.ReviewRate.setText(String.valueOf(reviews.getRate()));
            this.ReviewSum.setText(reviews.getReview().toString());
        }

        private void CNTRL()
        {
            int flag= Integer.parseInt(diffrence.getText().toString());
            if(flag<-10)
            {
               // Delete
            }
        }
    }
    public interface OnClickFunc1Listener
    {
        void OnItemClick1();
    }
    public void SetOnItemClickListener(OnClickFunc1Listener listener)
    {
        this.listener=listener;
    }
private void upd(String RestName,String Review,int newp)
{

    DocumentReference  documentReference;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    firestore.collection("Yorum").document(RestName).collection("Yorumlar").document(Review)
            .update("YorumP",newp);
}
private void newSet(String RestName, String Review, View CurrentItem)
{
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    CollectionReference collectionReference=firestore.collection("Yorum").document(RestName).collection("Yorumlar");
    Query query = collectionReference.whereEqualTo("RestRestourantName", RestName);
    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult())
                {
                    if(document.getData().get("yorum").toString().equals(Review))
                {
                    TextView diffrence=(TextView) CurrentItem.findViewById(R.id.Diffrence);
                    diffrence.setText(document.getData().get("YorumP").toString());}
                }
            }
        }
    });
}

}
