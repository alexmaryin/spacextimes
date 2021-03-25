package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CoreItemBindingImpl extends CoreItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.coreThumbnail, 3);
        sViewsWithIds.put(R.id.coreStatus, 4);
        sViewsWithIds.put(R.id.coreReusing, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CoreItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private CoreItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[3]
            );
        this.coreLastUpdate.setTag(null);
        this.coreSerial.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.core == variableId) {
            setCore((ru.alexmaryin.spacextimes_rx.data.model.Core) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCore(@Nullable ru.alexmaryin.spacextimes_rx.data.model.Core Core) {
        this.mCore = Core;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.core);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String CoreSerial1 = null;
        java.lang.String coreLastUpdateRu = null;
        java.lang.String coreLastUpdateRuJavaLangObjectNullCoreLastUpdateCoreLastUpdateRu = null;
        boolean coreLastUpdateRuJavaLangObjectNull = false;
        java.lang.String CoreLastUpdate1 = null;
        ru.alexmaryin.spacextimes_rx.data.model.Core core = mCore;

        if ((dirtyFlags & 0x3L) != 0) {



                if (core != null) {
                    // read core.serial
                    CoreSerial1 = core.getSerial();
                    // read core.lastUpdateRu
                    coreLastUpdateRu = core.getLastUpdateRu();
                }


                // read core.lastUpdateRu == null
                coreLastUpdateRuJavaLangObjectNull = (coreLastUpdateRu) == (null);
            if((dirtyFlags & 0x3L) != 0) {
                if(coreLastUpdateRuJavaLangObjectNull) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x8L) != 0) {

                if (core != null) {
                    // read core.lastUpdate
                    CoreLastUpdate1 = core.getLastUpdate();
                }
        }

        if ((dirtyFlags & 0x3L) != 0) {

                // read core.lastUpdateRu == null ? core.lastUpdate : core.lastUpdateRu
                coreLastUpdateRuJavaLangObjectNullCoreLastUpdateCoreLastUpdateRu = ((coreLastUpdateRuJavaLangObjectNull) ? (CoreLastUpdate1) : (coreLastUpdateRu));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.coreLastUpdate, coreLastUpdateRuJavaLangObjectNullCoreLastUpdateCoreLastUpdateRu);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.coreSerial, CoreSerial1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): core
        flag 1 (0x2L): null
        flag 2 (0x3L): core.lastUpdateRu == null ? core.lastUpdate : core.lastUpdateRu
        flag 3 (0x4L): core.lastUpdateRu == null ? core.lastUpdate : core.lastUpdateRu
    flag mapping end*/
    //end
}