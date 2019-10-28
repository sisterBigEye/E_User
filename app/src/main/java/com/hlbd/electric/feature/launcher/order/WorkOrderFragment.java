package com.hlbd.electric.feature.launcher.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.order.data.OrderData;
import com.hlbd.electric.feature.launcher.order.data.OrderDetailData;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;
import com.hlbd.electric.util.ToastUtil;

public class WorkOrderFragment extends BaseFragment implements OrderContract.View<OrderData>, View.OnClickListener {

  private static final String TAG = "WorkOrderFragment";
  private static final int PAGE_ORDER_LIST = 0;
  private static final int PAGE_DETAIL_INFO = 1;
  private ListView mWarningLv;
  private OrderAdapter mAdapter;
  private OrderRequest request;
  private OrderContract.Presenter presenter;
  private View mListPage;
  private View mDetailInfoPage;
  private OrderData.Row mCurrentClickRow;
  private Button mDetailBtn;

  private TextView mCreateName;
  private TextView mCreateTime;
  private TextView mDescTv;
  private TextView mOrderName;
  private TextView mOrderTime;
  private TextView mFinishTime;
  private TextView mResultTv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_work_order, container, false);
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
    request = new OrderRequest();
    request.user = HttpApi.getUserName();
    request.url = "Baoding_ElecKeeper_RepairOrder/Services/SelectDataByUser?";
    presenter = new OrderPresenter(this, request);
  }

  private void initView() {
    mCreateName = mBaseView.findViewById(R.id.tv_create_name_order);
    mCreateTime = mBaseView.findViewById(R.id.tv_create_time_order);
    mDescTv = mBaseView.findViewById(R.id.tv_desc_order);
    mOrderName = mBaseView.findViewById(R.id.tv_order_name_order);
    mOrderTime = mBaseView.findViewById(R.id.tv_order_time_order);
    mFinishTime = mBaseView.findViewById(R.id.tv_finish_time_order);
    mResultTv = mBaseView.findViewById(R.id.tv_result_order);

    mDetailBtn = mBaseView.findViewById(R.id.btn_detail_order);
    mDetailBtn.setOnClickListener(this);
    mListPage = mBaseView.findViewById(R.id.ll_first_page_order);
    mDetailInfoPage = mBaseView.findViewById(R.id.ll_second_page_order);
    mTitleTv.setText("工单状态");
    mBackIv.setOnClickListener(this);
    mWarningLv = mBaseView.findViewById(R.id.lv_warning_order);
    mAdapter = new OrderAdapter();
    mWarningLv.setAdapter(mAdapter);
    mWarningLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderData.Row temp = mAdapter.mInfoList.get(position);
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

  private void showDetail(String repairsn) {
    if (repairsn == null || TextUtils.isEmpty(repairsn)) {
      return;
    }
    OrderDetailRequest request = new OrderDetailRequest();
    request.url = "Baoding_ElecKeeper_RepairOrder/Services/SelectDataByRepairSN?";
    request.repairSN = repairsn;
    presenter.loadDetailInfo(request);
  }

  @Override
  public void updateOrderData(OrderData orderData) {
    mCurrentClickRow = null;
    if (orderData == null) {
      mAdapter.clear();
      return;
    }
    mAdapter.addDataList(orderData.rows);
  }

  @Override
  public void updateDetailInfo(OrderDetailData data) {
    if (data == null) {
      ToastUtil.toast("获取数据为空");
      LogUtil.w(TAG, "updateDetailInfo() data is null");
      return;
    }
    OrderDetailData.Row row = null;
    try {
      for (OrderDetailData.Row temp : data.rows) {
        if (temp != null) {
          row = temp;
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateDetailInfo() error", e);
    }
    if (row == null) {
      LogUtil.w(TAG, "updateDetailInfo() row is null");
      return;
    }
    LogUtil.d(TAG, "updateDetailInfo() row=" + row);
    mCreateName.setText(row.createperson);
    if (row.createtime > 0) {
      mCreateTime.setText(TimeUtil.getDateTime(row.createtime));
    }
    mDescTv.setText(row.exceptiondesc);
    mOrderName.setText(row.receiveperson);
    if (row.receivcetime > 0) {
      mOrderTime.setText(TimeUtil.getDateTime(row.receivcetime));
    }
    if (row.managefinishtime > 0) {
      mFinishTime.setText(TimeUtil.getDateTime(row.managefinishtime));
    }
    mResultTv.setText(row.handleresult);
  }

  @Override
  public void setPersonal(OrderContract.Presenter p) {
    presenter = p;
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
      case R.id.iv_back:
        pageTurning(PAGE_ORDER_LIST);
        break;

      case R.id.btn_detail_order:
        pageTurning(PAGE_DETAIL_INFO);
        break;
    }
  }

  private void pageTurning(int page) {
    switch (page) {
      case PAGE_ORDER_LIST:
        mTitleTv.setText("工单状态");
        mListPage.setVisibility(View.VISIBLE);
        mDetailInfoPage.setVisibility(View.GONE);
        mBackIv.setVisibility(View.INVISIBLE);
        break;
      case PAGE_DETAIL_INFO:
        if (mCurrentClickRow == null) {
          ToastUtil.toast("请先选择列表中的一项数据");
          return;
        }

        if (mCurrentClickRow.repairsn == null || TextUtils.isEmpty(mCurrentClickRow.repairsn)) {
          ToastUtil.toast("当前选中的列表存在无效数据");
          return;
        }
        mTitleTv.setText("工单信息");
        mListPage.setVisibility(View.GONE);
        mDetailInfoPage.setVisibility(View.VISIBLE);
        mBackIv.setVisibility(View.VISIBLE);
        showDetail(mCurrentClickRow.repairsn);
        break;
      default:
        break;
    }
  }
}
