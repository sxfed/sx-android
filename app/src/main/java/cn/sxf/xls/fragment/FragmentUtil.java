package cn.sxf.xls.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import cn.sxf.xls.R;

/**
 * Fragment工具类
 * @author flh
 */
public class FragmentUtil {
    private static FragmentUtil instance;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    // 切换的视图
    private HomeFragment f1;
    private RankingFragment f2;
    private ClassifyFragment f3;
    private MyInfoFragment f4;

    public HomeFragment getF1() {
        return f1;
    }

    private FragmentUtil() {
    }

    public static FragmentUtil getInstance(){
        if(instance == null){
            instance = new FragmentUtil();
        }
        return instance;
    }

    /**
     * 切换界面
     * @param checkedId
     */
    public void switchFragmentView(int checkedId){
        clearFragment(f1,f2,f3,f4);
        switch (checkedId) {
            case 0:
                f1 = new HomeFragment();
                addFragment(checkedId, f1);
                break;
            case 1:
                f2 = new RankingFragment();
                addFragment(checkedId, f2);
                break;
            case 2:
                f3 = new ClassifyFragment();
                addFragment(checkedId, f3);
                break;
            case 3:
                f4 = new MyInfoFragment();
                addFragment(checkedId, f4);
                break;
        }
    }

    /**
     * 清空ViewTree
     */
    public void clearFragment(Fragment... args){
        if(args.length > 0){
            for(int frag=0; frag < args.length; frag++){
                Fragment fragment = args[frag];
                if(fragment != null)
                    transaction.detach(fragment);
            }
        }
    }

    /**
     * 添加View视图
     * @param checkedId
     * @param fragment
     */
    public void addFragment(int checkedId, Fragment fragment){
        transaction.replace(R.id.main_container, fragment);
        if(checkedId == 0){
            transaction.commitAllowingStateLoss();
        }else{
            transaction.commit();
        }
    }

    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
        this.transaction = manager.beginTransaction();
    }

    public FragmentTransaction getTransaction() {
        return transaction;
    }

}
