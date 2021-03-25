package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CapsuleItemBindingImpl extends CapsuleItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.capsuleStatus, 3);
        sViewsWithIds.put(R.id.capsuleReused, 4);
        sViewsWithIds.put(R.id.capsuleImage, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CapsuleItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private CapsuleItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            );
        this.capsuleSerial.setTag(null);
        this.capsuleUpdate.setTag(null);
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
        if (BR.capsule == variableId) {
            setCapsule((ru.alexmaryin.spacextimes_rx.data.model.Capsule) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCapsule(@Nullable ru.alexmaryin.spacextimes_rx.data.model.Capsule Capsule) {
        this.mCapsule = Capsule;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.capsule);
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
        ru.alexmaryin.spacextimes_rx.data.model.Capsule capsule = mCapsule;
        java.lang.String CapsuleSerial1 = null;
        java.lang.String capsuleLastUpdate = null;
        boolean capsuleLastUpdateRuJavaLangObjectNull = false;
        java.lang.String capsuleLastUpdateRuJavaLangObjectNullCapsuleLastUpdateCapsuleLastUpdateRu = null;
        java.lang.String capsuleLastUpdateRu = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (capsule != null) {
                    // read capsule.serial
                    CapsuleSerial1 = capsule.getSerial();
                    // read capsule.lastUpdateRu
                    capsuleLastUpdateRu = capsule.getLastUpdateRu();
                }


                // read capsule.lastUpdateRu == null
                capsuleLastUpdateRuJavaLangObjectNull = (capsuleLastUpdateRu) == (null);
            if((dirtyFlags & 0x3L) != 0) {
                if(capsuleLastUpdateRuJavaLangObjectNull) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x8L) != 0) {

                if (capsule != null) {
                    // read capsule.lastUpdate
                    capsuleLastUpdate = capsule.getLastUpdate();
                }
        }

        if ((dirtyFlags & 0x3L) != 0) {

                // read capsule.lastUpdateRu == null ? capsule.lastUpdate : capsule.lastUpdateRu
                capsuleLastUpdateRuJavaLangObjectNullCapsuleLastUpdateCapsuleLastUpdateRu = ((capsuleLastUpdateRuJavaLangObjectNull) ? (capsuleLastUpdate) : (capsuleLastUpdateRu));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.capsuleSerial, CapsuleSerial1);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.capsuleUpdate, capsuleLastUpdateRuJavaLangObjectNullCapsuleLastUpdateCapsuleLastUpdateRu);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): capsule
        flag 1 (0x2L): null
        flag 2 (0x3L): capsule.lastUpdateRu == null ? capsule.lastUpdate : capsule.lastUpdateRu
        flag 3 (0x4L): capsule.lastUpdateRu == null ? capsule.lastUpdate : capsule.lastUpdateRu
    flag mapping end*/
    //end
}