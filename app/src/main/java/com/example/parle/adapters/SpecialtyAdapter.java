package com.example.parle.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parle.R;

import java.util.Random;

public
class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder>{
    Context mContext;
    String[] mList;
    public String[] chosenOnes = {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};//initlia value of concentrate
    TextView number;

    int[] colors = {R.color.dark_wine,R.color.darker_orange,R.color.dark_blue_back};
    public SpecialtyAdapter(Context context,String[] list,TextView textView) {
        mContext = context;
        mList = list;
        this.number = textView;//textview that shows the number selected

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.concentrate_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.button.setText(mList[position]);

        final Button button = holder.button;

        number.setText(getNoOnes()+" "+ mContext.getString(R.string.selected));

        if(chosenOnes[position].equals("1"))//if the position is 1 then change how it loks accordingly
        {
            setButtonSelected(button,true);
        }
        else
        {
           setButtonSelected(button,false);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chosenOnes[position].equals("1"))
                {
                    setButtonSelected(button,false);
                    chosenOnes[position] = "0";

                }
                else
                {
                    setButtonSelected(button,true);
                    chosenOnes[position] = "1";
                }

               number.setText(getNoOnes()+" "+ mContext.getString(R.string.selected));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public String getChosenOnes()
    {
        String ans = "";
        for(String i : chosenOnes)
            ans = ans+i;
        return ans;
    }

    public int getNoOnes()
    {
        int no=0;
        for(String i:chosenOnes)
        {
            if(i.equals("1"))
                no+=1;
        }
        return no;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.specialty);


        }



    }

    public void setButtonSelected(Button button, boolean selected)
    {
        //changes the color of the button to show whether its selected or not
        final Typeface bold = ResourcesCompat.getFont(mContext,R.font.montserrat_bold);
        final Typeface normal = ResourcesCompat.getFont(mContext,R.font.montserrat);
        int n = new Random().nextInt(3);

        if(selected)
        {
            //add a random background tint
            //change font color
            //make the fint bold
            button.setBackgroundTintList(ColorStateList.valueOf(mContext.getColor(colors[n])));
            button.setTextColor(mContext.getColor(android.R.color.white));
            button.setTypeface(bold);
        }
        else
        {
            //remove the background
            //change fint color to black
            //make the font not bold
            button.setBackgroundTintList(null);
            button.setTextColor(mContext.getColor(android.R.color.black));
            button.setTypeface(normal);
        }
    }

}