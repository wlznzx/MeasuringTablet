package alauncher.cn.measuringtablet.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.utils.UriToPathUtil;
import alauncher.cn.measuringtablet.view.activity_view.DataUpdateInterface;
import alauncher.cn.measuringtablet.widget.ItemEditDialog;
import butterknife.BindView;
import butterknife.OnClick;


public class TemplateActivity extends BaseOActivity {

//    @BindView(R.id.sign_rv)
//    public RecyclerView signRV;
//
//    @BindView(R.id.title_p_rv)
//    public RecyclerView titlePRV;

    @BindView(R.id.vp2)
    public ViewPager vp2;

    public EditText leftHeaderEdt, midHeaderEdt, rightHeaderEdt, titleEdt, leftFooterEdt, midFooterEdt, rightFooterEdt;

    public RecyclerView signRV, listHeaderRV, AQLRV, RoSHRV;

    public Spinner signSP1, signSP2, signSP3;

    public Switch maximumSwitch, minimumSwitch, averageSwitch, rangeSwitch, judgeSwitch, aqlSwitch, roshSwitch;

    public TemplateBean mTemplateBean;

    public ListHeaderAdapter mListHeaderAdapter;

    public AQLHeaderAdapter mAQLHeaderAdapter;

    public RoHSHeaderAdapter mRoHSHeaderAdapter;

    public View addListHeaderBtn, addAQLBtn, addRoSHBtn;

    public View[] views = new View[2];

    public Button confirmationFrequencyBtn;

    public ImageView logoImageButton;

    public Long templateID;

    public DataUpdateInterface dataUpdateInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_template);
    }

    public void setDataUpdateInterface(DataUpdateInterface pDataUpdateInterface) {
        dataUpdateInterface = pDataUpdateInterface;
        if (dataUpdateInterface != null) dataUpdateInterface.dataUpdate();
    }

    @Override
    protected void initView() {

        templateID = getIntent().getLongExtra("TEMPLATE_ID", -1);
        mTemplateBean = App.getDaoSession().getTemplateBeanDao().load(templateID);
        if (mTemplateBean == null) {
            mTemplateBean = App.getDefaultTemplateBean();
        } else {
            boolean isCopy = getIntent().getBooleanExtra("IS_COPY", false);
            if (isCopy) {
                mTemplateBean.setTemplateID(0L);
            }
        }
        android.util.Log.d("wlDebug", "bean = " + mTemplateBean.toString());

        views[0] = LayoutInflater.from(TemplateActivity.this).inflate(R.layout.activity_template_frist, null);
        leftHeaderEdt = views[0].findViewById(R.id.left_header_edt);
        rightHeaderEdt = views[0].findViewById(R.id.right_header_edt);
        midHeaderEdt = views[0].findViewById(R.id.mid_header_edt);
        leftFooterEdt = views[0].findViewById(R.id.left_footer_edt);
        rightFooterEdt = views[0].findViewById(R.id.right_footer_edt);
        midFooterEdt = views[0].findViewById(R.id.mid_footer_edt);
        titleEdt = views[0].findViewById(R.id.title_edt);
        signSP1 = views[0].findViewById(R.id.sign_sp1);
        signSP2 = views[0].findViewById(R.id.sign_sp2);
        signSP3 = views[0].findViewById(R.id.sign_sp3);
        aqlSwitch = views[0].findViewById(R.id.aql_switch);
        roshSwitch = views[0].findViewById(R.id.rosh_switch);
        logoImageButton = views[0].findViewById(R.id.logo_btn);
        logoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        confirmationFrequencyBtn = views[0].findViewById(R.id.confirmation_frequency_btn);
        confirmationFrequencyBtn.setText(String.valueOf(mTemplateBean.getConfirmationFrequency()));
        confirmationFrequencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NumberPickerDialog(
                        TemplateActivity.this,
                        new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                confirmationFrequencyBtn.setText(newVal + "");
                            }
                        },
                        10, // 最大值
                        0, // 最小值
                        mTemplateBean.getConfirmationFrequency()) // 默认值
                        .setCurrentValue(mTemplateBean.getConfirmationFrequency()) // 更新默认值
                        .show();
            }
        });
        addListHeaderBtn = views[0].findViewById(R.id.add_list_header);
        addListHeaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ItemEditDialog(TemplateActivity.this, new ItemEditDialog.OnStringDialogCallBack() {
                    @Override
                    public void doString(String str, String type) {
                        ArrayList<String> _list = new ArrayList<>(mTemplateBean.getTitleList());
                        _list.add(str);
                        ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getTitleTypeList());
                        _typeList.add(type);
                        mTemplateBean.setTitleList(_list);
                        mTemplateBean.setTitleTypeList(_typeList);
                        mListHeaderAdapter.notifyDataSetChanged();
                    }
                }, "", "0").show();
            }
        });
        listHeaderRV = views[0].findViewById(R.id.title_p_rv);
        listHeaderRV.setLayoutManager(new LinearLayoutManager(this));
        views[1] = LayoutInflater.from(TemplateActivity.this).inflate(R.layout.activity_template_second, null);
        maximumSwitch = views[1].findViewById(R.id.maximum_switch);
        minimumSwitch = views[1].findViewById(R.id.minimum_switch);
        averageSwitch = views[1].findViewById(R.id.average_switch);
        rangeSwitch = views[1].findViewById(R.id.range_switch);
        judgeSwitch = views[1].findViewById(R.id.judge_switch);
        addAQLBtn = views[1].findViewById(R.id.add_aql);
        addAQLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ItemEditDialog(TemplateActivity.this, new ItemEditDialog.OnStringDialogCallBack() {
                    @Override
                    public void doString(String str, String type) {
                        ArrayList<String> _list = new ArrayList<>(mTemplateBean.getAQLList());
                        _list.add(str);
                        ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getAQLTypeList());
                        _typeList.add(type);
                        mTemplateBean.setAQLList(_list);
                        mTemplateBean.setAQLTypeList(_typeList);
                        mAQLHeaderAdapter.notifyDataSetChanged();
                    }
                }, "", "0").show();
            }
        });
        addRoSHBtn = views[1].findViewById(R.id.add_rosh);
        addRoSHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ItemEditDialog(TemplateActivity.this, new ItemEditDialog.OnStringDialogCallBack() {
                    @Override
                    public void doString(String str, String type) {
                        ArrayList<String> _list = new ArrayList<>(mTemplateBean.getRoHSList());
                        _list.add(str);
                        ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getRoHSTypeList());
                        _typeList.add(type);
                        mTemplateBean.setRoHSList(_list);
                        mTemplateBean.setRoHSTypeList(_typeList);
                        mListHeaderAdapter.notifyDataSetChanged();
                    }
                }, "", "0").show();
            }
        });
        AQLRV = views[1].findViewById(R.id.aql_rv);
        AQLRV.setLayoutManager(new LinearLayoutManager(this));
        RoSHRV = views[1].findViewById(R.id.rosh_rv);
        RoSHRV.setLayoutManager(new LinearLayoutManager(this));

        maximumSwitch.setChecked(mTemplateBean.getMaximumEnable());
        minimumSwitch.setChecked(mTemplateBean.getMinimumEnable());
        averageSwitch.setChecked(mTemplateBean.getAverageEnable());
        rangeSwitch.setChecked(mTemplateBean.getRangeEnable());
        judgeSwitch.setChecked(mTemplateBean.getJudgeEnable());
        aqlSwitch.setChecked(mTemplateBean.getAqlEnable());
        roshSwitch.setChecked(mTemplateBean.getRoshEnable());

        leftHeaderEdt.setText(mTemplateBean.getHeaderLeft());
        midHeaderEdt.setText(mTemplateBean.getHeaderMid());
        rightHeaderEdt.setText(mTemplateBean.getHeaderRight());
        leftFooterEdt.setText(mTemplateBean.getFooterLeft());
        rightFooterEdt.setText(mTemplateBean.getFooterRight());
        midFooterEdt.setText(mTemplateBean.getFooterMid());
        titleEdt.setText(mTemplateBean.getTitle());

        signSP1.setSelection(getRoleID(mTemplateBean.getSignList().get(0)));
        signSP2.setSelection(getRoleID(mTemplateBean.getSignList().get(1)));
        signSP3.setSelection(getRoleID(mTemplateBean.getSignList().get(2)));

        mListHeaderAdapter = new ListHeaderAdapter();
        mAQLHeaderAdapter = new AQLHeaderAdapter();
        mRoHSHeaderAdapter = new RoHSHeaderAdapter();

        vp2.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(views[position]);
                return views[position];
            }
        });
        if (mTemplateBean.getLogoPic() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(mTemplateBean.getLogoPic(), 0, mTemplateBean.getLogoPic().length, null);
            logoImageButton.setImageBitmap(bitmap);
        }
        listHeaderRV.setAdapter(mListHeaderAdapter);
        AQLRV.setAdapter(mAQLHeaderAdapter);
        RoSHRV.setAdapter(mRoHSHeaderAdapter);
        mListHeaderAdapter.notifyDataSetChanged();
        mAQLHeaderAdapter.notifyDataSetChanged();
        mRoHSHeaderAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_save)
    public void onSave(View v) {
        mTemplateBean.setAverageEnable(averageSwitch.isChecked());
        mTemplateBean.setMaximumEnable(maximumSwitch.isChecked());
        mTemplateBean.setMinimumEnable(minimumSwitch.isChecked());
        mTemplateBean.setRangeEnable(rangeSwitch.isChecked());
        mTemplateBean.setJudgeEnable(judgeSwitch.isChecked());
        mTemplateBean.setAqlEnable(aqlSwitch.isChecked());
        mTemplateBean.setRoshEnable(roshSwitch.isChecked());

        mTemplateBean.setHeaderLeft(leftHeaderEdt.getText().toString().trim());
        mTemplateBean.setHeaderMid(midHeaderEdt.getText().toString().trim());
        mTemplateBean.setHeaderRight(rightHeaderEdt.getText().toString().trim());

        mTemplateBean.setFooterLeft(leftFooterEdt.getText().toString().trim());
        mTemplateBean.setFooterMid(midFooterEdt.getText().toString().trim());
        mTemplateBean.setFooterRight(rightFooterEdt.getText().toString().trim());
        mTemplateBean.setDataNum(Integer.valueOf(confirmationFrequencyBtn.getText().toString().trim()));
        mTemplateBean.setConfirmationFrequency(Integer.valueOf(confirmationFrequencyBtn.getText().toString().trim()));
        mTemplateBean.setTitle(titleEdt.getText().toString().trim());

        List<String> signs = new ArrayList<>();
        signs.add((String) signSP1.getSelectedItem());
        signs.add((String) signSP2.getSelectedItem());
        signs.add((String) signSP3.getSelectedItem());
        signs.add("担当");
        mTemplateBean.setSignList(signs);

        //
        App.getDaoSession().getTemplateBeanDao().insertOrReplace(mTemplateBean);

        // Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();

        DialogUtils.showDialog(this, getResources().getString(R.string.save_success), getResources().getString(R.string.save_success), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        if (dataUpdateInterface != null) dataUpdateInterface.dataUpdate();
    }

    static final int REQUEST_TAKE_PHOTO = 2;

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    private String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private int getRoleID(String role) {
        String[] roles = getResources().getStringArray(R.array.sign);
        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equals(role)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String _path = UriToPathUtil.getFilePathByUri(this, uri);
            // _path = getDataColumn(this, uri, null, null);
            android.util.Log.d("wlDebug", "_path = " + _path);

            File file = new File(_path);
            if (!file.exists()) return;
            BitmapFactory.Options opts = new BitmapFactory.Options();
//设置为true,代表加载器不加载图片,而是把图片的宽高读出来
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

            int imageWidth = opts.outWidth;
            int imageHeight = opts.outHeight;
//得到屏幕的宽高
            Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
//获得像素大小
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            int widthScale = imageWidth / screenWidth;
            int heightScale = imageHeight / screenHeight;
            int scale = widthScale > heightScale ? widthScale : heightScale;
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
            logoImageButton.setImageBitmap(bm);
            mTemplateBean.setLogoPic(getBitmapByte(bm));
        }
    }

    public byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    class ListHeaderAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return ViewHolder.createViewHolder(TemplateActivity.this, parent, R.layout.string_item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String str = mTemplateBean.getTitleList().get(position);
            holder.setText(R.id.tv, getString(R.string.list_header) + (position + 1) + "            " + str);
            holder.setText(R.id.input_type_tv, getInputType(mTemplateBean.getTitleTypeList().get(position)));
            holder.setOnClickListener(R.id.str_item, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ItemEditDialog(TemplateActivity.this, new ItemEditDialog.OnStringDialogCallBack() {
                        @Override
                        public void doString(String str, String type) {
                            ArrayList<String> _list = new ArrayList<>(mTemplateBean.getTitleList());
                            _list.set(position, str);
                            ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getTitleTypeList());
                            _typeList.set(position, type);
                            mTemplateBean.setTitleList(_list);
                            mTemplateBean.setTitleTypeList(_typeList);
                            mListHeaderAdapter.notifyDataSetChanged();
                        }
                    }, str, mTemplateBean.getTitleTypeList().get(position)).show();
                }
            });
            holder.setOnLongClickListener(R.id.str_item, new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog builder = new AlertDialog.Builder(TemplateActivity.this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) return false;
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = builder.findViewById(R.id.tv_msg);
                    Button cancel = builder.findViewById(R.id.btn_cancle);
                    Button sure = builder.findViewById(R.id.btn_sure);
                    msg.setText("是否删除 " + getResources().getString(R.string.list_header) + (position + 1) + " ?");
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> _list = new ArrayList<>(mTemplateBean.getTitleList());
                            _list.remove(position);
                            ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getTitleTypeList());
                            _typeList.remove(position);
                            mTemplateBean.setTitleList(_list);
                            mTemplateBean.setTitleTypeList(_typeList);
                            builder.dismiss();
                            mListHeaderAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTemplateBean.getTitleList().size();
        }
    }

    class AQLHeaderAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return ViewHolder.createViewHolder(TemplateActivity.this, parent, R.layout.string_item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String str = mTemplateBean.getAQLList().get(position);
            holder.setText(R.id.tv, getString(R.string.aql_title) + (position + 1) + "            " + str);
            holder.setText(R.id.input_type_tv, getInputType(mTemplateBean.getAQLTypeList().get(position)));
            holder.setOnClickListener(R.id.str_item, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ItemEditDialog(TemplateActivity.this, new ItemEditDialog.OnStringDialogCallBack() {
                        @Override
                        public void doString(String str, String type) {
                            ArrayList<String> _list = new ArrayList<>(mTemplateBean.getAQLList());
                            _list.set(position, str);
                            ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getAQLTypeList());
                            _typeList.set(position, type);
                            mTemplateBean.setAQLList(_list);
                            mTemplateBean.setAQLTypeList(_typeList);
                            mAQLHeaderAdapter.notifyDataSetChanged();
                        }
                    }, str, mTemplateBean.getAQLTypeList().get(position)).show();
                }
            });
            holder.setOnLongClickListener(R.id.str_item, new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog builder = new AlertDialog.Builder(TemplateActivity.this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) return false;
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = builder.findViewById(R.id.tv_msg);
                    Button cancel = builder.findViewById(R.id.btn_cancle);
                    Button sure = builder.findViewById(R.id.btn_sure);
                    msg.setText("是否删除 " + getResources().getString(R.string.list_header) + (position + 1) + " ?");
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> _list = new ArrayList<String>(mTemplateBean.getAQLList());
                            _list.remove(position);
                            ArrayList<String> _typeList = new ArrayList<String>(mTemplateBean.getAQLTypeList());
                            _typeList.remove(position);
                            mTemplateBean.setAQLList(_list);
                            mTemplateBean.setAQLTypeList(_typeList);
                            builder.dismiss();
                            mAQLHeaderAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTemplateBean.getAQLList().size();
        }
    }

    class RoHSHeaderAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return ViewHolder.createViewHolder(TemplateActivity.this, parent, R.layout.string_item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String str = mTemplateBean.getRoHSList().get(position);
            holder.setText(R.id.tv, getString(R.string.info_item) + (position + 1) + "            " + str);
            holder.setText(R.id.input_type_tv, getInputType(mTemplateBean.getRoHSTypeList().get(position)));
            if (str.equals("RoHS确认频率") || str.equals("本批确认") || str.equals("上回RoHS确认日")) {
                return;
            }
            holder.setOnClickListener(R.id.str_item, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ItemEditDialog(TemplateActivity.this, new ItemEditDialog.OnStringDialogCallBack() {
                        @Override
                        public void doString(String str, String type) {
                            ArrayList<String> _list = new ArrayList<>(mTemplateBean.getRoHSList());
                            _list.set(position, str);
                            ArrayList<String> _typeList = new ArrayList<>(mTemplateBean.getRoHSTypeList());
                            _typeList.set(position, type);
                            mTemplateBean.setRoHSList(_list);
                            mTemplateBean.setRoHSTypeList(_typeList);
                            mRoHSHeaderAdapter.notifyDataSetChanged();
                        }
                    }, str, mTemplateBean.getRoHSTypeList().get(position)).show();
                }
            });
            holder.setOnLongClickListener(R.id.str_item, new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog builder = new AlertDialog.Builder(TemplateActivity.this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) return false;
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = builder.findViewById(R.id.tv_msg);
                    Button cancel = builder.findViewById(R.id.btn_cancle);
                    Button sure = builder.findViewById(R.id.btn_sure);
                    msg.setText("是否删除 " + getResources().getString(R.string.list_header) + (position + 1) + " ?");
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> _list = new ArrayList<String>(mTemplateBean.getRoHSList());
                            _list.remove(position);
                            ArrayList<String> _typeList = new ArrayList<String>(mTemplateBean.getRoHSTypeList());
                            _typeList.remove(position);
                            mTemplateBean.setRoHSList(_list);
                            mTemplateBean.setRoHSTypeList(_typeList);
                            builder.dismiss();
                            mRoHSHeaderAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTemplateBean.getRoHSList().size();
        }
    }

    private String getInputType(String index) {
        return getResources().getStringArray(R.array.input_type)[Integer.valueOf(index)];
    }

}
