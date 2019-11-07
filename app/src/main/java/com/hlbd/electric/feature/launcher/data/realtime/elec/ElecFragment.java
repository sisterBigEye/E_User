package com.hlbd.electric.feature.launcher.data.realtime.elec;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIA;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIB;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIC;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataP;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataPF;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataQ;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVA;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVB;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVC;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ElecFragment extends BaseFragment implements ElecContract.View<Information>, View.OnClickListener {

  private static final String TAG = "ElecFragment";
  private View mParentView;
  private ElecContract.Presenter mPresenter;
  private EventHandler mHandler;
  private boolean needShowPicker;

  private TextView mIADescTv;
  private TextView mIAValueTv;

  private TextView mIBDescTv;
  private TextView mIBValueTv;

  private TextView mICDescTv;
  private TextView mICValueTv;

  private TextView mVADescTv;
  private TextView mVAValueTv;

  private TextView mVBDescTv;
  private TextView mVBValueTv;

  private TextView mVCDescTv;
  private TextView mVCValueTv;

  private TextView mPDescTv;
  private TextView mPValueTv;

  private TextView mPFDescTv;
  private TextView mPFValueTv;

  private TextView mQDescTv;
  private TextView mQValueTv;

  private TextView mEleItemTv;

  private Intent mIntent;
  public List<Information.Row> mCurrentRows;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mParentView == null) {
      mParentView = inflater.inflate(R.layout.fragment_info_ele, container, false);
    }
    initView();
    init();
    return mParentView;
  }

  private void init() {
    mHandler = new EventHandler(this);

    CommonRequest electricRequest = new CommonRequest();
    electricRequest.userTag = HttpApi.getUserName();
    electricRequest.url = "Baoding_Overview_DataSupport/Services/GetUsersElecDevice?";

    mPresenter = new ElecPresenter(this, electricRequest);

    mPresenter.start();

  }

  private void initView() {
    mIADescTv = mParentView.findViewById(R.id.tv_ia_desc_info_ele);
    mIAValueTv = mParentView.findViewById(R.id.tv_ia_value_info_ele);

    mIBDescTv = mParentView.findViewById(R.id.tv_ib_desc_info_ele);
    mIBValueTv = mParentView.findViewById(R.id.tv_ib_value_info_ele);

    mICDescTv = mParentView.findViewById(R.id.tv_ic_desc_info_ele);
    mICValueTv = mParentView.findViewById(R.id.tv_ic_value_info_ele);

    mVADescTv = mParentView.findViewById(R.id.tv_va_desc_info_ele);
    mVAValueTv = mParentView.findViewById(R.id.tv_va_value_info_ele);

    mVBDescTv = mParentView.findViewById(R.id.tv_vb_desc_info_ele);
    mVBValueTv = mParentView.findViewById(R.id.tv_vb_value_info_ele);

    mVCDescTv = mParentView.findViewById(R.id.tv_vc_desc_info_ele);
    mVCValueTv = mParentView.findViewById(R.id.tv_vc_value_info_ele);

    mPDescTv = mParentView.findViewById(R.id.tv_p_desc_info_ele);
    mPValueTv = mParentView.findViewById(R.id.tv_p_value_info_ele);

    mPFDescTv = mParentView.findViewById(R.id.tv_pf_desc_info_ele);
    mPFValueTv = mParentView.findViewById(R.id.tv_pf_value_info_ele);

    mQDescTv = mParentView.findViewById(R.id.tv_q_desc_info_ele);
    mQValueTv = mParentView.findViewById(R.id.tv_q_value_info_ele);

    mEleItemTv = mParentView.findViewById(R.id.tv_ele_item_info_ele);
    mEleItemTv.setOnClickListener(this);
  }

  @Override
  public void updateElectricData(Information information) {
    LogUtil.d(TAG, "updateElectricData() --- ElectricInfo=" + information);
    if (information == null || information.rows == null || information.rows.size() == 0) {
      needShowPicker = false;
      ToastUtil.toast("请求列表失败，请稍后尝试");
    }
    mCurrentRows = information.rows;
    if (needShowPicker) {
      showPicker();
      needShowPicker = false;
      return;
    }
    Information.Row row = information.rows.get(0);
    mEleItemTv.setText(row.description);
    requestElectricDetail(row.name);

  }

  @Override
  public void updateElectricIA(ElectDataIA data) {
    if (data == null) {
      return;
    }
    mIADescTv.setText(data.getDesc());
    mIAValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricIB(ElectDataIB data) {
    if (data == null) {
      return;
    }
    mIBDescTv.setText(data.getDesc());
    mIBValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricIC(ElectDataIC data) {
    if (data == null) {
      return;
    }
    mICDescTv.setText(data.getDesc());
    mICValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricVA(ElectDataVA data) {
    if (data == null) {
      return;
    }
    mVADescTv.setText(data.getDesc());
    mVAValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricVB(ElectDataVB data) {
    if (data == null) {
      return;
    }
    mVBDescTv.setText(data.getDesc());
    mVBValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricVC(ElectDataVC data) {
    if (data == null) {
      return;
    }
    mVCDescTv.setText(data.getDesc());
    mVCValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricP(ElectDataP data) {
    if (data == null) {
      return;
    }
    mPDescTv.setText(data.getDesc());
    mPValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricQ(ElectDataQ data) {
    if (data == null) {
      return;
    }
    mQDescTv.setText(data.getDesc());
    mQValueTv.setText(data.getValue());
  }

  @Override
  public void updateElectricPF(ElectDataPF data) {
    if (data == null) {
      return;
    }
    mPFDescTv.setText(data.getDesc());
    mPFValueTv.setText(data.getValue());
  }

  @Override
  public void setPersonal(ElecContract.Presenter p) {
    mPresenter = p;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_ele_item_info_ele:
        if (mCurrentRows == null) {
          needShowPicker = true;
          mPresenter.start();
          return;
        }
        showPicker();
        break;
    }
  }

  private static class EventHandler extends Handler {

    private final WeakReference<ElecFragment> mRef;
    private static final int REQUEST = 1;

    private EventHandler(ElecFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      ElecFragment fragment = mRef.get();
      if (fragment == null) {
        return;
      }
      switch (msg.what) {
        case REQUEST:
          break;
        default:
          break;
      }
    }
  }

  public void startTask() {
    LogUtil.d(TAG, "startTask() isFirst");
  }

  private void requestElectricData() {
    mPresenter.loadElectricData();
  }

  private void requestElectricDetail(String url) {
    if (url == null || mPresenter == null) {
      return;
    }
    mPresenter.loadElectricDetail(url);
  }


  @Override
  public void stopTask() {
    super.stopTask();
  }

  private void showPicker() {
    /*Activity activity = getActivity();
    LogUtil.d(TAG, "showPicker() activity=" + activity);
    if (activity == null) {
      return;
    }
    if (mIntent == null) {
      mIntent = new Intent(activity, ElecListActivity.class);
      mIntent.putExtra(ElecListActivity.KEY_EXTRA_IN_TITLE, "选择电力监测设备");
    }
    startActivityForResult(mIntent, ElecListActivity.REQUEST_ELEC_CODE);*/

    if (mCurrentRows == null || mCurrentRows.size() == 0) {
      return;
    }
    OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(getActivity());
    final ArrayList<String> list = new ArrayList<>();
    // 设置数据
    for (Information.Row row : mCurrentRows) {
      if (row != null) {
        list.add(row.description);
      }
    }
    mOptionsPickerView.setPicker(list);
    // 设置选项单位
    //mOptionsPickerView.setLabels("性");
    mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(int option1, int option2, int option3) {
        String value = list.get(option1);
        ToastUtil.toast("选中了 " + value);
        for (Information.Row row : mCurrentRows) {
          if (row.description.equals(value)) {
            requestElectricDetail(row.name);
            mEleItemTv.setText(row.description);
            break;
          }
        }
      }
    });
    mOptionsPickerView.show();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    /*if (requestCode == ElecListActivity.REQUEST_ELEC_CODE && resultCode == ElecListActivity.RESULT_CODE) {
      String value = data.getStringExtra(ElecListActivity.KEY_EXTRA_OUT1);
      if (value == null || TextUtils.isEmpty(value)) {
        return;
      }
      mEleItemTv.setText(value);
      requestElectricDetail(value);
    }*/

  }
}
