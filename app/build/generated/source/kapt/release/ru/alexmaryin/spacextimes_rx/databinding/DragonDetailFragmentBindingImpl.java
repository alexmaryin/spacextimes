package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DragonDetailFragmentBindingImpl extends DragonDetailFragmentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(11);
        sIncludes.setIncludes(0, 
            new String[] {"progress_webview_part"},
            new int[] {5},
            new int[] {ru.alexmaryin.spacextimes_rx.R.layout.progress_webview_part});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.detailsView, 6);
        sViewsWithIds.put(R.id.imagesCarousel, 7);
        sViewsWithIds.put(R.id.wikiButton, 8);
        sViewsWithIds.put(R.id.enginesLabel, 9);
        sViewsWithIds.put(R.id.enginesList, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DragonDetailFragmentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private DragonDetailFragmentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.TextView) bindings[4]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[6]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[9]
            , (android.widget.ListView) bindings[10]
            , (android.widget.TextView) bindings[3]
            , (com.synnapps.carouselview.CarouselView) bindings[7]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.ImageButton) bindings[8]
            , (ru.alexmaryin.spacextimes_rx.databinding.ProgressWebviewPartBinding) bindings[5]
            );
        this.crewCapacity.setTag(null);
        this.dragonSizeLine.setTag(null);
        this.dragonStatusLine.setTag(null);
        this.heatShield.setTag(null);
        this.rootLayout.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        wikiFrame.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (wikiFrame.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.dragonViewModel == variableId) {
            setDragonViewModel((ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setDragonViewModel(@Nullable ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel DragonViewModel) {
        this.mDragonViewModel = DragonViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.dragonViewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        wikiFrame.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeDragonViewModelDragonDetails((androidx.lifecycle.LiveData<ru.alexmaryin.spacextimes_rx.data.model.Dragon>) object, fieldId);
            case 1 :
                return onChangeWikiFrame((ru.alexmaryin.spacextimes_rx.databinding.ProgressWebviewPartBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeDragonViewModelDragonDetails(androidx.lifecycle.LiveData<ru.alexmaryin.spacextimes_rx.data.model.Dragon> DragonViewModelDragonDetails, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeWikiFrame(ru.alexmaryin.spacextimes_rx.databinding.ProgressWebviewPartBinding WikiFrame, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
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
        ru.alexmaryin.spacextimes_rx.data.model.parts.Shield dragonViewModelDragonDetailsHeatShield = null;
        ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel dragonViewModel = mDragonViewModel;
        ru.alexmaryin.spacextimes_rx.data.model.Dragon dragonViewModelDragonDetailsGetValue = null;
        androidx.lifecycle.LiveData<ru.alexmaryin.spacextimes_rx.data.model.Dragon> dragonViewModelDragonDetails = null;
        int dragonViewModelDragonDetailsCrewCapacity = 0;

        if ((dirtyFlags & 0xdL) != 0) {



                if (dragonViewModel != null) {
                    // read dragonViewModel.dragonDetails
                    dragonViewModelDragonDetails = dragonViewModel.getDragonDetails();
                }
                updateLiveDataRegistration(0, dragonViewModelDragonDetails);


                if (dragonViewModelDragonDetails != null) {
                    // read dragonViewModel.dragonDetails.getValue()
                    dragonViewModelDragonDetailsGetValue = dragonViewModelDragonDetails.getValue();
                }


                if (dragonViewModelDragonDetailsGetValue != null) {
                    // read dragonViewModel.dragonDetails.getValue().heatShield
                    dragonViewModelDragonDetailsHeatShield = dragonViewModelDragonDetailsGetValue.getHeatShield();
                    // read dragonViewModel.dragonDetails.getValue().crewCapacity
                    dragonViewModelDragonDetailsCrewCapacity = dragonViewModelDragonDetailsGetValue.getCrewCapacity();
                }
        }
        // batch finished
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.DragonAdapters.postCrewCapacity(this.crewCapacity, dragonViewModelDragonDetailsCrewCapacity);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.DragonAdapters.postSizeLine(this.dragonSizeLine, dragonViewModelDragonDetailsGetValue);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.DragonAdapters.postStatusLine(this.dragonStatusLine, dragonViewModelDragonDetailsGetValue);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.DragonAdapters.postHeatShield(this.heatShield, dragonViewModelDragonDetailsHeatShield);
        }
        executeBindingsOn(wikiFrame);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): dragonViewModel.dragonDetails
        flag 1 (0x2L): wikiFrame
        flag 2 (0x3L): dragonViewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}