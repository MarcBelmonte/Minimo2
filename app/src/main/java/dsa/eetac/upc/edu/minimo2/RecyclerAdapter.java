package dsa.eetac.upc.edu.minimo2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<Element> values;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cityName;
        public TextView cityId;
        public ImageView photo;

        public ViewHolder(View v) {
            super(v);
            cityName = (TextView) v.findViewById(R.id.cityName);
            cityId = (TextView) v.findViewById(R.id.cityId);
            photo = v.findViewById(R.id.photo);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(List<Element> data) {
       this.values = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.item_city, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Element element = ((Element) values.get(position));
        holder.cityName.setText(element.getMunicipiNom());
        holder.cityId.setText(element.getIne());
        Picasso.with(context).load(element.getMunicipiEscut()).into(holder.photo);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
