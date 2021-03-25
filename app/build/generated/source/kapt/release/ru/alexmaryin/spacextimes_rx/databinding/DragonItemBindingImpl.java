package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DragonItemBindingImpl extends DragonItemBinding implements ru.alexmaryin.spacextimes_rx.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.expandDescriptionButton, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DragonItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private DragonItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[2]
            );
        this.description.setTag(null);
        this.dragonName.setTag(null);
        this.firstFlight.setTag(null);
        this.imageView.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        mCallback3 = new ru.alexmaryin.spacextimes_rx.generated.callback.OnClickListener(this, 2);
        mCallback2 = new ru.alexmaryin.spacextimes_rx.generated.callback.OnClickListener(this, 1);
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
        else if (BR.dragon == variableId) {
            setDragon((ru.alexmaryin.spacextimes_rx.data.model.Dragon) variable);
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
    public void setDragon(@Nullable ru.alexmaryin.spacextimes_rx.data.model.Dragon Dragon) {
        this.mDragon = Dragon;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.dragon);
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
        java.util.List<java.lang.String> dragonImages = null;
        java.lang.String DragonName1 = null;
        java.lang.String dragonDescriptionRuJavaLangObjectNullDragonDescriptionDragonDescriptionRu = null;
        java.lang.String dragonDescriptionRu = null;
        java.lang.String dragonDescription = null;
        ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById clickListener = mClickListener;
        java.util.Date dragonFirstFlight = null;
        boolean dragonDescriptionRuJavaLangObjectNull = false;
        ru.alexmaryin.spacextimes_rx.data.model.Dragon dragon = mDragon;
        java.lang.String dragonImages0 = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (dragon != null) {
                    // read dragon.images
                    dragonImages = dragon.getImages();
                    // read dragon.name
                    DragonName1 = dragon.getName();
                    // read dragon.descriptionRu
                    dragonDescriptionRu = dragon.getDescriptionRu();
                    // read dragon.firstFlight
                    dragonFirstFlight = dragon.getFirstFlight();
                }


                if (dragonImages != null) {
                    // read dragon.images[0]
                    dragonImages0 = getFromList(dragonImages, 0);
                }
                // read dragon.descriptionRu == null
                dragonDescriptionRuJavaLangObjectNull = (dragonDescriptionRu) == (null);
            if((dirtyFlags & 0x6L) != 0) {
                if(dragonDescriptionRuJavaLangObjectNull) {
                        dirtyFlags |= 0x10L;
                }
                else {
                        dirtyFlags |= 0x8L;
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x10L) != 0) {

                if (dragon != null) {
                    // read dragon.description
                    dragonDescription = dragon.getDescription();
                }
        }

        if ((dirtyFlags & 0x6L) != 0) {

                // read dragon.descriptionRu == null ? dragon.description : dragon.descriptionRu
                dragonDescriptionRuJavaLangObjectNullDragonDescriptionDragonDescriptionRu = ((dragonDescriptionRuJavaLangObjectNull) ? (dragonDescription) : (dragonDescriptionRu));
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.description, dragonDescriptionRuJavaLangObjectNullDragonDescriptionDragonDescriptionRu);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.dragonName, DragonName1);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.DateAdapter.firstFlightToString(this.firstFlight, dragonFirstFlight);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.ImageAdapter.loadImage(this.imageView, dragonImages0, true);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.dragonName.setOnClickListener(mCallback2);
            this.imageView.setOnClickListener(mCallback3);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // clickListener
                ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById clickListener = mClickListener;
                // dragon
                ru.alexmaryin.spacextimes_rx.data.model.Dragon dragon = mDragon;
                // clickListener != null
                boolean clickListenerJavaLangObjectNull = false;
                // dragon.id
                java.lang.String dragonId = null;
                // dragon != null
                boolean dragonJavaLangObjectNull = false;



                clickListenerJavaLangObjectNull = (clickListener) != (null);
                if (clickListenerJavaLangObjectNull) {



                    dragonJavaLangObjectNull = (dragon) != (null);
                    if (dragonJavaLangObjectNull) {


                        dragonId = dragon.getId();

                        clickListener.onClick(dragonId);
                    }
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // clickListener
                ru.alexmaryin.spacextimes_rx.ui.adapters.AdapterClickListenerById clickListener = mClickListener;
                // dragon
                ru.alexmaryin.spacextimes_rx.data.model.Dragon dragon = mDragon;
                // clickListener != null
                boolean clickListenerJavaLangObjectNull = false;
                // dragon.id
                java.lang.String dragonId = null;
                // dragon != null
                boolean dragonJavaLangObjectNull = false;



                clickListenerJavaLangObjectNull = (clickListener) != (null);
                if (clickListenerJavaLangObjectNull) {



                    dragonJavaLangObjectNull = (dragon) != (null);
                    if (dragonJavaLangObjectNull) {


                        dragonId = dragon.getId();

                        clickListener.onClick(dragonId);
                    }
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): clickListener
        flag 1 (0x2L): dragon
        flag 2 (0x3L): null
        flag 3 (0x4L): dragon.descriptionRu == null ? dragon.description : dragon.descriptionRu
        flag 4 (0x5L): dragon.descriptionRu == null ? dragon.description : dragon.descriptionRu
    flag mapping end*/
    //end
}