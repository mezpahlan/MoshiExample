package uk.co.mezpahlan.moshiexample.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by mpahlan on 11/07/16.
 */
public class StateMaintainer {
    protected final String TAG = getClass().getSimpleName();

    private final String mStateMaintenerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;

    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTag) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintenerTag = stateMaintainerTag;
    }

    public boolean firstTimeIn() {
        try {
            // Recovering the reference
            mStateMaintainerFrag = (StateMngFragment) mFragmentManager.get().findFragmentByTag(mStateMaintenerTag);

            if (mStateMaintainerFrag == null) {
                // Reference is null therefore it is likely the first time in and we have no retained fragment.
                // Create one.
                Log.d(TAG, "Creating a new RetainedFragment " + mStateMaintenerTag);
                mStateMaintainerFrag = new StateMngFragment();
                // And add it to the fragment manager.......... we must do this for all fragments.
                mFragmentManager.get().beginTransaction().add(mStateMaintainerFrag, mStateMaintenerTag).commit();

                // Return true to indicate we are starting for the first time...... duh.
                return true;
            } else {
                // We have a reference therefore we aren't starting for the first time. We should be safe to retrieve some state from the
                // retained fragment.
                Log.d(TAG, "Retained fragment already exists: " + mStateMaintenerTag);

                // Return false to indicate that we are not starting for the first time...... duh
                return false;
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "Error firstTimeIn()");
            // TODO: Check this. I reckon we should return true and reinitialise the retained fragment.
            // Something went wrong...... not safe to assume we can retrieve anything, start again.
            return false;
        }
    }

    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }

    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }

    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);

    }

    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }

    public static class  StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Grants that the frag will be preserved
            setRetainInstance(true);
        }

        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}
