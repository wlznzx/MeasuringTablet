package alauncher.cn.measuringtablet.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CodeBaseInfoFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.machine_tool_edt)
    public EditText machineToolEdt;

    @BindView(R.id.parts_edt)
    public EditText partEdt;

    @BindView(R.id.code_name_edt)
    public EditText codeNameEdt;

    @BindView(R.id.title_d_rv)
    public RecyclerView titleDefaultRV;

    public TemplateBean mTemplateBean;

    public CodeBean mCodeBean;

    public List<String> defaultTitles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.code_baseinfo_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @OnClick(R.id.save_btn)
    public void onSave(View v) {
        String _name = codeNameEdt.getText().toString().trim();
        String[] strArray = _name.split("-");
        if (strArray.length != 4) {
            // 命名不正确.
            DialogUtils.showDialog(getContext(), "无法保存", "程序命名不正确，请参考\"十工位-Block-TRA-100\"");
            return;
        }
        CodeBean _bean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
        if (_bean == null) {
//            _bean = new CodeBean(App.getSetupBean().getCodeID(), "",
//                    machineToolEdt.getText().toString().trim(), partEdt.getText().toString().trim(),false,null);
        } else {
            _bean.setMachineTool(machineToolEdt.getText().toString().trim());
            _bean.setParts(partEdt.getText().toString().trim());
            _bean.setName(codeNameEdt.getText().toString().trim());
            _bean.setDefaultTitles(defaultTitles);
        }
        App.getDaoSession().getCodeBeanDao().insertOrReplace(_bean);
        Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {

        mCodeBean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
        mTemplateBean = App.getDaoSession().getTemplateBeanDao().load(mCodeBean.getUseTemplateID());
        if (mCodeBean != null) {
            machineToolEdt.setText(mCodeBean.getMachineTool());
            partEdt.setText(mCodeBean.getParts());
            codeNameEdt.setText(mCodeBean.getName());
            titleDefaultRV.setLayoutManager(new LinearLayoutManager(getContext()));
            titleDefaultRV.setAdapter(new ListDefaultAdapter());
            defaultTitles = new ArrayList<>();
            if (mTemplateBean != null) {
                for (String str : mTemplateBean.getTitleList()) {
                    defaultTitles.add("");
                }
            }
        } else {

        }
    }

    class ListDefaultAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return ViewHolder.createViewHolder(getContext(), parent, R.layout.default_item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String str = mTemplateBean.getTitleList().get(position);
            holder.setText(R.id.tv, getString(R.string.list_header) + (position + 1) + "            " + str);
            if (mCodeBean.getDefaultTitles().size() > position) {
                holder.setText(R.id.default_edt, mCodeBean.getDefaultTitles().get(position));
                defaultTitles.set(position, mCodeBean.getDefaultTitles().get(position));
            }
            ((EditText) holder.getView(R.id.default_edt)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    defaultTitles.set(position, editable.toString().trim());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTemplateBean.getTitleList().size();
        }
    }

}
