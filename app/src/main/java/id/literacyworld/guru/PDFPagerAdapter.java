package id.literacyworld.guru;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.literacy.literacyworld.R;

import java.util.List;

public class PDFPagerAdapter extends RecyclerView.Adapter<PDFPagerAdapter.ViewHolder> {
    private Context context;
    private List<putPDF> pdfList;

    public PDFPagerAdapter(Context context, List<putPDF> pdfList) {
        this.context = context;
        this.pdfList = pdfList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_pdf_slide_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        putPDF pdf = pdfList.get(position);
        holder.textView.setText(pdf.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("application/pdf");
            intent.setData(Uri.parse(pdf.getUrl()));
            context.startActivity(intent);

            pdf.setRead(true);
            notifyDataSetChanged();
        });

        if (pdf.isRead()) {
            Drawable checkIcon = ContextCompat.getDrawable(context, R.drawable.checklist);
            checkIcon.setBounds(0, 0, 80, 80);
            holder.textView.setCompoundDrawables(checkIcon, null, null, null);
            holder.textView.setEnabled(false);
        } else {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.textView.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pdfTextView);
        }
    }
}