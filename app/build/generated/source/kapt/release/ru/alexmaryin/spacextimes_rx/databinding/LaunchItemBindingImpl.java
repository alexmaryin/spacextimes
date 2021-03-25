package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LaunchItemBindingImpl extends LaunchItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LaunchItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LaunchItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[2]
            );
        this.box.setTag(null);
        this.launchDate.setTag(null);
        this.launchName.setTag(null);
        this.patchImage.setTag(null);
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
        if (BR.launch == variableId) {
            setLaunch((ru.alexmaryin.spacextimes_rx.data.model.Launch) variable);
        }
        else if (BR.clickListener == variableId) {
            setClickListener((ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLaunch(@Nullable ru.alexmaryin.spacextimes_rx.data.model.Launch Launch) {
        this.mLaunch = Launch;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.launch);
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
        ru.alexmaryin.spacextimes_rx.data.model.extra.Links launchLinks = null;
        ru.alexmaryin.spacextimes_rx.data.model.Launch launch = mLaunch;
        java.lang.String launchLinksPatchSmall = null;
        java.lang.String LaunchName1 = null;
        ru.alexmaryin.spacextimes_rx.data.model.extra.PatchImages launchLinksPatch = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (launch != null) {
                    // read launch.links
                    launchLinks = launch.getLinks();
                    // read launch.name
                    LaunchName1 = launch.getName();
                }


                if (launchLinks != null) {
                    // read launch.links.patch
                    launchLinksPatch = launchLinks.getPatch();
                }


                if (launchLinksPatch != null) {
                    // read launch.links.patch.small
                    launchLinksPatchSmall = launchLinksPatch.getSmall();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.DateAdapter.launchDateToString(this.launchDate, launch);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.launchName, LaunchName1);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.ImageAdapter.loadImage(this.patchImage, launchLinksPatchSmall, (boolean)false);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): launch
        flag 1 (0x2L): clickListener
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}