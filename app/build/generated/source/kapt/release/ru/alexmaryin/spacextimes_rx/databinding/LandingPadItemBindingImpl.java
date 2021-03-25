package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LandingPadItemBindingImpl extends LandingPadItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LandingPadItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LandingPadItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            );
        this.landingPadFullName.setTag(null);
        this.landingPadLocation.setTag(null);
        this.landingPadName.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.landingPad == variableId) {
            setLandingPad((ru.alexmaryin.spacextimes_rx.data.model.LandingPad) variable);
        }
        else if (BR.clickListener == variableId) {
            setClickListener((ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLandingPad(@Nullable ru.alexmaryin.spacextimes_rx.data.model.LandingPad LandingPad) {
        this.mLandingPad = LandingPad;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.landingPad);
        super.requestRebind();
    }
    public void setClickListener(@Nullable ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById ClickListener) {
        this.mClickListener = ClickListener;
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
        java.lang.String landingPadLocality = null;
        ru.alexmaryin.spacextimes_rx.data.model.LandingPad landingPad = mLandingPad;
        java.lang.String LandingPadName1 = null;
        java.lang.String LandingPadFullName1 = null;
        java.lang.String landingPadLocalityJavaLangStringLandingPadRegion = null;
        java.lang.String landingPadRegion = null;
        java.lang.String landingPadLocalityJavaLangString = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (landingPad != null) {
                    // read landingPad.locality
                    landingPadLocality = landingPad.getLocality();
                    // read landingPad.name
                    LandingPadName1 = landingPad.getName();
                    // read landingPad.fullName
                    LandingPadFullName1 = landingPad.getFullName();
                    // read landingPad.region
                    landingPadRegion = landingPad.getRegion();
                }


                // read (landingPad.locality) + (", ")
                landingPadLocalityJavaLangString = (landingPadLocality) + (", ");


                // read ((landingPad.locality) + (", ")) + (landingPad.region)
                landingPadLocalityJavaLangStringLandingPadRegion = (landingPadLocalityJavaLangString) + (landingPadRegion);
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.landingPadFullName, LandingPadFullName1);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.landingPadLocation, landingPadLocalityJavaLangStringLandingPadRegion);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.landingPadName, LandingPadName1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): landingPad
        flag 1 (0x2L): clickListener
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}