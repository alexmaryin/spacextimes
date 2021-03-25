package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CrewItemBindingImpl extends CrewItemBinding implements ru.alexmaryin.spacextimes_rx.generated.callback.OnClickListener.Listener {

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
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CrewItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private CrewItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageView) bindings[1]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nameText.setTag(null);
        this.photoCrew.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new ru.alexmaryin.spacextimes_rx.generated.callback.OnClickListener(this, 1);
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
        else if (BR.crewMember == variableId) {
            setCrewMember((ru.alexmaryin.spacextimes_rx.data.model.Crew) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setClickListener(@Nullable ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById ClickListener) {
        this.mClickListener = ClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.clickListener);
        super.requestRebind();
    }
    public void setCrewMember(@Nullable ru.alexmaryin.spacextimes_rx.data.model.Crew CrewMember) {
        this.mCrewMember = CrewMember;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.crewMember);
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
        ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById clickListener = mClickListener;
        java.lang.String crewMemberName = null;
        ru.alexmaryin.spacextimes_rx.data.model.Crew crewMember = mCrewMember;
        java.lang.String crewMemberImage = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (crewMember != null) {
                    // read crewMember.name
                    crewMemberName = crewMember.getName();
                    // read crewMember.image
                    crewMemberImage = crewMember.getImage();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.nameText, crewMemberName);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.ImageAdapter.loadImage(this.photoCrew, crewMemberImage, (boolean)false);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.photoCrew.setOnClickListener(mCallback1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // clickListener
        ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById clickListener = mClickListener;
        // crewMember != null
        boolean crewMemberJavaLangObjectNull = false;
        // crewMember
        ru.alexmaryin.spacextimes_rx.data.model.Crew crewMember = mCrewMember;
        // clickListener != null
        boolean clickListenerJavaLangObjectNull = false;
        // crewMember.id
        java.lang.String crewMemberId = null;



        clickListenerJavaLangObjectNull = (clickListener) != (null);
        if (clickListenerJavaLangObjectNull) {



            crewMemberJavaLangObjectNull = (crewMember) != (null);
            if (crewMemberJavaLangObjectNull) {


                crewMemberId = crewMember.getId();

                clickListener.onClick(crewMemberId);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): clickListener
        flag 1 (0x2L): crewMember
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}