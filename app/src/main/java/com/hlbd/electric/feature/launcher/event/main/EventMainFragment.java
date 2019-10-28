package com.hlbd.electric.feature.launcher.event.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.event.EventFragment;
import com.hlbd.electric.feature.launcher.event.main.data.EventMainCommitResponse;
import com.hlbd.electric.feature.launcher.event.main.data.EventMainData;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.util.Map;

public class EventMainFragment extends BaseFragment implements EventMainContract.View<EventMainData>, View.OnClickListener {

  private static final String TAG = "EventMainFragment";
  private static final int PAGE_EVENT_LIST = 0;
  private static final int PAGE_COMMIT = 1;
  private ListView mWarningLv;
  private EventMainAdapter mAdapter;
  private EventMainRequest request;
  private EventMainContract.Presenter presenter;
  private View mEventListPage;
  private View mCommitPage;
  private EventMainData.Row mCurrentClickRow;
  private Button mCommitBtn;
  private EditText mCommitEt;
  // 报单按钮
  private Button mDeclBtn;
  private EventMainCommitRequest mCommitRequest;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_event_main, container, false);
    }
    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  private void init() {
    request = new EventMainRequest();
    request.userName = HttpApi.getUserName();
    Map<String, String> map = request.getParamMap();
    map.put("userName", request.userName);
    request.url = "Baoding_Overview_DataSupport/Services/HighClassQueryAlertHistory?";
    presenter = new EventMainPresenter(this, request);
  }

  private void initView() {
    mDeclBtn = mBaseView.findViewById(R.id.btn_declaration_event_main);
    mDeclBtn.setOnClickListener(this);
    mBaseView.findViewById(R.id.btn_order_event_main).setOnClickListener(this);
    mBaseView.findViewById(R.id.btn_history_event_main).setOnClickListener(this);

    mEventListPage = mBaseView.findViewById(R.id.ll_event_list_page_event);
    mCommitPage = mBaseView.findViewById(R.id.ll_commit_page_event);
    mCommitBtn = mBaseView.findViewById(R.id.btn_commit_event);
    mCommitBtn.setOnClickListener(this);
    mCommitEt = mBaseView.findViewById(R.id.et_commit_event);
    mTitleTv.setText("当前告警");
    mBackIv.setOnClickListener(this);
    mWarningLv = mBaseView.findViewById(R.id.lv_warning_event);
    mAdapter = new EventMainAdapter();
    mWarningLv.setAdapter(mAdapter);
    mWarningLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventMainData.Row temp = mAdapter.mInfoList.get(position);
        if (mCurrentClickRow != null) {
          if (mCurrentClickRow == temp) {
            return;
          } else {
            mCurrentClickRow.isSelect = false;
          }
        }
        temp.isSelect = true;
        mCurrentClickRow = temp;
        mAdapter.notifyDataSetChanged();
      }
    });
  }

  private void showDetail(EventMainData.Row data) {
    if (data == null) {
      return;
    }
    if (mCommitRequest == null) {
      mCommitRequest = new EventMainCommitRequest();
      mCommitRequest.url = "Baoding_ElecKeeper_RepairOrder/Services/AddRepairOrderByUser?";
    }
    mCommitRequest.alertName = data.name;
    mCommitRequest.currentUser = HttpApi.getUserName();
    mCommitRequest.alertSourceType = data.source;
    mCommitRequest.alertDescription = data.description;

    String source = data.source;
    String name = data.name;
    String description = data.description;
    String content = source + "设备出现了" + name;
    mCommitEt.setText(content);
  }

  @Override
  public void updateEventData(EventMainData data) {
    LogUtil.d(TAG, "updateEventData() EventMainData=" + data);
    mCurrentClickRow = null;
    if (data == null) {
      mAdapter.clear();
      return;
    }
    mAdapter.addDataList(data.rows);
  }

  @Override
  public void commitResult(EventMainCommitResponse response) {
    if (response == null) {
      ToastUtil.toast("提交失败，请稍后再试");
      return;
    }
    ToastUtil.toast("提交成功");
    pageTurning(PAGE_EVENT_LIST);
  }

  @Override
  public void setPersonal(EventMainContract.Presenter p) {

  }

  @Override
  public void startTask() {
    super.startTask(60 * 1000);
  }

  @Override
  public void updateTask() {
    super.updateTask();
    if (presenter != null) {
      presenter.start();
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_commit_event:
        ToastUtil.toast("正在提交");
        mCommitRequest.descriptionStr = mCommitEt.getText().toString().trim();
        presenter.commitEvent(mCommitRequest);
        break;
      case R.id.btn_declaration_event_main:
        pageTurning(PAGE_COMMIT);
        break;
      case R.id.btn_order_event_main:
        if (mPage != null) {
          mPage.turnPage(EventFragment.PAGE_EVENT_ORDER);
        }
        break;
      case R.id.btn_history_event_main:
        if (mPage != null) {
          mPage.turnPage(EventFragment.PAGE_EVENT_HISTORY);
        }
        break;
      case R.id.iv_back:
        pageTurning(PAGE_EVENT_LIST);
        break;
      default:
        break;
    }
  }

  private void pageTurning(int page) {
    switch (page) {
      case PAGE_EVENT_LIST:
        mTitleTv.setText("当前告警");
        mEventListPage.setVisibility(View.VISIBLE);
        mCommitPage.setVisibility(View.GONE);
        mBackIv.setVisibility(View.INVISIBLE);
        break;
      case PAGE_COMMIT:
        if (mCurrentClickRow == null) {
          ToastUtil.toast("请先选择列表中的一项数据");
          return;
        }
        mTitleTv.setText("维护报单");
        mEventListPage.setVisibility(View.GONE);
        mCommitPage.setVisibility(View.VISIBLE);
        mBackIv.setVisibility(View.VISIBLE);
        showDetail(mCurrentClickRow);
        break;
      default:
        break;
    }
  }
}
