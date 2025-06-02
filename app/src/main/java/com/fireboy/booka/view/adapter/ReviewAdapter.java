package com.fireboy.booka.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fireboy.booka.R;
import com.fireboy.booka.controller.BusinessController;
import com.fireboy.booka.controller.UserController;
import com.fireboy.booka.model.Review;
import com.fireboy.booka.utils.FormatUtils;

import java.util.List;

/**
 * Adaptador para mostrar reseñas en un RecyclerView.
 * Si se encuentra en el perfil del usuario, muestra el nombre del negocio.
 * Si se encuentra en la vista pública, muestra el nombre del usuario.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<Review> dataset;
    private final Activity activity;
    private final boolean isProfileView;
    private Context context;

    /**
     * Constructor del adaptador.
     *
     * @param dataset        Lista de reseñas a mostrar.
     * @param activity       Actividad de origen (para contexto y controladores).
     * @param isProfileView  {@code true} si se muestra en el perfil del usuario (muestra nombre del negocio).
     */
    public ReviewAdapter(List<Review> dataset, Activity activity, boolean isProfileView) {
        this.dataset = dataset;
        this.activity = activity;
        this.isProfileView = isProfileView;
    }

    /**
     * Infla la vista de cada ítem de reseña.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asocia los datos de la reseña a la vista, incluyendo nombre dinámico del usuario o negocio.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = dataset.get(position);
        BusinessController businessController = new BusinessController();
        UserController userController = new UserController(activity);

        if (isProfileView) {
            // Mostrar nombre del negocio
            businessController.getBusinessById(review.getBusinessId(), business -> {
                if (business != null) {
                    holder.lblUser.setText(business.getName());
                }
            });
        } else {
            // Mostrar nombre del usuario
            userController.getUserById(review.getUserId(), user -> {
                if (user != null) {
                    holder.lblUser.setText(user.getUsername());
                }
            });
        }

        holder.lblDate.setText(FormatUtils.formatFirebaseTimestamp(review.getTimestamp()));
        holder.lblRating.setText(String.valueOf((int) review.getRating()));
        holder.lblMessage.setText(review.getComment());
    }

    /**
     * @return Cantidad total de reseñas.
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * ViewHolder que contiene los elementos visuales de una reseña.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblUser, lblDate, lblRating, lblMessage;

        /**
         * Constructor del ViewHolder.
         *
         * @param itemView Vista inflada del ítem de reseña.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblUser = itemView.findViewById(R.id.lblUser);
            lblDate = itemView.findViewById(R.id.lblDate);
            lblRating = itemView.findViewById(R.id.lblRating);
            lblMessage = itemView.findViewById(R.id.lblMessage);
        }
    }
}
