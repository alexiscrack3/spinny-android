package com.alexiscrack3.spinny;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowViewGroup;

@Implements(SwipeRefreshLayout.class)
public class ShadowSwipeRefreshLayout extends ShadowViewGroup {
    @RealObject
    SwipeRefreshLayout realObject;
    private SwipeRefreshLayout.OnRefreshListener listener;

    @Implementation
    protected void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        this.listener = listener;
        Shadow.directlyOn(realObject, SwipeRefreshLayout.class).setOnRefreshListener(listener);
    }

    /**
     * @return OnRefreshListener that was previously set.
     */
    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return listener;
    }
}
