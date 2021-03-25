package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class RocketItemBindingImpl extends RocketItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.expandDescriptionButton, 4);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public RocketItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private RocketItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.TextView) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.ImageView) bindings[2]
            );
        this.description.setTag(null);
        this.name.setTag(null);
        this.root.setTag(null);
        this.thumbnail.setTag(null);
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
        if (BR.rocket == variableId) {
            setRocket((ru.alexmaryin.spacextimes_rx.data.model.Rocket) variable);
        }
        else if (BR.clickListener == variableId) {
            setClickListener((ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setRocket(@Nullable ru.alexmaryin.spacextimes_rx.data.model.Rocket Rocket) {
        this.mRocket = Rocket;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.rocket);
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
        boolean rocketDescriptionRuJavaLangObjectNull = false;
        java.lang.String rocketDescription = null;
        java.lang.String rocketName = null;
        ru.alexmaryin.spacextimes_rx.data.model.Rocket rocket = mRocket;
        java.util.List<java.lang.String> rocketImages = null;
        java.lang.String rocketDescriptionRuJavaLangObjectNullRocketDescriptionRocketDescriptionRu = null;
        java.lang.String rocketImages0 = null;
        java.lang.String rocketDescriptionRu = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (rocket != null) {
                    // read rocket.name
                    rocketName = rocket.getName();
                    // read rocket.images
                    rocketImages = rocket.getImages();
                    // read rocket.descriptionRu
                    rocketDescriptionRu = rocket.getDescriptionRu();
                }


                if (rocketImages != null) {
                    // read rocket.images[0]
                    rocketImages0 = getFromList(rocketImages, 0);
                }
                // read rocket.descriptionRu == null
                rocketDescriptionRuJavaLangObjectNull = (rocketDescriptionRu) == (null);
            if((dirtyFlags & 0x5L) != 0) {
                if(rocketDescriptionRuJavaLangObjectNull) {
                        dirtyFlags |= 0x10L;
                }
                else {
                        dirtyFlags |= 0x8L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x10L) != 0) {

                if (rocket != null) {
                    // read rocket.description
                    rocketDescription = rocket.getDescription();
                }
        }

        if ((dirtyFlags & 0x5L) != 0) {

                // read rocket.descriptionRu == null ? rocket.description : rocket.descriptionRu
                rocketDescriptionRuJavaLangObjectNullRocketDescriptionRocketDescriptionRu = ((rocketDescriptionRuJavaLangObjectNull) ? (rocketDescription) : (rocketDescriptionRu));
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.description, rocketDescriptionRuJavaLangObjectNullRocketDescriptionRocketDescriptionRu);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.name, rocketName);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.ImageAdapter.loadImage(this.thumbnail, rocketImages0, true);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): rocket
        flag 1 (0x2L): clickListener
        flag 2 (0x3L): null
        flag 3 (0x4L): rocket.descriptionRu == null ? rocket.description : rocket.descriptionRu
        flag 4 (0x5L): rocket.descriptionRu == null ? rocket.description : rocket.descriptionRu
    flag mapping end*/
    //end
}