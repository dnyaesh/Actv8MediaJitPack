package com.actv8.k2annex.actv8mediaplayer.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actv8.k2annex.actv8mediaplayer.Model.ContentObject;
import com.actv8.k2annex.actv8mediaplayer.R;

public class MyDlgManager
{

    OfferDlgButtonClickListener offerDlgButtonClickListener;
    //
    // Common method to display Avtcv8 input dialog.
    //
    public void showOfferDlg(final Context context, ContentObject detectedContent, String btnText)
    {
        Typeface tfRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface psBold = Typeface.createFromAsset(context.getAssets(), "fonts/Product Sans Bold.ttf");

        // custom dialog
        final Dialog dialog = new Dialog(context, R.style.OfferDlgTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.offer_dlg_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(tfRegular);
        tvTitle.setText(detectedContent.getDisplay_name());

        Button btnBuyNow = (Button) dialog.findViewById(R.id.btnBuyNow);
        btnBuyNow.setTypeface(psBold);
        btnBuyNow.setText(btnText);

        TextView btnSaveToGpay = (TextView) dialog.findViewById(R.id.btnSaveToGpay);

        if (!detectedContent.isAndroid_pass_enabled())
        {
            btnSaveToGpay.setAlpha(0.5f);
        }

        ImageView ivClose = (ImageView)  dialog.findViewById(R.id.ivClose);

        btnBuyNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (offerDlgButtonClickListener != null)
                {
                    offerDlgButtonClickListener.onOfferDlgButtonClicked(1);
                }
                dialog.dismiss();
            }
        });

        btnSaveToGpay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (offerDlgButtonClickListener != null)
                {
                    offerDlgButtonClickListener.onOfferDlgButtonClicked(2);
                }
                dialog.dismiss();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        //dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public interface OfferDlgButtonClickListener
    {
        public void onOfferDlgButtonClicked(int buttonNumber); // 1 for "Buy Now" and 2 for "G-Pay"
    }

    public void setOfferDlgButtonClickListener(OfferDlgButtonClickListener offerDlgButtonClickListener)
    {
        this.offerDlgButtonClickListener = offerDlgButtonClickListener;
    }
}
