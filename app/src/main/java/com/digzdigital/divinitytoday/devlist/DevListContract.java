package com.digzdigital.divinitytoday.devlist;

import com.digzdigital.divinitytoday.model.Devotional;

import java.util.Collection;

/**
 * Created by Digz on 26/12/2016.
 */

public interface DevListContract {
    interface View{

    }

    interface Presenter{
        Collection<Devotional> getDevotional();
    }
}
