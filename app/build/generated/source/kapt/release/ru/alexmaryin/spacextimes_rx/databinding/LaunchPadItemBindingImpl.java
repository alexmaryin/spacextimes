package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LaunchPadItemBindingImpl extends LaunchPadItemBinding  {

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

    public LaunchPadItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LaunchPadItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            );
        this.launchPadFullName.setTag(null);
        this.launchPadLocation.setTag(null);
        this.launchPadName.setTag(null);
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
        if (BR.clickListener == variableId) {
            setClickListener((ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById) variable);
        }
        else if (BR.launchPad == variableId) {
            setLaunchPad((ru.alexmaryin.spacextimes_rx.data.model.LaunchPad) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setClickListener(@Nullable ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById ClickListener) {
        this.mClickListener = ClickListener;
    }
    public void setLaunchPad(@Nullable ru.alexmaryin.spacextimes_rx.data.model.LaunchPad LaunchPad) {
        this.mLaunchPad = LaunchPad;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.launchPad);
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
        java.lang.String launchPadLocality = null;
        java.lang.String launchPadRegion = null;
        java.lang.String LaunchPadName1 = null;
        java.lang.String launchPadLocalityJavaLangString = null;
        java.lang.String LaunchPadFullName1 = null;
        ru.alexmaryin.spacextimes_rx.data.model.LaunchPad launchPad = mLaunchPad;
        java.lang.String launchPadLocalityJavaLangStringLaunchPadRegion = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (launchPad != null) {
                    // read launchPad.locality
                    launchPadLocality = launchPad.getLocality();
                    // read launchPad.region
                    launchPadRegion = launchPad.getRegion();
                    // read launchPad.name
                    LaunchPadName1 = launchPad.getName();
                    // read launchPad.fullName
                    LaunchPadFullName1 = launchPad.getFullName();
                }


                // read (launchPad.locality) + (", ")
                launchPadLocalityJavaLangString = (launchPadLocality) + (", ");


                // read ((launchPad.locality) + (", ")) + (launchPad.region)
                launchPadLocalityJavaLangStringLaunchPadRegion = (launchPadLocalityJavaLangString) + (launchPadRegion);
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.launchPadFullName, LaunchPadFullName1);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.launchPadLocation, launchPadLocalityJavaLangStringLaunchPadRegion);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.launchPadName, LaunchPadName1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): clickListener
        flag 1 (0x2L): launchPad
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}