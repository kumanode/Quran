/*
 * (c) Faisal Khan. Created on 20/11/2021.
 */

package com.quran.app.interfaceUtils;

import com.quran.app.components.readHistory.ReadHistoryModel;

public interface ReadHistoryCallbacks {
    void onReadHistoryRemoved(ReadHistoryModel model);

    void onReadHistoryAdded(ReadHistoryModel model);
}
