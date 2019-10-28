package com.hlbd.electric.feature.launcher.data.realtime.elec.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.common.dialog.DialogListAdapter;
import com.hlbd.electric.common.dialog.IContent;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.model.Information;

public class ElecListActivity extends AppCompatActivity implements View.OnClickListener, ElecListContract.View<Information> {

  private DialogListAdapter mAdapter;
  private ElecListContract.Presenter mPresenter;
  public static final int REQUEST_ELEC_CODE = 2;
  public static final int REQUEST_REPOTT_CODE = 3;
  public static final int RESULT_CODE = 200;

  public static final String KEY_EXTRA_IN_TITLE = "title";
  public static final String KEY_EXTRA_OUT1 = "out1";
  public static final String KEY_EXTRA_OUT2 = "out2";

  private static final String TAG = "ElecListActivity";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_elec_list);
    initView();
    init();
  }

  private void init() {
    CommonRequest electricRequest = new CommonRequest();
    electricRequest.userTag = HttpApi.getUserName();
    electricRequest.url = "Baoding_Overview_DataSupport/Services/GetUsersElecDevice?";

    mPresenter = new ElecListPresenter(this, electricRequest);
    mPresenter.start();
  }

  private void initView() {
    Intent intent = getIntent();
    String title = intent.getStringExtra(KEY_EXTRA_IN_TITLE);
    TextView tv = findViewById(R.id.tv_title_elec_list);
    tv.setText(title);
    mAdapter = new DialogListAdapter();
    ListView lv = findViewById(R.id.lv_list_elec_list);
    lv.setAdapter(mAdapter);
    findViewById(R.id.iv_cencel_elec_list).setOnClickListener(this);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IContent content = mAdapter.mInfoList.get(position);
        if (content == null || content.getDescription() == null) {
          finish();
          return;
        }
        Intent i = new Intent();
        i.putExtra(KEY_EXTRA_OUT1, content.getValue());
        i.putExtra(KEY_EXTRA_OUT2, content.getDescription());
        setResult(RESULT_CODE, i);
        finish();
      }
    });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_cencel_elec_list:
        finish();
        break;

      default:
        break;
    }
  }

  @Override
  public void updateElectricData(Information information) {
    if (information == null) {
      return;
    }
    mAdapter.clear();
    for (Information.Row row : information.rows) {
      if (row == null || row.description == null) {
        return;
      }
      mAdapter.addData(new ElecContent(row.description, row.name));
    }
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void setPersonal(ElecListContract.Presenter p) {
    mPresenter = p;
  }
}
