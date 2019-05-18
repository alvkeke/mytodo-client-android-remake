package com.alvkeke.tools.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.alvkeke.tools.todo.components.ColorSelector;


public class ProjectSettingActivity extends AppCompatActivity {

    private EditText etProjectName;
    private ColorSelector colorSelector;
    private Button btnOk;
    private Button btnCancel;
    private Intent intent;

    private long proId;
    private String proName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_setting);

        etProjectName = findViewById(R.id.pro_setting_et_proName);
        colorSelector = findViewById(R.id.pro_setting_color_selector);
        btnOk = findViewById(R.id.pro_setting_btn_ok);
        btnCancel = findViewById(R.id.pro_setting_btn_cancel);

        intent = getIntent();

        proName = intent.getStringExtra("proName");
        proId = intent.getLongExtra("proId", -1);
        int proColor = intent.getIntExtra("proColor", Color.TRANSPARENT);

        etProjectName.setText(proName);




        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etProjectName.getText().toString().isEmpty()) {
                    intent.putExtra("proId", proId);
                    intent.putExtra("proName", etProjectName.getText().toString());
                    intent.putExtra("proColor", colorSelector.getColor());

                    setResult(0, intent);

                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "项目名称不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.RESULT_CANCEL);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project_setting_menu, menu);
        if(proName == null){
            menu.removeItem(menu.getItem(2).getItemId());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_ok:
                btnOk.callOnClick();
                break;
            case R.id.menu_item_cancel:
                btnCancel.callOnClick();
                break;
            case R.id.menu_item_remove:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("您确定要删除该项目吗？\n\n此操作不可以回退，项目内的任务将同时被删除。")
                        .setNegativeButton(R.string.title_btn_cancel, null)
                        .setPositiveButton(R.string.title_btn_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(Constants.RESULT_DELETE_PROJECT, intent);
                                intent.putExtra("proId", proId);
                                finish();
                            }
                        });
                builder.create().show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
