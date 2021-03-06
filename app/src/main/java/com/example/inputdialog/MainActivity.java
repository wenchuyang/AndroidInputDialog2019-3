package com.example.inputdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.customize_button) Button customize_button;
    @BindView(R.id.default_button) Button default_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

    }

    @OnLongClick( {R.id.customize_button, R.id.default_button })
    public boolean show(View view){
        switch (view.getId()){
            case R.id.customize_button:
            case R.id.default_button:
                Toast.makeText(this, "按久了没有用哦", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void init(){
        customize_button.setOnClickListener(onClick);
        default_button.setOnClickListener(onClick);

    }

    public View.OnClickListener onClick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customize_button:
                    showCustomizeDialog(MainActivity.this, "Input Dialog", new Callback<String>() {
                        @Override
                        public void onCallback(String result) {
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case R.id.default_button:
                    showDefaultDialog(MainActivity.this, "Input Dialog", new Callback<String>() {
                        @Override
                        public void onCallback(String result) {
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
    public void showDefaultDialog(final Context context, final String title, final Callback<String> callback){
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setTitle(title);
        builder.setView(editText);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onCallback(editText.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    public void showCustomizeDialog(final Context context, final String title, final Callback<String> callback) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_input, null); //设置输入框样式
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog); //设置弹框样式
        builder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.input);

        editText.post(new Runnable() { // 自动弹出输入法
            @Override
            public void run() {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager)
                        context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        builder.setTitle(title); //弹框标题
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onCallback(editText.getText().toString());
            }
        }); //右边的按钮
        builder.setNegativeButton("Cancel", null); //左边的按钮
        builder.setCancelable(false);  //点击阴影处对话框不会消失

        builder.show();
    }

}
