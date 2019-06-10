package com.foora.perevozkadev.ui.rate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.my_order_info.MyOrderInfoActivity;

public class RateActivity extends AppCompatActivity {

    public static final String TAG = RateActivity.class.getSimpleName();
    private static final String KEY_ORDER_ID = "key_order_id";

    int orderId;

    private Button btnRate;
    private View btnback;

    public static void start(Activity activity, int orderId) {
        Intent intent = new Intent(activity, RateActivity.class);
        intent.putExtra(KEY_ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        orderId = getIntent().getIntExtra(KEY_ORDER_ID, 0);

        btnRate = findViewById(R.id.btn_rate);
        btnback = findViewById(R.id.btn_back);

        btnRate.setOnClickListener(v -> MyOrderInfoActivity.start(this, orderId));
        btnback.setOnClickListener(v -> finish());
    }

}
