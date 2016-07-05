package com.digzdigital.divinitytoday;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import io.realm.RealmResults;

/**
 * Created by Digz on 12/05 /2016. f
 */
public class devListAdapter extends RecyclerView.Adapter<devListAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    RealmResults<Devotional> devotionals;
    Context context;

    public devListAdapter(RealmResults<Devotional> devotionals, Context context) {
        this.devotionals = devotionals;
        this.context = context;
    }

    public Devotional getItem(int position) {
        return devotionals.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Devotional devotional = getItem(position);

        holder.viewTitle.setText(devotional.getTitle());
        holder.viewDate.setText(devotional.getDate());


        // Set a click listener for item remove button
        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
//                String itemLabel = devotionals.get(position);

                // Remove the item on remove/button click
//              Context context =
                String title = "Delete Devotional", accept = "Yes", reject = "Cancel";
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle(title);
                ad.setMessage("Are you sure you want to delete this devotional");


                ad.setPositiveButton(accept,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                devotionals.deleteFromRealm(position);
                            }
                        });
                ad.setNegativeButton(reject,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                final AlertDialog alert = ad.create();
                alert.show();


                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
                notifyItemRemoved(position);

                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
                notifyItemRangeChanged(position,devotionals.size());

                // Show the removed item label
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return devotionals.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView viewTitle, viewDate;
        ImageButton mRemoveButton;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            viewTitle = (TextView) itemView.findViewById(R.id.dev_title);
            viewDate = (TextView) itemView.findViewById(R.id.dev_date);
            mRemoveButton = (ImageButton) itemView.findViewById(R.id.dev_remove);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);

        }


    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}
