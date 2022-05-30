package com.example.cardgameproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Card> cards;
    LayoutInflater inflater;
    User user;
    ColorMatrix matrix = new ColorMatrix();
    ColorMatrixColorFilter colorFilter;


    public GridAdapter(Context context, ArrayList<Card> cards, User user) {
        this.context = context;
        this.cards = cards;
        this.user = user;
        matrix.setSaturation(0);
        colorFilter = new ColorMatrixColorFilter(matrix);
    }


    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String fragments = null;

        if(!(user.getObtainedFragments() == null)){
            fragments = user.getObtainedFragments().get(cards.get(i).getName());
        }
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = inflater.inflate(R.layout.grid_item, null);
        }
        ImageView imageView = view.findViewById(R.id.grid_image);
        TextView price = view.findViewById(R.id.tvPrice);
        TextView name = view.findViewById(R.id.tvName);



        Picasso.with(context).load(cards.get(i).getImageUrl()).into(imageView);


        if(!"complete".equals(fragments)){
            imageView.setColorFilter(colorFilter);
            imageView.setOnClickListener(clickListenerView -> {
                AlertDialog.Builder createAccountBuilder = new AlertDialog.Builder(clickListenerView.getContext());
                createAccountBuilder.setTitle("Do you want to buy this card??");
                createAccountBuilder.setMessage("Price " + cards.get(i).getPrice() + "\nYour money: " + user.getBerry());
                createAccountBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                createAccountBuilder.setNegativeButton("Cancell", null);

                createAccountBuilder.show();
            });
            if(fragments == null){
                name.setText("You don't have any fragment");
                price.setText("Price: " + cards.get(i).getPrice());
            }else{
                int finalPrice = Math.toIntExact((cards.get(i).getPrice() / 6) * Integer.parseInt(fragments));
                name.setText("Fragments: " + fragments);
                price.setText("Price: " + finalPrice);
            }
        }else{
            imageView.setColorFilter(null);
            name.setText("Complete");
            imageView.setOnLongClickListener(longClickListenerView -> {
                    ImageView im = longClickListenerView.findViewById(R.id.grid_image);
                    im.setBackgroundResource(R.drawable.highlight);
                    return true;
            });
        }


        return view;
    }




}
