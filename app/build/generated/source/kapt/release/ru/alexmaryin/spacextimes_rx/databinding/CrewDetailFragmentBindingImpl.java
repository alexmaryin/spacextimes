package ru.alexmaryin.spacextimes_rx.databinding;
import ru.alexmaryin.spacextimes_rx.R;
import ru.alexmaryin.spacextimes_rx.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CrewDetailFragmentBindingImpl extends CrewDetailFragmentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(7);
        sIncludes.setIncludes(0, 
            new String[] {"progress_webview_part"},
            new int[] {4},
            new int[] {ru.alexmaryin.spacextimes_rx.R.layout.progress_webview_part});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.detailsView, 5);
        sViewsWithIds.put(R.id.wikiButton, 6);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CrewDetailFragmentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private CrewDetailFragmentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.TextView) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (android.widget.ImageView) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageButton) bindings[6]
            , (ru.alexmaryin.spacextimes_rx.databinding.ProgressWebviewPartBinding) bindings[4]
            );
        this.agencyText.setTag(null);
        this.image.setTag(null);
        this.rootLayout.setTag(null);
        this.statusText.setTag(null);
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
        if (BR.crewViewModel == variableId) {
            setCrewViewModel((ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCrewViewModel(@Nullable ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel CrewViewModel) {
        this.mCrewViewModel = CrewViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.crewViewModel);
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
                return onChangeCrewViewModelCrewDetails((androidx.lifecycle.LiveData<ru.alexmaryin.spacextimes_rx.data.model.Crew>) object, fieldId);
            case 1 :
                return onChangeWikiFrame((ru.alexmaryin.spacextimes_rx.databinding.ProgressWebviewPartBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeCrewViewModelCrewDetails(androidx.lifecycle.LiveData<ru.alexmaryin.spacextimes_rx.data.model.Crew> CrewViewModelCrewDetails, int fieldId) {
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
        androidx.lifecycle.LiveData<ru.alexmaryin.spacextimes_rx.data.model.Crew> crewViewModelCrewDetails = null;
        ru.alexmaryin.spacextimes_rx.data.model.Crew crewViewModelCrewDetailsGetValue = null;
        ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.CrewDetailViewModel crewViewModel = mCrewViewModel;
        ru.alexmaryin.spacextimes_rx.data.model.enums.CrewStatus crewViewModelCrewDetailsStatus = null;
        java.lang.String agencyTextAndroidStringAgencyLabelTextCrewViewModelCrewDetailsAgency = null;
        java.lang.String crewViewModelCrewDetailsImage = null;
        java.lang.String crewViewModelCrewDetailsAgency = null;

        if ((dirtyFlags & 0xdL) != 0) {



                if (crewViewModel != null) {
                    // read crewViewModel.crewDetails
                    crewViewModelCrewDetails = crewViewModel.getCrewDetails();
                }
                updateLiveDataRegistration(0, crewViewModelCrewDetails);


                if (crewViewModelCrewDetails != null) {
                    // read crewViewModel.crewDetails.getValue()
                    crewViewModelCrewDetailsGetValue = crewViewModelCrewDetails.getValue();
                }


                if (crewViewModelCrewDetailsGetValue != null) {
                    // read crewViewModel.crewDetails.getValue().status
                    crewViewModelCrewDetailsStatus = crewViewModelCrewDetailsGetValue.getStatus();
                    // read crewViewModel.crewDetails.getValue().image
                    crewViewModelCrewDetailsImage = crewViewModelCrewDetailsGetValue.getImage();
                    // read crewViewModel.crewDetails.getValue().agency
                    crewViewModelCrewDetailsAgency = crewViewModelCrewDetailsGetValue.getAgency();
                }


                // read (@android:string/agency_label_text) + (crewViewModel.crewDetails.getValue().agency)
                agencyTextAndroidStringAgencyLabelTextCrewViewModelCrewDetailsAgency = (agencyText.getResources().getString(R.string.agency_label_text)) + (crewViewModelCrewDetailsAgency);
        }
        // batch finished
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.agencyText, agencyTextAndroidStringAgencyLabelTextCrewViewModelCrewDetailsAgency);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.CrewAdapters.setVisibility(this.agencyText, crewViewModelCrewDetailsAgency);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.ImageAdapter.loadImage(this.image, crewViewModelCrewDetailsImage, (boolean)false);
            ru.alexmaryin.spacextimes_rx.ui.adapters.bindAdapters.CrewAdapters.postStatus(this.statusText, crewViewModelCrewDetailsStatus);
        }
        executeBindingsOn(wikiFrame);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): crewViewModel.crewDetails
        flag 1 (0x2L): wikiFrame
        flag 2 (0x3L): crewViewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}