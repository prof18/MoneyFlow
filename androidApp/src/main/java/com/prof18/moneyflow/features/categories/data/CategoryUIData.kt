package com.prof18.moneyflow.features.categories.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.prof18.moneyflow.domain.entities.Category
//import kotlinx.parcelize.Parcelize

//@Parcelize
data class CategoryUIData(
    val id: Long,
    val name: String,
    @DrawableRes val icon: Int
) : Parcelable {

    // Parcelize plugin seems broken :(
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<CategoryUIData> {
            override fun createFromParcel(parcel: Parcel) = CategoryUIData(parcel)
            override fun newArray(size: Int) = arrayOfNulls<CategoryUIData>(size)
        }
    }

    private constructor(parcel: Parcel) : this(
            id = parcel.readLong(),
            name = parcel.readString() ?: "",
            icon = parcel.readInt(),
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(name)
        dest.writeInt(icon)
    }
}

fun Category.toCategoryUIData() = CategoryUIData(
    id = this.id,
    name = this.name,
    icon = this.icon.mapToAndroidIcon()
)