package br.com.gamesseller.yugiooh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import static br.com.gamesseller.yugiooh.R.layout.list_view_item_carta;

public class CartaAdapter extends BaseDynamicGridAdapter {

    List<Carta> items;

    public CartaAdapter(Context context, List<Carta> items, int columnCount) {
        super(context, items, columnCount);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Carta carta = (Carta) getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(list_view_item_carta, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.build(carta.getImagem());
        return convertView;
    }

    private class ViewHolder {
        private ImageView imgView;

        private ViewHolder(View view) {
            imgView = (ImageView) view.findViewById(R.id.imagem_item_carta);
        }

        void build(int img) {
            Picasso.with(getContext())
                    .load(img)
                    .into(imgView);
        }
    }

    public List<Carta> getList(){
        return items;
    }
}
