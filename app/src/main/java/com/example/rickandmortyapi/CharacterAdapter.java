package com.example.rickandmortyapi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Character> characters;

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.nameTextView.setText(character.getName());


        if(character.getGender().equals("Female")) {
            holder.genderView.setImageResource(R.drawable.female_pink);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
            params.gravity = Gravity.END;
            holder.imageView.setLayoutParams(params);

            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) holder.nameTextView.getLayoutParams();
            params2.gravity = Gravity.START;
            holder.nameTextView.setLayoutParams(params2);


        } else if(character.getGender().equals("Male")) {
            holder.genderView.setImageResource(R.drawable.male_blue);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
            params.gravity = Gravity.START;
            holder.imageView.setLayoutParams(params);
            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) holder.nameTextView.getLayoutParams();
            params2.gravity = Gravity.END;
            holder.nameTextView.setLayoutParams(params2);


        } else if (character.getGender().equals("unknown")){
            holder.genderView.setImageResource(R.drawable.question_mark_orange);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
            params.gravity = Gravity.END;
            holder.imageView.setLayoutParams(params);
            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) holder.nameTextView.getLayoutParams();
            params2.gravity = Gravity.START;
            holder.nameTextView.setLayoutParams(params2);

        }


        Glide.with(holder.itemView.getContext())
                .load(character.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,genderView;
        private TextView nameTextView;
        private FrameLayout frameLayout;
        private LinearLayout charLinarlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            frameLayout = itemView.findViewById(R.id.characterFl);
            genderView = itemView.findViewById(R.id.imageGender);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tıklanan karakterin detay sayfasına geçmesi
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    Character character = characters.get(position);

                    Intent intent = new Intent(context, CharacterDetailActivity.class);
                    intent.putExtra("name", character.getName());
                    intent.putExtra("species", character.getSpecies());
                    intent.putExtra("gender", character.getGender());
                    intent.putExtra("status", character.getStatus());
                    intent.putExtra("image", character.getImage());
                    intent.putExtra("location",character.getLocation().getName());
                    intent.putExtra("episode",character.getEpisode().toString());
                    intent.putExtra("origin",character.getOrigin().getName());
                    intent.putExtra("created",character.getCreated());
                    // Daha fazla özellik de buraya eklenebilir
                    context.startActivity(intent);
                }
            });
        }

    }
}
