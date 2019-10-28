package com.hlbd.electric.feature.launcher.data.realtime.elec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.hlbd.electric.feature.launcher.data.realtime.elec.list.ElecListActivity;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.util.LogUtil;

import java.lang.ref.WeakReference;

public class ElecFragment extends BaseFragment implements ElecContract.View<Information> {

  private static final String TAG = "ElecFragment";
  private View mParentView;
  private ElecContract.Presenter mPresenter;
  private EventHandler mHandler;
  private boolean needRequest;

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

  private boolean needStart = false;
  private boolean isActivityCreate = false;
  private Intent mIntent;

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
    if (needRequest) {
      mPresenter.start();
      needRequest = false;
    }
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
  }

  @Override
  public void updateElectricData(Information information) {
    LogUtil.d(TAG, "updateElectricData() --- ElectricInfo=" + information);
    String name = null;
    if (information != null) {
      name = getName(information.rows);
      requestElectricDetail(name);
    }
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
    needStart = true;
    LogUtil.d(TAG, "startTask()");
    if (isActivityCreate) {
      showDialog();
    }
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
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    isActivityCreate = true;
    LogUtil.d(TAG, "onActivityCreated()");
    if (needStart) {
      showDialog();
    }
  }


  @Override
  public void stopTask() {
    super.stopTask();
    needStart = false;
  }

  private void showDialog() {
    Activity activity = getActivity();
    LogUtil.d(TAG, "showDialog() activity=" + activity);
    if (activity == null) {
      return;
    }
    if (mIntent == null) {
      mIntent = new Intent(activity, ElecListActivity.class);
      mIntent.putExtra(ElecListActivity.KEY_EXTRA_IN_TITLE, "选择电力监测设备");
    }
    startActivityForResult(mIntent, ElecListActivity.REQUEST_ELEC_CODE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ElecListActivity.REQUEST_ELEC_CODE && resultCode == ElecListActivity.RESULT_CODE) {
      String value = data.getStringExtra(ElecListActivity.KEY_EXTRA_OUT1);
      if (value == null || TextUtils.isEmpty(value)) {
        return;
      }
      requestElectricDetail(value);
    }

  }
}
