package com.hlbd.electric.feature.launcher.mime.modify.info;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.mime.MimeFragment;
import com.hlbd.electric.feature.launcher.mime.MimeInfoMgr;
import com.hlbd.electric.feature.launcher.mime.modify.info.data.ModifyAvatarResult;
import com.hlbd.electric.feature.launcher.mime.modify.info.data.ModifyInfoData;
import com.hlbd.electric.util.BitmapUtil;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_OK;

public class ModifyInfoFragment extends BaseFragment implements ModifyInfoContract.View, View.OnClickListener {

  private static final String TAG = "ModifyInfoFragment";
  private ModifyInfoContract.Presenter mPresenter;
  private EventHandler mHandler;

  private ImageView mIv;
  private EditText mNameEt;
  private EditText mPhoneNumberEt;
  private EditText mCityEt;
  private EditText mAddressEt;

  private ModifyInfoRequest request;
  private ModifyAvatarRequest avatarRequest;

  protected static final int CHOOSE_PICTURE = 0;
  protected static final int TAKE_PICTURE = 1;
  private static final int CROP_SMALL_PICTURE = 2;

  private Uri tempUri;
  private boolean isBmpChange;
  private boolean isInfoChange;
  private Bitmap mUpdateBmp;
  private boolean modifyInfoSuccess = false;
  private boolean modifyAvatarSuccess = false;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_mime_modiify_info, container, false);
    }
    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  @Override
  public void setPersonal(ModifyInfoContract.Presenter p) {
    mPresenter = p;
  }

  public void startTask() {
    reset();
    Bitmap bmp = MimeInfoMgr.getsInstance().userAvatarBmp;
    if (bmp != null) {
      mIv.setImageBitmap(bmp);
    }

    String name = MimeInfoMgr.getsInstance().fullName;
    mNameEt.setText(name);

    String number = MimeInfoMgr.getsInstance().phoneNumber;
    mPhoneNumberEt.setText(number);


    String city = MimeInfoMgr.getsInstance().city;
    mCityEt.setText(city);


    String address = MimeInfoMgr.getsInstance().address;
    mAddressEt.setText(address);
  }

  private void init() {
    mHandler = new EventHandler(this);

    request = new ModifyInfoRequest();
    request.url = "Baoding_UserAccountManage/Services/UpdateUserData?";
    request.id = MimeInfoMgr.getsInstance().id;
    request.department = MimeInfoMgr.getsInstance().department;

    avatarRequest = new ModifyAvatarRequest();
    avatarRequest.url = "Baoding_UserAvatar.DT/Services/UpdateRow?";
    avatarRequest.username = HttpApi.getUserName();

    mPresenter = new ModifyInfoPresenter(this, request, avatarRequest);
  }

  private void initView() {
    mTitleTv.setText("修改资料");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mIv = mBaseView.findViewById(R.id.iv_image_mime_info_modify);

    mNameEt = mBaseView.findViewById(R.id.et_name_mime_info_modify);
    mPhoneNumberEt = mBaseView.findViewById(R.id.et_number_mime_info_modify);
    mCityEt = mBaseView.findViewById(R.id.et_city_mime_info_modify);
    mAddressEt = mBaseView.findViewById(R.id.et_address_mime_info_modify);

    mBaseView.findViewById(R.id.btn_sure_mime_info_modify).setOnClickListener(this);
    mBaseView.findViewById(R.id.iv_image_mime_info_modify).setOnClickListener(this);
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_DETAIL);
        }
      case R.id.btn_sure_mime_info_modify:
        modifyDetailInfo();
        break;

      case R.id.iv_image_mime_info_modify:
        Activity activity = getActivity();
        if (activity == null) {
          return;
        }
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
          showChoosePicDialog();
          return;
        }
        int permission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
          activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
          return;
        }
        showChoosePicDialog();
        break;

      default:
        break;
    }
  }

  @Override
  public void updateModifyInfo(ModifyInfoData data) {
    if (data == null) {
      LogUtil.w(TAG, "updateModifyInfo() error, ModifyInfoData is null");
      ToastUtil.toast("修改信息失败，请重试");
      modifyInfoSuccess = false;
      return;
    }
    try {
      for (ModifyInfoData.Row row : data.rows) {
        if (row.result != null && !TextUtils.isEmpty(row.result)) {
          LogUtil.d(TAG, "updateModifyInfo() row.result=" + row.result);
          if (row.result.contains("成功")) {
            modifyInfoSuccess = true;
          }
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateModifyInfo() error", e);
      ToastUtil.toast("修改信息失败，请重试");
      return;
    }
    if (!modifyInfoSuccess) {
      ToastUtil.toast("修改信息失败，请重试");
      LogUtil.w(TAG, "updateModifyInfo() error, ModifyInfoData is not success");
      return;
    }
    LogUtil.d(TAG, "updateModifyInfo() 修改信息成功");
    if (mPage != null && modifyInfoSuccess && (modifyAvatarSuccess || !isBmpChange)) {
      ToastUtil.toast("修改信息成功");
      mPage.turnPage(MimeFragment.PAGE_DETAIL);
      if (mUpdateBmp != null) {
        MimeInfoMgr.getsInstance().userAvatarBmp = mUpdateBmp;
      }
    }
  }

  @Override
  public void updateAvatarResult(ModifyAvatarResult result) {
    LogUtil.d(TAG, "updateAvatarResult() result=" + result + ", isBmpChange=" + isBmpChange);
    if (!isBmpChange) {
      return;
    }
    if (result == null) {
      modifyAvatarSuccess = false;
      ToastUtil.toast("修改头像失败，请重试");
      return;
    }
    try {
      for (ModifyAvatarResult.Row row : result.rows) {
        if (row.result != null && !TextUtils.isEmpty(row.result)) {
          LogUtil.d(TAG, "updateAvatarResult() row.result=" + row.result);
          if (row.result.contains("成功")) {
            modifyAvatarSuccess = true;
          }
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateAvatarResult() error", e);
      ToastUtil.toast("修改头像失败，请重试");
      return;
    }
    if (!modifyAvatarSuccess) {
      LogUtil.w(TAG, "updateAvatarResult() 修改头像失败");
      ToastUtil.toast("修改头像失败，请重试");
      return;
    }
    LogUtil.d(TAG, "updateAvatarResult() 修改头像成功");
    if (mPage != null && modifyAvatarSuccess && (modifyInfoSuccess || !isInfoChange)) {
      ToastUtil.toast("修改信息成功");
      mPage.turnPage(MimeFragment.PAGE_DETAIL);
      if (mUpdateBmp != null) {
        MimeInfoMgr.getsInstance().userAvatarBmp = mUpdateBmp;
      }
    }
  }

  private void modifyDetailInfo() {
    String fullname = mNameEt.getText().toString();
    String phonenumber = mPhoneNumberEt.getText().toString().trim();
    String city = mCityEt.getText().toString().trim();
    String address = mAddressEt.getText().toString().trim();
    isInfoChange = !fullname.equals(MimeInfoMgr.getsInstance().fullName) ||
            !phonenumber.equals(MimeInfoMgr.getsInstance().phoneNumber) ||
            !city.equals(MimeInfoMgr.getsInstance().city) ||
            !address.equals(MimeInfoMgr.getsInstance().address);
    if (!isInfoChange && !isBmpChange) {
      ToastUtil.toast("无资料修改");
      return;
    }
    request.username = HttpApi.getUserName();
    request.fullname = fullname;
    request.phonenumber = phonenumber;
    request.city = city;
    request.address = address;
    request.id = MimeInfoMgr.getsInstance().id;
    request.department = MimeInfoMgr.getsInstance().department;
    if (isBmpChange) {
      mPresenter.modifyAvatar();
    }
    if (isInfoChange) {
      mPresenter.modifyDetailInfo();
    }
  }

  private static class EventHandler extends Handler {

    private final WeakReference<ModifyInfoFragment> mRef;
    private static final int REQUEST = 1;

    private EventHandler(ModifyInfoFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      ModifyInfoFragment fragment = mRef.get();
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

  /**
   * 显示修改头像的对话框
   */
  protected void showChoosePicDialog() {
    Context context = getContext();
    LogUtil.d(TAG, "showChoosePicDialog() context=" + context);
    if (context == null) {
      return;
    }
    Intent openAlbumIntent = new Intent(
            Intent.ACTION_PICK);
    openAlbumIntent.setType("image/*");
    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("设置头像");
    String[] items = { "选择本地照片", "拍照" };
    builder.setNegativeButton("取消", null);
    builder.setItems(items, new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
          case CHOOSE_PICTURE: // 选择本地照片

            break;
          case TAKE_PICTURE: // 拍照
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            Uri tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
            break;
        }
      }
    });
    builder.create().show();*/
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) { // 如果返回码是可以用的
      switch (requestCode) {
        case TAKE_PICTURE:
          startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
          break;
        case CHOOSE_PICTURE:
          startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
          break;
        case CROP_SMALL_PICTURE:
          if (data != null) {
            setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
          }
          break;
      }
    }
  }

  /**
   * 裁剪图片方法实现
   *
   * @param uri
   */
  protected void startPhotoZoom(Uri uri) {
    if (uri == null) {
      LogUtil.i("tag", "The uri is not exist.");
    }
    tempUri = uri;
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uri, "image/*");
    // 设置裁剪
    intent.putExtra("crop", "true");
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    // outputX outputY 是裁剪图片宽高
    intent.putExtra("outputX", 150);
    intent.putExtra("outputY", 150);
    intent.putExtra("return-data", true);
    startActivityForResult(intent, CROP_SMALL_PICTURE);
  }

  /**
   * 保存裁剪之后的图片数据
   *
   * @param
   * @param data
   */
  protected void setImageToView(Intent data) {
    Bundle extras = data.getExtras();
    if (extras != null) {
      Bitmap photo = extras.getParcelable("data");
      mUpdateBmp = BitmapUtil.toRoundBitmap(photo);
      if (mUpdateBmp != null) {
        mIv.setImageBitmap(mUpdateBmp);
        isBmpChange = true;
        avatarRequest.userAvatar = BitmapUtil.bmpTo64String(mUpdateBmp);
        LogUtil.d(TAG, "setImageToView() avatarRequest.userAvatar=" + avatarRequest.userAvatar);
      }
      uploadPic(mUpdateBmp);
    }
  }

  private void uploadPic(Bitmap bitmap) {
    // 上传至服务器
    // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
    // 注意这里得到的图片已经是圆形图片了
    // bitmap是没有做个圆形处理的，但已经被裁剪了

   /* String imagePath = Utils.savePhoto(bitmap, Environment
            .getExternalStorageDirectory().getAbsolutePath(), String
            .valueOf(System.currentTimeMillis()));
    LogUtil.d("imagePath", imagePath+"");
    if(imagePath != null){
      // 拿着imagePath上传了
      // ...
    }*/
  }

  private void reset() {
    modifyAvatarSuccess = false;
    modifyInfoSuccess = false;
    isBmpChange = false;
    isInfoChange = false;
    mUpdateBmp = null;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    for (int i = 0; i < permissions.length; i++) {
      String permission = permissions[i];
      if ((permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)
              || permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
              && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
        showChoosePicDialog();
        return;
      }
    }
    ToastUtil.toast("没有获取到相册读取权限，请用户手动去系统设置中开通");
  }
}
