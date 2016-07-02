package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.Activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.models.TimeFormatter;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

// Taking the tweet objects and turning them into Views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // Override and setup custom template
    // ViewHolder pattern


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        Tweet tweet = getItem(position);
        // 2. Find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // 3. Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        final TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        final TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);

        String replyImageUri = "http://imgur.com/WZuzCZM.jpg";
        ImageView ivReplyIcon = (ImageView) convertView.findViewById(R.id.ivReplyIcon);
        Picasso.with(getContext()).load(replyImageUri).resize(35,35).into(ivReplyIcon);

        String retweetImageUri = "http://imgur.com/A4YrT1r.jpg";
        ImageView ivRetweetIcon = (ImageView) convertView.findViewById(R.id.ivRetweetIcon);
        Picasso.with(getContext()).load(retweetImageUri).resize(35,35).into(ivRetweetIcon);

        String likeImageUri = "http://imgur.com/8DIZBjv.jpg";
        ImageView ivLikeIcon = (ImageView) convertView.findViewById(R.id.ivLikeIcon);
        Picasso.with(getContext()).load(likeImageUri).resize(35,35).into(ivLikeIcon);

//        String directMessageImageUri = "http://imgur.com/8DIZBjv.jpg";
//        ImageView ivDirectMessageIcon = (ImageView) convertView.findViewById(R.id.ivDirectMessageIcon);
//        Picasso.with(getContext()).load(directMessageImageUri).into(ivDirectMessageIcon);

        // 4. Populate data into the subviews
        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvCreatedAt.setText(TimeFormatter.getTimeDifference(tweet.getCreatedAt()));
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out the old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(2, 2)).into(ivProfileImage);


        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("screen_name", tvScreenName.getText().toString());
                getContext().startActivity(intent);
            }


//        ivProfileImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Do something here
//                // The position is the index of the item pressed
//                // If the third item in a list was pressed, position is `2`
//            }
//        });

        });

        // 5. Return the view to be inserted into the list
        return convertView;

    }

}


