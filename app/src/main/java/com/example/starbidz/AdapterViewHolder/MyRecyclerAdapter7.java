//package com.example.starbidz.AdapterViewHolder;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.iss.starbidz.Datastore.FeedItem;
//import com.iss.starbidz.R;
//import com.iss.starbidz.ViewHolder.FeedListRowHolder7;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class MyRecyclerAdapter7 extends RecyclerView.Adapter<FeedListRowHolder7> {
//
//
//    private List<FeedItem> feedItemList;
//
//    private Context mContext;
//
//    public MyRecyclerAdapter7(Context context, List<FeedItem> feedItemList) {
//        this.feedItemList = feedItemList;
//        this.mContext = context;
//
//    }
//    @Override
//    public FeedListRowHolder7 onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_auction, null);
//        FeedListRowHolder7 mh = new FeedListRowHolder7(v);
//        return mh;
//    }
//    @Override
//    public void onBindViewHolder(final FeedListRowHolder7 feedListRowHolder7, final int i) {
//        final FeedItem feedItem = feedItemList.get(i);
//
//        feedListRowHolder7.txt_category.setText(feedItem.getA_cat_name());
//        feedListRowHolder7.txt_title.setText(feedItem.getA_pro_title());
//        feedListRowHolder7.txt_baseprice.setText("₹ "+feedItem.getA_pro_price());
//        feedListRowHolder7.txt_aucprice.setText("₹ "+feedItem.getA_auc_price());
//        feedListRowHolder7.txt_bname.setText(feedItem.getB_name());
//
//
//        Log.d("fgh",feedItem.getA_pro_image());
//        Picasso.with(mContext)
//                .load(feedItem.getA_pro_image())
//                .into(feedListRowHolder7.img_product);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return (null != feedItemList ? feedItemList.size() : 0);
//    }
//
//
//}
