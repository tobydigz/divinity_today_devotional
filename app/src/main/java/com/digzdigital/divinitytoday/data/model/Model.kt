package com.digzdigital.divinitytoday.data.model

import android.os.Parcel
import android.os.Parcelable
import com.digzdigital.divinitytoday.commons.adapter.AdapterConstants
import com.digzdigital.divinitytoday.commons.adapter.ViewType
import com.google.android.gms.ads.NativeExpressAdView

data class DevotionalWrapper(val devotionals: List<Devotional>) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DevotionalWrapper> = object : Parcelable.Creator<DevotionalWrapper> {
            override fun createFromParcel(source: Parcel): DevotionalWrapper = DevotionalWrapper(source)
            override fun newArray(size: Int): Array<DevotionalWrapper?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.createTypedArrayList(Devotional.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(devotionals)
    }
}

data class Devotional(val title: String = "",
                      val date: String = "",
                      val content: String = "",
                      var id: String = "") : ViewType, Parcelable {
    override fun getViewType() = AdapterConstants.DEVOTIONAL

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Devotional> = object : Parcelable.Creator<Devotional> {
            override fun createFromParcel(source: Parcel): Devotional = Devotional(source)
            override fun newArray(size: Int): Array<Devotional?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(date)
        dest.writeString(content)
        dest.writeString(id)
    }
}

data class DevotionalAd(val ad: NativeExpressAdView) : ViewType {
    override fun getViewType() = AdapterConstants.AD
}